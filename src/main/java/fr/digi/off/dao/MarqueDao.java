package fr.digi.off.dao;

import fr.digi.off.entite.Marque;

import java.util.List;

public interface MarqueDao {
    Marque findById(Long id);

    List<Marque> findAll();

    Marque findByNom(String nom);

    void save(Marque marque);

    void update(Marque marque);

    void delete(Marque marque);
}
