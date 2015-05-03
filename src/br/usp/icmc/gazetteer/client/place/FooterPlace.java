package br.usp.icmc.gazetteer.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class FooterPlace extends Place {
	
	private String helloName="footer";
	
	public FooterPlace(String token)
	{
		this.helloName = token;
	}

	public String getHelloName()
	{
		return helloName;
	}

	public static class Tokenizer implements PlaceTokenizer<FooterPlace>
	{

		@Override
		public String getToken(FooterPlace place)
		{
			return place.getHelloName();
		}

		@Override
		public FooterPlace getPlace(String token)
		{
			return new FooterPlace(token);
		}

	}

}