package br.usp.icmc.gazetteer.client.view;

import br.usp.icmc.gazetteer.client.view.FooterView.Presenter;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface LinkedDataView extends IsWidget
{
	void setName(String helloName);
	void setPresenter(Presenter listener);

	public interface Presenter
	{
		void goTo(Place place);
	}
}
