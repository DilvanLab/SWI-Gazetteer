package br.usp.icmc.gazetteer.client;

import java.util.List;

import br.usp.icmc.gazetteer.shared.Locality;
import br.usp.icmc.gazetteer.shared.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GazetteerServiceAsync {


	void agreeLinkedData(Locality locality, AsyncCallback<Void> callback);


	void findPlacesWithOutCoord(AsyncCallback<List<Locality>> asyncCallback);


	void getUser(String login, String senha, AsyncCallback<User> callback);

	void insertLocality(Locality locality, AsyncCallback<Integer> asyncCallback);

	void insertUser(String[] user, AsyncCallback<Void> callback);

	void searchLocalities(String search, AsyncCallback<List<Locality>> callback);

	void updateUser(String[] user, AsyncCallback<Void> callback);

	void getPolygons(AsyncCallback<List<Float[]>> callback);

	void getOntTypes(AsyncCallback<List<String>> callback);

	void infoServer(AsyncCallback<String[]> callback);



}
