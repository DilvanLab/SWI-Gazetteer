package br.usp.icmc.gazetteer.client.view.impl;

import br.usp.icmc.gazetteer.client.view.LinkedDataView;
import br.usp.icmc.gazetteer.client.view.PrincipalView.Presenter;

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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextArea;

public class LinkedData extends Composite implements LinkedDataView {

	private Presenter presenter;
	
	private static LinkedDataUiBinder uiBinder = GWT
			.create(LinkedDataUiBinder.class);
	@UiField Label place_text;
	@UiField Label county_place;
	@UiField Label species_text;
	@UiField Hyperlink rdf_link;
	@UiField Hyperlink gm_link;
	@UiField Hyperlink json_link;
	@UiField Label contributors_number;
	@UiField Label agreement;
	@UiField VerticalPanel graph_agr;
	@UiField SimpleCheckBox yes_point;
	@UiField Label point_text;
	@UiField SimpleCheckBox yes_polygon;
	@UiField Label polygon_text;
	@UiField Hyperlink external_link;
	@UiField Image image;
	@UiField TextArea description_text;

	interface LinkedDataUiBinder extends UiBinder<Widget, LinkedData> {
	}

	public LinkedData() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Label getPlace_text() {
		return place_text;
	}

	public void setPlace_text(Label place_text) {
		this.place_text = place_text;
	}

	public Label getCounty_place() {
		return county_place;
	}

	public void setCounty_place(Label county_place) {
		this.county_place = county_place;
	}

	public Label getSpecies_text() {
		return species_text;
	}

	public void setSpecies_text(Label species_text) {
		this.species_text = species_text;
	}

	public Hyperlink getRdf_link() {
		return rdf_link;
	}

	public void setRdf_link(Hyperlink rdf_link) {
		this.rdf_link = rdf_link;
	}

	public Hyperlink getGm_link() {
		return gm_link;
	}

	public void setGm_link(Hyperlink gm_link) {
		this.gm_link = gm_link;
	}

	public Hyperlink getJson_link() {
		return json_link;
	}

	public void setJson_link(Hyperlink json_link) {
		this.json_link = json_link;
	}

	public Label getContributors_number() {
		return contributors_number;
	}

	public void setContributors_number(Label contributors_number) {
		this.contributors_number = contributors_number;
	}

	public Label getAgreement() {
		return agreement;
	}

	public void setAgreement(Label agreement) {
		this.agreement = agreement;
	}

	public VerticalPanel getGraph_agr() {
		return graph_agr;
	}

	public void setGraph_agr(VerticalPanel graph_agr) {
		this.graph_agr = graph_agr;
	}

	public SimpleCheckBox getYes_point() {
		return yes_point;
	}

	public void setYes_point(SimpleCheckBox yes_point) {
		this.yes_point = yes_point;
	}

	public Label getPoint_text() {
		return point_text;
	}

	public void setPoint_text(Label point_text) {
		this.point_text = point_text;
	}

	public SimpleCheckBox getYes_polygon() {
		return yes_polygon;
	}

	public void setYes_polygon(SimpleCheckBox yes_polygon) {
		this.yes_polygon = yes_polygon;
	}

	public Label getPolygon_text() {
		return polygon_text;
	}

	public void setPolygon_text(Label polygon_text) {
		this.polygon_text = polygon_text;
	}

	public Hyperlink getExternal_link() {
		return external_link;
	}

	public void setExternal_link(Hyperlink external_link) {
		this.external_link = external_link;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public TextArea getDescription_text() {
		return description_text;
	}

	public void setDescription_text(TextArea description_text) {
		this.description_text = description_text;
	}

	@Override
	public void setName(String helloName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.presenter = listener;
		
	}

}
