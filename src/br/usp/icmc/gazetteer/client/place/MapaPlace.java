package br.usp.icmc.gazetteer.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MapaPlace extends Place {
	
	private String helloName="mapa";
	
	public MapaPlace(String token)
	{
		this.helloName = token;
	}

	public String getHelloName()
	{
		return helloName;
	}

	public static class Tokenizer implements PlaceTokenizer<MapaPlace>
	{

		@Override
		public String getToken(MapaPlace place)
		{
			return place.getHelloName();
		}

		@Override
		public MapaPlace getPlace(String token)
		{
			return new MapaPlace(token);
		}

	}

}