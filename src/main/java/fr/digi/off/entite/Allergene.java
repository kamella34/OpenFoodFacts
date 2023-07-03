package fr.digi.off.entite;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

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
    private Set<Produit> produits;
    {
        produits = new HashSet<>();
    }

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

    public Set<Produit> getProduits() {
        return produits;
    }

    public void setProduits(Set<Produit> produits) {
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