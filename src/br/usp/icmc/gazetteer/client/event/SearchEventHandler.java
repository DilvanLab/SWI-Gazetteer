package br.usp.icmc.gazetteer.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface SearchEventHandler  extends EventHandler {
	  void searchPlaces(SearchEvent event);
	}