package br.usp.icmc.gazetteer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class FindPlacesEvent extends GwtEvent<FindPlacesEventHandler> {
	  public static Type<FindPlacesEventHandler> TYPE = new Type<FindPlacesEventHandler>();
	  
	  @Override
	  public Type<FindPlacesEventHandler> getAssociatedType() {
	    return TYPE;
	  }

	  @Override
	  protected void dispatch(FindPlacesEventHandler handler) {
	    handler.onFindPlaces(this);
	  }
	}
