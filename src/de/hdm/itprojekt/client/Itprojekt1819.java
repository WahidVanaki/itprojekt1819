package de.hdm.itprojekt.client;

import de.hdm.itprojekt.shared.FieldVerifier;
import de.hdm.itprojekt.shared.LoginService;
import de.hdm.itprojekt.shared.LoginServiceAsync;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Nutzer;

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
	 * Instanziierung der GUI Objekten: Panels, Label, Anchor und Button
	 */
	private VerticalPanel loginPanel = new VerticalPanel();
	
	private Label willkommenLabel = new Label("Herzlich Willkomen auf der Social Media Pinnwand");
	private Label logMessageLabel = new Label("Bitte loggen Sie sich mit Ihrem Google Account ein");
	private Anchor signInAnchor = new Anchor("Sign In");
	private Anchor signOutAnchor = new Anchor("Sign Out");
	
	private Button loginButton = new Button("Anmelden");

	/**
	 * Instanziierung des Proxys
	 */
	private static SocialMediaAdminAsync socialMediaVerwaltung = ClientsideSettings
			.getSocialMediaVerwaltung();
	
	//	/**
//	 * The message displayed to the user when the server cannot be reached or
//	 * returns an error.
//	 */
//	private static final String SERVER_ERROR = "An error occurred while "
//			+ "attempting to contact the server. Please check your network " + "connection and try again.";
//
//	/**
//	 * Create a remote service proxy to talk to the server-side Greeting service.
//	 */
//	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
//
//	/**
//	 * This is the entry point method.
//	 */
	/**
	 * onModuleLoad des Moduls: itprojektss18gruppe3
	 * Ist die main-Methode in einem GWT-Projekt
	 */
	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "Itprojekt1819.html", new LoginCallback());
		
	}
	
	private void loadLogin(){
		
		loginButton.addClickHandler(new loginButtonClickHandler());
		loginButton.setStylePrimaryName("loginButton");
		willkommenLabel.setStylePrimaryName("landingPageWelcomeMessage");
		loginPanel.setStylePrimaryName("loginPanel");
		logMessageLabel.setStylePrimaryName("landingPageLoginMessage");
		loginPanel.add(willkommenLabel);
		loginPanel.add(logMessageLabel);
		loginPanel.add(loginButton);
		RootPanel.get("content").add(loginPanel);
	}
	
	/**
	 * Die loadPinnwand() Methode wird aufgerufen wenn der User bereits existiert. 
	 * Der User wird weitergeleitet auf den Social Media Pinnwand
	 */
	private void loadPinnwand() {

		// AUFRUF DES BAUMS
//		CustomTreeModel ctm = new CustomTreeModel();
		RootPanel.get("leftmenutree").clear();
//		RootPanel.get("leftmenutree").add();

		signOutAnchor.setHref(loginInfo.getLogoutUrl());

		
	}
	
	/**
	 * Nested Class für den Login Button
	 * 
	 */
	class loginButtonClickHandler implements ClickHandler {

		
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			signInAnchor.setHref(loginInfo.getLoginUrl());
			Window.open(signInAnchor.getHref(),  "_self", "");
		}
		
	}
	

	/**
	 * Nested Class für den Login Callback
	 * In der onSuccess wird überprüft, ob der User eingeloggt. Wenn er eingeloggt ist, wird mit der checkEmail
	 * überprüft ob die E-Mail Adresse bereits in der Datenbank existiert. 
	 * 
	 */
	class LoginCallback implements AsyncCallback<LoginInfo>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Anmelden" + caught.getMessage());
		}

		@Override
		public void onSuccess(LoginInfo result) {
			// TODO Auto-generated method stub
			loginInfo = result;
			if(loginInfo.isLoggedIn()){
				socialMediaVerwaltung.checkEmail(loginInfo.getEmailAddress(), new FindNutzerCallback());
				}
			else{
				loadLogin();
			}
	}
		
	}	
	
	/**
	 * Nested Class für den AsyncCallback checkEmail
	 * Wenn der User bereits existiert werden zwei Cookies erstellt und der Pinnwand geladen.
	 * Wenn nicht wird eine DialogBox geöffnet, für die Abfrage, ob der User sich registrieren will.
	 *
	 */
	class FindNutzerCallback implements AsyncCallback<Nutzer>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Anmelden " + caught.getMessage());
		}

		@Override
		public void onSuccess(Nutzer result) {
			// TODO Auto-generated method stub
			if(result != null){
				Cookies.setCookie("email", result.getEmail());
				Cookies.setCookie("id", result.getId() + "");
				Cookies.setCookie("signout", loginInfo.getLogoutUrl());
				loadPinnwand();
			}
			else {
				CreateNutzerDialogBox nutzerdialogBox = new CreateNutzerDialogBox(loginInfo.getEmailAddress());
				nutzerdialogBox.center();
			}
		}
		
	}
	
	class CreateNutzerDialogBox extends DialogBox {
		/**
		 * Instanziierung der GUI-Elemente 
		 */
		private Label frage = new Label(
				"Sie haben noch keinen Nutzer auf dieser Pinnwand. Möchten Sie einen neuen Nutzer anlegen?");
		private Button ja = new Button("Ja");
		private Button nein = new Button("Nein");
		private VerticalPanel vpanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();
		/**
		 * Instanziierung der googleMail. Diese speichert später die übergebene gmail Adresse.
		 */
		private String googleMail = "";

		/**
		 * Konstruktor der aufgerufen wird.
		 * @param mail: Email Adresse des angemeldeten Nutzers.
		 */
		public CreateNutzerDialogBox(String email) {
			googleMail = email;
			ja.addClickHandler(new DoCreateNutzerClickHandler());
			nein.addClickHandler(new DontCreateNutzerClickHandler());
			vpanel.add(frage);
			buttonPanel.add(ja);
			buttonPanel.add(nein);
			vpanel.add(buttonPanel);
			this.add(vpanel);

		}
		
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
				hide();
				loadPinnwand();
			}

		}
		
		/**
		 * Nested Class in der DialogBox. Wenn Nutzer einen User erstellen möchte, gibt es diesen ClickHandler Aufruf.
		 *
		 */
		class DoCreateNutzerClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				socialMediaVerwaltung.createNutzer(googleMail, new CreateNutzerCallback());

			}

		}

		/**
		 * Nested Class in der DialogBox. Wenn Nutzer keinen User erstellen möchte, gibt es diesen ClickHandler Aufruf.
		 * Der User wird dann auf die Startseite weitergeleitet.
		 *
		 */
		class DontCreateNutzerClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				hide();
				signOutAnchor.setHref(loginInfo.getLogoutUrl());
				Window.open(signOutAnchor.getHref(), "_self", "");

			}

		}

	}
}
