package br.usp.icmc.gazetteer.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AgreeGraphEventHandler extends EventHandler {
	void agreeGraphSubmit(AgreeGraphEvent agreeGraphEvent);
	}