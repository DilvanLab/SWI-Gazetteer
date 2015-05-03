package br.usp.icmc.gazetteer.client.view.impl;

import java.util.List;

import br.usp.icmc.gazetteer.client.view.TableView;
import br.usp.icmc.gazetteer.shared.Locality;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Table extends Composite implements TableView{
	private Presenter presenter;
	private static TableUiBinder uiBinder = GWT.create(TableUiBinder.class);
	@UiField FlexTable table_place;
	@UiField Button find_places;
	
	
	interface TableUiBinder extends UiBinder<Widget, Table> {
	}

	public Table() {
			
		initWidget(uiBinder.createAndBindUi(this));
		cabecalho();	
		insertEmpty();
	}

	public FlexTable getTable_place() {
		return table_place;
	}

	public void setTable_place(FlexTable table_place) {
		this.table_place = table_place;
	}

	public Button getFind_places() {
		return find_places;
	}

	public void setFind_places(Button find_places) {
		this.find_places = find_places;
	}

	@UiHandler("find_places")
	void onFind_placesClick(ClickEvent event) {
		if(presenter!=null){
			presenter.findPlaces();
		}
	}

	@Override
	public void setName(String helloName) {
		// TODO Auto-generated method stub
		
	}
	
	private void cabecalho(){
		table_place.setText(0, 0, "Place"); 
		table_place.setText(0, 1, "Coordinate");  
		table_place.setText(0, 2, "Selected");
		table_place.getRowFormatter().setStyleName(0, "watchListHeader");
	}
	

	@Override
	public void setPresenter(Presenter listener) {
		presenter = listener;
		
	}
	
	private void insertEmpty(){
		for(int i=0;i<6;i++){
			table_place.setText(i+1, 0, "");
			table_place.setText(i+1, 1, "[ ]");
			table_place.setText(i+1, 2, "");
		}
	}

	private void removeAllOfTable(){
		while(table_place.getRowCount()>0)
			table_place.removeRow(0);
	}
	
	@Override
	public void preenche(final List<Locality> localities) {
		table_place.clear();
		removeAllOfTable();
		cabecalho();
		System.out.println(localities.size());
		for(int i=0; i<localities.size();i++){
		//	System.out.println(localities.get(i));
			final Locality locality = localities.get(i);
		//	System.out.println(locality);
			table_place.setText(i+1, 0, locality.getLocality());
			table_place.setText(i+1, 1, locality.getGeometry());
			
			if(i%2==0){
				table_place.getCellFormatter().setStyleName(i+1, 0, "trPlaces");
				table_place.getCellFormatter().setStyleName(i+1, 1, "trPlaces");
				table_place.getCellFormatter().setStyleName(i+1, 2, "trPlaces");
			}
			RadioButton bt = new RadioButton("tableButton","");
			bt.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					if(presenter!=null){
						
						presenter.clickPlace(locality);
					}
				}
            });
			table_place.setWidget(i+1, 2,bt );	
		}
		  DOM.setInnerHTML(RootPanel.get("Loading-Message").getElement(), "");
	}
}
