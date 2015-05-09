package br.usp.icmc.gazetteer.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import br.usp.icmc.gazetteer.client.GazetteerService;
import br.usp.icmc.gazetteer.shared.Locality;
import br.usp.icmc.gazetteer.shared.Out_Polygon;
import br.usp.icmc.gazetteer.shared.User;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class GazetteerServiceImpl extends RemoteServiceServlet implements GazetteerService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final boolean isRead = false;
	
	private static HashMap<Integer,Locality> result;
	private static List<Locality> resultGeo;
	private static List<String> classes;
	private static String [] valueCoord;
	private static OntModel model;
	private final int listShow=5;
	private static int count =0;
	private static HashMap<String,OntClass> ontClasses = new HashMap<String,OntClass>();
	private static HashMap<String,OntProperty> ontPropertiess = new HashMap<String,OntProperty>();
	@Override
	public List<Locality> findPlacesWithOutCoord() {
		SPRQLQuery spql = new SPRQLQuery();
		List<Locality> lista = new ArrayList<Locality>();
		
		if(!spql.askService())
			return lista;
		
		if(result == null)
			result = spql.makeSPARQLQuery();

		for(int i=0; i< result.size(); i++){
			lista.add(result.get(count));
			count++;
			if(i>listShow || i==result.size())
				break;			
		}
	//	System.out.println(lista.size());
		return lista;
	}
	
	public void loadClasses(){
		ExtendedIterator<OntProperty> iter = model.listAllOntProperties();
		while (iter.hasNext()) {
			OntProperty thisClass = (OntProperty) iter.next();
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
					//	System.out.println(labl);
					ontPropertiess.put(labl, thisClass);
				}
			}
		}
		Set<String> keys = ontPropertiess.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String input = it.next();
			System.out.println(input+"  "+ontPropertiess.get(input));
		}
		
	}
	public void loadProperties(){
		ExtendedIterator<OntClass> iter = model.listClasses();
		while (iter.hasNext()) {
			OntClass thisClass = (OntClass) iter.next();
			ExtendedIterator label = thisClass.listLabels(null);
			while (label.hasNext()) {
				RDFNode thisLabel = (RDFNode) label.next();
				if(thisLabel.isLiteral()){
					if(!thisLabel.toString().contains("geosparql")){
						String labl = thisLabel.toString().split("http")[0].replaceAll("@en", "").replaceAll("@pt", "").toLowerCase();
						labl = Normalizer.normalize(labl, Normalizer.Form.NFD);  
						labl = labl.replaceAll("[^\\p{ASCII}]", "");
						labl = labl.replaceAll("^^", "");
						if(labl.contains("^^"))
							labl = labl.substring(0, labl.length()-2);
						//	System.out.println(labl);
						ontClasses.put(labl, thisClass);
					}
				}
			}
		}
		Set<String> keys = ontClasses.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String input = it.next();
			System.out.println(input+"  "+ontClasses.get(input));
		}
	}
	
//}else if(search.equals("reservas proximas há 100 km da cidade de Manaus")){
//	else if(search.equals("Campo da Catuquira")){
//	else if(search.equals("Rios no municipio de Atalaia do Norte")){
//	else if(search.equals("proximo ao Rio solimões e rio negro")){
//	else if(search.equals("Locais dentro do Parque Nacional do Pico da Neblina")){
//	else if(search.equals("Locais dentro do Parque Nacional do Jau")){
//	else if(search.equals("Lagos do estado do amazonas")){
//	
	
	private OntClass useMoreGeneric(List<OntClass> cl) {
		
		OntClass first = cl.get(0);
		boolean find = false;
		for(int i=1;i<cl.size();i++){
			//Answer true if the given class is a super-class of this class.
			if(cl.get(i).hasSuperClass(first)){
				find = true;
				//Answer true if the given class is a sub-class of this class.
			}else if(first.hasSuperClass(cl.get(i))){
				first = cl.get(i);
				find = true;
			}
		}
		if(find)
			return first;		
		if(!find && cl.size()>=1)
			return cl.get(0);
		return null;
	}
	private OntClass getMoreSpecifc(List<OntClass> cl) {
		
		OntClass first = cl.get(0);
		boolean find = false;
		for(int i=1;i<cl.size();i++){
			//Answer true if the given class is a sub-class of this class.
			if(cl.get(i).hasSubClass(first)){
				first = cl.get(i);
				find = true;
				//Answer true if the given class is a sub-class of this class.
			}else if(first.hasSubClass(cl.get(i))){
				first = cl.get(i);
				find = true;
			}
		}
		if(find)
			return first;		
		if(!find && cl.size()>=1)
			return cl.get(cl.size()-1);
		return null;
	}
	

	@Override
	public List<Locality> searchLocalities(String search) throws Exception {
		SPRQLQuery spql = new SPRQLQuery();
		List<String> parameters=new ArrayList<String>();
		parameters.add("instance");
		parameters.add("date");
		parameters.add("locality");
		parameters.add("contributors");
		parameters.add("agreement");			
		parameters.add("wkt");
		parameters.add("county");
		parameters.add("triplas");
		List<Locality> result = new ArrayList<Locality>();
		System.out.println(search);
		if(search.equals("reservas proximas a 100 km da cidade de Manaus")){
			
			String queryString = "";
			String path = "files"+File.separator+"query1.txt";
			try{
				String line;
				BufferedReader read =  new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		        while((line = read.readLine())!=null){
		        	queryString+=line+" ";
		        }
			}catch(Exception ex){
			   ex.printStackTrace();
			}
			System.out.println(queryString);
		
			result.addAll(spql.findQueryBox(queryString, parameters));
		}else if(search.equalsIgnoreCase("Campo da Catuquira")){
		
			String queryString = "";
			String path = "files"+File.separator+"query2.txt";
			try{
				String line;
				BufferedReader read =  new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		        while((line = read.readLine())!=null){
		        	queryString+=line+" ";
		        }
			}catch(Exception ex){
			   ex.printStackTrace();
			}
			
			System.out.println(queryString);
			result.addAll(spql.findQueryBox(queryString, parameters));
			System.out.println(result.size());
		}else if(search.equals("Rios no municipio de Atalaia do Norte")){
			String path = "files"+File.separator+"query3.txt";
			String queryString = "";
			try{
				String line;
				BufferedReader read =  new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		        while((line = read.readLine())!=null){
		        	queryString+=line+" ";
		        }
			}catch(Exception ex){
			   ex.printStackTrace();
			}
			result.addAll(spql.insidePolygon("atalaia",parameters,queryString));
		}else if(search.equals("Locais dentro do Parque Nacional do Pico da Neblina")){
			String path = "files"+File.separator+"query3.txt";
			String queryString = "";
			try{
				String line;
				BufferedReader read =  new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		        while((line = read.readLine())!=null){
		        	queryString+=line+" ";
		        }
			}catch(Exception ex){
			   ex.printStackTrace();
			}
			result.addAll(spql.insidePolygon("neblina",parameters,queryString));
		}else if(search.equals("Locais dentro do Parque Nacional do Jau")){
			String path = "files"+File.separator+"otherQueries.txt";
			String queryString = "";
			try{
				String line;
				BufferedReader read =  new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		        while((line = read.readLine())!=null){
		        	queryString+=line+" ";
		        }
			}catch(Exception ex){
			   ex.printStackTrace();
			}
			System.out.println(queryString);
				result.addAll(spql.insidePolygon("jau",parameters,queryString));
		}else if(search.equals("Rios do estado do amazonas")){
			String path = "files"+File.separator+"query3.txt";
			String queryString = "";
			try{
				String line;
				BufferedReader read =  new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		        while((line = read.readLine())!=null){
		        	queryString+=line+" ";
		        }
			}catch(Exception ex){
			   ex.printStackTrace();
			}
			result.addAll(spql.findQueryBox(queryString, parameters));
		}else if (search.equals("Places part-of Manaus")){
			String path = "files"+File.separator+"otherQueries.txt";
			String queryString = "";
			try{
				String line;
				BufferedReader read =  new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		        while((line = read.readLine())!=null){
		        	queryString+=line+" ";
		        }
			}catch(Exception ex){
			   ex.printStackTrace();
			}
			result.addAll(spql.insidePolygon("manaus",parameters,queryString));
		}else{
		
		
		System.out.println("vai iniciar a busca");
			if(model==null){
				model = loadOntology();
				loadClasses();
				loadProperties();
			}
			System.out.println("carregou as classes e propriedades <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			System.out.println(search);
			String temp [] =search.toLowerCase().split(" "); 
			List<OntClass> cl = new ArrayList<OntClass>();
			List<OntProperty> properties = new ArrayList<OntProperty>();
			for(int n=0;n<temp.length-1;n++){
				String tipo = temp[n].toLowerCase().trim()+" "+temp[n+1].toLowerCase().trim();
				System.out.println(tipo+"    "+temp[n]);
				if(ontPropertiess.containsKey(tipo)){
					properties.add(ontPropertiess.get(tipo));
				}else if(ontPropertiess.containsKey(temp[n])){
					properties.add(ontPropertiess.get(temp[n]));
				}			
				if(ontClasses.containsKey(tipo)){
					cl.add(ontClasses.get(tipo));
				}else if(ontClasses.containsKey(temp[n])){
					cl.add(ontClasses.get(temp[n]));
				}
			}
			System.out.println("vai entrar no while "+cl.size());
			for(OntClass s:cl){
				System.out.println(s.toString());
			}
			while(cl.size()>1){
				OntClass moreGeneric = useMoreGeneric(cl);
				System.out.println("MORE GENERIC "+moreGeneric.toString());
				OntClass moreSpecific = getMoreSpecifc(cl);
				System.out.println("MORE SPECIFIC "+moreSpecific.toString());
				result.addAll(spql.findIntersection(moreGeneric,moreSpecific,search.toLowerCase()));
				result.addAll(spql.findProximity(moreGeneric,moreSpecific,properties,search.toLowerCase()));
				cl.remove(moreSpecific);
			}
			if(cl.size()==1)
				result.addAll(spql.findSinglePlace(cl.get(0),search.toLowerCase()));
			else
				result.addAll(spql.findSinglePlace(ontClasses.get("locais"),search.toLowerCase()));
		}
		System.out.println("terminou a busca");
		return result;
	}

	

	public OntModel loadOntology(){
		//String ontologyIRI = "https://raw.githubusercontent.com/silviodc/Gazetteer/master/Collaborative_Gazetteer/files/Gazetteer_v_1_1.owl";
		
		String ontologyIRI = "files"+File.separator+"Gazetteer_v_1_1.owl";
		OntModel m = ModelFactory.createOntologyModel();
		InputStream in = FileManager.get().open(ontologyIRI);
		if (in == null) return null;
		
		return (OntModel) m.read(in,"");
	}

	@Override
	public Integer insertLocality(Locality locality) {
		SPRQLQuery spql = new SPRQLQuery();
		if(!spql.askService())
			return 0;
		
		int individual = spql.getIndex();
		String query = "DELETE WHERE{ <"+locality.getIdTriple()+"> ?p ?o .} ; ";
		query += " INSERT DATA { <"+locality.getIdTriple()+"> <http://www.semanticweb.org/ontologies/Gazetter#contributors> \""+locality.getContributors()+"\"^^<http://www.w3.org/2001/XMLSchema#long> . ";
		query += "<"+locality.getIdTriple()+"> <http://www.semanticweb.org/ontologies/Gazetter#agreement> \""+locality.getAgreeCoordinate()+"\"^^<http://www.w3.org/2001/XMLSchema#long> . ";
		query +="<"+locality.getIdTriple()+"> <http://www.semanticweb.org/ontologies/Gazetter#infotype> \"user\"^^<http://www.w3.org/2001/XMLSchema#string> . ";
		query += "<"+locality.getIdTriple()+"> <http://www.semanticweb.org/ontologies/Gazetter#locality> \""+locality.getLocality()+"\"^^<http://www.w3.org/2001/XMLSchema#string> . ";
		query += "<"+locality.getIdTriple()+"> <http://www.semanticweb.org/ontologies/Gazetter#county> \""+locality.getCounty()+"\"^^<http://www.w3.org/2001/XMLSchema#string> . ";
		query += "<"+locality.getIdTriple()+"> <http://www.semanticweb.org/ontologies/Gazetter#ntriples> \""+locality.getNtriplas()+"\"^^<http://www.w3.org/2001/XMLSchema#long> . ";
		query += "<"+locality.getIdTriple()+"> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <"+locality.getType()+"> . ";
        
		
		if(locality.getIdGeo().equals("") || locality.getIdGeo()==null){
				query+="<"+locality.getIdTriple()+"> <http://www.opengis.net/ont/geosparql#hasGeometry> <http://www.semanticweb.org/ontologies/Gazetter#"+(individual)+"> . ";
				query += " <http://www.semanticweb.org/ontologies/Gazetter#"+(individual)+"> <http://www.opengis.net/ont/geosparql#asWKT> \""+locality.getGeometry()+";http://www.opengis.net/def/crs/EPSG/0/4326\"^^<http://strdf.di.uoa.gr/ontology#WKT> . ";
				query += "<http://www.semanticweb.org/ontologies/Gazetter#"+(individual)+"> <http://www.semanticweb.org/ontologies/Gazetter#date> \""+locality.getDate()+"\"^^<http://www.w3.org/2001/XMLSchema#long> . ";
				query +="<http://www.semanticweb.org/ontologies/Gazetter#"+(individual)+">  <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.opengis.net/ont/sf#Geometry>  . }";
		}else{
			query+=" }";
			System.out.println(query);
			spql.insertDataEndpoint(query);
			query = "INSERT INTO <http://www.semanticweb.org/ontologies/GazetterTemp#> {" +
				""+locality.getIdTriple()+" <http://www.opengis.net/ont/geosparql#hasGeometry> <http://www.semanticweb.org/ontologies/GazetterTemp#"+(individual)+"> . "+
				" <http://www.semanticweb.org/ontologies/GazetterTemp#"+(individual)+"> <http://www.opengis.net/ont/geosparql#asWKT> \""+locality.getGeometry()+";http://www.opengis.net/def/crs/EPSG/0/4326\"^^<http://strdf.di.uoa.gr/ontology#WKT> . "+
				"<http://www.semanticweb.org/ontologies/GazetterTemp#"+(individual)+">  <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.opengis.net/ont/sf#Geometry>  . }";
			System.out.println(query);
		}
	
		System.out.println(query);
		if(spql.insertDataEndpoint(query))
			return 1;
		return 0;
	}

	@Override
	public void agreeLinkedData(Locality locality) {
		SPRQLQuery spql = new SPRQLQuery();
		String query = "DELETE where{ <"+locality.getIdTriple()+"> <http://www.semanticweb.org/ontologies/Gazetter#agreement> ?o .}; "
				+ "INSERT DATA { <"+locality.getIdTriple()+"> <http://www.semanticweb.org/ontologies/Gazetter#agreement> \""+locality.getAgreeCoordinate()+"\"^^<http://www.w3.org/2001/XMLSchema#long> . }";
		System.out.println(query);
		spql.insertDataEndpoint(query);
		query = "DELETE where{ <"+locality.getIdTriple()+"> <http://www.semanticweb.org/ontologies/Gazetter#contributors> ?o .}; "
				+ "INSERT DATA { <"+locality.getIdTriple()+"> <http://www.semanticweb.org/ontologies/Gazetter#contributors> \""+locality.getContributors()+"\"^^<http://www.w3.org/2001/XMLSchema#long> . }";	
		System.out.println(query);
		spql.insertDataEndpoint(query);
	}

	@Override
	public void updateUser(String[] user) {
		String update = "UPDATE user SET nome='"+user[1]+"', usuario='"+user[2]+"',senha='"+user[3]+"',type='"+user[4]+"' WHERE idUser="+user[0];
		
	}

	@Override
	public void insertUser(String[] user) {
		String insert = "INSERT INTO table_name (nome, usuario, senha,type) VALUES ('"+user[0]+"', '"+user[1]+"', '"+user[2]+"','"+user[3]+"')";
		
	}

	@Override
	public User getUser(String login, String senha) throws Exception {
		MySQLConnection mysql = new MySQLConnection();
		System.out.println("vai acessar o banco");
		return mysql.authenticateUser(login, senha);
	}

	
	@Override
	public List<Float[]> getPolygons(){
		 final List<Float[]> points = new ArrayList<Float[]>();
		  Out_Polygon out = new Out_Polygon();
		  SPRQLQuery spql = new SPRQLQuery();
		  
		  if(!spql.askService())
			  return points;
		  
			if(resultGeo == null){
			
				resultGeo=spql.getGeometriesInsideTripleStore();
			}
			
			System.out.println("Carregou dados");
		  try {
			out.setPoly(out.buildPolygon(new File("files"+File.separator+"Amazonas_polygon.txt").getAbsolutePath()));
		} catch (IOException e) {
			System.out.println("Erro ao carregar o poligono");
			e.printStackTrace();
		}

	  //  	File ff =new File("ponts.kml");
	  //  	try {
		//		FileWriter fw = new FileWriter(ff);
				
		  System.out.println("Vai construir os pontos");
		     //create a semi-random grid of features to be clustered
	         Iterator<Locality> iterator = resultGeo.iterator();
	        while(iterator.hasNext()){
	        	Locality loc =  iterator.next();
	        //	System.out.println(loc.getGeometry());
	        	if(loc.getGeometry().contains("POINT")){
		           	String value = loc.getGeometry().replaceAll(";http://www.opengis.net/def/crs/EPSG/0/4326", "");
		   //     	fw.write(value+"\n");
		        	value = value.substring(7, value.length()-2);
		        	float x = out.transformFloat(value.split(" ")[0]);
		        	float y = out.transformFloat(value.split(" ")[1]);
		        	
		        	if(out.insidePolygon(out.getPoly(), x, y))
		        		points.add(new Float []{ y,x});
	        	}
	        }
		
				//	for(int i=0;i<points.size();i++){
			//		fw.write("<Point> <coordinates>"+points.get(i)[0]+","+points.get(i)[1]+",0</coordinates> </Point>\n");}
	        //	fw.close();
		//	}catch(Exception ex){}
    System.out.println("Voltando os pontos");
		return points;
	}

	@Override
	public List<String> getOntTypes() {
		  SPRQLQuery spql = new SPRQLQuery();
		if(classes==null){
			classes = spql.getTypes();
		}
		return classes;
	}

	@Override
	public String[] infoServer() {
		if(!isRead){
			 SPRQLQuery spql = new SPRQLQuery();
			 if(spql.askService() && valueCoord==null){
				 String [] valueCoord = spql.getInfo();
				 
				 return valueCoord;
			 }
		}
		return valueCoord;
	}

}
