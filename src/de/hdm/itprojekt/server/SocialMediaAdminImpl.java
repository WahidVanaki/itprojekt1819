package de.hdm.itprojekt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itprojekt.server.db.NutzerMapper;
import de.hdm.itprojekt.shared.SocialMediaAdmin;
import de.hdm.itprojekt.shared.bo.Nutzer;

@SuppressWarnings("serial")
public class SocialMediaAdminImpl extends RemoteServiceServlet implements SocialMediaAdmin{

	private NutzerMapper nutzerMapper = null;
	
	public SocialMediaAdminImpl() throws IllegalArgumentException{
		
	}
	
	public void init() throws IllegalArgumentException{
		this.nutzerMapper = NutzerMapper.nutzerMapper();
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
	
	public void saveNutzer(Nutzer nutzer) throws IllegalArgumentException {
		nutzerMapper.updateNutzer(nutzer);
	}
	

}
