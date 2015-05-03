package br.usp.icmc.gazetteer.client.view;

import java.util.List;

import br.usp.icmc.gazetteer.client.view.FooterView.Presenter;
import br.usp.icmc.gazetteer.client.view.impl.InsertPlace;
import br.usp.icmc.gazetteer.shared.Locality;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface InsertPlaceView
{
	
	public interface Presenter<T>{
			void onSubmit_coordClick();		
			void TypeServer();
//			void getClickedOntyType();
	}
    InsertPlace getInsertPlace();
	void setPresenter(Presenter presenter);
	Widget asWidget();
	void callOntTypeServer();
	void OntFromServer(List<String> result);
	void insertPlaceinfo(Locality locality);
}
