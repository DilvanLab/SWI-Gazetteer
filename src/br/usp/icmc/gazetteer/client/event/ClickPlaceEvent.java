package br.usp.icmc.gazetteer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ClickPlaceEvent  extends GwtEvent<ClickPlaceEventHandler> {
	  public static Type<ClickPlaceEventHandler> TYPE = new Type<ClickPlaceEventHandler>();
	  
	  @Override
	  public Type<ClickPlaceEventHandler> getAssociatedType() {
	    return TYPE;
	  }

	  @Override
	  protected void dispatch(ClickPlaceEventHandler handler) {
	    handler.showInfo(this);
	  }
	}
