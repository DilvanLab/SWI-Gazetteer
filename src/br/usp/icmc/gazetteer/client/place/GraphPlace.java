package br.usp.icmc.gazetteer.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class GraphPlace extends Place {
	
	private String helloName="graph";
	
	public GraphPlace(String token)
	{
		this.helloName = token;
	}

	public String getHelloName()
	{
		return helloName;
	}

	public static class Tokenizer implements PlaceTokenizer<GraphPlace>
	{

		@Override
		public String getToken(GraphPlace place)
		{
			return place.getHelloName();
		}

		@Override
		public GraphPlace getPlace(String token)
		{
			return new GraphPlace(token);
		}

	}

}
