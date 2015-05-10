package br.usp.icmc.gazetteer.client.view.impl;

import br.usp.icmc.gazetteer.client.view.GraphView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;

public class Graph extends Composite implements GraphView {

	private Presenter presenter;
	
	private static GraphUiBinder uiBinder = GWT.create(GraphUiBinder.class);
	@UiField Button agreement_submit;
	@UiField RadioButton graph_yes;
	@UiField RadioButton graph_no;
	@UiField SimplePanel panel_graph;

	
	public int contributors;
	public int agreement;
	private ColumnChart chart;
		
	interface GraphUiBinder extends UiBinder<Widget, Graph> {
	}

	public Graph() {
		initWidget(uiBinder.createAndBindUi(this));
		initialize();
	}
	
	 private Widget getLineChart() {
         if (chart == null) {
        	 chart = new ColumnChart();
         }
         return chart;
	 }
	 
	public static GraphUiBinder getUiBinder() {
		return uiBinder;
	}

	public static void setUiBinder(GraphUiBinder uiBinder) {
		Graph.uiBinder = uiBinder;
	}

	public Button getAgreement_submit() {
		return agreement_submit;
	}

	public void setAgreement_submit(Button agreement_submit) {
		this.agreement_submit = agreement_submit;
	}

	public RadioButton getGraph_yes() {
		return graph_yes;
	}

	public void setGraph_yes(RadioButton graph_yes) {
		this.graph_yes = graph_yes;
	}

	public RadioButton getGraph_no() {
		return graph_no;
	}

	public void setGraph_no(RadioButton graph_no) {
		this.graph_no = graph_no;
	}

	public SimplePanel getPanel_graph() {
		return panel_graph;
	}

	public void setPanel_graph(SimplePanel panel_graph) {
		this.panel_graph = panel_graph;
	}

	public ColumnChart getChart() {
		return chart;
	}

	public void setChart(ColumnChart chart) {
		this.chart = chart;
	}

	@Override
	public void initialize() {
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {

			@Override
			public void run() {
				// Create and attach the chart
				chart = new ColumnChart();
				drawBar();
			}
		});
	}
	
	public void drawBar(){
		String[] countries = new String[] {  "Contributors","Agreement"  };
		int[] years = new int[] {2015};
	//	Window.alert(""+contributors+"   "+agreement);
		int[][] values = new int[][] { {contributors},{agreement}};

		// Prepare the data
		DataTable dataTable = DataTable.create();
		
		dataTable.addColumn(ColumnType.STRING, "Year");
		for (int i = 0; i < countries.length; i++) {
			dataTable.addColumn(ColumnType.NUMBER, countries[i]);
		}
		dataTable.addRows(years.length);
		for (int i = 0; i < years.length; i++) {
			dataTable.setValue(i, 0, String.valueOf(years[i]));
			
		}
		for (int col = 0; col < values.length; col++) {
			for (int row = 0; row < values[col].length; row++) {
				dataTable.setValue(row, col + 1, ""+values[col][row]);
				
			}
		}

		// Set options
		ColumnChartOptions options = ColumnChartOptions.create();
		options.setFontName("Tahoma");
		options.setTitle("Volunteers");
		options.setHAxis(HAxis.create("Year"));
		options.setVAxis(VAxis.create("Values"));
		chart.clearChart();
		// Draw the chart
		chart.draw(dataTable, options);
		chart.setWidth("300px");
		// Draw the chart
		chart.draw(dataTable, options);
        //panel_graph.setWidth(chart.getElement().getClientWidth()+" px");
		panel_graph.clear();
   		panel_graph.add(chart);	            
                
	}
	
	public void drawLine() {
		int years = this.agreement;
		int  values = this.contributors;
		String[] countries = new String[] { "Users"};
		

		// Prepare the data
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Contributors");
		for (int i = 0; i < countries.length; i++) {
			dataTable.addColumn(ColumnType.NUMBER, countries[i]);
		}
//		dataTable.addRows(years.length);
//		for (int i = 0; i < years.length; i++) {
//			dataTable.setValue(i, 0, String.valueOf(years[i]));
//		}
//		for (int col = 0; col < values.length; col++) {
//		
//				dataTable.setValue(col, 1, values[col]);
//			}
//		

		// Set options
		LineChartOptions options = LineChartOptions.create();
		options.setBackgroundColor("#f0f0f0");
		options.setFontName("Tahoma");
		options.setTitle("Contribution along years");
		options.setHAxis(HAxis.create("Year"));
		options.setVAxis(VAxis.create("Coordinates"));
		
		options.setWidth(300);
		
//		// Draw the chart
//		chart.draw(dataTable, options);
//		panel_graph.add(chart);
	}

	@Override
	public void setValues(int agreement, int contributors) {
		this.contributors = contributors;
		this.agreement = agreement;
		
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.presenter = listener;
		
	}
	@UiHandler("agreement_submit")
	void onAgreement_submitClick(ClickEvent event) {
		if(presenter!=null){
			
			presenter.submitAgree();
		}else{
			
		}
	}

	@Override
	public boolean trust() {		
		if(graph_yes.isChecked())
			return true;	
		else if(graph_no.isChecked())
			return false;
		return false;
	}
}
