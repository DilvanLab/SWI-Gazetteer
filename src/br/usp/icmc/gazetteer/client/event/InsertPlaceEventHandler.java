package br.usp.icmc.gazetteer.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface InsertPlaceEventHandler  extends EventHandler {
	  void onInsertPlace(InsertPlaceEvent event);
	}