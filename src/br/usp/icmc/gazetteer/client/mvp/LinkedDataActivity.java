package br.usp.icmc.gazetteer.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import br.usp.icmc.gazetteer.client.ClientFactory;
import br.usp.icmc.gazetteer.client.place.LinkedDataPlace;
import br.usp.icmc.gazetteer.client.view.LinkedDataView;
import br.usp.icmc.gazetteer.client.view.PrincipalView;

public class LinkedDataActivity implements Activity, LinkedDataView.Presenter{

	private ClientFactory clientFactory;
	private LinkedDataPlace place;
	public LinkedDataActivity(LinkedDataPlace place, ClientFactory clientFactory) {
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
	//	Window.alert("OII");
		System.out.println("OII");
		clientFactory.getLinkedDataView().setPresenter(this);
		
		panel.setWidget(clientFactory.getLinkedDataView().asWidget());
		//Window.alert("SETOU");
		
	}

	@Override
	public void goTo(Place place) {
		// TODO Auto-generated method stub
		
	}

}
