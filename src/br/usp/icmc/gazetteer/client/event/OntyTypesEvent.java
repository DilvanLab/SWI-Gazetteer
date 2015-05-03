package br.usp.icmc.gazetteer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class OntyTypesEvent extends GwtEvent<OntyTypesEventHandler> {
	  public static Type<OntyTypesEventHandler> TYPE = new Type<OntyTypesEventHandler>();
	  
	  @Override
	  public Type<OntyTypesEventHandler> getAssociatedType() {
	    return TYPE;
	  }

	  @Override
	  protected void dispatch(OntyTypesEventHandler handler) {
		  handler.getOntTypes(this);
	  }
	}