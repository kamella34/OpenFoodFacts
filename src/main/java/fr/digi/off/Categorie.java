package fr.digi.off;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Categorie")
public class Categorie {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String description;


    @OneToMany(mappedBy = "categorie")
    private Set<Produit> produits;

    {
        produits = new HashSet<Produit>();
    }

    public Categorie() {

    }

    public Categorie(String nom) {
        this.nom = nom;
    }


    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}