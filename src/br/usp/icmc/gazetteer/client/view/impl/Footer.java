package br.usp.icmc.gazetteer.client.view.impl;

import br.usp.icmc.gazetteer.client.view.FooterView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Footer extends Composite implements FooterView {

	private static BottomUiBinder uiBinder = GWT.create(BottomUiBinder.class);
	private Presenter listener;
	
	@UiField VerticalPanel tags;
	@UiField VerticalPanel stats;
	@UiField Label dataInBD;
	@UiField Label dataWithCoordinate;
	@UiField Label dataWithOut_coord;
	

	interface BottomUiBinder extends UiBinder<Widget, Footer> {
	}

	public Footer() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public VerticalPanel getTags() {
		return tags;
	}

	public void setTags(VerticalPanel tags) {
		this.tags = tags;
	}

	public VerticalPanel getStats() {
		return stats;
	}

	public void setStats(VerticalPanel stats) {
		this.stats = stats;
	}

	public Label getDataInBD() {
		return dataInBD;
	}

	public void setDataInBD(Label dataInBD) {
		this.dataInBD = dataInBD;
	}

	public Label getDataWithCoordinate() {
		return dataWithCoordinate;
	}

	public void setDataWithCoordinate(Label dataWithCoordinate) {
		this.dataWithCoordinate = dataWithCoordinate;
	}

	public Label getDataWithOut_coord() {
		return dataWithOut_coord;
	}

	public void setDataWithOut_coord(Label dataWithOut_coord) {
		this.dataWithOut_coord = dataWithOut_coord;
	}

	@Override
	public void setName(String helloName) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void setPresenter(Presenter listener) {
	this.listener = listener;
		
	}

	@Override
	public void callInfoServer() {
		if(listener!=null){
			listener.InfoServer();
		}
		
	}

	@Override
	public void passInfo(String[] info) {
		this.dataWithCoordinate.setText(info[0]+" data with coordinates.");
		this.dataWithOut_coord.setText(info[1]+" data without coordinates.");
		String value =  ""+(Integer.parseInt(info[0])+Integer.parseInt(info[1]));
		this.dataInBD.setText(value+" data in Database.");
		
		System.out.println("entrou na quantidade de dados");
	}


}
