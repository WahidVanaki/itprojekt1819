package de.hdm.itprojekt.server.db;

import de.hdm.itprojekt.shared.bo.Nutzer;
import de.hdm.itprojekt.shared.bo.Textbeitrag;

public class Testmapper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Textbeitrag t = new Textbeitrag();
		t.setId(1);
		t.setNutzerID(1);
		
		
		System.out.println(TextbeitragMapper.textbeitragMapper().findAllTextbeitrag());

	}

}
