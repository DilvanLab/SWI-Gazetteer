package br.usp.icmc.gazetteer.client.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.gwtopenmaps.openlayers.client.geometry.Point;

import br.usp.icmc.gazetteer.client.view.FooterView.Presenter;
import br.usp.icmc.gazetteer.shared.Locality;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;

public interface MapaView  extends IsWidget
{
	void setName(String helloName);
	void setPresenter(Presenter listener);
	HorizontalPanel getComponets();
	public interface Presenter
	{
		void goTo(Place place);

		void cluster();
	}
	void clear();
	void buildMap();
	String getGeometry();
	void PointsFromServer(List<Float[]> result);
	void callServerPoints();
	void showGeometry(Locality locality);
}
