package br.usp.icmc.gazetteer.client.view;

import br.usp.icmc.gazetteer.client.view.FooterView.Presenter;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public interface PrincipalView  extends IsWidget
{

	SimplePanel getSearch();
	SimplePanel getMenu();
	Image getLogo();
//	HorizontalPanel getUser();
	SimplePanel getTable();
	SimplePanel getInfo();
	SimplePanel getMap();
	SimplePanel getButtonMap();
	SimplePanel getInsertAndGraph();
//	String usuario();
//	String senha();
	void setPresenter(Presenter listener);

	public interface Presenter
	{
		void goTo(Place place);

//		void onButtonClick();
		
	}
}
