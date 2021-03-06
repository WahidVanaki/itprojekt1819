package de.hdm.itprojekt.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojekt.client.KommentarNutzerWrapper;
import de.hdm.itprojekt.shared.bo.Abonnement;
import de.hdm.itprojekt.shared.bo.Kommentar;
import de.hdm.itprojekt.shared.bo.Nutzer;
import de.hdm.itprojekt.shared.bo.Pinnwand;
import de.hdm.itprojekt.shared.bo.Textbeitrag;

@RemoteServiceRelativePath("pinnwand")
public interface SocialMediaAdmin extends RemoteService {

	void init() throws IllegalArgumentException;

	public Nutzer checkEmail(String email) throws IllegalArgumentException;

	public Nutzer createNutzer(String email, String vorname, String nachname, String nickname)
			throws IllegalArgumentException;

	public Textbeitrag createTextbeitrag(int nutzerid, String inhalt) throws IllegalArgumentException;

	Pinnwand createPinnwand(int nutzerid) throws IllegalArgumentException;

	void saveTextbeitrag(Textbeitrag textbeitrag) throws IllegalArgumentException;

	void saveKommentar(Kommentar kommentar) throws IllegalArgumentException;

	void saveAbonnement(Abonnement abonnement) throws IllegalArgumentException;

	void deleteNutzer(Nutzer nutzer) throws IllegalArgumentException;

	Vector<Abonnement> findAbonnementByNutzerID(int nutzerid) throws IllegalArgumentException;

	Vector<Nutzer> findAllNutzer() throws IllegalArgumentException;

	Nutzer findNutzerByID(int nutzerid) throws IllegalArgumentException;

	Vector<Pinnwand> findAllPinnwand() throws IllegalArgumentException;

	Pinnwand findPinnwandByNutzerID(int nutzerid) throws IllegalArgumentException;

	Vector<Textbeitrag> findTextbeitragByNutzerID(int nutzerid) throws IllegalArgumentException;

	Vector<Textbeitrag> findTextbeitragByPinnwandID(int pinnwandid) throws IllegalArgumentException;

	void deleteTextbeitrag(Textbeitrag textbeitrag) throws IllegalArgumentException;

	void deleteKommentar(Kommentar kommentar) throws IllegalArgumentException;

	void deletePinnwand(Pinnwand pinnwand) throws IllegalArgumentException;

	void saveNutzer(Nutzer nutzer) throws IllegalArgumentException;

	Vector<Nutzer> findNutzerByAbo(int nutzerid) throws IllegalArgumentException;

	void deleteAbonnement(int nutzerid, int pinnwandid) throws IllegalArgumentException;

	Vector<Nutzer> findAllNutzerById() throws IllegalArgumentException;

	Abonnement createAbonnement(int nutzerid, int pinnwandid) throws IllegalArgumentException;

	Abonnement findAllAbonnement(int nutzerid, int pinnwandid) throws IllegalArgumentException;

	Vector<KommentarNutzerWrapper> findKommentarByTextbeitragId(int textbeitragid) throws IllegalArgumentException;

	Kommentar createKommentar(int textbeitragid, int nutzerid, String inhalt) throws IllegalArgumentException;

}
