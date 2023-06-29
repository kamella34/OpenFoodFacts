package fr.digi.off;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Additif")
public class Additif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Additif")
    private int idAdditif;

    @Column(name = "nom")
    private String nom;

    @ManyToMany(mappedBy = "additifs")
    private Set<Produit> produits = new HashSet<>();

    public Additif() {
    }

    public Additif(String nom) {
        this.nom = nom;
    }

    public int getIdAdditif() {
        return idAdditif;
    }

    public void setIdAdditif(int idAdditif) {
        this.idAdditif = idAdditif;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "Additif{" +
                "idAdditif=" + idAdditif +
                ", nom='" + nom + '\'' +
                '}';
    }
}