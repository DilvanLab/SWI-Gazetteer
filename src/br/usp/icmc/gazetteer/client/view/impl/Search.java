package br.usp.icmc.gazetteer.client.view.impl;

import br.usp.icmc.gazetteer.client.view.SearchView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class Search extends Composite implements SearchView{

	private Presenter presenter;
	
	private static SearchUiBinder uiBinder = GWT.create(SearchUiBinder.class);
	@UiField ListBox quality_type;
	@UiField Label label_search;
	@UiField ListBox search_text;
	@UiField Button search;
	interface SearchUiBinder extends UiBinder<Widget, Search> {
	}

	public Search() {
		initWidget(uiBinder.createAndBindUi(this));
		build_quality_type();
		build_querys();
	}
	
	private void build_querys(){
		search_text.addItem("reservas proximas a 100 km da cidade de Manaus");
		search_text.addItem("Campo da Catuquira");
		search_text.addItem("Locais dentro do Parque Nacional do Jau");
		search_text.addItem("Rios do estado do amazonas");
		search_text.addItem("Places part-of Manaus");
	}
	
	private void build_quality_type(){
		quality_type.addItem("Type");
		quality_type.addItem("INPA");
		quality_type.addItem("IBGE");
		quality_type.addItem("Common user");
		quality_type.addItem("Computed");
	}

	public ListBox getQuality_type() {
		return quality_type;
	}

	public void setQuality_type(ListBox quality_type) {
		this.quality_type = quality_type;
	}

	public Label getLabel_search() {
		return label_search;
	}

	public void setLabel_search(Label label_search) {
		this.label_search = label_search;
	}

	@Override
	public void setName(String helloName) {
		// TODO Auto-generated method stub
		
	}

	public ListBox getSearch_text() {
		return search_text;
	}

	public void setSearch_text(ListBox search_text) {
		this.search_text = search_text;
	}

	@Override
	public void setPresenter(Presenter listener) {
		presenter = listener;
		
	}
	@UiHandler("search")
	void onSearchClick(ClickEvent event) {
		if(presenter!=null){
			presenter.showPlaceTable();
		}
	}

	@Override
	public Search getSearch() {
		return this;
	}
}
