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
    private Double joule;
    private Double graisse;

    @Enumerated
    private NutriScore nutriScore;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "CATEGORIE")
    private Categorie categorie;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "PROD_INGRT",
            joinColumns = @JoinColumn(name = "PROD_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "INGRT_ID", referencedColumnName = "ID")
    )
    private Set<Ingredient> ingredients;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "PROD-ADDF",
            joinColumns = @JoinColumn(name = "PROD_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ADDF_ID", referencedColumnName = "ID")
    )
    private Set<Additif> additifs;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "PROD_ALLG",
            joinColumns = @JoinColumn(name = "PROD_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ALLG_ID", referencedColumnName = "ID")
    )
    private Set<Allergene> allergenes;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "MARQUE_ID")
    private Marque marques;

    {
        ingredients = new HashSet<Ingredient>();
        additifs = new HashSet<Additif>();
        allergenes = new HashSet<Allergene>();
    }

    public Produit() {
    }

    public Produit(String nom, Double joule, Double graisse, NutriScore nutriScore, Categorie categorie, Set<Ingredient> ingredients, Set<Additif> additifs, Set<Allergene> allergenes, Marque marques) {
        this.nom = nom;
        this.joule = joule;
        this.graisse = graisse;
        this.nutriScore = nutriScore;
        this.categorie = categorie;
        this.ingredients = ingredients;
        this.additifs = additifs;
        this.allergenes = allergenes;
        this.marques = marques;
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

    public Double getJoule() {
        return joule;
    }

    public void setJoule(Double joule) {
        this.joule = joule;
    }

    public Double getGraisse() {
        return graisse;
    }

    public void setGraisse(Double graisse) {
        this.graisse = graisse;
    }

    public NutriScore getNutriScore() {
        return nutriScore;
    }

    public void setNutriScore(NutriScore nutriScore) {
        this.nutriScore = nutriScore;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Additif> getAdditifs() {
        return additifs;
    }

    public void setAdditifs(Set<Additif> additifs) {
        this.additifs = additifs;
    }

    public Set<Allergene> getAllergenes() {
        return allergenes;
    }

    public void setAllergenes(Set<Allergene> allergenes) {
        this.allergenes = allergenes;
    }

    public Marque getMarques() {
        return marques;
    }

    public void setMarques(Marque marques) {
        this.marques = marques;
    }
}