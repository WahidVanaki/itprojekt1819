package de.hdm.itprojekt.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.client.ClientsideSettings;
import de.hdm.itprojekt.shared.SocialMediaAdminAsync;
import de.hdm.itprojekt.shared.bo.Textbeitrag;

public class StartSeiteForm extends MainFrame {

	private static SocialMediaAdminAsync socialMediaVerwaltung = ClientsideSettings.getSocialMediaVerwaltung();

	private VerticalPanel vp = new VerticalPanel();
	private HTML headline = new HTML("<h2>Willkommen auf deiner Pinnwand</h2>");
	private TextArea textbeitragVerfassen = new TextArea();
	private Button textbeitragPosten = new Button("Beitrag teilen");

	public StartSeiteForm() {
		super.onLoad();

	}

	@Override
	protected void run() {

		AllAbonnementView allAbonnementview = new AllAbonnementView();
		Menubar menubar = new Menubar();
		textbeitragVerfassen.setHeight("1000");
		textbeitragVerfassen.setWidth("1000");
		textbeitragPosten.addClickHandler(new PostenHandler());
		vp.add(headline);
		vp.add(textbeitragVerfassen);
		vp.add(textbeitragPosten);
		RootPanel.get("content").add(vp);

	}

	class PostenHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Textbeitrag textbeitrag = new Textbeitrag();
			textbeitrag.setId(Integer.parseInt(Cookies.getCookie("id")));
			socialMediaVerwaltung.createTextbeitrag(textbeitrag.getPinnwandID(), textbeitrag.getNutzerID(),
					textbeitrag.getKommentarID(), textbeitragVerfassen.getValue(), new CreateTextbeitragCallback());
			Window.alert("funktioniert");
		}

	}

	public class CreateTextbeitragCallback implements AsyncCallback<Textbeitrag> {

		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Posten " + caught.getMessage());
		}

		public void onSuccess(Textbeitrag result) {
			// TODO Auto-generated method stub
			Textbeitrag t = new Textbeitrag();
			t = result;
			TextBox tb = new TextBox();
			VerticalPanel vpanel = new VerticalPanel();
			tb.setValue(t.getInhalt());

			vpanel.add(tb);

			RootPanel.get("content").add(vpanel);

		}

	}
}