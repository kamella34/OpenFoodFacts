package fr.digi.off;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="PRODUIT")
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
 @JoinColumn(name="CATEGORIE")
 private Categorie categorie;

 @ManyToMany
 @JoinTable(name="PROD_INGRT",
         joinColumns= @JoinColumn(name="PROD_ID", referencedColumnName="ID"),
         inverseJoinColumns= @JoinColumn(name="INGRT_ID", referencedColumnName="ID")
 )
 private Set<Ingredient> ingredients;

 @OneToMany(mappedBy="PRODUIT")
 private Set<Additif> additifs;

 @ManyToOne
 @JoinColumn(name="MARQUE_ID")
 private Marque marques;

 {
  ingredients = new HashSet<Ingredient>();
  additifs = new HashSet<Additif>();
 }

 public Produit() {
 }

}
