package models;

abstract class Produit {
    protected int id;
    protected String nom;
    protected double prix;
    protected int quantite;
    protected int categorieId;
    protected int fournisseurID;

    public Produit(int id, String nom, double prix, int quantite, int categorieId, int fournisseurID) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
        this.categorieId = categorieId;
        this.fournisseurID = fournisseurID;
    }

    // Methode abstraite
    public abstract String getType();

    // Les setters et getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public double getPrix() { return prix; }
    public int getQuantite() { return quantite; }
    public int getCategorieId() { return categorieId; }
    public int getFournisseurId() { return fournisseurID; }

    public void setQuantite(int quantite) { this.quantite = quantite; }
    public void setPrix(double prix) { this.prix = prix; }

}
