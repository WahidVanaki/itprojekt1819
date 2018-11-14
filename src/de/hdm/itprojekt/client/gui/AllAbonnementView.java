package de.hdm.itprojekt.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojekt.client.ClientsideSettings;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Nutzer;

public class AllAbonnementView extends LeftSideFrame{

	private static SocialMediaAdminAsync socialMediaVerwaltung = ClientsideSettings.getSocialMediaVerwaltung();
	
	private VerticalPanel vp = new VerticalPanel();
	private HorizontalPanel hp = new HorizontalPanel();
	private Button abonnieren = new Button("Abonnieren");
	private Vector<Nutzer> nutzerV = new Vector<Nutzer>();
	
	private SingleSelectionModel<Nutzer> ssmNutzer = new SingleSelectionModel<Nutzer>();
	
	private CellList<Nutzer> celllist = new CellList<Nutzer>(new CellListAbonnement());
	
	@Override
	protected void run() {
		// TODO Auto-generated method stub
		abonnieren.addClickHandler(new meinePinnwandClickHandler());
		Nutzer nutzer = new Nutzer();
		Nutzer nutzer2 = new Nutzer();
		nutzer.setNickname("MaschineTest");
		nutzer2.setNickname("RichtigeGruppe");
		

		nutzerV.add(nutzer);
		nutzerV.add(nutzer2);
		
		celllist.setRowData(0, nutzerV);
		celllist.setRowCount(nutzerV.size(), true);
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
