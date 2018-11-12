package de.hdm.itprojekt.client;

import java.io.Serializable;

/**
 * Login RPC Dienst - Ergebnis des Login-Dienstes soll ein Objekt der Klasse "LoginInfo" sein, in der alle
 * benötigten Elemente eines angemeldeten Nutzers verfügbar sind.
 * 
 * @version 1.0 01 November 2019
 * @author Wahid in Anlehnung an die Folien "GWT Benutzungsschnittstellen"
 *
 */
public class LoginInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * LoggedIn zur Prüfung, ob der Nutzer bereits eingeloggt ist.
	 */
	private boolean loggedIn = false;
	
	/**
	 * Abspeichern der LoginUrl
	 */
	private String loginUrl = "";
	
	/**
	 * Abspeichern der LogoutUrl
	 */
	private String logoutUrl = "";
	
	/**
	 * Abspeichern der GoogleMail Adresse
	 */
	private String emailAddress = "";
	
	private String vorname = "";
	
	private String nachname = "";
	
	/**
	 * Abspeichern des Nicknamen
	 */
	private String nickname = "";

	/**
	 * Prüfen ob user eingeloggt ist
	 * @return loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * Setzen des logins
	 * @param loggedIn
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	 * Auslesen der login url
	 * @return loginUrl
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * Setzen des loginUrl
	 * @param loginUrl
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * Auslesen des logoutsUrl
	 * @return logoutUrl
	 */
	public String getLogoutUrl() {
		return logoutUrl;
	}

	/**
	 * Setzen der LogoutUrl
	 * @param logoutUrl
	 */
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	/**
	 * Auslesen der E-Mail Adresse
	 * @return emailAdresse
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Setzen der E-Mail Adresse
	 * @param emailAddress
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Auslesen vom Nickname
	 * @return nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Setzen vom Nickname
	 * @param nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
}
