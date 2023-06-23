package fr.digi.off;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "INGREDIENT")
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

    public Ingredient() {
    }


}
