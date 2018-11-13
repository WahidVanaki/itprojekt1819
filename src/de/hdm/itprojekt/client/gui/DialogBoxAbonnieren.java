package de.hdm.itprojekt.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.client.ClientsideSettings;
import de.hdm.itprojekt.client.Itprojekt1819;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Abonnement;
import de.hdm.itprojekt.shared.bo.Pinnwand;

public class DialogBoxAbonnieren extends DialogBox {

	private static SocialMediaAdminAsync socialMediaVerwaltung = ClientsideSettings.getSocialMediaVerwaltung();
	
	private VerticalPanel vp = new VerticalPanel();
	private TextBox suchBox = new TextBox();
	private Label suchLabel = new Label("Geben Sie einen Nicknamen ein: ");
	private Button abonnieren = new Button("Abonnieren");
	private Button abbrechen = new Button("Abbrechen");
	private FlexTable ft = new FlexTable();
	
	public DialogBoxAbonnieren(){
		
		abonnieren.addClickHandler(new AbonnierenClickHandler());
		abbrechen.addClickHandler(new AbbrechenClickHandler());
		ft.setWidget(1, 0, suchLabel);
		ft.setWidget(3, 0, suchBox);
		ft.setWidget(6, 0, abonnieren);
		ft.setWidget(6, 6, abbrechen);
		
		vp.add(ft);
		
		this.add(vp);
		
		
	}
	
	class AbonnierenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			Abonnement abo = new Abonnement();
		
//			socialMediaVerwaltung.createAbonnement(abo.setNutzerID(), abo.setPinnwandID(), new AbonnierenAsyncCallback<Abonnement>);
		}
		
	}
	
	class AbbrechenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			StartSeiteForm it = new StartSeiteForm();
			AllAbonnementView vw = new AllAbonnementView();
			RootPanel.get("leftmenutree").clear();
			RootPanel.get("leftmenutree").add(vw);
		hide();
		}
		
	}
	
	public class AbonnierenAsyncCallback implements AsyncCallback<Abonnement>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Abonnement result) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
