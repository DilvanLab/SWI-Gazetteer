package br.usp.icmc.gazetteer.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LinkedDataPlace extends Place {
	
	public static String URL="LinkedDataPlace:";
	
	private String token;
	
	
	public LinkedDataPlace(String token)
	{
		this.token = token;
	}

	public String getHelloName()
	{
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<LinkedDataPlace>
	{

		@Override
		public String getToken(LinkedDataPlace place)
		{
			return place.getHelloName();
		}

		@Override
		public LinkedDataPlace getPlace(String token)
		{
			return new LinkedDataPlace(token);
		}

	}

}