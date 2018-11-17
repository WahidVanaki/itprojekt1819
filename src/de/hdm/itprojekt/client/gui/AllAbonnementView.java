package de.hdm.itprojekt.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojekt.client.ClientsideSettings;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Abonnement;
import de.hdm.itprojekt.shared.bo.Nutzer;

public class AllAbonnementView extends LeftSideFrame{

	private static SocialMediaAdminAsync socialMediaVerwaltung = ClientsideSettings.getSocialMediaVerwaltung();
	
	private VerticalPanel vp = new VerticalPanel();
	private Button abonnieren = new Button("Abonnieren");
	
	private SingleSelectionModel<Nutzer> ssmNutzer = new SingleSelectionModel<Nutzer>();
	
	private CellList<Nutzer> celllist = new CellList<Nutzer>(new CellListAbonnement());
	
	@Override
	protected void run() {
		abonnieren.addClickHandler(new meinePinnwandClickHandler());

		Abonnement abonnement = new Abonnement();
		
		Nutzer nutzer = new Nutzer();
		
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		
		socialMediaVerwaltung.findNutzerByAbo(nutzer.getId(), new CellListCallback());
		
		celllist.setSelectionModel(ssmNutzer);
		
		ssmNutzer.addSelectionChangeHandler(new SelectionChangeEvent.Handler(){
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				// TODO Auto-generated method stub
				Window.alert(ssmNutzer.getSelectedObject().getNickname());
			}
		});
		
		vp.add(celllist);
		vp.add(abonnieren);
		RootPanel.get("leftmenutree").add(vp);
	}
	
	class CellListCallback implements AsyncCallback<Vector<Nutzer>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			celllist.setRowData(0, result);
			celllist.setRowCount(result.size(), true);
		}
		
	}
	
	class meinePinnwandClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			DialogBoxAbonnieren abonnierenSuchen = new DialogBoxAbonnieren();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(abonnierenSuchen);
		}
		
	}

}
