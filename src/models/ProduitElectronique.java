package models;

//Difference par rapport a produit, c'est l'ajout de la duree de garantie (en mois)

public class ProduitElectronique extends Produit{
    private String datePeremption;

    public ProduitElectronique(int id, String nom, double prix, int quantite, int categorieId, int fournisseurID, String description) {
        super(id, nom, prix, quantite, categorieId, fournisseurID, description);
        this.datePeremption = datePeremption;
    }


    @Override
    public String getType() {return "Électro";
    }

    public String getDatePeremption() {
        return datePeremption;
    }
}
