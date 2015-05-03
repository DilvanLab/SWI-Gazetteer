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
package br.usp.icmc.gazetteer.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.usp.icmc.gazetteer.CommunicateWithOtherDataSource.Build_Polygons_using_IBGE;
import br.usp.icmc.gazetteer.CommunicateWithOtherDataSource.DBpedia;
import br.usp.icmc.gazetteer.CommunicateWithOtherDataSource.Geonames;
import br.usp.icmc.gazetteer.CountAndStatisticAnalyze.Count_Coordinates;
import br.usp.icmc.gazetteer.ImproveCoordinates.Summarize;
import br.usp.icmc.gazetteer.MappingOntology.Mapping;
import br.usp.icmc.gazetteer.PrepareSampleToCheck.Random_Sample;
import br.usp.icmc.gazetteer.PrepareSampleToCheck.SemanticQuery;
import br.usp.icmc.gazetteer.PutDataInTripleStore.Insert_Triple_Store;
import br.usp.icmc.gazetteer.ReadFiles.Read_Biodiversity_files;
import br.usp.icmc.gazetteer.ReadFiles.Transform_and_Filter;
import br.usp.icmc.gazetteer.TAD.County;
import br.usp.icmc.gazetteer.TAD.Group;
import br.usp.icmc.gazetteer.TAD.Place;
import br.usp.icmc.gazetteer.cluster.Star_algorithm;
import analyze_geographical_coordinates.Out_Polygon;

public class Test {
	
	private static final float similarity = (float) 0.4;
	public static void main(String[] args) throws Exception {

		long inicio = System.currentTimeMillis() / 1000;
		SemanticQuery q = new SemanticQuery();
		Random_Sample sample = new Random_Sample();
		Geonames geonames = new Geonames();
		DBpedia dbpedia = new DBpedia();
		Read_Biodiversity_files rb = new Read_Biodiversity_files();
		Transform_and_Filter tsf = new Transform_and_Filter();
		Out_Polygon out = new Out_Polygon();
		Star_algorithm start = new Star_algorithm();
		Summarize sumy = new Summarize();
		Mapping map = new Mapping();
		Insert_Triple_Store tripleStore = new Insert_Triple_Store();
		
//		ArrayList<Group> group = new ArrayList<Group>();
//		ArrayList<Place> all_place = new ArrayList<Place>();
//		HashMap<Integer,Place> candidate_place = new HashMap<Integer,Place>();
//		ArrayList<County> county = new ArrayList<County>();
//		all_place.addAll(Build_Polygons_using_IBGE.loadNationalParks());
//		all_place.addAll(Build_Polygons_using_IBGE.loadReservas());
//		
//		File clustered = new File("cluster"+similarity+".ser");
//		if(!clustered.exists()){
//			try {
//				rb.read_Expression();
//				rb.start_read();
//				tsf.transform_Repository_to_Place(rb.getRepository());
//
//				for (int i = 0; i < rb.getRepository().size(); i++) {
//					Star_algorithm.fLogger.log(Level.SEVERE,"Statistics: <<<<<<<");
//					Star_algorithm.fLogger.log(Level.SEVERE,"All records: "
//							+ rb.getRepository().get(i).getNumbers()
//							.getAllrecords());
//					Star_algorithm.fLogger.log(Level.SEVERE,"No record:  "
//							+ rb.getRepository().get(i).getNumbers().getNoRecord());
//					Star_algorithm.fLogger.log(Level.SEVERE,"County: "
//							+ rb.getRepository().get(i).getNumbers()
//							.getOnlyCounty());
//					Star_algorithm.fLogger.log(Level.SEVERE,"Locality and County "
//							+ rb.getRepository().get(i).getNumbers()
//							.getOnlyLocalityAndCounty());
//					Star_algorithm.fLogger.log(Level.SEVERE,"Place "
//							+ rb.getRepository().get(i).getNumbers()
//							.getOnlyPlace());
//					Star_algorithm.fLogger.log(Level.SEVERE,"=========================");
//					Star_algorithm.fLogger.log(Level.SEVERE,"Repositorio "
//							+ rb.getRepository().get(i).getName()
//							+ " Fora do poligono "
//							+ out.count_out_Polygon(rb.getRepository().get(i)
//									.getPolygon(), rb.getRepository().get(i)
//									.getPlaces()));
//					out.clean_noise_coordinates(rb.getRepository().get(i)
//							.getPolygon(), rb.getRepository().get(i).getPlaces());
//					int lugar = 0;
//					for (Place pl : rb.getRepository().get(i).getPlaces()) {
//						if (pl.getGeometry() != null)
//							lugar++;
//					}
//					Star_algorithm.fLogger.log(Level.SEVERE,"Quantidade de coordenadas: " + lugar);
//					int[][] years = Count_Coordinates.countDate(rb.getRepository()
//							.get(i).getPlaces(), rb.getRepository().get(i)
//							.getPolygon());
//					Count_Coordinates.build_csv(years, rb.getRepository().get(i)
//							.getName());
//
//				}
//		} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ParserConfigurationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SAXException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Star_algorithm.fLogger.log(Level.SEVERE,"Read all files DONE!!");
//
//			for (int i = 0; i < rb.getRepository().size(); i++) {
//				all_place.addAll(rb.getRepository().get(i).getPlaces());
//			}
//			for (int i = 0; i < rb.getRepository().size(); i++)
//				rb.getRepository().get(i).getPlaces().clear();
//			Star_algorithm.fLogger.log(Level.SEVERE,"Amount places: " + all_place.size());
//			all_place.addAll(geonames.getGeonamesPlaces());
//			
//			if (dbpedia.DBpediaWorks()) {
//				all_place.addAll(dbpedia.pull_query());
//				county.addAll(dbpedia.getMunicipalityFromAmazonas(all_place));
//			}
//			
//			for(int i=0;i<all_place.size();i++){
//				candidate_place.put(i, all_place.get(i));
//			}
//						
//			
//			Star_algorithm.fLogger.log(Level.SEVERE,"Starting clustering ...");
//			group.addAll(start.start_clustering(candidate_place,county));
//			Star_algorithm.fLogger.log(Level.SEVERE,"clustering done!!");
//
//			Star_algorithm.fLogger.log(Level.SEVERE,"Improve Coordinates....");
//			sumy.referenciaGeo(group);
//			Star_algorithm.fLogger.log(Level.SEVERE,"Improve Coordinates DONE!!");
//
//			Star_algorithm.fLogger.log(Level.SEVERE,"Writing file in disk");
//			writeCluster(group,"cluster"+similarity+".ser");
//			Star_algorithm.fLogger.log(Level.SEVERE,"File wrote");
//
//		}else{
//			Star_algorithm.fLogger.log(Level.SEVERE,"Reading file in disk");
//			group.addAll(extracted("cluster"+similarity+".ser"));
//			Star_algorithm.fLogger.log(Level.SEVERE,"File read");
//			Star_algorithm.fLogger.log(Level.SEVERE,null, group.size());
//			Star_algorithm.fLogger.log(Level.SEVERE,null, group.get(0).getPlaces().size());
//			for(int i=0;i<group.get(0).getPlaces().size();i++){
//				System.out.println(group.get(0).getPlaces().get(i).getLocation());
//			}
//		}
//		System.gc();
//		
//
//		int tmp = 0;
//		for (int i = 0; i < rb.getRepository().size(); i++) {
//			String name = rb.getRepository().get(i).getName();
//			for (int k = 0; k < group.size(); k++) {
//				if (group.get(k).getRepository().equals(name)) {
//					rb.getRepository().get(i).getPlaces()
//					.addAll(group.get(k).getPlaces());
//					rb.getRepository().get(i).getPlaces()
//					.add(group.get(k).getCentroid());
//				}
//			}
//		}
//
//		for (int i = 0; i < rb.getRepository().size(); i++) {
//			int relative_date[][] = Count_Coordinates.countDate(rb
//					.getRepository().get(i).getPlaces(), rb.getRepository()
//					.get(i).getPolygon());
//			Count_Coordinates.build_csv(relative_date, rb.getRepository()
//					.get(i).getName()
//					+ "New"+similarity);
//	}
//
//		Star_algorithm.fLogger.log(Level.SEVERE,"Preparing sample...");
//		sample.random_Centroid(group, group.size(),""+similarity);
//		sample.random_inner_Group(group, 30,""+similarity);
//		Star_algorithm.fLogger.log(Level.SEVERE,"Data sample DONE!!!");
//
//		Star_algorithm.fLogger.log(Level.SEVERE,"Mapping data ....");
//		map.build_RDF(group);
//		Star_algorithm.fLogger.log(Level.SEVERE,"Mapping data DONE!!!");
//		
//		Star_algorithm.fLogger.log(Level.SEVERE,"Preparing the sample to check coordinates after SWI");
//		int lugar = 0;
//		for (int i = 0; i < rb.getRepository().size(); i++) {
//			for (Place pl : rb.getRepository().get(i).getPlaces()) {
//				if (pl.getGeometry() != null)
//					lugar++;
//			}
//			sample.corrected_link_before_swi(rb.getRepository().get(i)
//					.getPlaces(), "corrected_link_after_swi"+similarity+".txt");
//		}
		Star_algorithm.fLogger.log(Level.SEVERE,"Sample after SWI prepared");
//		Star_algorithm.fLogger.log(Level.SEVERE,"Amouth of coordinates after processing: " + lugar);
//		Star_algorithm.fLogger.log(Level.SEVERE,"Coordinates improved" + Summarize.improved);

////		
//		Star_algorithm.fLogger.log(Level.SEVERE,"Inserting data in triple store...");
		try {
			tripleStore.insertDataLocalhost();
		//	sample.readAndWritePolygon("jau.txt");
		//	sample.readAndWritePolygon("atalaia.txt");
		//	sample.readAndWritePolygon("manaus.txt");
		//	sample.readAndWritePolygon("neblina.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Star_algorithm.fLogger.log(Level.SEVERE,"Data inserted!!!");
		//Star_algorithm.fLogger.log(Level.SEVERE,"Building sample semantic query");
		// q.testQuery();
		// q.prepareSample("semanticquery.txt");
//		long fim = System.currentTimeMillis() / 1000;
//		
//		 System.out.println("time spend: "+(fim-inicio));
	}

	private static void writeCluster(ArrayList<Group> group,String path){
		try{ 
			OutputStream file = new FileOutputStream(path);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);

			output.writeObject(group);
			output.close();
		}  
		catch(IOException ex){
			Star_algorithm.fLogger.log(Level.SEVERE, "Cannot perform output.", ex);
		}
	}



	private static ArrayList<Group> extracted(String path) {


		try{
			//use buffering
			InputStream file = new FileInputStream(path);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);
			try{
				//deserialize the List
				ArrayList <Group> group = (ArrayList<Group>)input.readObject();
				//display its data
				return group;
			}
			finally{
				input.close();
			}
		}
		catch(ClassNotFoundException ex){
			Star_algorithm.fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
		}
		catch(IOException ex){
			Star_algorithm.fLogger.log(Level.SEVERE, "Cannot perform input.", ex);
		}
		return null;
	}

}
