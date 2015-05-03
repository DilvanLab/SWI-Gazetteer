package br.usp.icmc.gazetteer.client;

import br.usp.icmc.gazetteer.client.mvp.AppActivityMapper;
import br.usp.icmc.gazetteer.client.mvp.AppPlaceHistoryMapper;
import br.usp.icmc.gazetteer.client.place.PrincipalPlace;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IcmcGazetteer implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	
	private PrincipalPlace defaultplace = new PrincipalPlace("Home");
	
	public void onModuleLoad() {
		
		    
			RootPanel.get("GWT-Application_Panel");
		
		 	ClientFactory clientFactory = GWT.create(ClientFactory.class);
	        EventBus eventBus = clientFactory.getEventBus();
	        PlaceController placeController = clientFactory.getPlaceController();
	        
	   	        
	        // Start ActivityManager for the main widget with our ActivityMapper
	        ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
	        ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
	        
	        activityManager.setDisplay(clientFactory.getRootWidget());

	        // Start PlaceHistoryHandler with our PlaceHistoryMapper
	        AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
	        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
	      
	        historyHandler.register(placeController, eventBus, defaultplace);
        
	        
	        // Goes to the place represented on URL else default place
	        historyHandler.handleCurrentHistory();
	        History.fireCurrentHistoryState();
	}
}
