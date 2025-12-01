package models;

public class MouvementStock {
    private int id;
    private int produitId;
    private String typeMouvement;
    private int quantite;
    private String date;

    public MouvementStock(int id, int produitId, String type, int quantite, String date) {
        this.id = id;
        this.produitId = produitId;
        this.typeMouvement = type;
        this.quantite = quantite;
        this.date = date;
    }

    // setters et getters
    public int getId() {
        return id;
    }

    public int getProduitId() {
        return produitId;
    }

    public String getTypeMouvement() {
        return typeMouvement;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getDate() {
        return date;
    }
}
