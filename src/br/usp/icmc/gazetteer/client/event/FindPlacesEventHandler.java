package br.usp.icmc.gazetteer.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface FindPlacesEventHandler  extends EventHandler {
	  void onFindPlaces(FindPlacesEvent event);
	}