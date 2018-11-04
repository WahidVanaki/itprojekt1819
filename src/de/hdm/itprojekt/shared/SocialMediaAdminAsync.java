package de.hdm.itprojekt.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojekt.shared.bo.Nutzer;

public interface SocialMediaAdminAsync {
	
	void init(AsyncCallback<Void> callback);
	
	void createNutzer(String email, AsyncCallback<Nutzer> callback);

	void checkEmail(String email, AsyncCallback<Nutzer> callback);

}
