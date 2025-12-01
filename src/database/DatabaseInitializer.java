package database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = DatabaseConn.getConnection();
                Statement stmt = conn.createStatement()) {

            String createCatTable = """
                    CREATE TABLE IF NOT EXISTS categorie (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        nom VARCHAR(100) NOT NULL
                    )
                    """;
            stmt.executeUpdate(createCatTable);
            System.out.println("Table categorie created successfully");

            String createFourTable = """
                    CREATE TABLE IF NOT EXISTS fournisseur (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        nom VARCHAR(100) NOT NULL
                    )
                    """;
            stmt.executeUpdate(createFourTable);
            System.out.println("Table fournisseur created successfully");

            String createProdTable = """
                    CREATE TABLE IF NOT EXISTS produit (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        nom VARCHAR(100) NOT NULL,
                        prix DOUBLE NOT NULL,
                        description TEXT,
                        status VARCHAR(20) DEFAULT 'AVAILABLE',
                        quantite INT NOT NULL DEFAULT 0,
                        categorie_id INT,
                        fournisseur_id INT,
                        type VARCHAR(50),
                        date_peremption VARCHAR(50),
                        FOREIGN KEY (categorie_id) REFERENCES categorie(id) ON DELETE SET NULL,
                        FOREIGN KEY (fournisseur_id) REFERENCES fournisseur(id) ON DELETE SET NULL
                    )
                    """;
            stmt.executeUpdate(createProdTable);
            System.out.println("Table produit created successfully");

            String createMouvementTable = """
                    CREATE TABLE IF NOT EXISTS mouvement_stock (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        produit_id INT NOT NULL,
                        type_mouvement VARCHAR(50) NOT NULL,
                        quantite INT NOT NULL,
                        date VARCHAR(50) NOT NULL,
                        FOREIGN KEY (produit_id) REFERENCES produit(id) ON DELETE CASCADE
                    )
                    """;
            stmt.executeUpdate(createMouvementTable);
            System.out.println("Table mouvement_stock created successfully");

            System.out.println("Database initialization completed successfully!");

        } catch (SQLException e) {
            System.err.println("Database initialization failed");
            e.printStackTrace();
        }
    }
}
