package de.hdm.itprojekt.client.gui;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Abstrakte Basisklasse für das MainFrame, also für den Content.
 * 
 * @version 1.0 12 November 2018
 * @author Wahid
 *
 */

public abstract class MainFrame extends VerticalPanel {

	
	/**
	 * Automatisch geladene Methode onLoad(). Löscht den Div-Container content und ruft die Methode run() auf.
	 */
	public void onLoad() {
	    /*
	     * Bevor wir unsere eigene Formatierung veranslassen, überlassen wir es der
	     * Superklasse eine Initialisierung vorzunehmen.
	     */
	    super.onLoad();
	    RootPanel.get("content").clear();	  
	    this.run();
	}

	protected abstract void run();
	
}

