package fr.digi.off.dao.jpa;

import fr.digi.off.entite.Allergene;
import fr.digi.off.dao.AllergeneDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AllergeneDAOImpl implements AllergeneDao {
    private final EntityManager em;

    public AllergeneDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Allergene findById(Long id) {
        return em.find(Allergene.class, id);
    }

    @Override
    public List<Allergene> findAll() {
        TypedQuery<Allergene> query = em.createQuery("SELECT a FROM Allergene a", Allergene.class);
        return query.getResultList();
    }

    @Override
    public void save(Allergene allergene) {
        em.persist(allergene);
    }

    @Override
    public void update(Allergene allergene) {
        em.merge(allergene);
    }

    @Override
    public void delete(Allergene allergene) {
        em.remove(allergene);
    }

    @Override
    public Allergene findByNom(String nom) {
        TypedQuery<Allergene> query = em.createQuery("SELECT a FROM Allergene a WHERE a.nom = :nom", Allergene.class);
        query.setParameter("nom", nom);
        return query.getSingleResult();
    }
}
