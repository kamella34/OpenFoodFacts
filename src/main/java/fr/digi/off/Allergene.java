package fr.digi.off;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ALLERGENE")
public class Allergene {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    @ManyToMany
    @JoinTable(name = "PROD_ALLG",
            joinColumns = @JoinColumn(name = "ALLG_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PROD_ID", referencedColumnName = "ID")
    )
    private Set<Produit> produits;

    {
        produits = new HashSet<Produit>();
    }

    public Allergene() {
    }

    public Allergene(String nom, Set<Produit> produits) {
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
}