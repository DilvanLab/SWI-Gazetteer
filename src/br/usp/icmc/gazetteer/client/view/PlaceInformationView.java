package br.usp.icmc.gazetteer.client.view;

import br.usp.icmc.gazetteer.client.view.FooterView.Presenter;
import br.usp.icmc.gazetteer.shared.Locality;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface PlaceInformationView  extends IsWidget
{
	void setName(String helloName);
	void setPresenter(Presenter listener);
	void setInfomation(Locality locality);
	
	public interface Presenter
	{
		
		void goTo(Place place);
	}
}
