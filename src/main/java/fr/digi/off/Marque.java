package fr.digi.off;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="MARQUE")
public class Marque {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    @OneToMany(mappedBy="MARQUE")
    private Set<Produit> produits;

    {
        produits = new HashSet<Produit>();
    }
    public Marque() {
    }


}
