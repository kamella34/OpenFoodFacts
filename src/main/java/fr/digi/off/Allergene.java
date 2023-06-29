package fr.digi.off;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Allergene")
public class Allergene {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Allergene")
    private int idAllergene;

    @Column(name = "nom")
    private String nom;

    @ManyToMany(mappedBy = "allergenes")
    private List<Produit> produits = new ArrayList<>();

    public Allergene() {
    }

    public Allergene(String nom) {
        this.nom = nom;
    }

    public int getIdAllergene() {
        return idAllergene;
    }

    public void setIdAllergene(int idAllergene) {
        this.idAllergene = idAllergene;
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
        return "Allergene{" +
                "idAllergene=" + idAllergene +
                ", nom='" + nom + '\'' +
                '}';
    }
}