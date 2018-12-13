package de.hdm.itprojekt.client.gui;

import java.util.Vector;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojekt.client.ClientsideSettings;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Nutzer;

public class AllAbonnementView extends LeftSideFrame {

	/**
	 * Instanziierung des Proxy Objekts für den Editor
	 */
	private static SocialMediaAdminAsync socialmediaVerwaltung = ClientsideSettings.getSocialMediaVerwaltung();

	/**
	 * Instanzierung der GUI-Elemente
	 */
	private VerticalPanel vpanel = new VerticalPanel();
	private SingleSelectionModel<Nutzer> ssm = new SingleSelectionModel<Nutzer>();
	private Nutzer nutzer = new Nutzer();

	// Create a CellList that uses the cell.
	private CellList<Nutzer> cellList = new CellList<Nutzer>(new CellListAbonnement());

	public AllAbonnementView() {

		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		socialmediaVerwaltung.findNutzerByAbo(nutzer.getId(), new CellListCallback());

		cellList.setSelectionModel(ssm);
		ssm.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {

				if (ssm.getSelectedObject().getNickname() != null) {
					AbonniertePinnwand abonniertePinnwand = new AbonniertePinnwand(ssm.getSelectedObject().getId());
				} else {
					StartSeiteForm startseiteForm = new StartSeiteForm();
				}

			}
		});

		vpanel.add(cellList);
		this.add(vpanel);

		onLoad();

	}

	@Override
	protected void run() {

	}

	class CellListCallback implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden" + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			cellList.setRowData(0, result);
			cellList.setRowCount(result.size(), true);

		}
	}
}