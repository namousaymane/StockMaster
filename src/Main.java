import DAO.*;
import models.*;
import exceptions.*;

import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ProduitDAO produitDAO = new ProduitDAO();
    private static FournisseurDAO fournisseurDAO = new FournisseurDAO();
    private static CategorieDAO categorieDAO = new CategorieDAO();

    // HashMap pour les categories
    private static HashMap<String, Categorie> categoriesMap = new HashMap<>();

    public static void main(String[] args) {
        chargerCategories();
        menuPrincipal();
    }

    // Chargement des categories
    private static void chargerCategories() {
        List<Categorie> categories = categorieDAO.getAllCategories();
        categories.forEach(c -> categoriesMap.put(c.getNom(), c));
    }

    private static void menuPrincipal() {
        while (true) {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║             MENU PRINCIPAL             ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ 1. Gestion des Fournisseurs            ║");
            System.out.println("║ 2. Gestion des Produits                ║");
            System.out.println("║ 3. Gestion des Stocks                  ║");
            System.out.println("║ 4. Rapports & Analyses (Streams)       ║");
            System.out.println("║ 5. Exporter  en .csv                   ║");
            System.out.println("║ 6. Recharger les catégories            ║");
            System.out.println("║ 0. Quitter                             ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Indiquez votre choix:");

            int choix = lireEntier();

            try {
                switch (choix) {
                    case 1: menuFournisseurs(); break;
                    case 2: menuProduits(); break;
                    case 3: menuStocks(); break;
                    case 4: menuRapports(); break;
                    case 5: exporterCSV(); break;
                    case 6:
                        chargerCategories();
                        System.out.println("Catégories rechargées");
                        break;
                    case 0:
                        System.out.println("Fermeture du programme...");
                        System.exit(0);
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (ProduitNonTrouveException e) {
                System.err.println("Erreur : " + e.getMessage());
            } catch (StockInsuffisantException e) {
                System.err.println("Erreur de stock : " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Erreur: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Partie des fournisseurs
    private static void menuFournisseurs() {
        System.out.println("=== GESTION FOURNISSEURS ===");
        System.out.println("1. Ajouter un fournisseur");
        System.out.println("2. Afficher les fournisseurs");
        System.out.println("3. Modifier un fournisseur");
        System.out.println("4. Supprimer un fournisseur");
        System.out.print("Choix: ");

        int choix = lireEntier();

        switch (choix) {
            case 1:
                System.out.print("Nom du fournisseur: ");
                String nom = scanner.nextLine();
                if (fournisseurDAO.createFournisseur(nom)) {
                    System.out.println("Fournisseur ajouté avec succès !");
                } else {
                    System.out.println("Échec de l'ajout.");
                }
                break;

            case 2:
                List<Fournisseur> fournisseurs = fournisseurDAO.getAllFournisseurs();
                System.out.println("=== LISTE DES FOURNISSEURS ===");
                if (fournisseurs.isEmpty()) {
                    System.out.println("Aucun fournisseur disponible.");
                } else {
                    fournisseurs.forEach(f ->
                            System.out.printf("ID: %d | Nom: %s%n", f.getId(), f.getNom())
                    );
                }
                break;

            case 3:
                System.out.print("Nom actuel du fournisseur: ");
                String nomActuel = scanner.nextLine();
                System.out.print("Nouveau nom: ");
                String nouveauNom = scanner.nextLine();
                if (fournisseurDAO.updateFournisseur(nomActuel, nouveauNom)) {
                    System.out.println("Fournisseur modifié !");
                } else {
                    System.out.println("Fournisseur introuvable.");
                }
                break;

            case 4:
                System.out.print("Nom du fournisseur à supprimer: ");
                String nomSupp = scanner.nextLine();
                if (fournisseurDAO.deleteFournisseur(nomSupp)) {
                    System.out.println("Fournisseur supprimé !");
                } else {
                    System.out.println("Fournisseur introuvable.");
                }
                break;

            default:
                System.out.println("Choix invalide.");
        }
    }

    // Partie des Produits
    private static void menuProduits() throws ProduitNonTrouveException {
        System.out.println("\n=== GESTION PRODUITS ===");
        System.out.println("1. Ajouter un produit");
        System.out.println("2. Afficher les produits");
        System.out.println("3. Modifier un produit");
        System.out.println("4. Supprimer un produit");
        System.out.print("Choix: ");

        int choix = lireEntier();

        switch (choix) {
            case 1:
                ajouterProduit();
                break;

            case 2:
                List<Produit> produits = produitDAO.getAllProduits();
                System.out.println("\n=== LISTE DES PRODUITS ===");
                if (produits.isEmpty()) {
                    System.out.println("Aucun produit disponible.");
                } else {
                    produits.forEach(p ->
                            System.out.printf("ID: %d | %s | Type: %s | Prix: %.2f dhs | Stock: %d%n",
                                    p.getId(), p.getNom(), p.getType(), p.getPrix(), p.getQuantite())
                    );
                }
                break;

            case 3:
                System.out.print("Nom actuel du produit: ");
                String nomActuel = scanner.nextLine();

                // Vérifier que le produit existe avant de modifier
                Produit produitExistant = produitDAO.getProduitByNom(nomActuel);
                if (produitExistant == null) {
                    throw new ProduitNonTrouveException("Le produit '" + nomActuel + "' n'existe pas dans la base de données.");
                }

                System.out.print("Nouveau nom: ");
                String nouveauNom = scanner.nextLine();
                System.out.print("Nouveau prix: ");
                double nouveauPrix = lireDouble();
                System.out.print("Nouvelle description: ");
                String nouvelleDesc = scanner.nextLine();

                if (produitDAO.updateProduit(nomActuel, nouveauNom, nouveauPrix, nouvelleDesc)) {
                    System.out.println("Produit modifié !");
                } else {
                    System.out.println("Erreur lors de la modification.");
                }
                break;

            case 4:
                System.out.print("Nom du produit à supprimer: ");
                String nomSupp = scanner.nextLine();

                // Vérifier que le produit existe avant de supprimer
                Produit produitASupprimer = produitDAO.getProduitByNom(nomSupp);
                if (produitASupprimer == null) {
                    throw new ProduitNonTrouveException("Le produit '" + nomSupp + "' n'existe pas.");
                }

                if (produitDAO.deleteProduit(nomSupp)) {
                    System.out.println("Produit supprimé !");
                } else {
                    System.out.println("Erreur lors de la suppression.");
                }
                break;

            default:
                System.out.println("Choix invalide.");
        }
    }

    private static void ajouterProduit() {
        System.out.println("\nType de produit:");
        System.out.println("1. Périssable");
        System.out.println("2. Électronique");
        System.out.print("Choix: ");
        int type = lireEntier();

        System.out.print("Nom du produit: ");
        String nom = scanner.nextLine();
        System.out.print("Prix: ");
        double prix = lireDouble();
        System.out.print("Quantité initiale: ");
        int quantite = lireEntier();
        System.out.print("Description: ");
        String description = scanner.nextLine();

        // Catégories depuis la hashmap
        System.out.println("\nCatégories disponibles:");
        if (categoriesMap.isEmpty()) {
            System.out.println("Aucune catégorie disponible.");
            return;
        }
        categoriesMap.keySet().forEach(c -> System.out.println("- " + c));
        System.out.print("Nom de la catégorie: ");
        String categorieNom = scanner.nextLine();

        // Vérifier que la catégorie existe dans le Hashmap
        if (!categoriesMap.containsKey(categorieNom)) {
            System.out.println("Catégorie non trouvée dans le programme");
            return;
        }

        // Afficher les fournisseurs disponibles
        List<Fournisseur> fournisseurs = fournisseurDAO.getAllFournisseurs();
        System.out.println("\nFournisseurs disponibles:");
        fournisseurs.forEach(f -> System.out.println("- " + f.getNom()));
        System.out.print("Nom du fournisseur: ");
        String fournisseurNom = scanner.nextLine();

        String typeStr = (type == 1) ? "Périssable" : "Électro";
        String datePeremption = "";

        if (type == 1) {
            System.out.print("Date de péremption (formatt: 2025-10-30): ");
            datePeremption = scanner.nextLine();
        } else {
            System.out.print("Garantie (en mois): ");
            datePeremption = scanner.nextLine() + " mois";
        }

        if (produitDAO.createProduit(nom, prix, quantite, categorieNom, fournisseurNom,
                description, typeStr, datePeremption)) {
            System.out.println("Produit ajouté");
        } else {
            System.out.println("Échec de l'ajout");
        }
    }

    // Partie pour les stocks
    private static void menuStocks() throws ProduitNonTrouveException, StockInsuffisantException {
        System.out.println("\n=== GESTION STOCKS ===");
        System.out.println("1. Ajouter du stock (entrée)");
        System.out.println("2. Retirer du stock (sortie)");
        System.out.print("Choix: ");

        int choix = lireEntier();

        System.out.print("Nom du produit: ");
        String nom = scanner.nextLine();

        Produit produit = produitDAO.getProduitByNom(nom);
        if (produit == null) {
            throw new ProduitNonTrouveException("Le produit '" + nom + "' n'existe pas dans le stock");
        }

        System.out.print("Quantité: ");
        int quantite = lireEntier();

        if (choix == 1) {
            // Entrée de stock
            int nouvelleQuantite = produit.getQuantite() + quantite;
            if (produitDAO.updateQuantite(nom, nouvelleQuantite)) {
                System.out.println("Stock ajouté ! Nouveau stock: " + nouvelleQuantite);
            }
        } else if (choix == 2) {
            // Sortie de stock
            if (produit.getQuantite() < quantite) {
                throw new StockInsuffisantException(
                        "Stock insuffisant pour '" + nom + "' (disponible: " + produit.getQuantite() + ", demandé: " + quantite + ")"
                );
            }
            int nouvelleQuantite = produit.getQuantite() - quantite;
            if (produitDAO.updateQuantite(nom, nouvelleQuantite)) {
                System.out.println("Stock retiré ! Nouveau stock: " + nouvelleQuantite);
            }
        } else {
            System.out.println("Choix invalide.");
        }
    }

    // Les rapports
    private static void menuRapports() {
        System.out.println("\n=== RAPPORTS & ANALYSES ===");
        System.out.println("1. Produits en stock faible (<10)");
        System.out.println("2. Produits triés par prix");
        System.out.println("3. Nombre de produits par catégorie");
        System.out.println("4. Afficher les catégories en mémoire (Hashmap)");
        System.out.print("Choix: ");

        int choix = lireEntier();
        List<Produit> produits = produitDAO.getAllProduits();

        switch (choix) {
            case 1:
                System.out.println("\n=== STOCK FAIBLE (<10) ===");
                List<Produit> stockFaible = produits.stream()
                        .filter(p -> p.getQuantite() < 10)
                        .collect(Collectors.toList());

                if (stockFaible.isEmpty()) {
                    System.out.println("Aucun produit en stock faible.");
                } else {
                    System.out.println(stockFaible.size() + " produit(s) en alerte:");
                    stockFaible.forEach(p ->
                            System.out.printf("  • %s : %d unités%n", p.getNom(), p.getQuantite())
                    );
                }
                break;

            case 2:
                System.out.println("\n=== PRODUITS TRIÉS PAR PRIX ===");
                produits.stream()
                        .sorted(Comparator.comparingDouble(Produit::getPrix))
                        .forEach(p ->
                                System.out.printf("  • %s : %.2f dhs%n", p.getNom(), p.getPrix())
                        );
                break;

            case 3:
                System.out.println("\n=== NOMBRE PAR CATÉGORIE ===");
                Map<Integer, Long> comptage = produits.stream()
                        .collect(Collectors.groupingBy(Produit::getCategorieId, Collectors.counting()));

                comptage.forEach((catId, count) -> {
                    // Trouver le nom de la catégorie dans Hashmapp
                    String nomCategorie = categoriesMap.values().stream()
                            .filter(c -> c.getId() == catId)
                            .map(Categorie::getNom)
                            .findFirst()
                            .orElse("Inconnu");
                    System.out.printf("  • %s (ID: %d) : %d produit(s)%n", nomCategorie, catId, count);
                });
                break;

            case 4:
                System.out.println("\n=== CATÉGORIES EN MÉMOIRE (Hashmap) ===");
                if (categoriesMap.isEmpty()) {
                    System.out.println("Aucune catégorie.");
                } else {
                    categoriesMap.forEach((nom, cat) ->
                            System.out.printf("  • %s (ID: %d)%n", nom, cat.getId())
                    );
                }
                break;

            default:
                System.out.println("Choix invalide.");
        }
    }

    // Export csv
    private static void exporterCSV() {
        System.out.print("Nom du fichier (ex: rapport.csv): ");
        String fichier = scanner.nextLine();

        try (PrintWriter writer = new PrintWriter(new FileWriter(fichier))) {
            writer.println("ID,Nom,Type,Prix,Quantite,CategorieID,FournisseurID,Description");

            List<Produit> produits = produitDAO.getAllProduits();
            for (Produit p : produits) {
                writer.printf("%d,%s,%s,%.2f,%d,%d,%d,%s%n",
                        p.getId(), p.getNom(), p.getType(), p.getPrix(),
                        p.getQuantite(), p.getCategorieId(), p.getFournisseurId(),
                        p instanceof ProduitPerissable ?
                                ((ProduitPerissable)p).getDatePeremption() :
                                ((ProduitElectronique)p).getGarantieMois()
                );
            }

            System.out.println("Inventaire exporté dans " + fichier);
            System.out.println("Total: " + produits.size() + " produits exportés.");

        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    // Fonctions internes pour les choix
    private static int lireEntier() {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Entrez un nombre valide: ");
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    private static double lireDouble() {
        while (!scanner.hasNextDouble()) {
            scanner.next();
            System.out.print("Entrez un nombre valide: ");
        }
        double val = scanner.nextDouble();
        scanner.nextLine();
        return val;
    }
}