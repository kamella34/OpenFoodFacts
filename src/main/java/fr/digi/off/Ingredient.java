package fr.digi.off;

import jakarta.persistence.*;

@Entity
@Table(name = "Ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Ingredient")
    private int idIngredient;

    @Column(name = "nom")
    private String nom;

    public Ingredient() {
    }

    public Ingredient(String nom) {
        this.nom = nom;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "idIngredient=" + idIngredient +
                ", nom='" + nom + '\'' +
                '}';
    }
}