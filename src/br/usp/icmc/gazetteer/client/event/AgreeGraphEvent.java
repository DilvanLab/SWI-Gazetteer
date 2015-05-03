package br.usp.icmc.gazetteer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AgreeGraphEvent extends GwtEvent<AgreeGraphEventHandler> {
	  public static Type<AgreeGraphEventHandler> TYPE = new Type<AgreeGraphEventHandler>();
	  
	  @Override
	  public Type<AgreeGraphEventHandler> getAssociatedType() {
	    return TYPE;
	  }

	  @Override
	  protected void dispatch(AgreeGraphEventHandler handler) {
	    handler.agreeGraphSubmit(this);
	  }
	}
