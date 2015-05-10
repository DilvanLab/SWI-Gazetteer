package br.usp.icmc.gazetteer.client;


import java.util.List;

import br.usp.icmc.gazetteer.client.event.AgreeGraphEvent;
import br.usp.icmc.gazetteer.client.event.AgreeGraphEventHandler;
import br.usp.icmc.gazetteer.client.event.FindPlacesEvent;
import br.usp.icmc.gazetteer.client.event.FindPlacesEventHandler;
import br.usp.icmc.gazetteer.client.event.InsertPlaceEvent;
import br.usp.icmc.gazetteer.client.event.InsertPlaceEventHandler;
import br.usp.icmc.gazetteer.client.event.SearchEvent;
import br.usp.icmc.gazetteer.client.event.SearchEventHandler;
import br.usp.icmc.gazetteer.client.mvp.PrincipalActivity;
import br.usp.icmc.gazetteer.client.view.FooterView;
import br.usp.icmc.gazetteer.client.view.GraphView;
import br.usp.icmc.gazetteer.client.view.InsertPlaceView;
import br.usp.icmc.gazetteer.client.view.LinkedDataView;
import br.usp.icmc.gazetteer.client.view.MapaView;
import br.usp.icmc.gazetteer.client.view.PlaceInformationView;
import br.usp.icmc.gazetteer.client.view.PrincipalView;
import br.usp.icmc.gazetteer.client.view.SearchView;
import br.usp.icmc.gazetteer.client.view.TableView;
import br.usp.icmc.gazetteer.client.view.impl.Footer;
import br.usp.icmc.gazetteer.client.view.impl.Graph;
import br.usp.icmc.gazetteer.client.view.impl.InsertPlace;
import br.usp.icmc.gazetteer.client.view.impl.LinkedData;
import br.usp.icmc.gazetteer.client.view.impl.Mapa;
import br.usp.icmc.gazetteer.client.view.impl.PlaceInformation;
import br.usp.icmc.gazetteer.client.view.impl.Principal;
import br.usp.icmc.gazetteer.client.view.impl.Search;
import br.usp.icmc.gazetteer.client.view.impl.Table;
import br.usp.icmc.gazetteer.shared.Locality;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class ClientFactoryImp implements ClientFactory{

	private static final EventBus eventBus = new SimpleEventBus();
	private static final PlaceController placeController = new PlaceController(eventBus);
	private static final GazetteerServiceAsync rpc = GWT.create(GazetteerService.class);

	private static final FooterView bottom = new Footer();
	private static final GraphView graph = new Graph();
	private static final InsertPlaceView insertPlace = new InsertPlace();
	private static final LinkedDataView linkData = new LinkedData(); 
	private static final MapaView map = new Mapa();
	private static final PlaceInformationView plInfo = new PlaceInformation();
	private static final PrincipalView main = new Principal();
	private static final SearchView search = new Search();
	private static final TableView table = new Table();
	private static final RootWidget rootWidget = new RootWidget();


	public static Locality Selected;
	
	public ClientFactoryImp(){
		criarEventBus();
	}

	public static class RootWidget implements AcceptsOneWidget {
		// Create a tab panel with three tabs, each of which displays a
		// different
		SimplePanel tp = new SimplePanel();

		public RootWidget() {
			RootLayoutPanel.get().clear();
			RootLayoutPanel.get().add(tp);
		}

		@Override
		public void setWidget(IsWidget w) {
			if (w == null)
				return;
			tp.clear();
			tp.add(w);
		}
	}

	@Override
	public EventBus getEventBus() {		
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public RootWidget getRootWidget() {
		return rootWidget;
	}

	@Override
	public FooterView getBottomView() {
		return bottom;
	}

	@Override
	public GraphView getGraphView() {
		return graph;
	}

	@Override
	public InsertPlaceView getInsertPlaceView() {
		return insertPlace;
	}

	@Override
	public LinkedDataView getLinkedDataView() {
		return linkData;
	}

	@Override
	public MapaView getMapaView() {
		return map;
	}

	@Override
	public PlaceInformationView getPlaceInformationView() {
		return plInfo;
	}

	@Override
	public PrincipalView getPrincipalView() {
		return main;
	}

	@Override
	public SearchView getSearchView() {
		return search;
	}

	@Override
	public TableView getTableView() {
		return table;
	}

	@Override
	public GazetteerServiceAsync getRPC() {
		return rpc;
	}


	public boolean isDate(String date){
		try{
			Integer.parseInt(date);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	public Locality getInfoLocality(){
		String today = "2015"; // modificar para pegar a data corrente, (estou sem tempo agora)
		Locality locality = null;
		if(PrincipalActivity.locality!=null){
			locality = PrincipalActivity.locality;
		}else{
			locality = new Locality();
		}
		locality.setContributors(locality.getContributors()+1);
		
		if(insertPlace.getInsertPlace().getDraw_radio().getFormValue().equals("map")){
			if(map.getGeometry().equals("invalido")){
				Window.alert("Coordenadas invalidas");
				return null;
			}
			locality.setGeometry(map.getGeometry());
		}else
			locality.setGeometry(insertPlace.getInsertPlace().getCoord_text().getText());
				
		
		locality.setLocality(insertPlace.getInsertPlace().getPlace_name().getText());

		if(!isDate(locality.getDate()))
			locality.setDate(today);
		locality.setCounty(insertPlace.getInsertPlace().getCounty().getText());
		
		int index = getInsertPlaceView().getInsertPlace().getOnty_type().getSelectedIndex();
		if(index!=-1){
			String type = getInsertPlaceView().getInsertPlace().getOnty_type().getItemText(index);
			locality.setType(type);			
		}else{
			locality.setType(locality.getType());
		}
		if(insertPlace.getInsertPlace().getExt_link().getText().equals("") || insertPlace.getInsertPlace().getExt_link().getText().equals("não informado"))
			locality.setCounty(insertPlace.getInsertPlace().getExt_link().getText());
	
		return locality;
	}


	private void criarEventBus(){



		getEventBus().addHandler(SearchEvent.TYPE, new SearchEventHandler() {
			@Override
			public void searchPlaces(SearchEvent event) {
				DOM.setInnerHTML(RootPanel.get("Loading-Message").getElement(), " <img src=\"ajax-loader.gif\"  alt=\"loading\"> Loading, please wait... ");
				RootPanel.get("GWT-Application_Panel");
				
				int index = getSearchView().getSearch().getSearch_text().getSelectedIndex();
				String queryString="";
				System.out.println("INdex selecionado"+index);
				if(index!=-1)
					queryString = getSearchView().getSearch().getSearch_text().getItemText(index);
				rpc.searchLocalities(queryString, new AsyncCallback<List<Locality>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Cannot retrieval information in the Database.");
						
					}

					@Override
					public void onSuccess(List<Locality> result) {
						table.preenche(result);
					}
				});
				
			}
		});

		//
		getEventBus().addHandler(AgreeGraphEvent.TYPE, new AgreeGraphEventHandler(){
			
			@Override
			public void agreeGraphSubmit(AgreeGraphEvent agreeGraphEvent) {
				DOM.setInnerHTML(RootPanel.get("Loading-Message").getElement(), " <img src=\"ajax-loader.gif\"  alt=\"loading\"> Loading, please wait... ");
				RootPanel.get("GWT-Application_Panel");
				final Locality locality= PrincipalActivity.locality;		
				
				if(graph.trust()){
					locality.setContributors(locality.getContributors()+1);
					locality.setAgreeCoordinate(locality.getAgreeCoordinate()+1);
					rpc.agreeLinkedData(locality, new AsyncCallback<Void>() {
						
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Cannot insert your information in the Database.");						
						}
	
						@Override
						public void onSuccess(Void result) {
							Window.alert("Information inserted in the Database.");	
							PrincipalView main = getPrincipalView();
							main.getInsertAndGraph().clear();
							getGraphView().setValues(locality.getAgreeCoordinate(), locality.getContributors());
							getGraphView().initialize();
							main.getInsertAndGraph().add(getGraphView().asWidget());
							PrincipalActivity.locality=null;		
						}
					});

				}else{
					 locality.setAgreeCoordinate(locality.getAgreeCoordinate()-1);
					 int contri = locality.getContributors();
					 int agree = locality.getAgreeCoordinate();
					 if((agree/contri)<0.7){
						 locality.setUpdateGeo(true);
						 locality.setAgreeCoordinate(0);
						 main.getInsertAndGraph().clear();						
						 getInsertPlaceView().insertPlaceinfo(locality);
						 main.getInsertAndGraph().add(insertPlace.asWidget());						 
					 }else{
							rpc.agreeLinkedData(locality, new AsyncCallback<Void>() {
								
								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Cannot insert your information in the Database.");						
								}
			
								@Override
								public void onSuccess(Void result) {
									Window.alert("Information inserted in the Database.");	
									PrincipalView main = getPrincipalView();
									main.getInsertAndGraph().clear();
									getGraphView().setValues(locality.getAgreeCoordinate(), locality.getContributors());
									getGraphView().initialize();
									main.getInsertAndGraph().add(getGraphView().asWidget());
									PrincipalActivity.locality=null;		
								}
							});
					 }
				}
				
				DOM.setInnerHTML(RootPanel.get("Loading-Message").getElement(), "");
			}
			
		});
		
		
		getEventBus().addHandler(InsertPlaceEvent.TYPE, new InsertPlaceEventHandler(){
			@Override
			public void onInsertPlace(InsertPlaceEvent event) {	
				DOM.setInnerHTML(RootPanel.get("Loading-Message").getElement(), " <img src=\"ajax-loader.gif\"  alt=\"loading\"> Loading, please wait... ");
				RootPanel.get("GWT-Application_Panel");
				
				Locality locality= getInfoLocality();
				
				rpc.insertLocality(locality, new AsyncCallback<Integer>(){
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Cannot insert your information in the Database.");						
					}
					@Override
					public void onSuccess(Integer result) {
						if(result.intValue()>0){
							PrincipalActivity.locality=null;
							Window.alert("Information inserted in the Database.");
						}
					}

				});
				DOM.setInnerHTML(RootPanel.get("Loading-Message").getElement(), "");
			}
		});


		eventBus.addHandler(FindPlacesEvent.TYPE, new FindPlacesEventHandler() {
			
			@Override
			public void onFindPlaces(FindPlacesEvent event) {
				DOM.setInnerHTML(RootPanel.get("Loading-Message").getElement(), " <img src=\"ajax-loader.gif\"  alt=\"loading\"> Loading, please wait... ");
				RootPanel.get("GWT-Application_Panel");
				rpc.findPlacesWithOutCoord(new AsyncCallback<List<Locality>>() {
					@Override
					public void onFailure(Throwable caught) {
						GWT.log("Can't access database :(", caught);
						Window.alert("Can't access database :("+caught.getMessage());
					}
					@Override
					public void onSuccess(List<Locality> result) {
						System.out.println("::::::"+result.size());
						table.preenche(result);
						
					}});
			}
		});
	}
}
