package de.hdm.itprojekt.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.itprojekt.client.LoginInfo;
import de.hdm.itprojekt.shared.LoginService;
import de.hdm.itprojekt.shared.LoginServiceAsync;


public class Menubar extends MenuBar {

	private LoginInfo loginInfo = null;
	private Anchor signOutLink = new Anchor("Sign Out");
	private HorizontalPanel hp = new HorizontalPanel();
	private MenuBar menubar = new MenuBar();
	private MenuBar menubarLeftSight = new MenuBar();
	private Button buttonSeite = new Button("Mein Profil");
	


	
	public Menubar() {
		RootPanel.get("menubar").clear();
		addMenuItemsToArray();
		run();
	}
	
	public void addMenuItemsToArray() {
		
	}
		
	

	public void run() {
		
		
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "Itprojekt1819.html", new LoginCallback());

		buttonSeite.addClickHandler(new ProfilClickHandler());
		menubar.setAutoOpen(true);
		menubar.setAnimationEnabled(true);
		menubar.setWidth("auto");
		menubar.setHeight("inherit");
		
		menubar.addSeparator();
		menubar.addSeparator();
		menubar.setStylePrimaryName("menuBarImage");
		menubarLeftSight.addItem("Ausloggen", new Command() {
			public void execute() {
				signOutLink.setHref(loginInfo.getLogoutUrl());
				Window.open(signOutLink.getHref(), "_self", "");
			}
		});
		hp.add(buttonSeite);
		hp.add(menubar);
		hp.add(menubarLeftSight);

		RootPanel.get("menubar").clear();
		RootPanel.get("menubar").add(hp);
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
	
	class NutzerClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class ProfilClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			MeinProfil mp = new MeinProfil();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(mp);
			
		}
		
	}
	
	
}
