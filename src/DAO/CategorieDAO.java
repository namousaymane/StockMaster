package DAO;

import database.DatabaseConn;
import models.Categorie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO {

	public List<Categorie> getAllCategories() {
		List<Categorie> categories = new ArrayList<>();
		String sql = "SELECT id, nom FROM categorie";

		try (Connection conn = DatabaseConn.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				int id = rs.getInt("id");
				String nom = rs.getString("nom");
				categories.add(new Categorie(id, nom));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}

	public Categorie getCategorieByNom(String nom) {
		String sql = "SELECT id, nom FROM categorie WHERE nom = ?";

		try (Connection conn = DatabaseConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, nom);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id");
				return new Categorie(id, nom);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
