package fr.digi.off.dao;

import fr.digi.off.Categorie;

import java.util.List;

public interface CategorieDao {
    Categorie findById(Long id);

    Categorie findByNom(String nom);

    List<Categorie> findAll();

    void save(Categorie categorie);

    void update(Categorie categorie);

    void delete(Categorie categorie);
}
