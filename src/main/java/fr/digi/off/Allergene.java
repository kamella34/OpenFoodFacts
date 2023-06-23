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


}
