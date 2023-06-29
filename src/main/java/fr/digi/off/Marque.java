package fr.digi.off;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Marque")
public class Marque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Marque")
    private int idMarque;

    @Column(name = "nom")
    private String nom;

    @OneToMany(mappedBy = "marque", cascade = CascadeType.ALL)
    private List<Produit> produits = new ArrayList<>();

    public Marque() {
    }

    public Marque(String nom) {
        this.nom = nom;
    }

    public int getIdMarque() {
        return idMarque;
    }

    public void setIdMarque(int idMarque) {
        this.idMarque = idMarque;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "Marque{" +
                "idMarque=" + idMarque +
                ", nom='" + nom + '\'' +
                '}';
    }
}
