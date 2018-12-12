package de.hdm.itprojekt.client.gui;


import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.SingleSelectionModel;
import de.hdm.itprojekt.shared.bo.Textbeitrag;

public class CellTableTextbeitrag extends CellTable<Textbeitrag>{



	private final SingleSelectionModel<Textbeitrag> ssm = new SingleSelectionModel<Textbeitrag>();

	public CellTableTextbeitrag(Textbeitrag textbeitrag) {
		run();
	}

	public void run() {
		this.setSelectionModel(ssm);
	}

	public class DateColumn extends Column<Textbeitrag, String> {

		public DateColumn(Cell<String> cell) {
			super(cell);
		}

		@Override
		public String getValue(Textbeitrag object) {
			if(object.getModifikationsdatum() == null){
				
				return object.getErzeugungsdatum().toString();
			}
			return object.getModifikationsdatum().toString();
		}

	}

	public class BeitragColumn extends Column<Textbeitrag, String> {


		public BeitragColumn(Cell<String> cell) {
			super(cell);
		}

		@Override
		public String getValue(Textbeitrag object) {
			return object.getInhalt();
		}

	}

}