package de.hdm.itprojekt.client;

import de.hdm.itprojekt.client.gui.AllAbonnementView;
import de.hdm.itprojekt.client.gui.DialogBoxNutzerUpdate;
import de.hdm.itprojekt.client.gui.Footer;
import de.hdm.itprojekt.client.gui.Menubar;
import de.hdm.itprojekt.client.gui.StartSeiteForm;
import de.hdm.itprojekt.shared.FieldVerifier;
import de.hdm.itprojekt.shared.LoginService;
import de.hdm.itprojekt.shared.LoginServiceAsync;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Nutzer;
import de.hdm.itprojekt.shared.bo.Pinnwand;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
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

	private Nutzer nutzer = new Nutzer();

	/**
	 * Instanziierung des Proxys
	 */
	private static SocialMediaAdminAsync socialMediaVerwaltung = ClientsideSettings.getSocialMediaVerwaltung();

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "Itprojekt1819.html", new LoginCallback());

	}

	private void loadLogin() {

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
	 * Die loadPinnwand() Methode wird aufgerufen wenn der User bereits
	 * existiert. Der User wird weitergeleitet auf den Social Media Pinnwand
	 */
	private void loadPinnwand() {
		Footer ft = new Footer();
		StartSeiteForm st = new StartSeiteForm();
		AllAbonnementView ab = new AllAbonnementView();
		RootPanel.get("leftmenutree").clear();
		RootPanel.get("leftmenutree").add(ab);
		signOutAnchor.setHref(loginInfo.getLogoutUrl());
//		DialogBoxNutzerUpdate np = new DialogBoxNutzerUpdate();
		Menubar mb = new Menubar();

	}

	/**
	 * Nested Class für den Login Button
	 * 
	 */
	class loginButtonClickHandler implements ClickHandler {

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
	 * 
	 */
	class LoginCallback implements AsyncCallback<LoginInfo> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Anmelden" + caught.getMessage());
		}

		@Override
		public void onSuccess(LoginInfo result) {
			// TODO Auto-generated method stub
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
	 * existiert werden zwei Cookies erstellt und Pinnwand geladen. Wenn
	 * nicht wird eine DialogBox geöffnet, für die Abfrage, ob der User sich
	 * registrieren will.
	 *
	 */
	class FindNutzerCallback implements AsyncCallback<Nutzer> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Anmelden " + caught.getMessage());
		}

		@Override
		public void onSuccess(Nutzer result) {
			// TODO Auto-generated method stub
			if (result != null) {
				Cookies.setCookie("email", result.getEmail());

				Cookies.setCookie("id", result.getId() + "");

				Cookies.setCookie("signout", loginInfo.getLogoutUrl());
				loadPinnwand();
			} else {
				CreateNutzerDialogBox nutzerdialogBox = new CreateNutzerDialogBox();
				nutzerdialogBox.center();
			}
		}

	}

	class CreateNutzerDialogBox extends DialogBox {
		/**
		 * Instanziierung der GUI-Elemente
		 */
		private Label frage = new Label("Bitte legen Sie Ihren Nutzer für die Social Media Pinnwand an?");
		private Button speichern = new Button("Speichern");
		private Button abbrechen = new Button("Abbrechen");

		private Label vL = new Label("Vorname: ");
		private Label nL = new Label("Nachname: ");
		private Label nickL = new Label("Nickname: ");

		private TextBox vT = new TextBox();
		private TextBox nT = new TextBox();
		private TextBox nickT = new TextBox();

		/**
		 * Eine flexible Tabelle, die bei Bedarf Zellen erstellt. Es kann
		 * gezackt sein (dh jede Zeile kann eine andere Anzahl von Zellen
		 * enthalten) und einzelne Zellen können so eingestellt werden, dass sie
		 * sich über mehrere Zeilen oder Spalten erstrecken.
		 */
		private FlexTable ft = new FlexTable();

		/**
		 * Ein rechteckiges Raster, das Text, HTML oder ein untergeordnetes
		 * Widget in seinen Zellen enthalten kann. Sie muss explizit auf die
		 * gewünschte Anzahl von Zeilen und Spalten angepasst werden.
		 */
		// private Grid grid = new Grid(10,10);

		private VerticalPanel vpanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();
		
		/**
		 * Instanziierung der googleMail. Diese speichert später die übergebene
		 * gmail Adresse.
		 */
		private String googleMail = "";

		/**
		 * non-Konstruktor der aufgerufen wird.
		 * 
		 */
		public CreateNutzerDialogBox() {

			speichern.addClickHandler(new DoCreateNutzerClickHandler());
			abbrechen.addClickHandler(new DontCreateNutzerClickHandler());
			vpanel.add(frage);

			// vpanel.add(vL);
			// vpanel.add(vT);
			// vpanel.add(nL);
			// vpanel.add(nT);
			// vpanel.add(nickL);
			// vpanel.add(nickT);

			ft.setWidget(3, 0, vL);
			ft.setWidget(4, 0, vT);
			ft.setWidget(5, 0, nL);
			ft.setWidget(6, 0, nT);
			ft.setWidget(7, 0, nickL);
			ft.setWidget(8, 0, nickT);
			ft.setWidget(11, 0, speichern);
			ft.setWidget(11, 1, abbrechen);

			// buttonPanel.add(speichern);
			// buttonPanel.add(abbrechen);

			vpanel.add(ft);
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
				socialMediaVerwaltung.createPinnwand(result.getId(), new PinnwandCallback());
				loadPinnwand();
			}

		}

		/**
		 * Nested Class in der DialogBox. Wenn Nutzer einen User erstellen
		 * möchte, gibt es diesen ClickHandler Aufruf.
		 *
		 */
		class DoCreateNutzerClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {

				socialMediaVerwaltung.createNutzer(loginInfo.getEmailAddress(), vT.getValue(), nT.getValue(),
						nickT.getValue(), new CreateNutzerCallback());
				hide();
				// neue nested class und neue dialogbox
			}
		}

		/**
		 * Nested Class in der DialogBox. Wenn Nutzer keinen User erstellen
		 * möchte, gibt es diesen ClickHandler Aufruf. Der User wird dann auf
		 * die Startseite weitergeleitet.
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
		
		class PinnwandCallback implements AsyncCallback<Pinnwand>{

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Fehler " + caught.getMessage());
			}

			@Override
			public void onSuccess(Pinnwand result) {
				// TODO Auto-generated method stub
				Window.alert("Eine Pinnwand wurde für Sie gleichzeitig erstellt.");
			}
			
		}
		
	}
	
	
}
