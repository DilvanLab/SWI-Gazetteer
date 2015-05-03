package br.usp.icmc.gazetteer.Kmeans;

import java.util.*;

import br.usp.icmc.gazetteer.TAD.Place;
import br.usp.icmc.gazetteer.cluster.Similarity;

public class Cluster {

	private final List<Place> points;
	private Place centroid;
	private static float AvgMean;
	private static final float similarity=(float) 0.4;
	public Cluster(Place firstPoint) {
		points = new ArrayList<Place>();
		centroid = firstPoint;
	}
	
	public Place getCentroid(){
		return centroid;
	}
	
	private int frequencia(List<String> strings, String verify){
		Similarity sm = new Similarity();
		int count=0;
		for(String s: strings){
			if((sm.jaccardSimilarity(s,verify)>=0.4)){
				count++;
			}
		}
		return count;
	}
	
	public void updateCentroid(){
		
		int max = Integer.MIN_VALUE;
		List<String> locate = new ArrayList<String>();
		for(int i=0;i<points.size();i++){
			locate.add(points.get(i).getNameFilter());
		}
		int index=-1;
		
		for(int i=0;i<points.size();i++){
			int temp = frequencia(locate,points.get(i).getNameFilter());
			if(temp>max )
				max = temp;
				index = i;
		}
		if(max!=Integer.MIN_VALUE && index!=-1 && !points.get(index).equals(centroid))
			centroid = points.get(index);
	}
	
	public List<Place> getPoints() {
		return points;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder("This cluster contains the following points:\n");
		for (Place point : points)
			builder.append(point.getLocation() +"  "+point.getID()+",\n");
		return builder.deleteCharAt(builder.length() - 2).toString();	
	}
}
