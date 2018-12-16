package de.hdm.itprojekt.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.CellPreviewEvent.Handler;

import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Abonnement;
import de.hdm.itprojekt.shared.bo.Nutzer;
import de.hdm.itprojekt.shared.bo.Textbeitrag;

public class AbonniertePinnwand extends VerticalPanel {

	private Label nicknameLabel = new Label();
	private String nickname = null;
	private int aboID = 0;
	private VerticalPanel beitragPanel = new VerticalPanel();
	private VerticalPanel mainPanel = new VerticalPanel();
	private Button aboLoeschen = new Button("Entfolgen");
	private TextArea beitragTb = new TextArea();
	private Button postBt = new Button("Posten");
	private Nutzer nutzer = new Nutzer();
	private Textbeitrag textbeitrag = null;
	private SingleSelectionModel<Textbeitrag> ssm = new SingleSelectionModel<Textbeitrag>();

	// Create a CellTable
	private HorizontalPanel allTextbeitragCellTableContainer = new HorizontalPanel();
	private EditTextCell editTextCell = new EditTextCell();
	private CellTableTextbeitrag.DateColumn dateColumn = null;
	private CellTableTextbeitrag.BeitragColumn beitragColumn = null;
	private TextCell textCell = new TextCell();
	private CellTableTextbeitrag allTextbeitragCellTable = null;

	private static SocialMediaAdminAsync socialMediaVerwaltung = de.hdm.itprojekt.client.ClientsideSettings
			.getSocialMediaVerwaltung();

	public AbonniertePinnwand(final int nutzerID) {

		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));

		socialMediaVerwaltung.findAbonnementByNutzerID(nutzerID, new FindAboCallback());

		socialMediaVerwaltung.findTextbeitragByNutzerID(nutzerID, new CellTableCallback());

		nicknameLabel.setStylePrimaryName("h3");
		beitragTb.setStylePrimaryName("gwt-TextArea");
		beitragTb.setCharacterWidth(100);
		beitragTb.setVisibleLines(5);
		postBt.setStylePrimaryName("gwt-Button3");
		allTextbeitragCellTableContainer.setStylePrimaryName("cellTableWidgetContainerPanel");
		

		/**
		 * Hat zur Folge, dass das Erstellen von Textbeiträgen nur auf der
		 * eigenen Pinnwand möglich ist.
		 */
		if (nutzerID == nutzer.getId()) {
			beitragPanel.add(beitragTb);
			beitragPanel.add(postBt);
		}

		postBt.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				socialMediaVerwaltung.createTextbeitrag(nutzer.getId(), beitragTb.getValue(),
						new AsyncCallback<Textbeitrag>() {

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Fehler: " + caught.getMessage());

							}

							@Override
							public void onSuccess(Textbeitrag result) {
								AbonniertePinnwand pw = new AbonniertePinnwand(nutzerID);
							}
						});
			}
		});

		socialMediaVerwaltung.findNutzerByID(nutzerID, new AsyncCallback<Nutzer>() {

			@Override
			public void onSuccess(Nutzer result) {
				nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
				if (nutzer.getId() == result.getId()) {
					nicknameLabel.setText("Du befindest dich auf deiner Pinnwand " + result.getVorname());
				} else {

					nicknameLabel.setText("Du befindest dich auf der Pinnwand von: " + result.getVorname());
				}
				nickname = result.getNickname();
				aboID = result.getId();
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim Laden " + caught.getMessage());
			}
		});

		aboLoeschen.setHTML("<img src = 'images/unfollow.png'/>");
		aboLoeschen.setPixelSize(40, 40);
		aboLoeschen.setStylePrimaryName("unfollowButton");
		mainPanel.add(nicknameLabel);
		if (nutzerID != nutzer.getId()) {
			aboLoeschen.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					socialMediaVerwaltung.deleteAbonnement(nutzer.getId(), nutzerID, new AbonnementLoeschenCallback());
				}

			});
			mainPanel.add(aboLoeschen);

		}

		allTextbeitragCellTable = new CellTableTextbeitrag(textbeitrag);
		dateColumn = allTextbeitragCellTable.new DateColumn(textCell);
		beitragColumn = allTextbeitragCellTable.new BeitragColumn(editTextCell) {

			@Override
			public void onBrowserEvent(Context context, Element elem, Textbeitrag object, NativeEvent event) {

				if (nutzerID == nutzer.getId()) {
					if (event.getKeyCode() == KeyCodes.KEY_ENTER) {

						setFieldUpdater(new FieldUpdater<Textbeitrag, String>() {

							@Override
							public void update(int index, Textbeitrag object, String value) {
								object.setInhalt(value);
								if (value == "") {
									socialMediaVerwaltung.deleteTextbeitrag(object, new AsyncCallback<Void>() {

										@Override
										public void onFailure(Throwable caught) {
											Window.alert("Fehler: " + caught.getMessage());

										}

										@Override
										public void onSuccess(Void result) {
											AbonniertePinnwand abonniertePinnwand = new AbonniertePinnwand(nutzerID);

										}
									});
								}
								socialMediaVerwaltung.saveTextbeitrag(object, new AsyncCallback<Void>() {

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Fehler: " + caught.getMessage());
									}

									@Override
									public void onSuccess(Void result) {
										AbonniertePinnwand abonniertePinnwand = new AbonniertePinnwand(nutzerID);
									}

								});

							}

						});
					}
					super.onBrowserEvent(context, elem, object, event);

				}
			}
		};

		allTextbeitragCellTable.addCellPreviewHandler(new PreviewClickHandler());
		allTextbeitragCellTableContainer.clear();
		allTextbeitragCellTableContainer.add(allTextbeitragCellTable);
		allTextbeitragCellTable.addColumn(dateColumn);
		allTextbeitragCellTable.addColumn(beitragColumn);
		allTextbeitragCellTableContainer.clear();
		allTextbeitragCellTableContainer.add(allTextbeitragCellTable);
		mainPanel.add(beitragPanel);
		mainPanel.add(allTextbeitragCellTableContainer);
		this.add(mainPanel);
		super.onLoad();
		RootPanel.get("content").clear();
		RootPanel.get("content").add(mainPanel);
		allTextbeitragCellTable.setStylePrimaryName("AllCellTable");
		beitragColumn.setCellStyleNames("CellStyle");
		dateColumn.setCellStyleNames("CellStyleInfoCells");

	}

	class CellTableCallback implements AsyncCallback<Vector<Textbeitrag>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Beim Laden der Daten ist ein Fehler aufgetreten" + caught.getMessage());

		}

		@Override
		public void onSuccess(Vector<Textbeitrag> result) {
			Range range = new Range(0, result.size());
			allTextbeitragCellTable.setVisibleRangeAndClearData(range, true);

			for (Textbeitrag textbeitrag : result) {
				CellTableTextbeitrag cellTableTextbeitrag = new CellTableTextbeitrag(textbeitrag);

			}
			allTextbeitragCellTable.setRowCount(result.size(), true);
			allTextbeitragCellTable.setRowData(0, result);
		}

	}

	class FindAboCallback implements AsyncCallback<Vector<Abonnement>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Beim Laden der Daten ist ein Fehler aufgetreten" + caught.getMessage());

		}

		@Override
		public void onSuccess(Vector<Abonnement> result) {

		}

	}

	class AbonnementLoeschenCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden " + caught.getMessage());

		}

		@Override
		public void onSuccess(Void result) {

			Window.alert("User entfolgt");
			RootPanel.get("content").clear();
			RootPanel.get("leftmenutree").clear();
			RootPanel.get("leftmenutree").add(new Toolbar());
			RootPanel.get("leftmenutree").add(new AllAbonnementView());

		}

	}

	public class PreviewClickHandler implements Handler<Textbeitrag> {

		long initialClick = -1000;

		@Override
		public void onCellPreview(CellPreviewEvent<Textbeitrag> event) {

			long clickedAt = System.currentTimeMillis();

			if (event.getNativeEvent().getType().contains("click")) {

				/*
				 * Wenn nicht mehr als 300ms zwischen zwei Klicks liegen, so
				 * wird ein Doppelklick ausgelöst und die Profilansicht des
				 * angeklickten Kontaktes geöffnet. Andernfalls wird der Kontakt
				 * lediglich selektiert.
				 */

				if (clickedAt - initialClick < 300) {

					KommentarForm kf = new KommentarForm(event.getValue());
				}

				initialClick = System.currentTimeMillis();

				final Textbeitrag textbeitrag = event.getValue();

			}

		}

	}
}