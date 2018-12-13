package de.hdm.itprojekt.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.itprojekt.client.ClientsideSettings;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Nutzer;

public class Footer extends HorizontalPanel {

	private Label footerL = new HTML("IT PROJEKT WS 18/19 | HOCHSCHULE DER MEDIEN | ");
	private Button deleteNutzer = new Button("Nutzer löschen");
	private HorizontalPanel hp = new HorizontalPanel();

	private static SocialMediaAdminAsync socialMediaVerwaltung = ClientsideSettings.getSocialMediaVerwaltung();

	public Footer() {

		deleteNutzer.addClickHandler(new DeleteNutzerClickHandler());
		deleteNutzer.setStylePrimaryName("impressumButton");
		hp.add(footerL);
		hp.add(deleteNutzer);
		hp.add(new HTML(""));
		hp.setStylePrimaryName("footerPanel");

		RootPanel.get("footer").clear();
		RootPanel.get("footer").add(hp);

	}

	public void run() {

	}

	public class DeleteNutzerClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			boolean deleteNutzer = Window.confirm("Möchten Sie ihren Nutzer wirklich löschen");
			if (deleteNutzer == true) {
				Nutzer nutzer = new Nutzer();
				nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
				nutzer.setEmail(Cookies.getCookie("email"));

				socialMediaVerwaltung.deleteNutzer(nutzer, new DeleteNutzerCallback());
			}
		}

	}

	public class DeleteNutzerCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Löschen " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			Anchor signOutLink = new Anchor();
			Window.alert("Nutzer wurde erfolgreich gelöscht");
			signOutLink.setHref(Cookies.getCookie("signout"));
			Window.open(signOutLink.getHref(), "_self", "");
		}

	}

}
