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
                return "Categorie{" +" nom='" + nom +'}';
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