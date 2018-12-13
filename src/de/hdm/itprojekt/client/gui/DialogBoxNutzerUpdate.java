package de.hdm.itprojekt.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.client.ClientsideSettings;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Nutzer;

public class DialogBoxNutzerUpdate extends DialogBox {

	private static SocialMediaAdminAsync socialMediaVerwaltung = ClientsideSettings.getSocialMediaVerwaltung();

	private VerticalPanel vp = new VerticalPanel();
	private TextBox nB = new TextBox();
	private TextBox vB = new TextBox();
	private TextBox nickB = new TextBox();
	private TextBox emailB = new TextBox();

	private Label nT = new Label("Nachname: ");
	private Label vT = new Label("Vorname: ");
	private Label nickT = new Label("Nickname: ");
	private Label emailT = new Label("Email: ");

	private Label updateLabel = new Label("Hier können Sie ihre Daten ändern: ");
	private Button speichern = new Button("Speichern");
	private Button abbrechen = new Button("Abbrechen");
	private FlexTable ft = new FlexTable();

	public DialogBoxNutzerUpdate() {

		Nutzer n = new Nutzer();
		n.setId(Integer.parseInt(Cookies.getCookie("id")));
		n.setEmail(Cookies.getCookie("email"));

		socialMediaVerwaltung.findNutzerByID(n.getId(), new BearbeitenCallback());

		speichern.setStylePrimaryName("gwt-Button3");
		abbrechen.setStylePrimaryName("gwt-Button3");
		speichern.addClickHandler(new UpdateClickHandler());
		abbrechen.addClickHandler(new AbbrechenClickHandler());

		ft.setWidget(0, 0, updateLabel);
		ft.setWidget(1, 0, vT);
		ft.setWidget(3, 0, vB);
		ft.setWidget(5, 0, nT);
		ft.setWidget(7, 0, nB);
		ft.setWidget(9, 0, nickT);
		ft.setWidget(11, 0, nickB);
		ft.setWidget(13, 0, emailT);
		ft.setWidget(15, 0, emailB);
		ft.setWidget(17, 0, speichern);
		ft.setWidget(17, 4, abbrechen);

		vp.add(ft);

		this.add(vp);

	}

	public DialogBoxNutzerUpdate(Nutzer nutzer) {

		Nutzer n = new Nutzer();
		n.setId(Integer.parseInt(Cookies.getCookie("id")));

		speichern.addClickHandler(new UpdateClickHandler());
		abbrechen.addClickHandler(new AbbrechenClickHandler());

	}

	class UpdateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Nutzer n = new Nutzer();
			n.setId(Integer.parseInt(Cookies.getCookie("id")));
			n.setVorname(vB.getValue());
			n.setNachname(nB.getValue());
			n.setNickname(nickB.getValue());
			n.setEmail(emailB.getValue());

			socialMediaVerwaltung.saveNutzer(n, new CreateNutzerCallback());

		}

	}

	class AbbrechenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			StartSeiteForm meinprofil = new StartSeiteForm();

		}

	}

	// Zum Anzeigen der Profildaten
	class BearbeitenCallback implements AsyncCallback<Nutzer> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden des Profils" + caught.getMessage());

		}

		@Override
		public void onSuccess(Nutzer result) {
			vB.setValue(result.getVorname());
			nB.setValue(result.getNachname());
			nickB.setValue(result.getNickname());
			emailB.setValue(result.getEmail());
		}

	}

	public class CreateNutzerCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler " + caught.getMessage());

		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Nutzer erfolgreich geändert");
			hide();
			StartSeiteForm st = new StartSeiteForm();

		}

	}

}
