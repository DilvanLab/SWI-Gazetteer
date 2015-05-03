package br.usp.icmc.gazetteer.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class PlaceInformationPlace extends Place {
	
	private String helloName="info";
	
	public PlaceInformationPlace(String token)
	{
		this.helloName = token;
	}

	public String getHelloName()
	{
		return helloName;
	}

	public static class Tokenizer implements PlaceTokenizer<PlaceInformationPlace>
	{

		@Override
		public String getToken(PlaceInformationPlace place)
		{
			return place.getHelloName();
		}

		@Override
		public PlaceInformationPlace getPlace(String token)
		{
			return new PlaceInformationPlace(token);
		}

	}

}