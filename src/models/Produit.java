package models;

public abstract class Produit {
    protected int id;
    protected String nom;
    protected double prix;
    protected String description;
    protected ProductStatus status; // ProductStatus enum
    protected int quantite;
    protected int categorieId;
    protected int fournisseurID;

    public Produit(int id, String nom, double prix, int quantite, int categorieId, int fournisseurID,
            String description) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
        this.categorieId = categorieId;
        this.fournisseurID = fournisseurID;
        this.description = description;
        this.status = ProductStatus.AVAILABLE; // by default
    }

    // Methode abstraite
    public abstract String getType();

    // Les setters et getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public int getCategorieId() {
        return categorieId;
    }

    public int getFournisseurId() {
        return fournisseurID;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

}
