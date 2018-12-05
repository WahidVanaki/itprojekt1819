package de.hdm.itprojekt.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojekt.shared.bo.Abonnement;
import de.hdm.itprojekt.shared.bo.Kommentar;
import de.hdm.itprojekt.shared.bo.Nutzer;
import de.hdm.itprojekt.shared.bo.Pinnwand;
import de.hdm.itprojekt.shared.bo.Textbeitrag;

public interface SocialMediaAdminAsync {
	
	void init(AsyncCallback<Void> callback);
	
	void createNutzer(String email, String vorname, String nachname, String nickname, AsyncCallback<Nutzer> callback);

	void checkEmail(String email, AsyncCallback<Nutzer> callback);


//	void createAbonnement(int nutzerid, int pinnwandid, AsyncCallback<Abonnement> callback);

	void createKommentar(int nutzerid, int textbeitragid, String inhalt, AsyncCallback<Kommentar> callback);

	void createTextbeitrag(int nutzerid, String inhalt,
			AsyncCallback<Textbeitrag> callback);

	void createPinnwand(int nutzerid, AsyncCallback<Pinnwand> callback);

	void saveTextbeitrag(Textbeitrag textbeitrag, AsyncCallback<Void> callback);
//
	void saveKommentar(Kommentar kommentar, AsyncCallback<Void> callback);

	void saveAbonnement(Abonnement abonnement, AsyncCallback<Void> callback);

	void deleteNutzer(Nutzer nutzer, AsyncCallback<Void> callback);

	void findAbonnementByNutzerID(int nutzerid, AsyncCallback<Vector<Abonnement>> callback);


	void findAllNutzer(AsyncCallback<Vector<Nutzer>> callback);

//	void findNutzerByEmail(String email, AsyncCallback<Nutzer> callback);

	void findNutzerByID(int nutzerid, AsyncCallback<Nutzer> callback);

	void findAllPinnwand(AsyncCallback<Vector<Pinnwand>> callback);

	void findPinnwandByNutzerID(int nutzerid, AsyncCallback<Pinnwand> callback);

//	void findAllKommentare(AsyncCallback<Vector<Kommentar>> callback);

//	void findKommentarByNutzerID(int nutzerid, AsyncCallback<Vector<Kommentar>> callback);
//
	void findKommentarByTextbeitragID(int textbeitragid, AsyncCallback<Vector<Kommentar>> callback);
//
//	void findAllTextbeitrag(AsyncCallback<Vector<Textbeitrag>> callback);
//
	void findTextbeitragByNutzerID(int nutzerid, AsyncCallback<Vector<Textbeitrag>> callback);
	
	void findTextbeitragByPinnwandID(int pinnwandid, AsyncCallback<Vector<Textbeitrag>> callback);

	void deleteAbonnement(int nutzerid, int pinnwandid, AsyncCallback<Void> callback);

	void deleteTextbeitrag(Textbeitrag textbeitrag, AsyncCallback<Void> callback);

	void deleteKommentar(Kommentar kommentar, AsyncCallback<Void> callback);

	void deletePinnwand(Pinnwand pinnwand, AsyncCallback<Void> callback);

	void saveNutzer(Nutzer nutzer, AsyncCallback<Void> callback);

	void findNutzerByAbo(int nutzerid, AsyncCallback<Vector<Nutzer>> callback);

}
