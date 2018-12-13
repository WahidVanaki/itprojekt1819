package de.hdm.itprojekt.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojekt.shared.bo.Kommentar;

public class KommentarMapper {

	private static KommentarMapper kommentarMapper = null;

	protected KommentarMapper() {

	};

	public static KommentarMapper kommentarMapper() {
		if (kommentarMapper == null) {
			KommentarMapper kommentar = new KommentarMapper();
		}
		return kommentarMapper;
	}

	public Kommentar createKommentar(Kommentar kommentar) {

		Connection con = DBConnection.connection();
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(kommentar.getErzeugungsdatum().getTime());
		java.sql.Timestamp sqlDate1 = new java.sql.Timestamp(kommentar.getModifikationsdatum().getTime());

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAXid(id) AS maxid FROM kommentar");
			if (rs.next()) {

				PreparedStatement stmt1 = con.prepareStatement(
						"INSERT INTO kommentar (id, textbeitragid, nutzerid, inhalt, erzeugungsdatum, modifikationsdatum"
								+ "VALUES(?, ?, ?, ?, ?, ? )",

						Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1, kommentar.getId());
				stmt1.setInt(2, kommentar.getTextbeitragID());
				stmt1.setInt(3, kommentar.getNutzerID());
				stmt1.setString(4, kommentar.getInhaltKommentar());
				stmt1.setTimestamp(5, sqlDate);
				stmt1.setTimestamp(6, sqlDate1);

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
		return kommentar;
	}

	public Kommentar updateKommentar(Kommentar kommentar) {
		String sql = "UPDATE kommentar SET `inhalt`= ? `modifikationsdatum`= ? WHERE id= ?";
		java.sql.Timestamp sqlDate2 = new java.sql.Timestamp(kommentar.getModifikationsdatum().getTime());

		Connection con = DBConnection.connection();

		try {
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, kommentar.getInhaltKommentar());
			stmt.setTimestamp(2, sqlDate2);
			stmt.setInt(3, kommentar.getId());

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
		return kommentar;
	}

	public void deleteKommentar(Kommentar kommentar) {

		Connection con = DBConnection.connection();

		try {
			PreparedStatement stmt = con.prepareStatement("DELETE FROM kommentar WHERE id= ? ");

			stmt.setInt(1, kommentar.getId());

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

	public Vector<Kommentar> findAllKommentare() {

		Connection con = DBConnection.connection();

		Vector<Kommentar> result = new Vector<Kommentar>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM `kommentar` ORDER BY `erzeugungsdatum` DESC");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Kommentar kommentar = new Kommentar();

				kommentar.setId(rs.getInt("id"));
				kommentar.setTextbeitragID(rs.getInt("textbeitragid"));
				kommentar.setNutzerID(rs.getInt("nutzerid"));
				kommentar.setInhaltKommentar(rs.getString("inhalt"));
				kommentar.setErzeugungsdatum(rs.getTimestamp("erzeugungsdatum"));
				kommentar.setModifikationsdatum(rs.getTimestamp("modifikationsdatum"));

				result.addElement(kommentar);
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
		return result;
	}

	public Vector<Kommentar> findKommentarByNutzerID(int nutzerid) {

		Connection con = DBConnection.connection();

		Vector<Kommentar> result = new Vector<Kommentar>();

		try {

			PreparedStatement stmt = con
					.prepareStatement("SELECT * FROM kommentar WHERE nutzerid= ? ORDBER BY kommentarid ASC ");

			stmt.setInt(1, nutzerid);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Kommentar kommentar = new Kommentar();

				kommentar.setId(rs.getInt("id"));
				kommentar.setTextbeitragID(rs.getInt("textbeitragid"));
				kommentar.setNutzerID(rs.getInt("nutzerid"));
				kommentar.setInhaltKommentar(rs.getString("inhalt"));
				kommentar.setErzeugungsdatum(rs.getTimestamp("erzeugungsdatum"));
				kommentar.setModifikationsdatum(rs.getTimestamp("modifikationsdatum"));

				result.addElement(kommentar);
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
		return result;
	}

	public Vector<Kommentar> findKommentarByTextbeitragId(int textbeitragId) {

		Connection con = DBConnection.connection();

		Vector<Kommentar> result = new Vector<Kommentar>();

		try {
			PreparedStatement stmt = con
					.prepareStatement("SELECT * FROM `kommentar` WHERE `textbeitragid`= ?" + " ORDER BY id DESC");

			stmt.setInt(1, textbeitragId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Kommentar kommentar = new Kommentar();

				kommentar.setId(rs.getInt("id"));
				kommentar.setTextbeitragID(rs.getInt("textbeitragid"));
				kommentar.setNutzerID(rs.getInt("nutzerid"));
				kommentar.setInhaltKommentar(rs.getString("inhalt"));
				kommentar.setErzeugungsdatum(rs.getTimestamp("erzeugungsdatum"));
				kommentar.setModifikationsdatum(rs.getTimestamp("modifikationsdatum"));

				result.addElement(kommentar);
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
		return result;
	}
}
