package de.hdm.itprojekt.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.client.ClientsideSettings;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Nutzer;

public class KontaktSuche extends VerticalPanel{
		
	/**
	 * Instanziierung des Proxy Objekts für den Editor
	 */
	private static SocialMediaAdminAsync socialmediaVerwaltung = ClientsideSettings.getSocialMediaVerwaltung();

	/**
	 * Instanzierung der GUI-Elemente
	 */
	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable ft = new FlexTable();
	private Label lb1 = new Label("Wen suchen Sie?");
	private Button abonnieren = new Button("Abonnieren");
	private Button abbrechen = new Button("Zur Startseite");
    private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
    final SuggestBox suggestBox = new SuggestBox(oracle);

	
	public KontaktSuche(){
		abbrechen.addClickHandler(new AbbrechenClickHandler());
		
		socialmediaVerwaltung.findAllNutzer(new AsyncCallback<Vector<Nutzer>>() {
			
			@Override
			public void onSuccess(Vector<Nutzer> result) {
				for (Nutzer nutzer : result) {
					oracle.add(nutzer.getEmail());
					oracle.add(nutzer.getNachname());
					oracle.add(nutzer.getVorname());
					oracle.add(nutzer.getNickname());

				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler): " + caught.getMessage());
			}
		});
		
		abonnieren.addClickHandler(new AbonnierenClickHandler());
		
		ft.setWidget(0, 0, lb1);
		ft.setWidget(1, 0, suggestBox);
		ft.setWidget(2, 0, abonnieren);
		ft.setWidget(2, 1, abbrechen);
		
		mainPanel.add(ft);

		this.add(mainPanel);
		
		RootPanel.get("content").clear();
		RootPanel.get("content").add(mainPanel);
	}


	class AbonnierenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			suggestBox.getValue();
			
			//Frage: wie kriege ich das dazugehörige Nutzerobjekt der suggestbox?
			}
		
	}
	
	class AbbrechenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			StartSeiteForm startseiteForm = new StartSeiteForm();
		}
		
	}
}