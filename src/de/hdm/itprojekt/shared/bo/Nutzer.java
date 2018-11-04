package de.hdm.itprojekt.shared.bo;

import de.hdm.itprojekt.shared.bo.BusinessObject;

public class Nutzer extends BusinessObject {

	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;
	
	private String email ="";
	
	private String nachname ="";
	
	private String vorname ="";
	
	private String nickname ="";
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String toString(){
		return "<table><tr><td>"+this.getEmail()+"</td></tr></table>";
	}
	
}
