package br.usp.icmc.gazetteer.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface OntyTypesEventHandler  extends EventHandler {
	  void getOntTypes(OntyTypesEvent event);
	}