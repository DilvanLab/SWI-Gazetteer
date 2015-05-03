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

import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author Silvio
 */

public class Grafico{

    CalculoMedias cm = new CalculoMedias();
    private int height;
    private int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Grafico(int height, int width) {
        this.height = height;
        this.width = width;
    }
    
    
    
 public ChartPanel criaGrafico() {
        
        // create the chart...  
        JFreeChart graf = ChartFactory.createXYLineChart(  
            "MAP ENTRE AS QUERYS",      // chart title  
            "QUERY",                      // x axis label  
            "MAP",                      // y axis label  
            createDataset1(),                  // data  
            PlotOrientation.VERTICAL,  
            true,                     // include legend  
            true,                     // tooltips  
            false                     // urls  
        );  
          
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...  
        graf.setBackgroundPaint(Color.white);  
          
        // get a reference to the plot for further customisation...  
        XYPlot plot = (XYPlot) graf.getPlot();  
        plot.setDataset(1, createDataset2());  
        plot.setBackgroundPaint(Color.lightGray);  
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));  
        plot.setDomainGridlinePaint(Color.white);  
        plot.setRangeGridlinePaint(Color.white);  
          
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();  
        renderer.setShapesVisible(false);  
          
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);  
        plot.setRenderer(1, renderer2);  
          
        // change the auto tick unit selection to integer units only...  
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();  
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());  
        // OPTIONAL CUSTOMISATION COMPLETED.  
        
        ChartPanel myChartPanel = new ChartPanel(graf, true);
        return myChartPanel;
}
    private  XYDataset createDataset1() {  
       XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
       XYSeries team1_xy_data = new XYSeries("MAP1");
       int k =126;
       for(BigDecimal  d: cm.getMapQ()){            
            team1_xy_data.add(k,d.floatValue());
             k++;
         }
        xySeriesCollection.addSeries(team1_xy_data);
        return xySeriesCollection;
    }  
      
    private  XYDataset createDataset2() {  
       XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
       XYSeries team1_xy_data = new XYSeries("MAP2");
       int k =126;
       for(BigDecimal  d: cm.getFire()){            
            team1_xy_data.add(k,d.floatValue());
             k++;
         }
      xySeriesCollection.addSeries(team1_xy_data);
      return xySeriesCollection; 
    }  

       private  XYDataset createDatasetprecision(int index) {  
           index = index-126;
               System.out.println("Plotando precsion");
            XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
            XYSeries team1_xy_data = new XYSeries(String.format("%d", index));
            Query q = CalculoMedias.getQuerys().get(index);
            double [][] temp = q.getRecalprecision();
            for(int i=0;i<temp.length;i++){
                team1_xy_data.add(temp[i][1],temp[i][0]);
             //   System.out.println(temp[i][0]+","+temp[i][1]);
            }
            xySeriesCollection.addSeries(team1_xy_data);
            return xySeriesCollection; 
         
    } 
    
   
       private  XYDataset createDatasetInterpoled(int index) {  
            index = index-126;
          // System.out.println("Plotando interpoled");
            XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
           XYSeries team1_xy_data = new XYSeries(String.format("%d", index));
            Query q = CalculoMedias.getQuerys().get(index);
            double [][] temp = q.getInterpoled();
            for(int i=0;i<temp.length;i++){
                team1_xy_data.add(temp[i][1],temp[i][0]);
           //     System.out.println(temp[i][0]+","+temp[i][1]);
            }
       
             xySeriesCollection.addSeries(team1_xy_data);
            return xySeriesCollection; 
    }  
      
    private  XYDataset createDatasetOnze(){  
         System.out.println("_______________________________________________________________");
            System.out.println("Plotando onze pontos");
            XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
            XYSeries team1_xy_data = new XYSeries("Media");
            ArrayList<Query> query = CalculoMedias.getQuerys();
            double [][]mat = new double[11][2];
            for(Query q: query){
            double [][] temp = q.getOnzepontos();
                for(int i=0;i<temp.length;i++){
                    mat[i][0]+=temp[i][0];
                    mat[i][1]=temp[i][1];
                }
            }
            for(int i=0;i<mat.length;i++){
                double valor = (double)mat[i][0]/50;
                team1_xy_data.add(mat[i][1],valor);
                System.out.println(mat[i][0]+","+mat[i][1]);
            }
        
            xySeriesCollection.addSeries(team1_xy_data);
            return xySeriesCollection; 
    }  

    
    
    
    /***************************************** INDIVIDUAIS **************************************************/
    
    
    
     public ChartPanel precisionrecall(int index) {
        
          // create the chart...  
        JFreeChart graf = ChartFactory.createXYLineChart(  
            "Precision Recall",      // chart title  
            "Recall",                      // x axis label  
            "Precision",                      // y axis label  
            createDatasetprecision(index),                  // data  
            PlotOrientation.VERTICAL,  
            true,                     // include legend  
            true,                     // tooltips  
            false                     // urls  
        );  
          
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...  
        graf.setBackgroundPaint(Color.white);  
          
         // get a reference to the plot for further customisation...  
        XYPlot plot = (XYPlot) graf.getPlot();  
        plot.setBackgroundPaint(Color.lightGray);  
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));  
        plot.setDomainGridlinePaint(Color.white);  
        plot.setRangeGridlinePaint(Color.white);  
          
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();  
        renderer.setShapesVisible(false);  
          
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);  
        plot.setRenderer(1, renderer2);  
          
        // change the auto tick unit selection to integer units only...  
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();  
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());  
        // OPTIONAL CUSTOMISATION COMPLETED.  
        
        ChartPanel myChartPanel = new ChartPanel(graf, true);
        return myChartPanel;
}
    
         public ChartPanel interpolado(int index) {
        
          // create the chart...  
        JFreeChart graf = ChartFactory.createXYLineChart(  
            "Interpolado",      // chart title  
            "Recall",                      // x axis label  
            "Precision",                      // y axis label  
            createDatasetInterpoled(index),                  // data  
            PlotOrientation.VERTICAL,  
            true,                     // include legend  
            true,                     // tooltips  
            false                     // urls  
        );  
          
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...  
        graf.setBackgroundPaint(Color.white);  
          
         // get a reference to the plot for further customisation...  
        XYPlot plot = (XYPlot) graf.getPlot();  
        plot.setBackgroundPaint(Color.lightGray);  
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));  
        plot.setDomainGridlinePaint(Color.white);  
        plot.setRangeGridlinePaint(Color.white);  
          
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();  
        renderer.setShapesVisible(false);  
          
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);  
        plot.setRenderer(1, renderer2);  
          
        // change the auto tick unit selection to integer units only...  
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();  
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());  
        // OPTIONAL CUSTOMISATION COMPLETED.  
        
        ChartPanel myChartPanel = new ChartPanel(graf, true);
        return myChartPanel;
}
         
        
         public ChartPanel media11() {
        
          // create the chart...  
        JFreeChart graf = ChartFactory.createXYLineChart(  
            "Media 11 Pontos",      // chart title  
            "Recall",                      // x axis label  
            "Precision",                      // y axis label  
            createDatasetOnze(),                  // data  
            PlotOrientation.VERTICAL,  
            true,                     // include legend  
            true,                     // tooltips  
            false                     // urls  
        );  
          
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...  
        graf.setBackgroundPaint(Color.white);  
          
         // get a reference to the plot for further customisation...  
        XYPlot plot = (XYPlot) graf.getPlot();  
        plot.setBackgroundPaint(Color.lightGray);  
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));  
        plot.setDomainGridlinePaint(Color.white);  
        plot.setRangeGridlinePaint(Color.white);  
          
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();  
        renderer.setShapesVisible(false);  
          
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);  
        plot.setRenderer(1, renderer2);  
          
        // change the auto tick unit selection to integer units only...  
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();  
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());  
        // OPTIONAL CUSTOMISATION COMPLETED.  
        
        ChartPanel myChartPanel = new ChartPanel(graf, true);
        return myChartPanel;
}

         
       public ChartPanel media11(int index) {
        
          // create the chart...  
        JFreeChart graf = ChartFactory.createXYLineChart(  
            "Media 11 pontos",      // chart title  
            "Recall",                      // x axis label  
            "Precision",                      // y axis label  
            createDatasetOnze(index),                  // data  
            PlotOrientation.VERTICAL,  
            true,                     // include legend  
            true,                     // tooltips  
            false                     // urls  
        );  
          
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...  
        graf.setBackgroundPaint(Color.white);  
          
         // get a reference to the plot for further customisation...  
        XYPlot plot = (XYPlot) graf.getPlot();  
        plot.setBackgroundPaint(Color.lightGray);  
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));  
        plot.setDomainGridlinePaint(Color.white);  
        plot.setRangeGridlinePaint(Color.white);  
          
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();  
        renderer.setShapesVisible(false);  
          
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);  
        plot.setRenderer(1, renderer2);  
          
        // change the auto tick unit selection to integer units only...  
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();  
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());  
        // OPTIONAL CUSTOMISATION COMPLETED.  
        
        ChartPanel myChartPanel = new ChartPanel(graf, true);
        return myChartPanel;
}       

    private XYDataset createDatasetOnze(int index) {
         index = index-126;
          // System.out.println("Plotando interpoled");
            XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
           XYSeries team1_xy_data = new XYSeries(String.format("%d", index));
            Query q = CalculoMedias.getQuerys().get(index);
            double [][] temp = q.getOnzepontos();
            for(int i=0;i<temp.length;i++){
                team1_xy_data.add(temp[i][1],temp[i][0]);
           //     System.out.println(temp[i][0]+","+temp[i][1]);
            }
       
             xySeriesCollection.addSeries(team1_xy_data);
            return xySeriesCollection; 
    }
         
}

