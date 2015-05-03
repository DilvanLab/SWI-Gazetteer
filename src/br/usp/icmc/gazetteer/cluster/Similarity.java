/**
 *  This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.usp.icmc.gazetteer.cluster;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Similarity {
	
	/* Generates number between 0.00 and 1.00 */
	public double stringSimilarityScore(List<char[]> bigram1, List<char[]> bigram2) {
	    List<char[]> copy = new ArrayList<char[]>(bigram2);
	    int matches = 0;
	    for (int i = bigram1.size(); --i >= 0;) {
	        char[] bigram = bigram1.get(i);
	        for (int j = copy.size(); --j >= 0;) {
	            char[] toMatch = copy.get(j);
	            if (bigram[0] == toMatch[0] && bigram[1] == toMatch[1]) {
	                copy.remove(j);
	                matches += 2;
	                break;
	            }
	        }
	    }
//	    System.out.println( (double) matches / (bigram1.size() + bigram2.size()));
	    return (double) matches / (bigram1.size() + bigram2.size());
	}
	
	public List<char[]> bigram(String input) {
	    ArrayList<char[]> bigram = new ArrayList<char[]>();
	    for (int i = 0; i < input.length() - 1; i++) {
	        char[] chars = new char[2];
	        chars[0] = input.charAt(i);
	        chars[1] = input.charAt(i+1);
	        bigram.add(chars);
	    }
	    return bigram;
	}
	
	   public double jaccardSimilarity(String similar1, String similar2) {
	        HashSet<String> h1 = new HashSet<String>();
	        HashSet<String> h2 = new HashSet<String>();
	        for (String s : similar1.split("\\s+")) {
	            h1.add(s);
	        }
	        for (String s : similar2.split("\\s+")) {
	            h2.add(s);
	        }
	        int sizeh1 = h1.size();
	        //Retains all elements in h3 that are contained in h2 ie intersection
	        h1.retainAll(h2);
	        //h1 now contains the intersection of h1 and h2
	        h2.removeAll(h1);
	        //h2 now contains unique elements
	        //Union 
	        int union = sizeh1 + h2.size();
	        int intersection = h1.size();
	        return (double) intersection / union;
	    }
	
}
