package fr.digi.off.dao;

import fr.digi.off.Ingredient;

import java.util.List;

public interface IngredientDao {
    Ingredient findById(Long id);

    List<Ingredient> findAll();

    Ingredient findByNom(String nom);

    void save(Ingredient ingredient);

    void update(Ingredient ingredient);

    void delete(Ingredient ingredient);
}
