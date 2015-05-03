package br.usp.icmc.gazetteer.client.mvp;

import br.usp.icmc.gazetteer.client.place.FooterPlace;
import br.usp.icmc.gazetteer.client.place.GraphPlace;
import br.usp.icmc.gazetteer.client.place.InsertPlace;
import br.usp.icmc.gazetteer.client.place.LinkedDataPlace;
import br.usp.icmc.gazetteer.client.place.MapaPlace;
import br.usp.icmc.gazetteer.client.place.PlaceInformationPlace;
import br.usp.icmc.gazetteer.client.place.PrincipalPlace;
import br.usp.icmc.gazetteer.client.place.SearchPlace;
import br.usp.icmc.gazetteer.client.place.TablePlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

/**
 * PlaceHistoryMapper interface is used to attach all places which the
 * PlaceHistoryHandler should be aware of. This is done via the @WithTokenizers
 * annotation or by extending PlaceHistoryMapperWithFactory and creating a
 * separate TokenizerFactory.
 */
@WithTokenizers( { PrincipalPlace.Tokenizer.class, FooterPlace.Tokenizer.class, GraphPlace.Tokenizer.class, 
	InsertPlace.Tokenizer.class, LinkedDataPlace.Tokenizer.class, MapaPlace.Tokenizer.class, 
	PlaceInformationPlace.Tokenizer.class, SearchPlace.Tokenizer.class, TablePlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
