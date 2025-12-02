package DAO;

import database.DatabaseConn;
import models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO {

	public boolean createProduit(String nom, double prix, int quantite, String categorieNom, String fournisseurNom,
			String description, String type, String datePeremption) {

		// Look up category and fournisseur IDs from their names
		CategorieDAO categorieDAO = new CategorieDAO();
		FournisseurDAO fournisseurDAO = new FournisseurDAO();

		Categorie categorie = categorieDAO.getCategorieByNom(categorieNom);
		if (categorie == null) {
			System.out.println("Erreur: Catégorie introuvable: " + categorieNom);
			return false;
		}

		Fournisseur fournisseur = fournisseurDAO.getFournisseurByNom(fournisseurNom);
		if (fournisseur == null) {
			System.out.println("Erreur: Fournisseur introuvable: " + fournisseurNom);
			return false;
		}

		String sql = "INSERT INTO produit (nom, prix, description, quantite, categorie_id, fournisseur_id, type, date_peremption) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, nom);
			pstmt.setDouble(2, prix);
			pstmt.setString(3, description);
			pstmt.setInt(4, quantite);
			pstmt.setInt(5, categorie.getId());
			pstmt.setInt(6, fournisseur.getId());
			pstmt.setString(7, type);
			pstmt.setString(8, datePeremption);

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("Produit créé: " + nom);
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Produit> getAllProduits() {
		List<Produit> produits = new ArrayList<>();
		String sql = "SELECT * FROM produit";

		try (Connection conn = DatabaseConn.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				int id = rs.getInt("id");
				String nom = rs.getString("nom");
				double prix = rs.getDouble("prix");
				String description = rs.getString("description");
				int quantite = rs.getInt("quantite");
				int categorieId = rs.getInt("categorie_id");
				int fournisseurId = rs.getInt("fournisseur_id");
				String type = rs.getString("type");
				String datePeremption = rs.getString("date_peremption");

				if ("Électro".equals(type)) {
					produits.add(new ProduitElectronique(id, nom, prix, quantite, categorieId,
							fournisseurId, description, datePeremption));
				} else if ("Périssable".equals(type)) {
					produits.add(new ProduitPerissable(id, nom, prix, quantite, categorieId,
							fournisseurId, description, datePeremption));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produits;
	}

	public Produit getProduitByNom(String nom) {
		String sql = "SELECT * FROM produit WHERE nom = ?";

		try (Connection conn = DatabaseConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, nom);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id");
				double prix = rs.getDouble("prix");
				String description = rs.getString("description");
				int quantite = rs.getInt("quantite");
				int categorieId = rs.getInt("categorie_id");
				int fournisseurId = rs.getInt("fournisseur_id");
				String type = rs.getString("type");
				String datePeremption = rs.getString("date_peremption");

				if ("Électro".equals(type)) {
					return new ProduitElectronique(id, nom, prix, quantite, categorieId,
							fournisseurId, description, datePeremption);
				} else if ("Perrisable".equals(type)) {
					return new ProduitPerissable(id, nom, prix, quantite, categorieId,
							fournisseurId, description, datePeremption);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateProduit(String nomActuel, String nouveauNom, double prix, String description) {
		String sql = "UPDATE produit SET nom = ?, prix = ?, description = ? WHERE nom = ?";

		try (Connection conn = DatabaseConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, nouveauNom);
			pstmt.setDouble(2, prix);
			pstmt.setString(3, description);
			pstmt.setString(4, nomActuel);

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("Produit modifié: " + nomActuel);
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateQuantite(String nom, int quantite) {
		String sql = "UPDATE produit SET quantite = ? WHERE nom = ?";

		try (Connection conn = DatabaseConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, quantite);
			pstmt.setString(2, nom);

			int result = pstmt.executeUpdate();
			if (result > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteProduit(String nom) {
		String sql = "DELETE FROM produit WHERE nom = ?";

		try (Connection conn = DatabaseConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, nom);
			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("Produit supprimé: " + nom);
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
