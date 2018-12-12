package de.hdm.itprojekt.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojekt.server.db.DBConnection;
import de.hdm.itprojekt.shared.bo.*;

public class AbonnementMapper {

	/**
	 * Die Klasse NutzerMapper wird nur einmal instantiiert. Hier spricht man
	 * von einem sogenannten Singleton. Durch static nur einmal vorhanden.
	 */
	private static AbonnementMapper abonnementMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit "new" neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected AbonnementMapper() {
	};

	/**
	 * Kann aufgerufen werden durch NutzerMapper nutzerMapper. Sie stellt die
	 * Singelton-Eigenschaft sicher. Methode soll nur über diese statische
	 * Methode aufgerufen werden
	 * 
	 * @return nutzerMapper
	 * @see createNutzer
	 */
	public static AbonnementMapper abonnementMapper() {
		if (abonnementMapper == null) {
			abonnementMapper = new AbonnementMapper();
		}
		return abonnementMapper;
	}

	/**
	 * Die Methode "createAbonnement" ermöglicht das Einfügen von Objekten "Nutzer".
	 * 
	 * @param abonnement
	 * @return
	 * @see abonnementMapper
	 */
	public Abonnement createAbonnement(Abonnement abonnement) {

		/**
		 * Verbindung zur Datenbank wird aufgebaut
		 */
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			/**
			 * Zunächst schauen wir nach, welches der momentan höchste
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM abonnement");
			if (rs.next()) {

				/**
				 * Druchführen der Einfüge Operation via Prepared Statement
				 */
				PreparedStatement stmt1 = con.prepareStatement(
						"INSERT INTO abonnement (id, nutzerid, pinnwandid) VALUES (?, ?, ?) ",

						Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1, abonnement.getId());
				stmt1.setInt(2, abonnement.getNutzerID());
				stmt1.setInt(3, abonnement.getPinnwandID());

				System.out.println(stmt);
				stmt1.executeUpdate();
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return abonnement;
	}

	public Abonnement updateAbonnement(Abonnement abonnement) {

		/**
		 * Verbindung zur DB Connection aufbauen
		 */
		Connection con = DBConnection.connection();

		try {

			/**
			 * Durchführung der Update-Operation via Prepared Statement
			 */
			PreparedStatement stmt = con.prepareStatement(
					"UPDATE `abonnement` SET `nutzerid`= ? `pinnwandid`= ? WHERE id= ?");

			stmt.setInt(1, abonnement.getId());
			stmt.setInt(2, abonnement.getNutzerID());
			stmt.setInt(3, abonnement.getPinnwandID());
		
		

			stmt.executeUpdate();

			System.out.println("Updated");

		} catch (SQLException e2) {
			e2.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return abonnement;
	}

	/**
	 * Die Methode "deleteAbonnement" ermöglicht das Löschen von einem Objekt
	 * "Abonnement".
	 * 
	 * @param abonnement
	 */
	public void deleteAbonnement(Abonnement abonnement) {

		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();

		try {

			/**
			 * Durchführen der Löschoperation
			 */
			PreparedStatement stmt = con.prepareStatement("DELETE FROM abonnement WHERE id= ?");

			stmt.setInt(1, abonnement.getNutzerID());
			stmt.setInt(2, abonnement.getPinnwandID());
			stmt.executeUpdate();

		} catch (SQLException e2) {
			e2.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * Methode "findAllAbonnement" um alle Abonnement aus dem Vector<Abonnement>
	 * zurückzugeben
	 * 
	 * @param abonnementid
	 * @return result - gibt als Result alle Abonnement zurück
	 */
	public Abonnement findAllAbonnement(int nutzerid, int pinnwandid) {

		/**
		 * Verbindung zur Datenbank aufbauen
		 */
		Connection con = DBConnection.connection();

		/**
		 * Es wird ein Vector um Nutzer darin zu speichern
		 */
		Abonnement a = new Abonnement();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM `abonnement` ORDER BY `nutzerid` DESC");

			stmt.setInt(1, nutzerid);
			stmt.setInt(2, pinnwandid);
			ResultSet rs = stmt.executeQuery();
			
			/**
			 * Für jeden Eintrag Nutzer ein Nutzer-Objekt erstellt.
			 */
			while (rs.next()) {
				Abonnement abonnement = new Abonnement();

				abonnement.setId(rs.getInt("id"));
				abonnement.setNutzerID(rs.getInt("nutzerid"));
				abonnement.setPinnwandID(rs.getInt("pinnwandid"));
				

				a = abonnement;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		/**
		 * Ergebnisvektor zurückgeben
		 */
		return a;
	}

	public Vector <Abonnement> findAbonnementByNutzerID(int nutzerID) {

		/**
		 * Verbindung zur DB Connection
		 */
		Connection con = DBConnection.connection();

		Vector<Abonnement> result = new Vector<Abonnement>();

		try {

			PreparedStatement stmt = con.prepareStatement("SELECT * FROM `abonnement` WHERE `nutzerid` = ?");

			stmt.setInt(1, nutzerID);
			ResultSet rs = stmt.executeQuery();

			/**
			 * Für jeden Eintrag im Suchergebnis wird nun ein Nutzer-Objekt
			 * erstellt.
			 */
			while (rs.next()) {
				Abonnement abonnement = new Abonnement();

				abonnement.setId(rs.getInt("id"));
				abonnement.setNutzerID(rs.getInt("nutzerid"));
				abonnement.setPinnwandID(rs.getInt("pinnwandid"));
				
				result.addElement(abonnement);			
				
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		/**
		 * Ergebnisvektor zurückgeben
		 */
		finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return result;
	}
}