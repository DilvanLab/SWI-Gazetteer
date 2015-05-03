package br.usp.icmc.gazetteer.client.view;

import java.util.ArrayList;
import java.util.List;

import br.usp.icmc.gazetteer.client.view.FooterView.Presenter;
import br.usp.icmc.gazetteer.shared.Locality;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface TableView  extends IsWidget
{
	void setName(String helloName);
	void setPresenter(Presenter listener);
	
	public interface Presenter
	{
		void findPlaces();
		void clickPlace(Locality locality);
		void goTo(Place place);
	}

	void preenche(List<Locality> localities);
}
