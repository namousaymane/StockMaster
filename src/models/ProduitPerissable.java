package models;

//Difference avec Produit, c'est la date de peremption

public class ProduitPerissable extends Produit {
    private String datePeremption;

    public ProduitPerissable(int id, String nom, double prix, int quantite, int categorieId, int fournisseurID,
            String description, String datePeremption) {
        super(id, nom, prix, quantite, categorieId, fournisseurID, description);
        this.datePeremption = datePeremption;
    }

    @Override
    public String getType() {
        return "Perrisable";
    }

    public String getDatePeremption() {
        return datePeremption;
    }
}
