package br.usp.icmc.gazetteer.client.view.impl;

import br.usp.icmc.gazetteer.client.view.PrincipalView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class Principal extends Composite implements PrincipalView {

	private Presenter presenter;
	private static PrincipalUiBinder uiBinder = GWT
			.create(PrincipalUiBinder.class);
	@UiField SimplePanel menu;
	@UiField SimplePanel busca;
	@UiField SimplePanel informacoes;
	@UiField SimplePanel tabela;
	@UiField SimplePanel insertAndGraph;
	@UiField SimplePanel mapa;
	@UiField SimplePanel botoes;
	@UiField Image image;
//	@UiField HorizontalPanel user;
//	@UiField Label login;
//	@UiField Label senha;
//	@UiField Button button;
	

	interface PrincipalUiBinder extends UiBinder<Widget, Principal> {
	}

	public Principal() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public SimplePanel getSearch() {
		return busca;
	}

	@Override
	public SimplePanel getMenu() {
		return menu;
	}

	@Override
	public Image getLogo() {
		return image;
	}

//	@Override
//	public HorizontalPanel getUser() {
//		return user;
//	}

	@Override
	public SimplePanel getTable() {
		return tabela;
	}

	@Override
	public SimplePanel getInfo() {
		return informacoes;
	}

	@Override
	public SimplePanel getMap() {
		return mapa;
	}

	@Override
	public SimplePanel getButtonMap() {
		return botoes;
	}

	@Override
	public SimplePanel getInsertAndGraph() {
		return insertAndGraph;
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.presenter = listener;
	}
//	@Override
//	public String usuario(){
//		return login.getText();
//	}
//	@Override
//	public String senha(){
//		return senha.getText();
//	}
//
//	@UiHandler("button")
//	void onButtonClick(ClickEvent event) {
//		if(this.presenter!=null){
//			this.presenter.onButtonClick();
//		}else{
//			Window.alert("PRESENTER NULL");
//		}
//	}

}
