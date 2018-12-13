package de.hdm.itprojekt.client;

import com.google.gwt.core.client.GWT;

import de.hdm.itprojekt.shared.CommonSettings;
import de.hdm.itprojekt.shared.SocialMediaAdmin;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;

public class ClientsideSettings extends CommonSettings {

	private static SocialMediaAdminAsync socialMediaVerwaltung = null;

	public static SocialMediaAdminAsync getSocialMediaVerwaltung() {
		if (socialMediaVerwaltung == null) {
			socialMediaVerwaltung = GWT.create(SocialMediaAdmin.class);
		}
		return socialMediaVerwaltung;
	}

}
