package de.hdm.itprojekt.client.gui;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;

import de.hdm.itprojekt.shared.bo.Nutzer;

import com.google.gwt.view.client.CellPreviewEvent.Handler;




public class CellTableNutzer extends CellTable<Nutzer>{

	private final MultiSelectionModel<Nutzer> ssmAuspraegung = new MultiSelectionModel<Nutzer>();
	private final Handler<Nutzer> selectionEventManager = DefaultSelectionEventManager
			.createCheckboxManager();

	public CellTableNutzer(){
		run();
	}
	public void run(){
		this.setSelectionModel(ssmAuspraegung, selectionEventManager);
	}


	public class NutzerColumn extends Column<Nutzer, String>{

		public NutzerColumn(Cell<String> cell) {
			super(cell);
		}

		@Override
		public String getValue(Nutzer object) {
			return object.getEmail();
		}
	}
	public class ButtonColumn extends Column<Nutzer, String>{

		public ButtonColumn(Cell<String> cell) {
			super(cell);
		}

		@Override
		public String getValue(Nutzer object) {
			return "x";
		}

	}
}
