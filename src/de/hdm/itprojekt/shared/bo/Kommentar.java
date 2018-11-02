package de.hdm.itprojekt.shared.bo;

import java.util.Date;

public class Kommentar extends BusinessObject {

	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;

	private String inhaltKommentar ="";
	
	private Date erzeugungsdatum =null;
	
	private Date modifikationsdatum =null;
	
	private int nutzerID =0;
	
	private int textbeitragID =0;

	public String getInhaltKommentar() {
		return inhaltKommentar;
	}

	public void setInhaltKommentar(String inhaltKommentar) {
		this.inhaltKommentar = inhaltKommentar;
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

	public int getTextbeitragID() {
		return textbeitragID;
	}

	public void setTextbeitragID(int textbeitragID) {
		this.textbeitragID = textbeitragID;
	}
	
	
}
