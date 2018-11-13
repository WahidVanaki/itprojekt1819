package de.hdm.itprojekt.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.client.ClientsideSettings;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Nutzer;

public class DialogBoxNutzerUpdate extends DialogBox{

private static SocialMediaAdminAsync socialMediaVerwaltung = ClientsideSettings.getSocialMediaVerwaltung();
	
	private VerticalPanel vp = new VerticalPanel();
	private TextBox nB = new TextBox();
	private TextBox vB = new TextBox();
	private TextBox nickB = new TextBox();
	private Label nT = new Label("Nachname: ");
	private Label vT = new Label("Vorname: ");
	private Label nickT = new Label("Nickname: ");
	
	private Label updateLabel = new Label("Hier können Sie ihre Daten ändern: ");
	private Button speichern = new Button("Speichern");
	private Button abbrechen = new Button("Abbrechen");
	private FlexTable ft = new FlexTable();
	
	public DialogBoxNutzerUpdate(){
		
		speichern.addClickHandler(new UpdateClickHandler());
		abbrechen.addClickHandler(new AbbrechenClickHandler());
		ft.setWidget(0, 0, updateLabel);
		ft.setWidget(1, 0, vT);
		ft.setWidget(3, 0, vB);
		ft.setWidget(5, 0, nT);
		ft.setWidget(7, 0, nB);
		ft.setWidget(9, 0, nickT);
		ft.setWidget(11, 0, nickB);
		
		ft.setWidget(14, 0, speichern);
		ft.setWidget(14, 4, abbrechen);
		
		vp.add(ft);
		
		this.add(vp);
		
		
	}
	
	class UpdateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			Nutzer n = new Nutzer();
//			socialMediaVerwaltung.saveNutzer(n, new SpeichernCallback<Nutzer>);
		
//			socialMediaVerwaltung.createAbonnement(abo.setNutzerID(), abo.setPinnwandID(), new AbonnierenAsyncCallback<Abonnement>);
		}
		
	}
	
	class AbbrechenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			MeinProfil meinprofil = new MeinProfil();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(meinprofil);
		hide();
		}
		
	}
	
	class SpeichernCallback implements AsyncCallback<Nutzer>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Nutzer result) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
