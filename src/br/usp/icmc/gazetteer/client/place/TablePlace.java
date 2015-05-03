package br.usp.icmc.gazetteer.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class TablePlace extends Place {
	
	private String helloName="search";
	
	public TablePlace(String token)
	{
		this.helloName = token;
	}

	public String getHelloName()
	{
		return helloName;
	}

	public static class Tokenizer implements PlaceTokenizer<TablePlace>
	{

		@Override
		public String getToken(TablePlace place)
		{
			return place.getHelloName();
		}

		@Override
		public TablePlace getPlace(String token)
		{
			return new TablePlace(token);
		}

	}

}