package de.hdm.itprojekt.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojekt.shared.bo.Nutzer;

@RemoteServiceRelativePath("pinnwand")
public interface SocialMediaAdmin extends RemoteService {

	void init() throws IllegalArgumentException;

//	Nutzer createNutzer(String email) throws IllegalArgumentException;
	
	public Nutzer checkEmail(String email) throws IllegalArgumentException;

	Nutzer createNutzer(String email, String vorname, String nachname, String nickname) throws IllegalArgumentException;

	public void saveNutzer(Nutzer nutzer) throws IllegalArgumentException;
}
