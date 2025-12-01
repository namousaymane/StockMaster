package models;

//Difference par rapport a produit, c'est l'ajout de la duree de garantie (en mois)

public class ProduitElectronique extends Produit{
    private int dureeGarantie;

    public ProduitElectronique(int id, String nom, double prix, int quantite, int categorieId, int fournisseurID) {
        super(id, nom, prix, quantite, categorieId, fournisseurID);
        this.dureeGarantie = dureeGarantie;
    }

    @Override
    public String getType() {return "Électro";
    }

    public int getDureeGarantie() {
        return dureeGarantie;
    }
}
