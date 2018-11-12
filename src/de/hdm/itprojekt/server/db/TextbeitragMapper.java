package de.hdm.itprojekt.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojekt.shared.bo.Textbeitrag;

public class TextbeitragMapper {

	private static TextbeitragMapper textbeitragMapper = null;

	protected TextbeitragMapper() {
	};

	public static TextbeitragMapper textbeitragMapper() {
		if (textbeitragMapper == null) {
			textbeitragMapper = new TextbeitragMapper();
		}
		return textbeitragMapper;
	}

	public Textbeitrag createTextbeitrag(Textbeitrag textbeitrag) {

		Connection con = DBConnection.connection();
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(textbeitrag.getErzeugungsdatum().getTime());
		java.sql.Timestamp sqlDate1 = new java.sql.Timestamp(textbeitrag.getModifikationsdatum().getTime());

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM textbeitrag");
			if (rs.next()) {

				PreparedStatement stmt1 = con.prepareStatement(
						"INSERT INTO textbeitrag (id, pinnwandid, nutzerid, kommentarid, inhalt, erzeugungsdatum, modifikationsdatum) VALUES (?, ?, ?, ?, ?, ?, ?) ",

						Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1, textbeitrag.getId());
				stmt1.setInt(2, textbeitrag.getPinnwandID());
				stmt1.setInt(3, textbeitrag.getNutzerID());
				stmt1.setInt(4, textbeitrag.getKommentarID());
				stmt1.setString(5, textbeitrag.getInhalt());
				stmt1.setTimestamp(6, sqlDate);
				stmt1.setTimestamp(7, sqlDate1);

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
		return textbeitrag;
	}

	public Textbeitrag updateTextbeitrag(Textbeitrag textbeitrag) {
		String sql = "UPDATE textbeitrag SET `inhalt`= ? `modifikationsdatum`= ? WHERE id= ? ";
		java.sql.Timestamp sqlDate1 = new java.sql.Timestamp(textbeitrag.getModifikationsdatum().getTime());

		Connection con = DBConnection.connection();

		try {
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, textbeitrag.getInhalt());
			stmt.setTimestamp(2, sqlDate1);
			stmt.setInt(3, textbeitrag.getId());

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
		return textbeitrag;
	}

	public void deleteTextbeitrag(Textbeitrag textbeitrag) {

		Connection con = DBConnection.connection();

		try {

			PreparedStatement stmt = con.prepareStatement("DELETE FROM textbeitrag WHERE id= ?");

			stmt.setInt(1, textbeitrag.getId());

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

	public Vector<Textbeitrag> findAllTextbeitrag() {

		Connection con = DBConnection.connection();

		Vector<Textbeitrag> text = new Vector<Textbeitrag>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM textbeitrag ORDER BY id ASC");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Textbeitrag t = new Textbeitrag();
				t.setId(rs.getInt("id"));
				t.setPinnwandID(rs.getInt("pinnwandid"));
				t.setNutzerID(rs.getInt("nutzerID"));
				t.setKommentarID(rs.getInt("kommentarid"));
				t.setInhalt(rs.getString("inhalt"));
				t.setErzeugungsdatum(rs.getTimestamp("erzeugungsdatum"));
				t.setModifikationsdatum(rs.getTimestamp("modifikationsdatum"));

				text.addElement(t);

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
		return text;
	}

	public Vector<Textbeitrag> findTextbeitragByNutzerID(int nutzerid) {

		Connection con = DBConnection.connection();

		Vector<Textbeitrag> result = new Vector<Textbeitrag>();

		try {
			PreparedStatement stmt = con
					.prepareStatement("SELECT * FROM textbeitrag WHERE nutzerid= ? ORDER BY textbeitragid ASC ");

			stmt.setInt(1, nutzerid);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Textbeitrag beitrag = new Textbeitrag();

				beitrag.setId(rs.getInt("id"));
				beitrag.setPinnwandID(rs.getInt("pinnwandid"));
				beitrag.setNutzerID(rs.getInt("nutzerID"));
				beitrag.setKommentarID(rs.getInt("kommentarid"));
				beitrag.setInhalt(rs.getString("inhalt"));
				beitrag.setErzeugungsdatum(rs.getTimestamp("erzeugungsdatum"));
				beitrag.setModifikationsdatum(rs.getTimestamp("modifikationsdatum"));

				/**
				 * Hinzufügen des neuen Objektes zum Ergebnisvektor
				 */
				result.addElement(beitrag);
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

	public Vector<Textbeitrag> findTextbeitragByKommentarID(int kommentarid) {

		Connection con = DBConnection.connection();

		Vector<Textbeitrag> result = new Vector<Textbeitrag>();

		try {
			PreparedStatement stmt = con
					.prepareStatement("SELECT * FROM textbeitrag WHERE kommentarid= ? ORDER BY textbeitragid ASC ");

			stmt.setInt(1, kommentarid);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Textbeitrag beitrag = new Textbeitrag();

				beitrag.setId(rs.getInt("id"));
				beitrag.setPinnwandID(rs.getInt("pinnwandid"));
				beitrag.setNutzerID(rs.getInt("nutzerID"));
				beitrag.setKommentarID(rs.getInt("kommentarid"));
				beitrag.setInhalt(rs.getString("inhalt"));
				beitrag.setErzeugungsdatum(rs.getTimestamp("erzeugungsdatum"));
				beitrag.setModifikationsdatum(rs.getTimestamp("modifikationsdatum"));

				result.addElement(beitrag);
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

	public Vector<Textbeitrag> findTextbeitragByPinnwandID(int pinnwandid) {

		Connection con = DBConnection.connection();

		Vector<Textbeitrag> result = new Vector<Textbeitrag>();

		try {
			PreparedStatement stmt = con
					.prepareStatement("SELECT * FROM textbeitrag WHERE pinnwandid= ? ORDER BY textbeitrag ASC ");

			stmt.setInt(1, pinnwandid);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Textbeitrag beitrag = new Textbeitrag();

				beitrag.setId(rs.getInt("id"));
				beitrag.setPinnwandID(rs.getInt("pinnwandid"));
				beitrag.setNutzerID(rs.getInt("nutzerID"));
				beitrag.setKommentarID(rs.getInt("kommentarid"));
				beitrag.setInhalt(rs.getString("inhalt"));
				beitrag.setErzeugungsdatum(rs.getTimestamp("erzeugungsdatum"));
				beitrag.setModifikationsdatum(rs.getTimestamp("modifikationsdatum"));

				result.addElement(beitrag);
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