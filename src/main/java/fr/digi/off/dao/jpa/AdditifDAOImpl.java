package fr.digi.off.dao.jpa;

import fr.digi.off.Additif;
import fr.digi.off.dao.AdditifDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class AdditifDAOImpl implements AdditifDao {
    private final EntityManager em;

    public AdditifDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Additif findById(Long id) {
        return em.find(Additif.class, id);
    }

    @Override
    public List<Additif> findAll() {
        TypedQuery<Additif> query = em.createQuery("SELECT a FROM Additif a", Additif.class);
        return query.getResultList();
    }

    @Override
    public void save(Additif additif) {
        em.persist(additif);
    }

    @Override
    public void update(Additif additif) {
        em.merge(additif);
    }

    @Override
    public void delete(Additif additif) {
        em.remove(additif);
    }

    @Override
    public Additif findByNom(String nom) {
        TypedQuery<Additif> query = em.createQuery("SELECT a FROM Additif a WHERE a.nom = :nom", Additif.class);
        query.setParameter("nom", nom);
        return query.getSingleResult();
    }
}
