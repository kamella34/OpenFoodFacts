package fr.digi.off;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.*;


@Entity
@Table(name="ADDITIF")
public class Additif {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String nom;
    @ManyToOne
    @JoinColumn(name="PROD_ID")
    private Produit produits;

    public Additif() {
    }




}