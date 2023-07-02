package fr.digi.off.dao;

import fr.digi.off.Allergene;
import java.util.List;

public interface AllergeneDao {
    Allergene findById(Long id);

    List<Allergene> findAll();

    void save(Allergene allergene);

    void update(Allergene allergene);

    void delete(Allergene allergene);

    Allergene findByNom(String nom);
}
