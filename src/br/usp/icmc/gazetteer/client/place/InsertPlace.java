package br.usp.icmc.gazetteer.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class InsertPlace extends Place {
	
	private String helloName="insert";
	
	public InsertPlace(String token)
	{
		this.helloName = token;
	}

	public String getHelloName()
	{
		return helloName;
	}

	public static class Tokenizer implements PlaceTokenizer<InsertPlace>
	{

		@Override
		public String getToken(InsertPlace place)
		{
			return place.getHelloName();
		}

		@Override
		public InsertPlace getPlace(String token)
		{
			return new InsertPlace(token);
		}

	}

}