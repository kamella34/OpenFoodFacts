package fr.digi.off.dao;

import fr.digi.off.Additif;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface AdditifDao {
    Additif findById(Long id);

    List<Additif> findAll();

    void save(Additif additif);

    void update(Additif additif);

    void delete(Additif additif);

    Additif findByNom(String additifName);
}
