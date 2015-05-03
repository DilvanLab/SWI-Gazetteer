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
package br.usp.icmc.gazetteer.SemanticSearchTest;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author Silvio
 */
public class CalculoMedias {
    
    private int [][] prLucene = new int [2][2];
    private int relevantesFire ;
    private BigDecimal mapTotal;
    private BigDecimal SmallMapQ;
    private BigDecimal LargeMapQ;
    private BigDecimal SmallMapF;
    private BigDecimal LargeMapF;
    private BigDecimal precision;
    private BigDecimal recall;
    private BigDecimal F1;
    private int docsRecall;
    private int TotalRelevanteRetornado;
    private int totalRelevanteFire;
    private static ArrayList<BigDecimal> map = new ArrayList<BigDecimal>();
    private static ArrayList<BigDecimal> mapQ = new ArrayList<BigDecimal>();
    private static ArrayList<BigDecimal> mapQSmall = new ArrayList<BigDecimal>();
    private static ArrayList<BigDecimal> mapQlarge = new ArrayList<BigDecimal>();
    private static ArrayList<BigDecimal> mapFSmall = new ArrayList<BigDecimal>();
    private static ArrayList<BigDecimal> mapFlarge = new ArrayList<BigDecimal>();
    private static ArrayList<BigDecimal> fire = new ArrayList<BigDecimal>();
    private static ArrayList<Query> querys = new ArrayList<Query>();
    private BigDecimal mapTotalFire;

    public BigDecimal getSmallMapQ() {
        return SmallMapQ;
    }

    public void setSmallMapQ(BigDecimal SmallMapQ) {
        this.SmallMapQ = SmallMapQ;
    }

    public BigDecimal getLargeMapQ() {
        return LargeMapQ;
    }

    public void setLargeMapQ(BigDecimal LargeMapQ) {
        this.LargeMapQ = LargeMapQ;
    }

    public BigDecimal getSmallMapF() {
        return SmallMapF;
    }

    public void setSmallMapF(BigDecimal SmallMapF) {
        this.SmallMapF = SmallMapF;
    }

    public BigDecimal getLargeMapF() {
        return LargeMapF;
    }

    public void setLargeMapF(BigDecimal LargeMapF) {
        this.LargeMapF = LargeMapF;
    }

    public static ArrayList<BigDecimal> getMapQSmall() {
        return mapQSmall;
    }

    public static void setMapQSmall(ArrayList<BigDecimal> mapQSmall) {
        CalculoMedias.mapQSmall = mapQSmall;
    }

    public static ArrayList<BigDecimal> getMapQlarge() {
        return mapQlarge;
    }

    public static void setMapQlarge(ArrayList<BigDecimal> mapQlarge) {
        CalculoMedias.mapQlarge = mapQlarge;
    }

    public static ArrayList<BigDecimal> getMapFSmall() {
        return mapFSmall;
    }

    public static void setMapFSmall(ArrayList<BigDecimal> mapFSmall) {
        CalculoMedias.mapFSmall = mapFSmall;
    }

    public static ArrayList<BigDecimal> getMapFlarge() {
        return mapFlarge;
    }

    public static void setMapFlarge(ArrayList<BigDecimal> mapFlarge) {
        CalculoMedias.mapFlarge = mapFlarge;
    }
    
    public int[][] getPrLucene() {
        return prLucene;
    }

    public ArrayList<BigDecimal> getFire() {
        return fire;
    }

    public void setFire(ArrayList<BigDecimal> fire) {
        this.fire = fire;
    }

    public BigDecimal getMapTotalFire() {
        return mapTotalFire;
    }

    public void setMapTotalFire(BigDecimal mapTotalFire) {
        this.mapTotalFire = mapTotalFire;
    }

    public int getTotalRelevanteRetornado() {
        return TotalRelevanteRetornado;
    }

    public void setTotalRelevanteRetornado(int TotalRelevanteRetornado) {
        this.TotalRelevanteRetornado = TotalRelevanteRetornado;
    }

    public int getTotalRelevanteFire() {
        return totalRelevanteFire;
    }

    public void setTotalRelevanteFire(int totalRelevanteFire) {
        this.totalRelevanteFire = totalRelevanteFire;
    }

    public void setPrLucene(int[][] prLucene) {
        this.prLucene = prLucene;
    }

    public int getRelevantesFire() {
        return relevantesFire;
    }

    public void setRelevantesFire(int relevantesFire) {
        this.relevantesFire = relevantesFire;
    }

    public BigDecimal getMapTotal() {
        return mapTotal;
    }

    public void setMapTotal(BigDecimal mapTotal) {
        this.mapTotal = mapTotal;
    }

    public BigDecimal getPrecisao() {
        return precision;
    }

    public void setPrecisao(BigDecimal precisao) {
        this.precision = precisao;
    }

    public BigDecimal getRecall() {
        return recall;
    }

    public void setRecall(BigDecimal recall) {
        this.recall = recall;
    }

    public BigDecimal getF1() {
        return F1;
    }

    public void setF1(BigDecimal F1) {
        this.F1 = F1;
    }

    public int getDocsRecall() {
        return docsRecall;
    }

    public void setDocsRecall(int docsRecall) {
        this.docsRecall = docsRecall;
    }

    public ArrayList<BigDecimal> getMap() {
        return map;
    }

    public void setMap(ArrayList<BigDecimal> map) {
        this.map = map;
    }

    public ArrayList<BigDecimal> getMapQ() {
        return mapQ;
    }

    public void setMapQ(ArrayList<BigDecimal> mapQ) {
        this.mapQ = mapQ;
    }

    public BigDecimal getPrecision() {
        return precision;
    }

    public void setPrecision(BigDecimal precision) {
        this.precision = precision;
    }

    public static ArrayList<Query> getQuerys() {
        return querys;
    }

    public static void setQuerys(ArrayList<Query> querys) {
        CalculoMedias.querys = querys;
    }
    
        
    
    public void verDocsRelevantes() throws InterruptedException{
        for(Documento d:LuceneSearcher.getDoc()){
             for(Documento arq : LeituraArquivos.getDocumentos()){
                 if(d.getFilename().equals(arq.getFilename()) && d.getNumQ()==arq.getNumQ()){
                     d.setRelevante(arq.getRelevante());                     
                     break;
                     }   
            }
        }
    
        //Faz a Matriz de Precisao Recall do Fire
        for(Documento d:LeituraArquivos.getDocumentos()){
                if(d.getRelevante()==1)
                  relevantesFire++;
         }
            
        //Faz a Matriz Precisao Recall do Lucene
         for(Documento d:LuceneSearcher.getDoc()){
                if(d.getRelevante()==1)
                   prLucene[0][0]++; //true positive
                else
                   prLucene[0][1]++; // false positive
         }
         precision = new BigDecimal((double) prLucene[0][0]/(prLucene[0][0]+prLucene[0][1]));         
         recall =  new BigDecimal((double) prLucene[0][0]/relevantesFire);
         
         TotalRelevanteRetornado = prLucene[0][0];
         totalRelevanteFire=relevantesFire;
         docsRecall =LuceneSearcher.getDoc().size();
         
         double tempF1 =(double) 2*((precision.doubleValue()*recall.doubleValue()) / (precision.doubleValue()+recall.doubleValue()));
         F1 = new BigDecimal(tempF1);
         
         Documento comp =  LuceneSearcher.getDoc().get(0);
         double tempMap=0;
         int contR=0,contD=0;
         boolean ranking=true;
         int relevant=0,i=1;
         BigDecimal calc;
         BigDecimal calc1;
         for(Documento d:LuceneSearcher.getDoc()){
             if(d.getNumQ()!=comp.getNumQ()){ 
                 relevant=0;i=1;
                  for(Documento doc:LuceneSearcher.getDoc()){
                      if(doc.getNumQ()==comp.getNumQ()){                      
                        if(doc.getRelevante()==1)
                                relevant++; 
                        if(contR>0){
                          //  System.out.println((double)(relevant/contR)+","+(double)(contR/i));
                            double temp = (double)relevant/contR;
                             calc = new BigDecimal(temp);
                           /*  if(doc.getNumQ()==126){
                                System.out.println("R: "+relevant+"/"+contR+"="+calc.doubleValue());
                             }*/  
                             
                        }else{
                            calc = new BigDecimal(0.0);
                        }
                         doc.setRecall(calc);
                        double temp1 = (double)relevant/i;
                         calc1 = new BigDecimal(temp1);
                       doc.setPrecisao(calc1);
                       /*/if(doc.getNumQ()==126){
                                System.out.println("P: "+relevant+"/"+i+"="+calc1.doubleValue());
                        }*/
                       i++;
                      }
                  }
                 
                 comp = d;
                 tempMap=0;
                  
                 for(BigDecimal k : map){                      
                    tempMap= (double) tempMap+k.doubleValue();
                 }              
                 if(map.size()>0){
                    mapQ.add(new BigDecimal((double)tempMap/map.size()));
                 }else{
                     mapQ.add(new BigDecimal((double)0.0));
                 }
                  BigDecimal temp = new BigDecimal((double)tempMap);
                  double temp1= (double)(contR+1)/(contR+1);
                  double temp2= (double)(contR+2)/(contR+2);
                  double sum = (double)(temp.doubleValue()+temp1+temp2)/(contR+2);
                  temp = new BigDecimal(sum);
                  System.out.println("NUMQ "+comp.getNumQ()+", "+temp);
                  mapQlarge.add(new BigDecimal(temp.doubleValue()));
                  
                  temp = new BigDecimal((double)tempMap);
                  temp1= (double)(contR+1)/(contD+1);
                  temp2= (double)(contR+2)/(contD+2);
                  sum = (double)(temp.doubleValue()+temp1+temp2)/(contR+2);
                  temp = new BigDecimal(sum);
                  System.out.println("NUMQ "+comp.getNumQ()+", "+temp);
                  mapQSmall.add(new BigDecimal(temp.doubleValue()));
                 contR=0;
                 contD=0;
                 ranking=true;
                 map.clear();
             }
             if(d.getRelevante()==1 && ranking){
                 contR++;
                 map.add(new BigDecimal((double)1.0));
             }else if (d.getRelevante()==1){
                 contR++;
                 tempMap = (double) contR/contD;
                 map.add(new BigDecimal(tempMap));
             }else{
                 ranking=false;
             }
            
             contD++;    
         }
         tempMap=0;
         for(BigDecimal k : map){
            tempMap= (double) tempMap+k.doubleValue();
         }                 
         if(map.size()>0){
             
                  for(Documento doc:LuceneSearcher.getDoc()){
                      if(doc.getNumQ()==comp.getNumQ()){                      
                        if(doc.getRelevante()==1)
                                relevant++; 
                        if(contR>0){
                          //  System.out.println((double)(relevant/contR)+","+(double)(contR/i));
                            double temp = (double)relevant/contR;
                             calc = new BigDecimal(temp);
                           /*  if(doc.getNumQ()==126){
                                System.out.println("R: "+relevant+"/"+contR+"="+calc.doubleValue());
                             }*/  
                             
                        }else{
                            calc = new BigDecimal(0.0);
                        }
                         doc.setRecall(calc);
                        double temp1 = (double)relevant/i;
                         calc1 = new BigDecimal(temp1);
                       doc.setPrecisao(calc1);
                       /*/if(doc.getNumQ()==126){
                                System.out.println("P: "+relevant+"/"+i+"="+calc1.doubleValue());
                        }*/
                       i++;
                      }
                  }
            mapQ.add(new BigDecimal((double )tempMap/map.size()));
            }else{
                     mapQ.add(new BigDecimal((double )0.0));
        }
         double temp = (double)tempMap;
         double temp1= (double)(contR+1)/(contR+1);
         double temp2= (double)(contR+2)/(contR+2);
         temp = (double)(temp+temp1+temp2)/contR+2;
         System.out.println("NUMQ "+175+", "+temp);
         mapQlarge.add(new BigDecimal(temp));        
         temp = (double)tempMap;
         temp1= (double)(contR+1)/(contD+1);
         temp2= (double)(contR+2)/(contD+2);
         temp = (double)(temp+temp1+temp2)/contR+2;
          System.out.println("NUMQ "+175+", "+temp);         
         mapQSmall.add(new BigDecimal(temp));
         tempMap=0;
         for(BigDecimal  d: mapQ){
             tempMap+=d.doubleValue();
         }
          mapTotal = new BigDecimal((double)tempMap/mapQ.size());
           tempMap=0;
          for(BigDecimal  d: mapQlarge){
             tempMap+=d.doubleValue();
         }
          LargeMapQ = new BigDecimal((double)tempMap/mapQlarge.size());
          tempMap=0;
         for(BigDecimal  d: mapQSmall){
             tempMap+=d.doubleValue();
         }
        SmallMapQ = new BigDecimal((double)tempMap/mapQSmall.size());
         
    }
    
    
    
    
    
    public void calcularMAPFIRE(){
        
        System.out.println("________________________________________________________");
         Documento comp =   LeituraArquivos.getDocumentos().get(0);
         double tempMap=0;
         int contR=0,contD=0;
         boolean ranking=true;
         for(Documento d: LeituraArquivos.getDocumentos()){
             if(d.getNumQ()!=comp.getNumQ()){ 
                 comp = d;
                  tempMap=0;
                 for(BigDecimal k : map){
                    tempMap= (double) tempMap+k.doubleValue();
                 }              
                 if(map.size()>0)
                    fire.add(new BigDecimal((double)tempMap/map.size()));
                 else
                     fire.add(new BigDecimal((double)0.0));
                  
                   BigDecimal temp = new BigDecimal((double)tempMap);
                  double temp1= (double)(contR+1)/(contR+1);
                  double temp2= (double)(contR+2)/(contR+2);
                  double sum = (double)(temp.doubleValue()+temp1+temp2)/(contR+2);
                  temp = new BigDecimal(sum);
                  System.out.println("NUMQ "+comp.getNumQ()+", "+temp);
                  mapFlarge.add(new BigDecimal(temp.doubleValue()));
                  
                  temp = new BigDecimal((double)tempMap);
                  temp1= (double)(contR+1)/(contD+1);
                  temp2= (double)(contR+2)/(contD+2);
                  sum = (double)(temp.doubleValue()+temp1+temp2)/(contR+2);
                  temp = new BigDecimal(sum);
                  System.out.println("NUMQ "+comp.getNumQ()+", "+temp);
                  mapFSmall.add(new BigDecimal(temp.doubleValue()));
                 
                 tempMap=0;
                 contR=0;
                 contD=0;
                 ranking=true;
                 map.clear();
             }
             if(d.getRelevante()==1 && ranking){
                 contR++;
                 map.add(new BigDecimal((double)1.0));
             }else if (d.getRelevante()==1){
                 contR++;
                 tempMap = (double) contR/contD;
                 map.add(new BigDecimal(tempMap));
             }else{
                 ranking=false;
             }
            
             contD++;    
         }
         tempMap=0;
         for(BigDecimal k : map){
            tempMap= (double) tempMap+k.doubleValue();
         }                 
         if(map.size()>0)
            fire.add(new BigDecimal((double )tempMap/map.size()));
         else
             fire.add(new BigDecimal((double )0.0));
         
         double temp = (double)tempMap;
         double temp1= (double)(contR+1)/(contR+1);
         double temp2= (double)(contR+2)/(contR+2);
         temp = (double)(temp+temp1+temp2)/contR+2;
         System.out.println("NUMQ "+175+", "+temp);
         mapFlarge.add(new BigDecimal(temp));        
         temp = (double)tempMap;
         temp1= (double)(contR+1)/(contD+1);
         temp2= (double)(contR+2)/(contD+2);
         temp = (double)(temp+temp1+temp2)/contR+2;
          System.out.println("NUMQ "+175+", "+temp); 
                  
         mapFSmall.add(new BigDecimal(temp));
         
         tempMap=0;
         for(BigDecimal  d: fire){
             tempMap+=d.doubleValue();
         }
         mapTotalFire = new BigDecimal((double)tempMap/fire.size());
         tempMap=0;
         for(BigDecimal  d: mapFlarge){
             tempMap+=d.doubleValue();
         }
          LargeMapF = new BigDecimal((double)tempMap/mapFlarge.size());
          tempMap=0;
         for(BigDecimal  d: mapFSmall){
             tempMap+=d.doubleValue();
         }
        SmallMapF = new BigDecimal((double)tempMap/mapFSmall.size());
        
    }
    public void recallprecision(){
        
        Documento comp =  LuceneSearcher.getDoc().get(0);
        ArrayList<BigDecimal> precision = new ArrayList<BigDecimal>();
        ArrayList<BigDecimal> recall = new ArrayList<BigDecimal>();
        
         for(Documento d:LuceneSearcher.getDoc()){
             if(d.getNumQ()!=comp.getNumQ()){ 
                 double [][] temp = new double[precision.size()][2];
               
                 for(int i=0; i<temp.length;i++){
                     temp[i][0] = precision.get(i).doubleValue();
                      temp[i][1] = recall.get(i).doubleValue();
                 }
                // if(comp.getNumQ()==126){
                  //  System.out.println("QUERY 126");
                    //for(int i=0;i<temp.length;i++){
                      //  System.out.println(temp[i][0]+","+temp[i][1]);
                    //}
                 //}
               //  System.out.println(comp.getNumQ());
                 querys.add(new Query(temp));
                 comp = d;
                 precision.clear();
                 recall.clear();
             }
              
             precision.add(new BigDecimal(d.getPrecisao().doubleValue()));
             recall.add(new BigDecimal(d.getRecall().doubleValue()));
         }
         double [][] temp = new double[precision.size()][2];
         for(int i=0; i<precision.size();i++){
                     temp[i][0] = precision.get(i).doubleValue();
                      temp[i][1] = recall.get(i).doubleValue();
                 }
                 querys.add(new Query(temp));
    }
    public void interpoled(){
        for(Query q: querys){
            int tam = q.getRecalprecision().length;
            double [][] temp1 = q.getRecalprecision();
            double [][] temp = new double[tam][2];
            for(int k=0;k<temp.length;k++){
                temp[k][0] = temp1[k][0];
                temp[k][1] = temp1[k][1];                
            }          
            for(int i=0; i<temp.length;i++){
                double maior = temp[i][0];
                for(int j=i+1; j<temp.length;j++){
                  if(temp[j][0]>maior)
                      maior = temp[j][0];
                }
                temp[i][0]=maior;
            }
          q.setInterpoled(temp);
          
        }
    }
    //procura qual maior recall na onze pontos, na matriz do outro lado
    // depois procura qual a maior precisao
    //depois seta a precisao
   public void onzepontos(){
       int numQ=126;
       for(Query q: querys){
            double [][] temp1 = q.getInterpoled();
            double [][] temp = new double[11][2];
            float j=0;
            for(int i=0;i<temp.length;i++){     
                temp[i][1]=j;
                j+=0.1;
            }
            int k=0,p=0,z=0;
            double verific=0,verificp=0;
            for(int i=0; i<temp.length;i=k){
                if(p < temp1.length ){
                     verific = temp1[p][1];// recal interpoled
                     verificp = temp1[p][0];
                }
                
                while((k < temp.length) && (temp[k][1]<=verific)){
                    k++;
                }
                for(int n=p+1;n<temp1.length;n++){
                    if(temp1[n][0]>verificp){
                        verificp = temp1[n][0];
                    }
                }
                 while((p < temp1.length) && (k < temp.length) && (temp1[p][1]<=temp[k][1])){
                    p++;
                }
                 if(p==temp1.length){
                     while(k<temp.length)
                         k++;
                 }
                 
                for(z=i;z<k;z++){
                    temp[z][0]=verificp;
                } 
                
            }
          q.setOnzepontos(temp);
          numQ++;
       }
   }
}
