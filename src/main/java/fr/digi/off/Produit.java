package fr.digi.off;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODUIT")
public class Produit {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private Double Joule;
    private Double Graisse;

    @Embedded
    private NutriScore nutriScore;
    @ManyToOne
    @JoinColumn(name = "CATEGORIE")
    private Categorie categorie;

    @ManyToMany
    @JoinTable(name = "PROD_INGRT",
            joinColumns = @JoinColumn(name = "PROD_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "INGRT_ID", referencedColumnName = "ID")
    )
    private Set<Ingredient> ingredients;

    @ManyToMany
    @JoinTable(name = "PROD-ADDF",
            joinColumns = @JoinColumn(name = "PROD_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ADDF_ID", referencedColumnName = "ID")
    )
    private Set<Additif> additifs;
    @ManyToMany
    @JoinTable(name = "PROD_ALLG",
            joinColumns = @JoinColumn(name = "PROD_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ALLG_ID", referencedColumnName = "ID")
    )
    private Set<Allergene> allergenes;
    @ManyToOne
    @JoinColumn(name = "MARQUE_ID")
    private Marque marques;

    {
        ingredients = new HashSet<Ingredient>();
        additifs = new HashSet<Additif>();
        allergenes = new HashSet<Allergene>();
    }

    public Produit() {
    }

}
