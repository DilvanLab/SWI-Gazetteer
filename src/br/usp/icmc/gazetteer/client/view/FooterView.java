package br.usp.icmc.gazetteer.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface FooterView  extends IsWidget
{
	void setName(String helloName);
	void setPresenter(Presenter listener);

	public interface Presenter
	{
		void goTo(Place place);
		void InfoServer();
	}
	
	void callInfoServer();
	void passInfo(String[] info);
}
