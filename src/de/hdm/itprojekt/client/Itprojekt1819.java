package de.hdm.itprojekt.client;

import de.hdm.itprojekt.client.gui.AllAbonnementView;
import de.hdm.itprojekt.client.gui.Footer;
import de.hdm.itprojekt.client.gui.LeftSideFrame;
import de.hdm.itprojekt.client.gui.StartSeiteForm;
import de.hdm.itprojekt.client.gui.Toolbar;
import de.hdm.itprojekt.shared.LoginService;
import de.hdm.itprojekt.shared.LoginServiceAsync;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Nutzer;
import de.hdm.itprojekt.shared.bo.Pinnwand;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
@SuppressWarnings("unused")
public class Itprojekt1819 implements EntryPoint {

	/**
	 * Deklarierung der Klasse LoginInfo für die Google API
	 */
	private LoginInfo loginInfo = null;

	/**
	 * Instanzierung der Objekte
	 */
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label welcomeLabel = new Label("Herzlich Willkommen");
	private Label loginLabel = new Label("Bitte loggen Sie sich mit Ihrem Google Account ein.");
	private Button loginButton = new Button("Login");
	private Anchor signInAnchor = new Anchor();
	private Anchor signOutAnchor = new Anchor();

	/**
	 * Instanziierung des Proxys
	 */
	private static SocialMediaAdminAsync socialMediaVerwaltung = ClientsideSettings.getSocialMediaVerwaltung();

	@Override
	public void onModuleLoad() {

		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "itprojekt1819.html", new LoginCallback());
	}

	/**
	 * Die loadLogin() Methode wird verwendet für das Anzeigen der API
	 */
	private void loadLogin() {

		loginButton.addClickHandler(new loginButtonClickHandler());
		loginPanel.add(welcomeLabel);
		loginPanel.add(loginLabel);
		loginPanel.add(loginButton);
		RootPanel.get("content").add(loginPanel);
		welcomeLabel.setStylePrimaryName("landingPageWelcomeMessage");
		loginLabel.setStylePrimaryName("landingPageWelcomeMessage");
		loginButton.setStylePrimaryName("loginButton");

	}

	/**
	 * Die loadPinnwand() Methode wird aufgerufen wenn der User bereits
	 * existiert. Der User wird weitergeleitet auf die Pinnwand
	 */
	private void loadPinnwand() {

		StartSeiteForm startseiteform = new StartSeiteForm();
		Footer foot = new Footer();
		Toolbar toolbar = new Toolbar();
		AllAbonnementView apv = new AllAbonnementView();
		RootPanel.get("leftmenutree").clear();
		RootPanel.get("leftmenutree").add(toolbar);
		RootPanel.get("leftmenutree").add(apv);

		signOutAnchor.setHref(loginInfo.getLogoutUrl());
	}

	/**
	 * Nested Class für den Login Button
	 * 
	 */

	class loginButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			signInAnchor.setHref(loginInfo.getLoginUrl());
			Window.open(signInAnchor.getHref(), "_self", "");
		}
	}

	/**
	 * Nested Class für den Login Callback In der onSuccess wird überprüft, ob
	 * der User eingeloggt. Wenn er eingeloggt ist, wird mit der checkEmail
	 * überprüft ob die E-Mail Adresse bereits in der Datenbank existiert.
	 */
	class LoginCallback implements AsyncCallback<LoginInfo> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Login");

		}

		@Override
		public void onSuccess(LoginInfo result) {
			loginInfo = result;
			if (loginInfo.isLoggedIn()) {
				socialMediaVerwaltung.checkEmail(loginInfo.getEmailAddress(), new FindNutzerCallback());
			} else {
				loadLogin();
			}
		}

	}

	/**
	 * Nested Class für den AsyncCallback checkEmail Wenn der User bereits
	 * existiert werden zwei Cookies erstellt und die Pinnwand geladen. Wenn
	 * nicht wird eine DialogBox geöffnet, für die Abfrage, ob der User sich
	 * registrieren will.
	 *
	 */
	class FindNutzerCallback implements AsyncCallback<Nutzer> {

		@Override
		public void onFailure(Throwable caught) {

			Window.alert("Fehler bei der Anmeldung" + caught.getMessage());
		}

		@Override
		public void onSuccess(Nutzer result) {

			if (result != null) {
				Cookies.setCookie("email", result.getEmail());
				Cookies.setCookie("vorname", result.getVorname());
				Cookies.setCookie("nachname", result.getNachname());
				Cookies.setCookie("nickname", result.getNickname());
				Cookies.setCookie("id", result.getId() + "");
				Cookies.setCookie("signout", loginInfo.getLogoutUrl());
				loadPinnwand();
			} else {
				CreateNutzerDialogBox nutzerDialogBox = new CreateNutzerDialogBox(loginInfo.getEmailAddress());
				nutzerDialogBox.center();
			}
		}

	}

	/**
	 * Nested Class einer DialogBox für die Nutzer Erstellung Abfrage.
	 *
	 */
	class CreateNutzerDialogBox extends DialogBox {

		/**
		 * Instanziierung der GUI-Elemente
		 */
		private Label frage = new Label("Sie haben noch keinen Nutzer angelegt.");
		private Label vornameLabel = new Label("Vorname:");
		private Label nachnameLabel = new Label("Nachname:");
		private Label nicknameLabel = new Label("Nickname:");
		private TextBox vornameTextbox = new TextBox();
		private TextBox nachnameTextbox = new TextBox();
		private TextBox nicknameTextbox = new TextBox();
		private Button ja = new Button("Speichern");
		private Button nein = new Button("Abbrechen");
		private VerticalPanel vpanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		/**
		 * Instanziierung der googleMail. Diese speichert später die übergebene
		 * gmail Adresse.
		 */
		private String googleMail = "";

		/**
		 * Konstruktor der aufgerufen wird.
		 * 
		 * @param mail:
		 *            Email Adresse des angemeldeten Nutzers.
		 */
		public CreateNutzerDialogBox(String email) {
			googleMail = email;
			ja.addClickHandler(new DoCreateClickHandler());
			nein.addClickHandler(new DontCreateClickHandler());
			vpanel.add(frage);
			vpanel.add(vornameLabel);
			vpanel.add(vornameTextbox);
			vpanel.add(nachnameLabel);
			vpanel.add(nachnameTextbox);
			vpanel.add(nicknameLabel);
			vpanel.add(nicknameTextbox);
			buttonPanel.add(ja);
			buttonPanel.add(nein);
			this.add(vpanel);
			vpanel.add(buttonPanel);

			ja.setStylePrimaryName("gwt-Button3");
			nein.setStylePrimaryName("gwt-Button3");
		}

		class DoCreateClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {

				socialMediaVerwaltung.createNutzer(loginInfo.getEmailAddress(), vornameTextbox.getValue(),
						nachnameTextbox.getValue(), nicknameTextbox.getValue(), new CreateNutzerCallback());
			}

		}

		class DontCreateClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {

				hide();
				signOutAnchor.setHref(loginInfo.getLogoutUrl());
				Window.open(signOutAnchor.getHref(), "_self", "");
			}

		}

		/**
		 * Nested Class in der DialogBox. Wenn Nutzer einen User erstellen
		 * möchte, gibt es diesen Callback Aufruf.
		 *
		 */
		class CreateNutzerCallback implements AsyncCallback<Nutzer> {

			@Override
			public void onFailure(Throwable caught) {

				Window.alert("Ihr User konnte nicht erstellt werden" + caught.getMessage());

			}

			@Override
			public void onSuccess(Nutzer result) {

				Window.alert("Ihr Nutzer wurde erfolgreich angelegt");
				Cookies.setCookie("signout", loginInfo.getLogoutUrl());
				Cookies.setCookie("email", result.getEmail());
				Cookies.setCookie("id", result.getId() + "");
				socialMediaVerwaltung.createPinnwand(result.getId(), new PinnwandCallback());
				hide();
				loadPinnwand();

			}

			class PinnwandCallback implements AsyncCallback<Pinnwand> {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Fehler beim Anlegen deiner Pinnwand" + caught.getMessage());
				}

				@Override
				public void onSuccess(Pinnwand result) {
					Window.alert("Für Sie wurde gleichzeitig eine Pinnwand angelegt.");
				}

			}

		}
	}
}