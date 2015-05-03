package br.usp.icmc.gazetteer.client;

import br.usp.icmc.gazetteer.client.ClientFactoryImp.RootWidget;
import br.usp.icmc.gazetteer.client.view.FooterView;
import br.usp.icmc.gazetteer.client.view.GraphView;
import br.usp.icmc.gazetteer.client.view.InsertPlaceView;
import br.usp.icmc.gazetteer.client.view.LinkedDataView;
import br.usp.icmc.gazetteer.client.view.MapaView;
import br.usp.icmc.gazetteer.client.view.PlaceInformationView;
import br.usp.icmc.gazetteer.client.view.PrincipalView;
import br.usp.icmc.gazetteer.client.view.SearchView;
import br.usp.icmc.gazetteer.client.view.TableView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

public interface ClientFactory {

	EventBus getEventBus();
	
	PlaceController getPlaceController();
	
	RootWidget getRootWidget();
	
	FooterView getBottomView();
	
	GraphView getGraphView();
	
	InsertPlaceView getInsertPlaceView();
	
	LinkedDataView getLinkedDataView();
	
	MapaView getMapaView();
	
	PlaceInformationView getPlaceInformationView();
	
	PrincipalView getPrincipalView();
	
	SearchView getSearchView();
	
	TableView getTableView();
	
	GazetteerServiceAsync getRPC();
	
}
