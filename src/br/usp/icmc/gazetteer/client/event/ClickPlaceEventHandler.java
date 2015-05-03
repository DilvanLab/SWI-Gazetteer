package br.usp.icmc.gazetteer.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ClickPlaceEventHandler extends EventHandler {
	  void showInfo(ClickPlaceEvent event);
	}