package fr.digi.off;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Produit")
    private Integer idProduit;

    @Column(name = "nom")
    private String nom;

    @Column(name = "joule")
    private double joule;

    @Column(name = "graisse")
    private double graisse;

    @Column(name = "nutriscore")
    @Enumerated(EnumType.STRING)
    private NutriScore nutriscore;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_mategorie")
    private Categorie categorie;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_marque")
    private Marque marque;

    @ManyToMany
    @JoinTable(name = "contenir",
            joinColumns = @JoinColumn(name = "id_produit"),
            inverseJoinColumns = @JoinColumn(name = "id_allergene"))
    private Set<Allergene> allergenes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "inclure",
            joinColumns = @JoinColumn(name = "id_produit"),
            inverseJoinColumns = @JoinColumn(name = "id_additif"))
    private Set<Additif> additifs = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "comporter",
            joinColumns = @JoinColumn(name = "id_produit"),
            inverseJoinColumns = @JoinColumn(name = "id_ingredient"))
    private Set<Ingredient> ingredients = new HashSet<>();

    public Produit() {
    }

    public Produit(String nom, double joule, double graisse, NutriScore nutriscore, Categorie categorie, Marque marque) {
        this.nom = nom;
        this.joule = joule;
        this.graisse = graisse;
        this.nutriscore = nutriscore;
        this.categorie = categorie;
        this.marque = marque;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getJoule() {
        return joule;
    }

    public void setJoule(double joule) {
        this.joule = joule;
    }

    public double getGraisse() {
        return graisse;
    }

    public void setGraisse(double graisse) {
        this.graisse = graisse;
    }

    public NutriScore getNutriscore() {
        return nutriscore;
    }

    public void setNutriscore(NutriScore nutriscore) {
        this.nutriscore = nutriscore;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Set<Allergene> getAllergenes() {
        return allergenes;
    }

    public void setAllergenes(Set<Allergene> allergenes) {
        this.allergenes = allergenes;
    }

    public Set<Additif> getAdditifs() {
        return additifs;
    }

    public void setAdditifs(Set<Additif> additifs) {
        this.additifs = additifs;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "idProduit=" + idProduit +
                ", nom='" + nom + '\'' +
                ", joule=" + joule +
                ", graisse=" + graisse +
                ", nutriscore=" + nutriscore +
                ", categorie=" + categorie +
                ", marque=" + marque +
                ", allergenes=" + allergenes +
                ", additifs=" + additifs +
                ", ingredients=" + ingredients +
                '}';
    }
}