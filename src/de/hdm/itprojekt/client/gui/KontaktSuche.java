package de.hdm.itprojekt.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojekt.client.ClientsideSettings;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Abonnement;
import de.hdm.itprojekt.shared.bo.Nutzer;

public class KontaktSuche extends VerticalPanel {

	/**
	 * Instanziierung des Proxy Objekts für den Editor
	 */
	private static SocialMediaAdminAsync socialmediaVerwaltung = ClientsideSettings.getSocialMediaVerwaltung();

	/**
	 * Instanzierung der GUI-Elemente
	 */
	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable ft = new FlexTable();
	private Label lb1 = new Label("Wen suchen Sie?");
	private Button abonnieren = new Button("Suchen");
	private Button abbrechen = new Button("Zur Startseite");
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	final SuggestBox suggestBox = new SuggestBox(oracle);
	private Nutzer nutzer = new Nutzer();

	private ClickableTextCell tCell = new ClickableTextCell();
	private SingleSelectionModel<Nutzer> ssm = new SingleSelectionModel<Nutzer>();
	private CellTableNutzer nutzerCt = new CellTableNutzer();
	private CellTableNutzer.VornameColumn vornameColumn = nutzerCt.new VornameColumn(tCell);
	private CellTableNutzer.NachnameColumn nachnameColumn = nutzerCt.new NachnameColumn(tCell);
	private CellTableNutzer.NicknameColumn nicknameColumn = nutzerCt.new NicknameColumn(tCell);
	private CellTableNutzer.EmailColumn emailColumn = nutzerCt.new EmailColumn(tCell);

	private Vector<Nutzer> resultNutzer = new Vector<Nutzer>();

	public KontaktSuche() {

		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));

		abbrechen.addClickHandler(new AbbrechenClickHandler());

		socialmediaVerwaltung.findAllNutzer(new AsyncCallback<Vector<Nutzer>>() {

			@Override
			public void onSuccess(Vector<Nutzer> result) {
				for (Nutzer nutzer : result) {
					oracle.add(nutzer.getEmail());
					oracle.add(nutzer.getNachname());
					oracle.add(nutzer.getVorname());
					oracle.add(nutzer.getNickname());
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler): " + caught.getMessage());
			}
		});

		abonnieren.addClickHandler(new AbonnierenClickHandler());
		ssm.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				socialmediaVerwaltung.createAbonnement(nutzer.getId(), ssm.getSelectedObject().getId(),
						new CreateAboCallback());
			}

		});

		nutzerCt.setSelectionModel(ssm);
		nutzerCt.addColumn(vornameColumn);
		nutzerCt.addColumn(nachnameColumn);
		nutzerCt.addColumn(nicknameColumn);
		nutzerCt.addColumn(emailColumn);

		ft.setWidget(0, 0, lb1);
		ft.setWidget(1, 0, suggestBox);
		ft.setWidget(2, 0, abonnieren);
		ft.setWidget(2, 1, abbrechen);

		mainPanel.add(ft);

		this.add(mainPanel);

		RootPanel.get("content").clear();
		RootPanel.get("content").add(mainPanel);
	}

	class AbonnierenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			final String searchItem = suggestBox.getValue();

			socialmediaVerwaltung.findAllNutzer(new AsyncCallback<Vector<Nutzer>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Fehler: " + caught.getMessage());
				}

				@Override
				public void onSuccess(Vector<Nutzer> result) {

					for (Nutzer nutzer : result) {
						if (nutzer.getVorname().contentEquals(searchItem)) {
							int searchNutzerID = nutzer.getId();
							nutzer.setId(searchNutzerID);
							resultNutzer.add(nutzer);
						}
						if (nutzer.getNachname().contentEquals(searchItem)) {
							int searchNutzerID = nutzer.getId();
							nutzer.setId(searchNutzerID);
							resultNutzer.add(nutzer);

						}
						if (nutzer.getNickname().contentEquals(searchItem)) {
							int searchNutzerID = nutzer.getId();
							nutzer.setId(searchNutzerID);
							resultNutzer.add(nutzer);

						}
						if (nutzer.getEmail().contentEquals(searchItem)) {
							int searchNutzerID = nutzer.getId();
							nutzer.setId(searchNutzerID);
							resultNutzer.add(nutzer);

						}
					}
					socialmediaVerwaltung.findAllNutzerById(new SucheCallBack());
				}

			});

		}

	}

	class AbbrechenClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			StartSeiteForm startseiteForm = new StartSeiteForm();
		}

	}

	class SucheCallBack implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			result = resultNutzer;

			nutzerCt.setRowCount(resultNutzer.size(), true);
			nutzerCt.setRowData(0, result);
			resultNutzer.clear();

			VerticalPanel cellTableContainer = new VerticalPanel();

			cellTableContainer.clear();
			cellTableContainer.add(nutzerCt);

			RootPanel.get("content").add(mainPanel);
			RootPanel.get("content").add(cellTableContainer);

		}

	}

	class CreateAboCallback implements AsyncCallback<Abonnement> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Abonnement result) {

			if (result == null) {
				Window.alert("Sie folgen diesem Nutzer bereits.");
			} else {

				Window.alert("Sie folgen jetzt " + ssm.getSelectedObject().getNickname());
				StartSeiteForm startseiteform = new StartSeiteForm();
				Toolbar toolbar = new Toolbar();
				AllAbonnementView apv = new AllAbonnementView();
				RootPanel.get("leftmenutree").clear();
				RootPanel.get("leftmenutree").add(toolbar);
				RootPanel.get("leftmenutree").add(apv);
			}

		}

	}
}