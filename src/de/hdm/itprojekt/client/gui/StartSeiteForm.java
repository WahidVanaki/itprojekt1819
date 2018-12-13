package de.hdm.itprojekt.client.gui;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class StartSeiteForm extends VerticalPanel {

	public StartSeiteForm() {
		/**
		 * Anlegen der GUI-Elemente
		 */
		VerticalPanel homePanel = new VerticalPanel();
		Label welcomeLabel = new Label("Herzlich Willkommen auf deiner Startseite");

		welcomeLabel.setStylePrimaryName("h3");
		homePanel.setSpacing(10);
		this.setSpacing(8);
		homePanel.add(welcomeLabel);
		this.add(homePanel);
		RootPanel.get("content").clear();
		RootPanel.get("content").add(homePanel);
	}

}