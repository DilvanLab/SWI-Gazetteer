package br.usp.icmc.gazetteer.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SearchPlace extends Place {
	
	private String helloName="search";
	
	public SearchPlace(String token)
	{
		this.helloName = token;
	}

	public String getHelloName()
	{
		return helloName;
	}

	public static class Tokenizer implements PlaceTokenizer<SearchPlace>
	{

		@Override
		public String getToken(SearchPlace place)
		{
			return place.getHelloName();
		}

		@Override
		public SearchPlace getPlace(String token)
		{
			return new SearchPlace(token);
		}

	}

}