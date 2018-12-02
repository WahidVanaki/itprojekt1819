package de.hdm.itprojekt.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.itprojekt.client.ClientsideSettings;
import de.hdm.itprojekt.client.LoginInfo;
import de.hdm.itprojekt.shared.LoginService;
import de.hdm.itprojekt.shared.LoginServiceAsync;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;

public class Toolbar extends LeftSideFrame {

	/**
	 * Instanziierung des Proxy Objekts für den Editor
	 */
	private static SocialMediaAdminAsync socialmediaVerwaltung = ClientsideSettings.getSocialMediaVerwaltung();

	private LoginInfo loginInfo = null;

	/**
	 * Instanzierung der GUI-Elemente
	 */
	private HorizontalPanel toolbarPanel = new HorizontalPanel();
	private Button abonnierenBt = new Button("suche/abonnieren");
	private Button meinProfilBt = new Button("MeinProfil");
	private Button logoutBt = new Button("Logout");
	private Anchor signOutLink = new Anchor("Logout");

	public Toolbar() {
		super.onLoad();
	}

	@Override
	protected void run() {
		abonnierenBt.setHTML("<img src = 'images/add.png'/>");
		abonnierenBt.setPixelSize(40, 40);

		meinProfilBt.setHTML("<img src = 'images/profile.png'/>");
		meinProfilBt.setPixelSize(40, 40);

		logoutBt.setHTML("<img src = 'images/logout.png'/>");
		logoutBt.setPixelSize(40, 40);

		abonnierenBt.setStylePrimaryName("toolbarButton");
		meinProfilBt.setStylePrimaryName("toolbarButton");
		logoutBt.setStylePrimaryName("toolbarButton");
		toolbarPanel.setStylePrimaryName("toolbarPanel");


		toolbarPanel.add(abonnierenBt);
		toolbarPanel.add(meinProfilBt);
		toolbarPanel.add(logoutBt);

		this.add(toolbarPanel);

		meinProfilBt.addClickHandler(new meinProfilHandler());
		logoutBt.addClickHandler(new LogoutHandler());

		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "Itprojekt1819.html", new LoginCallback());

	}

	class LoginCallback implements AsyncCallback<LoginInfo> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Der Vorgang konnte nicht abgeschlossen werden: " + caught.getMessage());
		}

		@Override
		public void onSuccess(LoginInfo result) {
			loginInfo = result;
		}
	}

	class meinProfilHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			DialogBoxNutzerUpdate dialogBoxNutzerUpdate = new DialogBoxNutzerUpdate();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(dialogBoxNutzerUpdate);
		}

	}

	class LogoutHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			signOutLink.setHref(loginInfo.getLogoutUrl());
			Window.open(signOutLink.getHref(), "_self", "");
		}

	}

}