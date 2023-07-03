package fr.digi.off.dao.jpa;

import fr.digi.off.entite.Categorie;
import fr.digi.off.dao.CategorieDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CategorieDAOImpl implements CategorieDao {
    private final EntityManager em;

    public CategorieDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Categorie findById(Long id) {
        return em.find(Categorie.class, id);
    }

    @Override
    public Categorie findByNom(String nom) {
        TypedQuery<Categorie> query = em.createQuery("SELECT c FROM Categorie c WHERE c.nom = :nom", Categorie.class);
        query.setParameter("nom", nom);
        return query.getSingleResult();
    }

    @Override
    public List<Categorie> findAll() {
        TypedQuery<Categorie> query = em.createQuery("SELECT c FROM Categorie c", Categorie.class);
        return query.getResultList();
    }

    @Override
    public void save(Categorie categorie) {
        em.persist(categorie);
    }

    @Override
    public void update(Categorie categorie) {
        em.merge(categorie);
    }

    @Override
    public void delete(Categorie categorie) {
        em.remove(categorie);
    }
}
