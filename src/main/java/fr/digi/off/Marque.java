package fr.digi.off;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MARQUE")
//@Cacheable
public class Marque {

        @Id
        @Column(name = "ID")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String nom;

       @OneToMany(mappedBy = "marques")
        private Set<Produit> produits;

        {
            produits = new HashSet<Produit>();
        }

        public Marque() {
        }

        public Marque( String nom) {
                this.nom = nom;

        }

        @Override
        public String toString() {
                return "Marque{" +
                        " nom='" + nom + '\'' +'}';
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


}