package fr.digi.off.dao;

import fr.digi.off.entite.Produit;

import java.util.List;

public interface ProduitDao {
    Produit findById(Long id);

    List<Produit> findAll();

    void save(Produit produit);

    void update(Produit produit);

    void delete(Produit produit);
}
