package fr.digi.off;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "INGREDIENT")
//@Cacheable
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;

    @ManyToMany
    @JoinTable(name = "PROD_INGRT",
            joinColumns = @JoinColumn(name = "INGRT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PROD_ID", referencedColumnName = "ID")
    )
    private Set<Produit> produits;


    {
        produits = new HashSet<Produit>();

    }

    @Override
    public String toString() {
        return "Ingredient{" + " nom='" + nom + '\'' +'}';
    }

    public Ingredient() {
    }

    public Ingredient(String nom) {
        this.nom = nom;
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