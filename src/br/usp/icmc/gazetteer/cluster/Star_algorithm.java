/*    This file is part of SWI Gazetteer.

    SWI Gazetteer is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SWI Gazetteer is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with SWI Gazetteer.  If not, see <http://www.gnu.org/licenses/>.
    */
package br.usp.icmc.gazetteer.cluster;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.usp.icmc.gazetteer.TAD.County;
import br.usp.icmc.gazetteer.TAD.Group;
import br.usp.icmc.gazetteer.TAD.Place;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class Star_algorithm {
	
	public static final Logger fLogger =  Logger.getLogger(Star_algorithm.class.getPackage().getName());
	private OntModel model;
	private final double similarity = 0.4;
	private List <OntClass> usedClass = new ArrayList<OntClass>();
	private HashMap<Integer,Place> places;
	private HashMap<String,OntClass> classes = new HashMap<String,OntClass>();
	
	public ArrayList<Group> start_clustering(HashMap<Integer,Place> places,ArrayList<County>municipality) throws InstantiationException,
	IllegalAccessException, CloneNotSupportedException, IOException {

		this.places = places;
		findComposite(this.places);
		
		HashMap<Integer,Place> candidate_place = new HashMap<Integer,Place>();
		ArrayList<Group> group = new ArrayList<Group>();
		fLogger.log(Level.SEVERE,"Used class = "+usedClass.size());
		for (OntClass e :usedClass) {
			candidate_place.clear();		
			for(int k=0;k<municipality.size();k++){
				for (int i=0; i<places.size();i++) {
					if (places.get(i)!=null &&  verific_county(places.get(i).getCounty(),municipality.get(k)) && contaisSomeClass(this.places.get(i).getTypes(),e.toString()) && !this.places.get(i).isUsed()) {
						Place candidate = this.places.get(i);
						this.places.remove(i);
						candidate_place.put(i,candidate);
					}
				}
			//	fLogger.log(Level.SEVERE,"Candidate_place: "+candidate_place.size());
				agroup(candidate_place, group);
				candidate_place.clear();
				 verifyCreatedGroups(group.get(group.size()-1),group);
			}
			fLogger.log(Level.SEVERE,e+"    "+group.size()+"  "+this.places.size());
		}
		fLogger.log(Level.SEVERE,"agruping others "+places.size());
		for(int i=0;i<places.size();i++){
			if(places.get(i)!=null)
			places.get(i).setInteration(Integer.MAX_VALUE);
		}
		
		while(places.size()>0){
			agroup(places, group);
		}
		for(int i=0;i<group.size();i++){
			 verifyCreatedGroups(group.get(i),group);
		}
		return group;
	}

	private boolean verific_county(County county, County county1) {
		Similarity jaccard = new Similarity();
		if (county == null || county1 == null || county1.getNome().equals("")	|| county1.getNome().equals("não informado") || county.getNome().equals(" ")
				|| county1.getNome().equals(" ") || county.getNome().equals("não informado")) {
			return true;
		}else{
			double value = jaccard.stringSimilarityScore(jaccard.bigram(county1.getNome()),jaccard.bigram(county.getNome()));	
			if(value>=0.7)
				return true;
		}
		return false;
	}

	public boolean contaisSomeClass(List<String> types, String type){
		Set<String> set = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		set.addAll(types);
		types = new ArrayList<String>(set);
		if(types.contains(type))
			return true;
		return false;
	}

	private void agroup(HashMap<Integer,Place> candidate_place, ArrayList<Group> group){
		while (candidate_place.size() > 0) {			
			int index =  lookForGoldStandart(candidate_place); // get some centroid to start matching
			Place centroid = candidate_place.get(index);
			candidate_place.remove(index);// remove centroid from candidate places

			Group group_created = clustering_using_start(candidate_place,centroid);
			group_created.setCentroid(centroid);
			group_created.setRepository(centroid.getRepository());
			//Verify others Groups
			
			boolean newGroup = verifyCreatedGroups(group_created,group);
			if(newGroup){
				group.add(group_created);
			}
			
		//	fLogger.log(Level.SEVERE,"DONE VERIFY GROUPS ");
		}
	}

	private int  lookForGoldStandart(HashMap<Integer,Place> candidate_place) {
		Set<Integer> key = candidate_place.keySet();
		Random rand = new Random();
		int r = rand.nextInt(key.size());
		int	index=0;
		Iterator<Integer> it2 = key.iterator();
		for(int j=0;j<r;j++){
			index = (Integer) it2.next();
			if(candidate_place.get(index).isGoldStandart())
				break;
		}
		if(index==0){
			it2 = key.iterator();
			return it2.next();
		}
		return index;
	}

	private boolean forward(List<String> tempArrayList, List<String>group){
		Similarity bigram = new Similarity();

		Set<String> set = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		set.addAll(tempArrayList);
		tempArrayList = new ArrayList<String>(set);

		Set<String> set2 = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		set2.addAll(group);
		group = new ArrayList<String>(set);


		for(String t: tempArrayList){
			for(String p:group ){
				if(verific_county(bigram,new County(t), new County(p))){
					return true;
				}		
			}
		}
		return false;
	}

	private void classesStartAlgorithm(){
		/*
		 * get the class from ontology
		 */
		ExtendedIterator<OntClass> iter = model.listClasses();
		while (iter.hasNext()) {
			OntClass thisClass = (OntClass) iter.next();
			classes.put(thisClass.toString(),thisClass);

		}
	}

	private boolean verifyInternal(Group group_created,Group group){
		List<Place> tempArrayList = new ArrayList<Place>();
		tempArrayList.addAll( group_created.getPlaces());
		tempArrayList.add(group_created.getCentroid());
		tempArrayList.remove(null);
		
		Similarity bigram = new Similarity(); // try resolve matching using bigram similarity metric
		List<String> listGroup = new ArrayList<String>();
		for(Place p: group.getPlaces())
			listGroup.add(p.getNameFilter());

		Set<String> set = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		set.addAll(listGroup);
		listGroup = new ArrayList<String>(set);

		List<String> listCreated = new ArrayList<String>();
		for(Place p: tempArrayList){
			if(p!=null)						
				listCreated.add(p.getNameFilter());
		}

		Set<String> set2 = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		set2.addAll(listCreated);
		listCreated = new ArrayList<String>(set2);

		Iterator <String> it = listGroup.iterator();
		while(it.hasNext()){
			String nome = (String) it.next();
			Iterator <String> it2 = listCreated.iterator();
			while(it2.hasNext()){
				String nome2 = (String) it2.next();	
				if(bigram.stringSimilarityScore(bigram.bigram(nome),bigram.bigram(nome2))>similarity){
					if(!group_created.getCentroid().isGoldStandart() || !group.getCentroid().isGoldStandart()){
						group.getPlaces().add(group_created.getCentroid());
						return true;
					}					
				}
			}
		}			
	return false;
	}

	
	private boolean verifyCreatedGroups(Group group_created,ArrayList<Group> group){
		List<Group> candidates = new ArrayList<Group>();
		for(int i=0;i<group.size();i++){
			boolean forward = forward(group_created.getCounty(),group.get(i).getCounty());
				if(forward){
					candidates.add(group.get(i));
				}
			}
		
		//fLogger.log(Level.SEVERE,"Candidates size verify :::: "+candidates.size());
		if(candidates.size()>0){
				for(int i=0;i<candidates.size();i++){
					if(!candidates.get(i).equals(group_created) && verifyInternal(group_created,candidates.get(i)))
						return false;
				}
		}
		
		return true;
	}



	public OntModel loadOntology() {
		// String ontologyIRI =
		// "https://raw.githubusercontent.com/silviodc/Gazetteer/master/Collaborative_Gazetteer/files/Gazetteer_v_1_1.owl";

		String path = new File("files" + File.separator + "Gazetteer_v_1_1.owl")
		.getAbsolutePath();
		OntModel m = ModelFactory.createOntologyModel();
		InputStream in = FileManager.get().open(path);
		if (in == null)
			return null;

		return (OntModel) m.read(in, "");
	}

	
	
	public Group clustering_using_start(HashMap<Integer,Place> candidate_place,
			Place centroid){

		// CLUSTERING!!! using star
		centroid.setUsed(true);
		Group local_group = new Group();
		local_group.getPlaces().add(centroid);

		Similarity jaccard = new Similarity(); // try resolve matching using jaccard similarity metric

		for (int i = 0; i < candidate_place.size(); i++) {
			if(candidate_place.get(i)!=null  && !candidate_place.get(i).isGoldStandart()){

				double value = jaccard.jaccardSimilarity(centroid.getNameFilter(),candidate_place.get(i).getNameFilter());
				boolean county = verific_county(jaccard,candidate_place.get(i).getCounty(),centroid.getCounty());			
				if (value >= similarity && county) {
					Place candidate = candidate_place.get(i);
					local_group.getPlaces().add(candidate); //add similar place in a new group
					candidate.setUsed(true);
					candidate_place.remove(i); //remove place similar  from list
					System.gc();
					if(candidate.getCounty()!=null)
						local_group.getCounty().add(candidate.getCounty().getNome());
				}else{
					if(verifyGroupInternal(local_group.getPlaces(),candidate_place.get(i))){
						local_group.getPlaces().add(candidate_place.get(i));
						if(candidate_place.get(i).getCounty()!=null)
							local_group.getCounty().add(candidate_place.get(i).getCounty().getNome());
						candidate_place.remove(i);
					}
				}
			}
		}

		return local_group;
	}


	private boolean verifyGroupInternal(ArrayList<Place> places2, Place place) {
		Similarity jaccard = new Similarity(); 
		for(int i=0;i<places2.size();i++){
			if(jaccard.jaccardSimilarity(place.getNameFilter(),place.getNameFilter())>=similarity)
				return true;
		}
		return false;
	}

	private boolean verific_county(Similarity jaccard, County county, County county1) {

		if (county == null || county1 == null || county1.getNome().equals("")	|| county1.getNome().equals("não informado") || county.getNome().equals(" ")
				|| county1.getNome().equals(" ") || county.getNome().equals("não informado")) {
			return true;
		}else{
			double value = jaccard.stringSimilarityScore(jaccard.bigram(county1.getNome()),jaccard.bigram(county.getNome()));	
			if(value>=0.7)
				return true;
		}
		return false;
	}

	public ArrayList<OntClass> loadOntologyClass() {
		ArrayList<OntClass> classes = new ArrayList<OntClass>();
		ExtendedIterator<OntClass> iter = model.listClasses();
		while(iter.hasNext()){
			OntClass cl = iter.next();
			classes.add(cl);
		}
		return classes;
	}

	private void findComposite(HashMap<Integer,Place> places) throws CloneNotSupportedException {
		if(model==null)
			model = loadOntology();
		ExtendedIterator<OntClass> iter = model.listClasses();
		while (iter.hasNext()) {
			OntClass thisClass = (OntClass) iter.next();
			ExtendedIterator label = thisClass.listLabels(null);
			while (label.hasNext()) {
				RDFNode thisLabel = (RDFNode) label.next();
				if(thisLabel.isLiteral()){
					String labl = thisLabel.toString().split("http")[0].replaceAll("@en", "").replaceAll("@pt", "").toLowerCase();
					labl = Normalizer.normalize(labl, Normalizer.Form.NFD);  
					labl = labl.replaceAll("[^\\p{ASCII}]", "");
					labl = labl.replaceAll("^^", "");
					if(labl.contains("^^"))
						labl = labl.substring(0, labl.length()-2);
					//	fLogger.log(Level.SEVERE,(labl);
					classes.put(labl, thisClass);
				}
			}
		}
		List<OntClass> cl = new ArrayList<OntClass>();
		for(int i=0;i<places.size();i++){			
			String temp [] =places.get(i).getLocation().toLowerCase().split(" "); 
			for(int n=0;n<temp.length-1;n++){

				String tipo = temp[n].toLowerCase().trim()+" "+temp[n+1].toLowerCase().trim();
				if(classes.get(temp[n].toLowerCase().trim())!=null && !cl.contains(classes.get(temp[n].toLowerCase().trim())) ){
					cl.add(classes.get(temp[n].toLowerCase().trim()));					
				}else if(classes.get(tipo)!=null && !cl.contains(classes.get(tipo))){
					cl.add(classes.get(tipo));					
				}										
			}	
			usedClass.addAll(verifyContainClass(usedClass,cl));
			places.get(i).setTypes(getManyTypes(cl));			
			cl.clear();

		}
		usedClass.addAll(verifyContainClass(usedClass,cl));
		cl.clear();
		classes.clear();
		classesStartAlgorithm();

	}
	public List<String> getManyTypes(List<OntClass> test){
		List<String> types = new ArrayList<String>();
		for(OntClass t : test){
			types.add(t.toString());
		}
		if(types.size()==0)
			types.add(classes.get("feature").toString());
		return types;
	}

	public List<OntClass> verifyContainClass(List<OntClass> used,List<OntClass> test){
		List<OntClass> temp = new ArrayList<OntClass>();

		if(used.size()==0)
			test.add(classes.get("feature"));
		for(OntClass t: test){
			if(!used.contains(t)){
				temp.add(t);
			}
		}
		return temp;
	}

}
