package de.hdm.itprojekt.server;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

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

@SuppressWarnings("serial")
public class SocialMediaAdminImpl extends RemoteServiceServlet implements SocialMediaAdmin{

	private NutzerMapper nutzerMapper = null;
	
	private AbonnementMapper abonnementMapper = null;
	
	private KommentarMapper kommentarMapper = null;
	
	private PinnwandMapper pinnwandMapper = null;
	
	private TextbeitragMapper textbeitragMapper = null;

	
	public SocialMediaAdminImpl() throws IllegalArgumentException{
		
	}
	
	@Override
	public void init() throws IllegalArgumentException{
		this.nutzerMapper = NutzerMapper.nutzerMapper();
		this.abonnementMapper = AbonnementMapper.abonnementMapper();
		this.kommentarMapper = KommentarMapper.kommentarMapper();
		this.pinnwandMapper = PinnwandMapper.pinnwandMapper();
		this.textbeitragMapper = TextbeitragMapper.textbeitragMapper();
		
	}
	
	@Override
	public Nutzer createNutzer(String email, String vorname, String nachname, String nickname) throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer.setEmail(email);
		nutzer.setVorname(vorname);
		nutzer.setNachname(nachname);
		nutzer.setNickname(nickname);
		return this.nutzerMapper.createNutzer(nutzer);
	}

	@Override
	public Nutzer checkEmail(String email) throws IllegalArgumentException {
		Nutzer nutzer = new Nutzer();
		nutzer = this.nutzerMapper.findNutzerByEmail(email);
		
		if (nutzer.getId() == 0){
			return null;
		}
		else {
			return nutzer;
		}
	}
	
	@Override
	public Abonnement createAbonnement(int nutzerid, int pinnwandid){
		Abonnement abo = new Abonnement();
		abo.setNutzerID(nutzerid);
		abo.setPinnwandID(pinnwandid);
		
		return this.abonnementMapper.createAbonnement(abo);
	}
	
	@Override
	public Kommentar createKommentar(int nutzerid, int textbeitragid, String inhalt) throws IllegalArgumentException {
		Kommentar kommentar = new Kommentar();
		kommentar.setNutzerID(nutzerid);
		kommentar.setTextbeitragID(textbeitragid);
		kommentar.setInhaltKommentar(inhalt);
		kommentar.setErzeugungsdatum(new Date());
		kommentar.setModifikationsdatum(new Date());
		
		return this.kommentarMapper.createKommentar(kommentar);
	}
	
	@Override
	public Textbeitrag createTextbeitrag(int pinnwandid, int nutzerid, int kommentarid, String inhalt) throws IllegalArgumentException {
		Textbeitrag textbeitrag = new Textbeitrag();
		textbeitrag.setPinnwandID(pinnwandid);
		textbeitrag.setNutzerID(nutzerid);
		textbeitrag.setKommentarID(kommentarid);
		textbeitrag.setInhalt(inhalt);
		textbeitrag.setErzeugungsdatum(new Date());
		textbeitrag.setModifikationsdatum(new Date());
		
		return this.textbeitragMapper.createTextbeitrag(textbeitrag);
	}
	
	@Override
	public Pinnwand createPinnwand(int nutzerid) throws IllegalArgumentException {
		Pinnwand pinnwand = new Pinnwand();
		pinnwand.setNutzerID(nutzerid);
		
		return this.pinnwandMapper.createPinnwand(pinnwand);
	}
	
	@Override
	public void saveNutzer(Nutzer nutzer) throws IllegalArgumentException {
		nutzerMapper.updateNutzer(nutzer);
	}
	
	@Override
	public void savePinnwand(Pinnwand pinnwand) throws IllegalArgumentException {
		pinnwandMapper.updatePinnwand(pinnwand);
	}
	
	@Override
	public void saveTextbeitrag(Textbeitrag textbeitrag) throws IllegalArgumentException {
		textbeitragMapper.updateTextbeitrag(textbeitrag);
	}
	
	@Override
	public void saveKommentar(Kommentar kommentar) throws IllegalArgumentException {
		kommentarMapper.updateKommentar(kommentar);
	}
	
	@Override
	public void saveAbonnement(Abonnement abonnement) throws IllegalArgumentException {
		abonnementMapper.updateAbonnement(abonnement);
	}
	
	@Override
	public void deleteNutzer(Nutzer nutzer) throws IllegalArgumentException {
		nutzerMapper.deleteNutzer(nutzer);
	}
	
	@Override
	public Vector<Abonnement> findAllAbonnement() throws IllegalArgumentException {
		return this.abonnementMapper.findAllAbonnement();
	}
	
	@Override
	public Vector<Abonnement> findAbonnementByNutzerID(int nutzerid) throws IllegalArgumentException {
		return this.abonnementMapper.findAbonnementByNutzerID(nutzerid);
	}
	
	@Override
	public Vector<Abonnement> findAbonnementByPinnwandID(int pinnwandid) throws IllegalArgumentException {
		return this.abonnementMapper.findAbonnementByPinnwandID(pinnwandid);
	}
	
	@Override
	public Vector<Nutzer> findAllNutzer() throws IllegalArgumentException {
		return this.nutzerMapper.findAllNutzer();
	}
	
	@Override
	public Nutzer findNutzerByEmail(String email) throws IllegalArgumentException {
		return this.nutzerMapper.findNutzerByEmail(email);
	}
	
	@Override
	public Nutzer findNutzerByID(int nutzerid) throws IllegalArgumentException {
		return this.nutzerMapper.findNutzerByID(nutzerid);
	}
	
	@Override
	public Vector<Pinnwand> findAllPinnwand() throws IllegalArgumentException {
		return this.pinnwandMapper.findAllPinnwand();
	}
	
	@Override
	public Pinnwand findPinnwandByNutzerID(int nutzerid) throws IllegalArgumentException {
		return this.pinnwandMapper.findPinnwandByNutzerID(nutzerid);
	}
	

}
