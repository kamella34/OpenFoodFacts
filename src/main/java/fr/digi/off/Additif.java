package fr.digi.off;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name="ADDITIF")

public class Additif {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String nom;
    @ManyToMany
    @JoinTable(name="PROD-ADDF",
            joinColumns = @JoinColumn(name = "ADDF_ID",referencedColumnName="ID"),
            inverseJoinColumns = @JoinColumn(name = "PROD_ID",referencedColumnName="ID")
    )
    private Set<Produit> produits;

    public Additif() {
    }

    public Additif(String nom) {
        this.nom = nom;
    }

    public Additif(String nom, Set<Produit> produits) {
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
        return "Additif{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}