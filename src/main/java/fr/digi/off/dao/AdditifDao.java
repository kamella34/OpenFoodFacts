package fr.digi.off.dao;

import fr.digi.off.entite.Additif;
import java.util.List;

public interface AdditifDao {
    Additif findById(Long id);

    List<Additif> findAll();

    void save(Additif additif);

    void update(Additif additif);

    void delete(Additif additif);

    Additif findByNom(String additifName);
}
