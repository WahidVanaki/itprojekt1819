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
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.client.KommentarNutzerWrapper;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Kommentar;
import de.hdm.itprojekt.shared.bo.Nutzer;
import de.hdm.itprojekt.shared.bo.Textbeitrag;

public class KommentarForm extends VerticalPanel {

	// Textbeitrag Widgets
	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel beitragPanel = new VerticalPanel();
	private Label nutzerLb = new Label();
	private TextArea beitrag = new TextArea();
	private Label kommentarLb = new Label("Diesen Post kommentieren:");
	private TextArea kommentarTa = new TextArea();
	private Button kommentieren = new Button("Kommentieren");
	private Label datumLb = new Label();
	private FlexTable ft = new FlexTable();

	// Kommentar Widgets
	private HorizontalPanel allKommentarContainer = new HorizontalPanel();
	private EditTextCell editTextCell = new EditTextCell();
	private TextCell textCell = new TextCell();
	private KommentarNutzerWrapper kommentar = null;
	private CellTableKommentar allKommentarCellTable = null;
	private CellTableKommentar.DateColumn dateColumn = null;
	private CellTableKommentar.KommentarColumn kommentarColumn = null;
	private CellTableKommentar.NutzerColumn nutzerColumn = null;
	private static SocialMediaAdminAsync socialMediaVerwaltung = de.hdm.itprojekt.client.ClientsideSettings
			.getSocialMediaVerwaltung();

	public KommentarForm(final Textbeitrag textbeitrag) {
		final Nutzer nutzer = new Nutzer();
		nutzer.setId(Integer.parseInt(Cookies.getCookie("id")));
		nutzer.setNickname(Cookies.getCookie("nickname"));

		kommentieren.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				socialMediaVerwaltung.createKommentar(textbeitrag.getId(), nutzer.getId(), kommentarTa.getValue(),
						new AsyncCallback<Kommentar>() {

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Fehler: " + caught.getMessage());
							}

							@Override
							public void onSuccess(Kommentar result) {
								Window.alert("Kommentar erfolgreich angelegt.");

								socialMediaVerwaltung.findKommentarByTextbeitragId(textbeitrag.getId(),
										new ReloadCallback());

							}
						});

			}
		});

		// kommentarTa.addKeyDownHandler(new KeyDownHandler() {
		//
		// @Override
		// public void onKeyDown(KeyDownEvent event) {
		// if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		// kommentarTa.getValue();
		//
		// Window.alert("" + textbeitrag.getId() + textbeitrag.getNutzerID() +
		// kommentarTa.getValue() +
		// nutzer.getNickname());
		//
		// socialMediaVerwaltung.createKommentar(textbeitrag.getId(),
		// textbeitrag.getNutzerID(), kommentarTa.getValue(),
		// nutzer.getNickname(), new CreateKommentarCallback());
		//
		//
		//
		// }
		// }
		// });

		socialMediaVerwaltung.findNutzerByID(textbeitrag.getNutzerID(), new AsyncCallback<Nutzer>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Nutzer result) {
				nutzerLb.setText("Post von: " + result.getNickname());
			}
		});

		beitrag.setEnabled(false);
		beitrag.setText(textbeitrag.getInhalt());

		if (textbeitrag.getModifikationsdatum() != null) {
			datumLb.setText("vom: " + textbeitrag.getModifikationsdatum().toString());
		} else {
			datumLb.setText("vom: " + textbeitrag.getErzeugungsdatum().toString());
		}

		ft.setWidget(0, 0, nutzerLb);
		ft.setWidget(0, 1, datumLb);
		ft.setWidget(1, 0, beitrag);
		ft.setWidget(2, 0, kommentarLb);
		ft.setWidget(3, 0, kommentarTa);
		ft.setWidget(4, 0, kommentieren);

		beitragPanel.add(ft);

		socialMediaVerwaltung.findKommentarByTextbeitragId(textbeitrag.getId(), new CellTableKommentarCallback());

		allKommentarCellTable = new CellTableKommentar(kommentar);
		nutzerColumn = allKommentarCellTable.new NutzerColumn(textCell);
		dateColumn = allKommentarCellTable.new DateColumn(textCell);
		kommentarColumn = allKommentarCellTable.new KommentarColumn(editTextCell) {

			@Override
			public void onBrowserEvent(Context context, Element elem, KommentarNutzerWrapper object,
					NativeEvent event) {

				if (nutzer.getId() == object.getNutzer().getId()) {

					if (event.getKeyCode() == KeyCodes.KEY_ENTER) {

						setFieldUpdater(new FieldUpdater<KommentarNutzerWrapper, String>() {

							@Override
							public void update(int index, KommentarNutzerWrapper object, String value) {
								object.getKommentar().setInhaltKommentar(value);
								if (value == "") {
									socialMediaVerwaltung.deleteKommentar(object.getKommentar(),
											new AsyncCallback<Void>() {

												@Override
												public void onFailure(Throwable caught) {
													Window.alert("Fehler: " + caught.getMessage());

												}

												@Override
												public void onSuccess(Void result) {
													Window.alert("Ihr Kommentar wurde erfolgreich entfernt.");
													socialMediaVerwaltung.findKommentarByTextbeitragId(
															textbeitrag.getId(), new ReloadCallback());

												}
											});
								}
								socialMediaVerwaltung.saveKommentar(object.getKommentar(), new AsyncCallback<Void>() {

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Fehler: " + caught.getMessage());
									}

									@Override
									public void onSuccess(Void result) {
										Window.alert("Ihr Kommentar wurde erfolgreich geändert.");
										socialMediaVerwaltung.findKommentarByTextbeitragId(textbeitrag.getId(),
												new ReloadCallback());

									}

								});

							}

						});

					}
					super.onBrowserEvent(context, elem, object, event);
				}
			}
		};

		allKommentarContainer.clear();
		allKommentarContainer.add(allKommentarCellTable);
		allKommentarCellTable.addColumn(kommentarColumn);
		allKommentarCellTable.addColumn(nutzerColumn);
		allKommentarCellTable.addColumn(dateColumn);
		allKommentarContainer.add(allKommentarCellTable);
		mainPanel.add(beitragPanel);
		mainPanel.add(allKommentarContainer);
		this.add(mainPanel);
		super.onLoad();
		RootPanel.get("content").clear();
		RootPanel.get("content").add(mainPanel);

	}

	class CellTableKommentarCallback implements AsyncCallback<Vector<KommentarNutzerWrapper>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<KommentarNutzerWrapper> result) {

			allKommentarCellTable.setRowCount(result.size(), true);
			allKommentarCellTable.setRowData(0, result);

		}

	}

	class CreateKommentarCallback implements AsyncCallback<Kommentar> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Kommentar result) {
			Window.alert("Ihr Kommentar wurde dem Beitrag erfolgreich hinzugefügt.");

		}

	}

	class ReloadCallback implements AsyncCallback<Vector<KommentarNutzerWrapper>> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler: " + caught.getMessage());

		}

		@Override
		public void onSuccess(Vector<KommentarNutzerWrapper> result) {

			kommentarTa.setValue(null);
			allKommentarCellTable.setRowCount(result.size(), true);
			allKommentarCellTable.setRowData(0, result);
		}

	}
}
