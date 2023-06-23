package fr.digi.off;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ALLERGENE")
public class Allergene {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    @ManyToMany
    @JoinTable(name="INGRT_ALLG",
            joinColumns= @JoinColumn(name="ID_ALL", referencedColumnName="ID"),
            inverseJoinColumns= @JoinColumn(name="ID_INGRT", referencedColumnName="ID")
    )
    private Set<Ingredient> ingredients;
    {
        ingredients = new HashSet<Ingredient>();
    }
    public Allergene() {
    }


}
