package de.hdm.itprojekt.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojekt.shared.bo.Abonnement;
import de.hdm.itprojekt.shared.bo.Kommentar;
import de.hdm.itprojekt.shared.bo.Nutzer;
import de.hdm.itprojekt.shared.bo.Pinnwand;
import de.hdm.itprojekt.shared.bo.Textbeitrag;

@RemoteServiceRelativePath("pinnwand")
public interface SocialMediaAdmin extends RemoteService {

	void init() throws IllegalArgumentException;

//	Nutzer createNutzer(String email) throws IllegalArgumentException;
	
	public Nutzer checkEmail(String email) throws IllegalArgumentException;

	Nutzer createNutzer(String email, String vorname, String nachname, String nickname) throws IllegalArgumentException;

	public void saveNutzer(Nutzer nutzer) throws IllegalArgumentException;
	
	Abonnement createAbonnement(int nutzerid, int pinnwandid) throws IllegalArgumentException;

	Kommentar createKommentar(int nutzerid, int textbeitragid, String inhalt) throws IllegalArgumentException;

	Textbeitrag createTextbeitrag(int pinnwandid, int nutzerid, int kommentarid, String inhalt) throws IllegalArgumentException;

	Pinnwand createPinnwand(int nutzerid) throws IllegalArgumentException;

	void savePinnwand(Pinnwand pinnwand) throws IllegalArgumentException;

	void saveTextbeitrag(Textbeitrag textbeitrag) throws IllegalArgumentException;

	void saveKommentar(Kommentar kommentar) throws IllegalArgumentException;

	void saveAbonnement(Abonnement abonnement) throws IllegalArgumentException;

	void deleteNutzer(Nutzer nutzer) throws IllegalArgumentException;

	Vector<Abonnement> findAllAbonnement() throws IllegalArgumentException;

	Vector<Abonnement> findAbonnementByNutzerID(int nutzerid) throws IllegalArgumentException;

	Vector<Abonnement> findAbonnementByPinnwandID(int pinnwandid) throws IllegalArgumentException;

	Vector<Nutzer> findAllNutzer() throws IllegalArgumentException;

	Nutzer findNutzerByEmail(String email) throws IllegalArgumentException;

	Nutzer findNutzerByID(int nutzerid) throws IllegalArgumentException;

	Vector<Pinnwand> findAllPinnwand() throws IllegalArgumentException;

	Pinnwand findPinnwandByNutzerID(int nutzerid) throws IllegalArgumentException;

	Vector<Kommentar> findAllKommentare() throws IllegalArgumentException;

	Vector<Kommentar> findKommentarByNutzerID(int nutzerid) throws IllegalArgumentException;

	Vector<Kommentar> findKommentarByTextbeitragID(int textbeitragid) throws IllegalArgumentException;

	Vector<Textbeitrag> findAllTextbeitrag() throws IllegalArgumentException;

	Vector<Textbeitrag> findTextbeitragByNutzerID(int nutzerid) throws IllegalArgumentException;

	Vector<Textbeitrag> findTextbeitragByKommentarID(int kommentarid) throws IllegalArgumentException;

	Vector<Textbeitrag> findTextbeitragByPinnwandID(int pinnwandid) throws IllegalArgumentException;

}
