package br.usp.icmc.gazetteer.client.mvp;

import br.usp.icmc.gazetteer.client.ClientFactory;
import br.usp.icmc.gazetteer.client.place.FooterPlace;
import br.usp.icmc.gazetteer.client.place.GraphPlace;
import br.usp.icmc.gazetteer.client.place.InsertPlace;
import br.usp.icmc.gazetteer.client.place.LinkedDataPlace;
import br.usp.icmc.gazetteer.client.place.MapaPlace;
import br.usp.icmc.gazetteer.client.place.PlaceInformationPlace;
import br.usp.icmc.gazetteer.client.place.PrincipalPlace;
import br.usp.icmc.gazetteer.client.place.SearchPlace;
import br.usp.icmc.gazetteer.client.place.TablePlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	/**
	 * AppActivityMapper associates each Place with its corresponding
	 * {@link Activity}
	 * 
	 * @param clientFactory
	 *            Factory to be passed to activities
	 */
	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	/**
	 * Map each Place to its corresponding Activity. This would be a great use
	 * for GIN.
	 */
	@Override
	public Activity getActivity(Place place) {
		// This is begging for GIN
	/*	if (place instanceof MapaPlace)
			return new MapaActivity((MapaPlace) place, clientFactory);
		
		 if (place instanceof GraphPlace)
			return new GraphActivity((GraphPlace) place, clientFactory);
		
		if (place instanceof InsertPlace)
			return new InsertActivity((InsertPlace) place, clientFactory);
		
		if (place instanceof PlaceInformationPlace)
			return new PlaceInformationActivity((PlaceInformationPlace) place, clientFactory);
		*/
		 if (place instanceof LinkedDataPlace)
			return new LinkedDataActivity((LinkedDataPlace) place, clientFactory);
		
//		if (place instanceof TablePlace)
//			return new TableActivity((TablePlace) place, clientFactory);
//		
//		 if (place instanceof SearchPlace)
//			return new SearchActivity((SearchPlace) place, clientFactory);
//		
//		if (place instanceof FooterPlace)
//			return new FooterActivity((FooterPlace) place, clientFactory);
//		
		 if (place instanceof PrincipalPlace)
			return new PrincipalActivity((PrincipalPlace) place, clientFactory);
		
		return null;
	}

}
