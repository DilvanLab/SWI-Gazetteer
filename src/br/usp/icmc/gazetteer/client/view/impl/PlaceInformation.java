package br.usp.icmc.gazetteer.client.view.impl;

import br.usp.icmc.gazetteer.client.place.LinkedDataPlace;
import br.usp.icmc.gazetteer.client.view.PlaceInformationView;
import br.usp.icmc.gazetteer.client.view.PrincipalView.Presenter;
import br.usp.icmc.gazetteer.shared.Locality;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Hyperlink;

public class PlaceInformation extends Composite implements PlaceInformationView {

	private Presenter presenter;
	
	private static Place_InformationUiBinder uiBinder = GWT
			.create(Place_InformationUiBinder.class);
	@UiField Label placeSelected;
	@UiField Label agree_text;
	@UiField Label place_text;
	@UiField Label county_text;
	@UiField Label species_text;
	@UiField Label communCoordinate;
	@UiField Hyperlink placeLink;
	@UiField Label ntriples;

	interface Place_InformationUiBinder extends
			UiBinder<Widget, PlaceInformation> {
	}

	public PlaceInformation() {
		initWidget(uiBinder.createAndBindUi(this));
		placeLink.setTargetHistoryToken(LinkedDataPlace.URL);
		placeLink.setText("http://www.semanticweb.org/ontologies/Gazetter#_____");
	}

	public Label getPlaceSelected() {
		return placeSelected;
	}

	public void setPlaceSelected(Label placeSelected) {
		this.placeSelected = placeSelected;
	}

	public Label getAgree_text() {
		return agree_text;
	}

	public void setAgree_text(Label agree_text) {
		this.agree_text = agree_text;
	}

	public Label getPlace_text() {
		return place_text;
	}

	public void setPlace_text(Label place_text) {
		this.place_text = place_text;
	}

	public Label getCounty_text() {
		return county_text;
	}

	public void setCounty_text(Label county_text) {
		this.county_text = county_text;
	}

	public Label getSpecies_text() {
		return species_text;
	}

	public void setSpecies_text(Label species_text) {
		this.species_text = species_text;
	}

	public Label getCommunCoordinate() {
		return communCoordinate;
	}

	public void setCommunCoordinate(Label communCoordinate) {
		this.communCoordinate = communCoordinate;
	}

	public Hyperlink getPlaceLink() {
		return placeLink;
	}

	public void setPlaceLink(Hyperlink placeLink) {
		this.placeLink = placeLink;
	}

	@Override
	public void setName(String helloName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.presenter = listener;
		
	}

	@UiHandler("placeLink")
	void onPlaceLinkClick(ClickEvent event) {
		
	}

	/*
	 * [0] => placeSlected (String)
	 * [1] => agree_text (String)
	 * [2] => place_text (String)
	 * [3] => county_text (String)
	 * [4] => species_text (String)
	 * [5] => communCoordinate (String)
	 * [6] => placeLink (String)
	 */
	@Override
	public void setInfomation(Locality locality) {
		this.place_text.setText("Place: "+locality.getLocality());		
		this.agree_text.setText("Coordinate agreement: "+locality.getAgreeCoordinate());		
		this.county_text.setText("Municipality: "+locality.getCounty());
		this.species_text.setText("Contributors: "+locality.getContributors());
		this.communCoordinate.setText("Date: "+locality.getDate());
		this.placeLink.setText(locality.getIdTriple());
		this.ntriples.setText("Common coordinates: "+locality.getNtriplas());
		
		this.getElement().addClassName("borda");
		
	}

	
}
