package fr.digi.off;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MARQUE")
public class Marque {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    @OneToMany(mappedBy = "MARQUE")
    private Set<Produit> produits;

    {
        produits = new HashSet<Produit>();
    }

    public Marque() {
    }

    public Marque( String nom) {
        this.nom = nom;
    }

    public Marque( String nom, Set<Produit> produits) {
        this.nom = nom;
        this.produits = produits;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "Marque{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
