package fr.digi.off.dao.jpa;

import java.util.List;
import fr.digi.off.Marque;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import fr.digi.off.dao.MarqueDao;

public class MarqueDAOImpl implements MarqueDao {
    private final EntityManager em;

    public MarqueDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Marque findById(Long id) {
        return em.find(Marque.class, id);
    }

    public Marque findByNom(String nom) {
        TypedQuery<Marque> query = em.createQuery("SELECT m FROM Marque m WHERE m.nom = :nom", Marque.class);
        query.setParameter("nom", nom);
        return query.getSingleResult();
    }

    @Override
    public List<Marque> findAll() {
        TypedQuery<Marque> query = em.createQuery("SELECT m FROM Marque m", Marque.class);
        return query.getResultList();
    }

    @Override
    public void save(Marque marque) {
        em.persist(marque);
    }

    @Override
    public void update(Marque marque) {
        em.merge(marque);
    }

    @Override
    public void delete(Marque marque) {
        em.remove(marque);
    }
}
