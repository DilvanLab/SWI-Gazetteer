package br.usp.icmc.gazetteer.client.mvp;

import java.util.List;

import br.usp.icmc.gazetteer.client.ClientFactory;
import br.usp.icmc.gazetteer.client.event.AgreeGraphEvent;
import br.usp.icmc.gazetteer.client.event.FindPlacesEvent;
import br.usp.icmc.gazetteer.client.event.InsertPlaceEvent;
import br.usp.icmc.gazetteer.client.event.SearchEvent;
import br.usp.icmc.gazetteer.client.place.PrincipalPlace;
import br.usp.icmc.gazetteer.client.view.FooterView;
import br.usp.icmc.gazetteer.client.view.GraphView;
import br.usp.icmc.gazetteer.client.view.InsertPlaceView;
import br.usp.icmc.gazetteer.client.view.MapaView;
import br.usp.icmc.gazetteer.client.view.PlaceInformationView;
import br.usp.icmc.gazetteer.client.view.PrincipalView;
import br.usp.icmc.gazetteer.client.view.SearchView;
import br.usp.icmc.gazetteer.client.view.TableView;
import br.usp.icmc.gazetteer.shared.Locality;
import br.usp.icmc.gazetteer.shared.User;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PrincipalActivity implements Activity, SearchView.Presenter,TableView.Presenter, 
MapaView.Presenter, FooterView.Presenter, PrincipalView.Presenter, InsertPlaceView.Presenter, 
PlaceInformationView.Presenter, GraphView.Presenter {

	private PrincipalPlace place;
	public static Locality locality;
	private ClientFactory clientFactory;
	public PrincipalActivity(PrincipalPlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.place = place;
		
	}

	@Override
	public String mayStop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		PrincipalView main = clientFactory.getPrincipalView();
		
		main.setPresenter(this);
		clientFactory.getPlaceInformationView().setPresenter(this);
		clientFactory.getSearchView().setPresenter(this);
		clientFactory.getTableView().setPresenter(this);
		clientFactory.getInsertPlaceView().setPresenter(this);
		clientFactory.getMapaView().setPresenter(this);
		clientFactory.getBottomView().setPresenter(this);
		clientFactory.getGraphView().setPresenter(this);
		
		
		main.getSearch().add(clientFactory.getSearchView().asWidget());
		main.getTable().add(clientFactory.getTableView().asWidget());
		main.getInfo().add(clientFactory.getPlaceInformationView().asWidget());
		main.getInsertAndGraph().add(clientFactory.getInsertPlaceView().asWidget());
		main.getMap().add(clientFactory.getMapaView().asWidget());
		clientFactory.getInsertPlaceView().callOntTypeServer();		
		clientFactory.getMapaView().clear();
		clientFactory.getMapaView().callServerPoints();
		clientFactory.getPrincipalView().getButtonMap().add(clientFactory.getMapaView().getComponets().asWidget());
		clientFactory.getBottomView().callInfoServer();
		panel.setWidget(main.asWidget());
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
		
	}

	@Override
	public void onSubmit_coordClick() {		
		clientFactory.getEventBus().fireEvent(new InsertPlaceEvent());
				
	}

	

	@Override
	public void findPlaces() {
		clientFactory.getEventBus().fireEvent(new FindPlacesEvent());
	}
		
			
	@Override
	public void clickPlace(Locality locality) {
		//[locality] [geometry] [agreeCoord] [county] [contributors][date][link]
		PrincipalView main = clientFactory.getPrincipalView();
		VerticalPanel temp = new VerticalPanel();
	
		if(locality.getGeometry()!=null && !locality.getGeometry().equals("")){
			main.getInsertAndGraph().clear();
			int agrement= locality.getAgreeCoordinate();
			int contributor = locality.getContributors();
			clientFactory.getGraphView().setPresenter(this);
			clientFactory.getGraphView().setValues(agrement, contributor);
			clientFactory.getGraphView().initialize();
			main.getInsertAndGraph().add(clientFactory.getGraphView().asWidget());
			clientFactory.getMapaView().showGeometry(locality);
			PrincipalActivity.locality = locality;
		}else{
			this.setInfoPlace(locality);
		}
		main.getInfo().clear();
		temp.add(clientFactory.getPlaceInformationView().asWidget());		
		temp.add(clientFactory.getBottomView().asWidget());
		main.getInfo().add(temp.asWidget());
		this.setInfomation(locality);
	}

	@Override
	public void submitAgree() {
		clientFactory.getEventBus().fireEvent(new AgreeGraphEvent());

	}

	@Override
	public void showPlaceTable() {
		clientFactory.getEventBus().fireEvent(new SearchEvent());
		
	}
	/*
	 * [0] => placeSlected (String)
	 * [1] => agree_text (String)
	 * [2] => place_text (String)
	 * [3] => county_text (String)
	 * [4] => species_text (String)
	 * [5] => communCoordinate (String)
	 * [6] => placeLink (String)
	 * 
	 */
	
	public void setInfomation(Locality locality) {
		PrincipalActivity.locality= locality;
		clientFactory.getPlaceInformationView().setInfomation(locality);
		
	}
	
	public void setInfoPlace(Locality locality){
		clientFactory.getInsertPlaceView().insertPlaceinfo(locality);
	
	}

	@Override
	public void cluster() {
	 clientFactory.getRPC().getPolygons( new AsyncCallback<List<Float[]>>(){

		@Override
		public void onFailure(Throwable caught) {
			 GWT.log("Can't access database :(", caught);
			 Window.alert("Can't access database :("+caught.getMessage());
			
		}

		@Override
		public void onSuccess(List<Float[]> result) {
			System.out.println("Voltando os pontos");
			clientFactory.getMapaView().PointsFromServer(result);
			clientFactory.getMapaView().buildMap();
		}

		});
	 
		
	}
//
//	@Override
//	public void onButtonClick() {
//		clientFactory.getRPC().getUser(clientFactory.getPrincipalView().usuario(), clientFactory.getPrincipalView().senha(), new AsyncCallback<User>(){
//
//			@Override
//			public void onFailure(Throwable caught) {
//				 Window.alert("can not access the database");
//			}
//
//			@Override
//			public void onSuccess(User result) {
//				 Window.alert("Gravando dados...");				
//			}});
//		
//		
//	}


	@Override
	public void TypeServer() {
		  clientFactory.getRPC().getOntTypes(new AsyncCallback<List<String>>(){
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Can not connect on server");
			}
			@Override
			public void onSuccess(List<String> result) {
				clientFactory.getInsertPlaceView().OntFromServer(result);			
			}  
	});
			
		
		
	}

	@Override
	public void InfoServer() {
		clientFactory.getRPC().infoServer(new AsyncCallback<String[]>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Can not connect on server");
			}

			@Override
			public void onSuccess(String[] result) {
				clientFactory.getBottomView().passInfo(result);				
			}
			
		});
		
	}

//	@Override
//	public void getClickedOntyType() {
//		int index = clientFactory.getInsertPlaceView().getInsertPlace().getOnty_type().getSelectedIndex();
//		String value = clientFactory.getInsertPlaceView().getInsertPlace().getOnty_type().getItemText(index).toString();
//		System.out.println(value);
//		
//	}


	

}
