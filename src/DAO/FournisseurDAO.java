package dao;

import database.DatabaseConn;
import models.Fournisseur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FournisseurDAO {

	public boolean createFournisseur(String nom) {
		String sql = "INSERT INTO fournisseur (nom) VALUES (?)";

		try (Connection conn = DatabaseConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, nom);
			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("Fournisseur créé: " + nom);
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Fournisseur> getAllFournisseurs() {
		List<Fournisseur> fournisseurs = new ArrayList<>();
		String sql = "SELECT id, nom FROM fournisseur";

		try (Connection conn = DatabaseConn.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				int id = rs.getInt("id");
				String nom = rs.getString("nom");
				fournisseurs.add(new Fournisseur(id, nom));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fournisseurs;
	}

	public Fournisseur getFournisseurByNom(String nom) {
		String sql = "SELECT id, nom FROM fournisseur WHERE nom = ?";

		try (Connection conn = DatabaseConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, nom);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id");
				return new Fournisseur(id, nom);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateFournisseur(String nomActuel, String nouveauNom) {
		String sql = "UPDATE fournisseur SET nom = ? WHERE nom = ?";

		try (Connection conn = DatabaseConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, nouveauNom);
			pstmt.setString(2, nomActuel);
			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("Fournisseur modifié: " + nomActuel);
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteFournisseur(String nom) {
		String sql = "DELETE FROM fournisseur WHERE nom = ?";

		try (Connection conn = DatabaseConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, nom);
			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("Fournisseur supprimé: " + nom);
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
