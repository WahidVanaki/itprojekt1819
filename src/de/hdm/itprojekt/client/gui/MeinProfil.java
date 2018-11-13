package de.hdm.itprojekt.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.client.ClientsideSettings;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Nutzer;

public class MeinProfil extends VerticalPanel {

	private static SocialMediaAdminAsync socialMediaVerwaltung = ClientsideSettings.getSocialMediaVerwaltung();
	
	private VerticalPanel vp = new VerticalPanel();
	private Button bearbeiten = new Button("Bearbeiten");
	private Label l = new HTML ("<h2>Ihr Profil</h2>");
	
	private Nutzer nutzer = null;
	
	public MeinProfil(){
		
		bearbeiten.addClickHandler(new UpdateClickHandler());
		vp.add(l);
		vp.add(bearbeiten);
		this.add(vp);
		

		
//		RootPanel.get("content").add(vp);		
	}
	
	class UpdateClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			DialogBoxNutzerUpdate nutzerupdate = new DialogBoxNutzerUpdate();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(nutzerupdate);
		}
		
	}


}
