//package de.hdm.itprojekt.client.gui;
//
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.i18n.client.DateTimeFormat;
//import com.google.gwt.user.client.Cookies;
//import com.google.gwt.user.client.Window;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.FlexTable;
//import com.google.gwt.user.client.ui.HTML;
//import com.google.gwt.user.client.ui.HorizontalPanel;
//import com.google.gwt.user.client.ui.Label;
//import com.google.gwt.user.client.ui.RootPanel;
//import com.google.gwt.user.client.ui.TextBox;
//import com.google.gwt.user.client.ui.VerticalPanel;
//
//import de.hdm.itprojekt.client.ClientsideSettings;
//import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
//import de.hdm.itprojekt.shared.bo.Nutzer;
//
//public class MeinProfil extends MainFrame {
//
//	private static SocialMediaAdminAsync socialMediaVerwaltung = ClientsideSettings.getSocialMediaVerwaltung();
//	
//	private Nutzer nutzer = null;
//	
//	private FlexTable flex = new FlexTable();
//	
//	private VerticalPanel vp = new VerticalPanel();
//	private HorizontalPanel hp = new HorizontalPanel();
//	
//	private Label modifikationsdatum = new Label("Modifikationsdatum: ");
//	private Label erstellungsdatum = new Label("Erstellungsdatum: ");
//	private Label nickname = new Label(" Nickname: ");
//	private Label vorname = new Label(" Vorname: ");
//	private Label nachname = new Label(" Nachname: ");
//	private Label email = new Label(" Email: ");
//
//	private TextBox nachText= new TextBox();
//	private TextBox vornText= new TextBox();
//	private TextBox nickText = new TextBox();
//	private TextBox emailText = new TextBox();
//
//	
//	private Button speichern = new Button("speichern");
//	private Button bearbeiten = new Button("Bearbeiten");
//	private Label l = new HTML ("<h2> Ihr Profil</h2>");
//	
//	
//	private Label label;
//	
//	@Override
//	protected void run() {
//		// TODO Auto-generated method stub
//		
//		vp.setStylePrimaryName("nutzerFromPanel");
//		vorname.setStylePrimaryName("nutzerNameLabel");
//		nachname.setStylePrimaryName("nutzerNameLabel");
//		nickname.setStylePrimaryName("nutzerNameLabel");
//		vornText.setStylePrimaryName("nutzerFromTextBox");
//		nachText.setStylePrimaryName("nutzerFromTextBox");
//		nickText.setStylePrimaryName("nutzerFromTextBox");
//		emailText.setStylePrimaryName("nutzerFromTextBox");
//		email.setStylePrimaryName("nutzerNameLabel");
//		
//		vp.add(l);
//		vp.add(vorname);
//		vp.add(vornText);
//		vp.add(nachname);
//		vp.add(nachText);
//		vp.add(nickname);
//		vp.add(nickText);
//		vp.add(email);
//		vp.add(emailText);
//		
//		bearbeiten.addClickHandler(new UpdateProfilClickHandler());
//		
//		vp.add(bearbeiten);
//
//		RootPanel.get("content").add(vp);
//	
//	}
//
////	public MeinProfil(Nutzer nutzer){
////		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
////		nutzer.setEmail(Cookies.getCookie("email"));
////		socialMediaVerwaltung.findNutzerByID(nutzer.getId(), new ProfilSeiteCallback());
////		RootPanel.get("content").clear();
////		RootPanel.get("content").add(this);
////		super.onLoad();
////		
////
////		
////	}
////
////	
////	public MeinProfil(){
////		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
////		nutzer.setEmail(Cookies.getCookie("email"));
////		RootPanel.get("content").clear();
////		RootPanel.get("content").add(this);
////		super.onLoad();		
////		
////		
////	}
////	
//	class UpdateProfilClickHandler implements ClickHandler{
//
//		@Override
//		public void onClick(ClickEvent event) {
//			// TODO Auto-generated method stu
//			
//			DialogBoxNutzerUpdate nutzerupdate = new DialogBoxNutzerUpdate();
//			
//			RootPanel.get("content").clear();
//			RootPanel.get("content").add(nutzerupdate);
//		}
//		
//	}
////	
////	class ProfilSeiteCallback implements AsyncCallback<Nutzer> {
////
////		@Override
////		public void onFailure(Throwable caught) {
////			// TODO Auto-generated method stub
////			
////		}
////
////		@Override
////		public void onSuccess(Nutzer result) {
////			// TODO Auto-generated method stub
////
////	}
////	
////	class UpdateProfilCallback implements AsyncCallback<Nutzer>{
////
////		@Override
////		public void onFailure(Throwable caught) {
////			// TODO Auto-generated method stub
////			
////		}
////
////		@Override
////		public void onSuccess(Nutzer result) {
////			// TODO Auto-generated method stub
////			Window.alert("sss");
////		}
////		
////	}
////	}	
////	
//}
//
//
