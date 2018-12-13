package de.hdm.itprojekt.client.gui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.itprojekt.shared.bo.Nutzer;

public class CellListAbonnement extends AbstractCell<Nutzer> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Nutzer value, SafeHtmlBuilder sb) {
		if (value == null) {
			return;
		}

		sb.appendEscaped(value.getNickname());
	}
}
