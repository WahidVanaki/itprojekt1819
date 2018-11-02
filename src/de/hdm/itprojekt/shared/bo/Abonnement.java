package de.hdm.itprojekt.shared.bo;

public class Abonnement extends BusinessObject {

	/**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;
	
	private int nutzerID =0;
	
	private int pinnwandID =0;

	public int getNutzerID() {
		return nutzerID;
	}

	public void setNutzerID(int nutzerID) {
		this.nutzerID = nutzerID;
	}

	public int getPinnwandID() {
		return pinnwandID;
	}

	public void setPinnwandID(int pinnwandID) {
		this.pinnwandID = pinnwandID;
	}
	

}
