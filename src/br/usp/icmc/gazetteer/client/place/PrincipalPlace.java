package br.usp.icmc.gazetteer.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class PrincipalPlace extends Place {
	
	private String helloName="home";
	
	public PrincipalPlace(String token)
	{
		this.helloName = token;
	}

	public String getHelloName()
	{
		return helloName;
	}

	public static class Tokenizer implements PlaceTokenizer<PrincipalPlace>
	{

		@Override
		public String getToken(PrincipalPlace place)
		{
			return place.getHelloName();
		}

		@Override
		public PrincipalPlace getPlace(String token)
		{
			return new PrincipalPlace(token);
		}

	}

}
