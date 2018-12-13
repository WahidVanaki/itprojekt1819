package de.hdm.itprojekt.client;

import java.io.Serializable;

import de.hdm.itprojekt.shared.bo.Kommentar;
import de.hdm.itprojekt.shared.bo.Nutzer;

public class KommentarNutzerWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Kommentar kommentar = new Kommentar();
	private Nutzer nutzer = new Nutzer();

	public KommentarNutzerWrapper() {

	}

	public KommentarNutzerWrapper(Kommentar kommentar, Nutzer nutzer) {
		this.setKommentar(kommentar);
		this.setNutzer(nutzer);
	}

	public Kommentar getKommentar() {
		return kommentar;
	}

	public void setKommentar(Kommentar kommentar) {
		this.kommentar = kommentar;
	}

	public Nutzer getNutzer() {
		return nutzer;
	}

	public void setNutzer(Nutzer nutzer) {
		this.nutzer = nutzer;
	}

}
