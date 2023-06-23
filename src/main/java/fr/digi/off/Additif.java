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




}