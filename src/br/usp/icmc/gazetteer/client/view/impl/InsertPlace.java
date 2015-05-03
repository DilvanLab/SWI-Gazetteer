package br.usp.icmc.gazetteer.client.view.impl;


import java.util.List;

import br.usp.icmc.gazetteer.client.view.InsertPlaceView;
import br.usp.icmc.gazetteer.shared.Locality;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class InsertPlace extends Composite implements InsertPlaceView{

	private Presenter presenter;
	
	private static InsertPlaceUiBinder uiBinder = GWT
			.create(InsertPlaceUiBinder.class);
	
	
	
	@UiField Label label_place_inf;
	@UiField TextArea place_name;
	@UiField ListBox onty_type;
	@UiField Label County;
	@UiField RadioButton point_radio;
	@UiField RadioButton polygon_radio;
	@UiField RadioButton draw_radio;
	@UiField TextArea coord_text;
	@UiField Button submit_coord;
	@UiField TextBox county;

	interface InsertPlaceUiBinder extends UiBinder<Widget, InsertPlace> {
	}

	public InsertPlace() {
		initWidget(uiBinder.createAndBindUi(this));
		onty_type.addItem("Type");
		place_name.setWidth("357px");
		
	}
	

	public Label getLabel_place_inf() {
		return label_place_inf;
	}

	public void setLabel_place_inf(Label label_place_inf) {
		this.label_place_inf = label_place_inf;
	}

	public TextArea getPlace_name() {
		return place_name;
	}

	public void setPlace_name(TextArea place_name) {
		this.place_name = place_name;
	}

	public ListBox getOnty_type() {
		return onty_type;
	}

	public void setOnty_type(ListBox onty_type) {
		this.onty_type = onty_type;
	}

	public Label getExt_link() {
		return County;
	}

	public void setExt_link(Label ext_link) {
		this.County = ext_link;
	}

	public RadioButton getPoint_radio() {
		return point_radio;
	}

	public void setPoint_radio(RadioButton point_radio) {
		this.point_radio = point_radio;
	}

	public RadioButton getPolygon_radio() {
		return polygon_radio;
	}

	public void setPolygon_radio(RadioButton polygon_radio) {
		this.polygon_radio = polygon_radio;
	}

	public RadioButton getDraw_radio() {
		return draw_radio;
	}

	public void setDraw_radio(RadioButton draw_radio) {
		this.draw_radio = draw_radio;
	}

	public TextArea getCoord_text() {
		return coord_text;
	}

	public void setCoord_text(TextArea coord_text) {
		this.coord_text = coord_text;
	}

	public Button getSubmit_coord() {
		return submit_coord;
	}

	public void setSubmit_coord(Button submit_coord) {
		this.submit_coord = submit_coord;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		
	}

	
	@UiHandler("submit_coord")
	void onSubmit_coordClick(ClickEvent event) {
		if(presenter!=null){
			presenter.onSubmit_coordClick();
		}else{
			Window.alert("PRESENTER NULL");
		}
	}

	@Override
	public InsertPlace getInsertPlace() {
		// TODO Auto-generated method stub
		return this;
	}
//	@UiHandler("onty_type")
//	void onOnty_typeClick(ClickEvent event) {
//		if(presenter!=null){
//			presenter.getClickedOntyType();
//		}else{
//			Window.alert("PRESENTER NULL");
//		}
//	}


	@Override
	public void callOntTypeServer() {
		if(presenter!=null){
			presenter.TypeServer();
		}else{
			Window.alert("PRESENTER NULL");
		}
		
	}


	@Override
	public void OntFromServer(List<String> result) {
		onty_type.clear();
		for(String s:result)
			this.onty_type.addItem(s);
		
	}


	@Override
	public void insertPlaceinfo(Locality locality) {
		this.getPlace_name().setText(locality.getLocality());
		this.county.setText(locality.getCounty());
		int index=-1;
		for(int i=0;i<this.onty_type.getItemCount();i++){
			if(this.onty_type.getItemText(i).equals(locality.getType())){
				index = i;
				break;
			}
		}
		
		this.getOnty_type().setSelectedIndex(index);
	}
}
