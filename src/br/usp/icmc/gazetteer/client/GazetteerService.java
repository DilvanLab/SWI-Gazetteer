package br.usp.icmc.gazetteer.client;

import java.util.List;

import br.usp.icmc.gazetteer.shared.Locality;
import br.usp.icmc.gazetteer.shared.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("GazetteerService")
public interface GazetteerService extends RemoteService  {

	List<Locality> findPlacesWithOutCoord();
	List<Locality> searchLocalities(String search) throws Exception;
	
	Integer insertLocality(Locality locality);
	void agreeLinkedData(Locality locality);
	void updateUser(String [] user);
	void insertUser(String [] user);
	User getUser(String login, String senha) throws Exception;
	List<Float[]> getPolygons();
	
	List<String> getOntTypes();
	String[] infoServer();
}
