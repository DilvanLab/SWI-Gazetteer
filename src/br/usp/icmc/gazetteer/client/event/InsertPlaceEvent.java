package br.usp.icmc.gazetteer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class InsertPlaceEvent extends GwtEvent<InsertPlaceEventHandler> {
	  public static Type<InsertPlaceEventHandler> TYPE = new Type<InsertPlaceEventHandler>();
	  
	  @Override
	  public Type<InsertPlaceEventHandler> getAssociatedType() {
	    return TYPE;
	  }

	  @Override
	  protected void dispatch(InsertPlaceEventHandler handler) {
	    handler.onInsertPlace(this);
	  }
	}
