package br.usp.icmc.gazetteer.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface GraphView  extends IsWidget
{
	void setValues( int i, int j);
	void setPresenter(Presenter listener);

	public interface Presenter
	{
		void submitAgree();
		void goTo(Place place);
	}

	void initialize();
	boolean trust();
}
