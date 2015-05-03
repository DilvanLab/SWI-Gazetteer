package br.usp.icmc.gazetteer.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LoginEventHandler extends EventHandler {
	  void getLogin(LoginEvent event);
	}