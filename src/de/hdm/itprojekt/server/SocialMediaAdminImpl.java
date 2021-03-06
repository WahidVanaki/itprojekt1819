package de.hdm.itprojekt.server;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojekt.client.KommentarNutzerWrapper;
import de.hdm.itprojekt.server.db.AbonnementMapper;
import de.hdm.itprojekt.server.db.KommentarMapper;
import de.hdm.itprojekt.server.db.NutzerMapper;
import de.hdm.itprojekt.server.db.PinnwandMapper;
import de.hdm.itprojekt.server.db.TextbeitragMapper;
import de.hdm.itprojekt.shared.SocialMediaAdmin;
import de.hdm.itprojekt.shared.bo.Abonnement;
import de.hdm.itprojekt.shared.bo.Kommentar;
import de.hdm.itprojekt.shared.bo.Nutzer;
import de.hdm.itprojekt.shared.bo.Pinnwand;
import de.hdm.itprojekt.shared.bo.Textbeitrag;

/**
 * Diese Klasse ist die Implementierungsklasse des Interface SocialMediaAdmin.
 * Hier befindet sich die Applikationslogik.
 * 
 * @see SocialMediaAdmin
 * @see SocialMediaAdminAsync
 * @see RemoteServiceServlet
 */
@SuppressWarnings("serial")
public class SocialMediaAdminImpl extends RemoteServiceServlet implements SocialMediaAdmin {

	/**
	 * Referenz auf den KommentarMapper, der Kommentarobjekte mit der Datenbank
	 * abgleicht.
	 */
	private KommentarMapper kommentarMapper = null;
	/**
	 * Referenz auf den NutzerMapper, der Nutzerobjekte mit der Datenbank
	 * abgleicht.
	 */
	private NutzerMapper nutzerMapper = null;
	/**
	 * Referenz auf den PinnwandMapper, der Pinnwandobjekte mit der Datenbank
	 * abgleicht.
	 */
	private PinnwandMapper pinnwandMapper = null;
	/**
	 * Referenz auf den TextbeitragMapper, der Textbeitragobjekte mit der
	 * Datenbank abgleicht.
	 */
	private TextbeitragMapper textbeitragMapper = null;
	/**
	 * Referenz auf den AbonnementMapper, der Abonnementobjekte mit der
	 * Datenbank abgleicht.
	 */
	private AbonnementMapper abonnementMapper = null;

	/**
	 * Ein No-Argument-Konstruktor für die Client-seitige Erzeugung von
	 * GWT.create
	 * 
	 * @see #init()
	 * @throws IllegalArgumentException
	 */
	public SocialMediaAdminImpl() throws IllegalArgumentException {

	}

	/**
	 * Initialsierungsmethode. Diese Methode muss für jede Instanz von
	 * SocialMediaAdminImpl aufgerufen werden.
	 */
	@Override
	public void init() throws IllegalArgumentException {
		this.kommentarMapper = KommentarMapper.kommentarMapper();
		this.nutzerMapper = NutzerMapper.nutzerMapper();
		this.pinnwandMapper = PinnwandMapper.pinnwandMapper();
		this.textbeitragMapper = TextbeitragMapper.textbeitragMapper();
		this.abonnementMapper = AbonnementMapper.abonnementMapper();

	}

	/**
	 * Anlegen eines Nutzers
	 * 
	 * @param email;
	 *            ist die Google E-Mail Adresse des Nutzers
	 * @return Nutzer; Zurückgegeben wird ein Objekt der Klasse Nutzer
	 */
	@Override
	public Nutzer createNutzer(String email, String vorname, String nachname, String nickname)
			throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer.setEmail(email);
		nutzer.setVorname(vorname);
		nutzer.setNachname(nachname);
		nutzer.setNickname(nickname);
		return this.nutzerMapper.createNutzer(nutzer);
	}

	@Override
	public Pinnwand createPinnwand(int nutzerID) throws IllegalArgumentException {
		Pinnwand pinnwand = new Pinnwand();
		pinnwand.setNutzerID(nutzerID);

		return this.pinnwandMapper.createPinnwand(pinnwand);
	}

	@Override
	public Nutzer checkEmail(String email) throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer = this.nutzerMapper.findNutzerByEmail(email);

		if (nutzer.getId() == 0) {
			return null;
		} else {
			return nutzer;
		}
	}

	@Override
	public Vector<Abonnement> findAbonnementByNutzerID(int nutzerID) throws IllegalArgumentException {
		return this.abonnementMapper.findAbonnementByNutzerID(nutzerID);

	}

	@Override
	public Vector<Pinnwand> findAllPinnwand() throws IllegalArgumentException {
		return this.pinnwandMapper.findAllPinnwand();
	}

	@Override
	public Vector<Nutzer> findNutzerByAbo(int nutzerID) throws IllegalArgumentException {

		Vector<Abonnement> aboVector = findAbonnementByNutzerID(nutzerID);

		Vector<Pinnwand> pwVector = findAllPinnwand();

		Vector<Nutzer> nutzerVector = new Vector<Nutzer>();

		nutzerVector.add(findNutzerByID(nutzerID));

		for (int i = 0; i < aboVector.size(); i++) {
			for (int o = 0; o < pwVector.size(); o++) {
				if (aboVector.elementAt(i).getNutzerID() == pwVector.elementAt(o).getNutzerID())
					;
				{
					nutzerVector.add(findNutzerByID(aboVector.elementAt(i).getPinnwandID()));
					break;
				}

			}
		}

		return nutzerVector;
	}

	@Override
	public Nutzer findNutzerByID(int nutzerid) {
		return this.nutzerMapper.findNutzerById(nutzerid);
	}

	@Override
	public Textbeitrag createTextbeitrag(int nutzerid, String inhalt) throws IllegalArgumentException {
		Textbeitrag textbeitrag = new Textbeitrag();
		textbeitrag.setPinnwandID(nutzerid);
		textbeitrag.setNutzerID(nutzerid);
		textbeitrag.setInhalt(inhalt);
		textbeitrag.setErzeugungsdatum(new Date());
		return this.textbeitragMapper.createTextbeitrag(textbeitrag);
	}

	@Override
	public Abonnement createAbonnement(int nutzerID, int pinnwandID) throws IllegalArgumentException {
		Abonnement abonnement = new Abonnement();
		abonnement.setNutzerID(nutzerID);
		abonnement.setPinnwandID(pinnwandID);

		Abonnement abo = new Abonnement();
		abo = this.abonnementMapper.findAllAbonnement(nutzerID, pinnwandID);

		if (abo.getId() != 0) {

			return null;
		} else {

			return this.abonnementMapper.createAbonnement(abonnement);
		}
	}

	@Override
	public void deleteNutzer(Nutzer nutzer) throws IllegalArgumentException {
		Pinnwand pn = new Pinnwand();
		pn.setNutzerID(nutzer.getId());

		this.pinnwandMapper.deletePinnwandNutzerID(pn);

		this.nutzerMapper.deleteNutzer(nutzer);
	}

	@Override
	public void deletePinnwand(Pinnwand pinnwand) throws IllegalArgumentException {
		pinnwand.setId(pinnwand.getId());

		this.pinnwandMapper.deletePinnwand(pinnwand);
	}

	@Override
	public void saveNutzer(Nutzer nutzer) throws IllegalArgumentException {
		this.nutzerMapper.updateNutzer(nutzer);
	}

	@Override
	public Kommentar createKommentar(int textbeitragid, int nutzerid, String inhalt) throws IllegalArgumentException {
		Kommentar kommentar = new Kommentar();
		kommentar.setTextbeitragID(textbeitragid);
		kommentar.setNutzerID(nutzerid);
		kommentar.setInhaltKommentar(inhalt);
		kommentar.setErzeugungsdatum(new Date());
		return this.kommentarMapper.createKommentar(kommentar);
		
	}

	@Override
	public void saveTextbeitrag(Textbeitrag textbeitrag) throws IllegalArgumentException {
		textbeitrag.setModifikationsdatum(new Date());
		textbeitragMapper.updateTextbeitrag(textbeitrag);
	}

	@Override
	public void saveKommentar(Kommentar kommentar) throws IllegalArgumentException {
		kommentar.setModifikationsdatum(new Date());
		kommentarMapper.updateKommentar(kommentar);
	}

	@Override
	public void saveAbonnement(Abonnement abonnement) throws IllegalArgumentException {
		this.abonnementMapper.updateAbonnement(abonnement);
	}

	@Override
	public Pinnwand findPinnwandByNutzerID(int nutzerID) {
		return this.pinnwandMapper.findPinnwandByNutzerId(nutzerID);
	}

	@Override
	public Vector<Textbeitrag> findTextbeitragByNutzerID(int nutzerid) throws IllegalArgumentException {
		return this.textbeitragMapper.findTextbeitragByNutzerID(nutzerid);
	}

	@Override
	public Vector<Textbeitrag> findTextbeitragByPinnwandID(int pinnwandid) throws IllegalArgumentException {
		return this.textbeitragMapper.findTextbeitragByPinnwandID(pinnwandid);
	}

	@Override
	public void deleteAbonnement(int nutzerid, int pinnwandid) throws IllegalArgumentException {
		Nutzer n = findNutzerByID(nutzerid);
		Abonnement abo = new Abonnement();
		abo.setNutzerID(nutzerid);
		abo.setPinnwandID(pinnwandid);

		this.abonnementMapper.deleteAbonnement(abo);

	}

	@Override
	public Vector<Nutzer> findAllNutzer() throws IllegalArgumentException {
		return this.nutzerMapper.findAllNutzer();
	}

	@Override
	public void deleteTextbeitrag(Textbeitrag textbeitrag) throws IllegalArgumentException {
		this.textbeitragMapper.deleteTextbeitrag(textbeitrag);
	}

	@Override
	public void deleteKommentar(Kommentar kommentar) throws IllegalArgumentException {
		this.kommentarMapper.deleteKommentar(kommentar);
	}

	@Override
	public Vector<Nutzer> findAllNutzerById() throws IllegalArgumentException {
		return this.nutzerMapper.findAllNutzer();
	}

	@Override
	public Abonnement findAllAbonnement(int nutzerid, int pinnwandid) throws IllegalArgumentException {
		return this.abonnementMapper.findAllAbonnement(nutzerid, pinnwandid);
	}
	
	@Override
	public Vector<KommentarNutzerWrapper> findKommentarByTextbeitragId(int textbeitragid)
			throws IllegalArgumentException {
		System.out.println("hier" + textbeitragid);
		Vector<Kommentar> kommentarVector = this.kommentarMapper.findKommentarByTextbeitragId(textbeitragid);
		Vector<KommentarNutzerWrapper> wrapperVector = new Vector<KommentarNutzerWrapper>();
		for (Kommentar kommentar : kommentarVector) {
			wrapperVector.add(new KommentarNutzerWrapper(kommentar, findNutzerByID(kommentar.getNutzerID())));
		
		}

		return wrapperVector;
		
	}
}