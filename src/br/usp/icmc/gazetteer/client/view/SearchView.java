package br.usp.icmc.gazetteer.client.view;

import br.usp.icmc.gazetteer.client.view.impl.Search;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface SearchView  extends IsWidget
{
	void setName(String helloName);
	void setPresenter(Presenter listener);
	
	public interface Presenter
	{
		void showPlaceTable();
		void goTo(Place place);
	}
	Search getSearch();
}
