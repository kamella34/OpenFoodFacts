package fr.digi.off.dao.jpa;

import java.util.List;
import fr.digi.off.entite.Produit;
import fr.digi.off.dao.ProduitDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ProduitDAOImpl implements ProduitDao {
    private final EntityManager em;

    public ProduitDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Produit findById(Long id) {
        return em.find(Produit.class, id);
    }

    @Override
    public List<Produit> findAll() {
        TypedQuery<Produit> query = em.createQuery("SELECT p FROM Produit p", Produit.class);
        return query.getResultList();
    }

    @Override
    public void save(Produit produit) {
        em.persist(produit);
    }

    @Override
    public void update(Produit produit) {
        em.merge(produit);
    }

    @Override
    public void delete(Produit produit) {
        em.remove(produit);
    }
}