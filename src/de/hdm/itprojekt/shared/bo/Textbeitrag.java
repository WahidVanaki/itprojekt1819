package de.hdm.itprojekt.shared.bo;

import java.util.Date;

public class Textbeitrag extends BusinessObject {

	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;

	private String inhalt ="";
	
	private Date erzeugungsdatum =null;
	
	private Date modifikationsdatum =null;
	
	private int nutzerID =0;
	
	private int pinnwandID =0;
	
	private int likesID =0;
	
	private int kommentarID =0;

	public String getInhalt() {
		return inhalt;
	}

	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}

	public Date getErzeugungsdatum() {
		return erzeugungsdatum;
	}

	public void setErzeugungsdatum(Date erzeugungsdatum) {
		this.erzeugungsdatum = erzeugungsdatum;
	}

	public Date getModifikationsdatum() {
		return modifikationsdatum;
	}

	public void setModifikationsdatum(Date modifikationsdatum) {
		this.modifikationsdatum = modifikationsdatum;
	}

	public int getNutzerID() {
		return nutzerID;
	}

	public void setNutzerID(int nutzerID) {
		this.nutzerID = nutzerID;
	}

	public int getPinnwandID() {
		return pinnwandID;
	}

	public void setPinnwandID(int pinnwandID) {
		this.pinnwandID = pinnwandID;
	}

	public int getLikesID() {
		return likesID;
	}

	public void setLikesID(int likesID) {
		this.likesID = likesID;
	}

	public int getKommentarID() {
		return kommentarID;
	}

	public void setKommentarID(int kommentarID) {
		this.kommentarID = kommentarID;
	}
	
	
}
