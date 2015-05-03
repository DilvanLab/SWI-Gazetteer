package br.usp.icmc.gazetteer.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import br.usp.icmc.gazetteer.client.ClientFactory;
import br.usp.icmc.gazetteer.client.event.InsertPlaceEvent;
import br.usp.icmc.gazetteer.client.event.InsertPlaceEventHandler;
import br.usp.icmc.gazetteer.client.event.OntyTypesEvent;
import br.usp.icmc.gazetteer.client.place.InsertPlace;
import br.usp.icmc.gazetteer.client.view.InsertPlaceView;
import br.usp.icmc.gazetteer.client.view.PrincipalView;

public class InsertActivity implements Activity {

	private InsertPlace place;
	private ClientFactory clientFactory;
	
	public InsertActivity(InsertPlace place, ClientFactory clientFactory) {
		this.place = place;
		this.clientFactory = clientFactory;
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
		// TODO Auto-generated method stub
		
	}

}
