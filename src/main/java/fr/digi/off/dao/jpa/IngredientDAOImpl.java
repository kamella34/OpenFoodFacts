package fr.digi.off.dao.jpa;

import fr.digi.off.Ingredient;
import fr.digi.off.dao.IngredientDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class IngredientDAOImpl implements IngredientDao {
    private final EntityManager em;

    public IngredientDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Ingredient findById(Long id) {
        return em.find(Ingredient.class, id);
    }

    @Override
    public List<Ingredient> findAll() {
        TypedQuery<Ingredient> query = em.createQuery("SELECT i FROM Ingredient i", Ingredient.class);
        return query.getResultList();
    }

    @Override
    public Ingredient findByNom(String nom) {
        TypedQuery<Ingredient> query = em.createQuery("SELECT i FROM Ingredient i WHERE i.nom = :nom", Ingredient.class);
        query.setParameter("nom", nom);
        return query.getSingleResult();
    }

    @Override
    public void save(Ingredient ingredient) {
        em.persist(ingredient);
    }

    @Override
    public void update(Ingredient ingredient) {
        em.merge(ingredient);
    }

    @Override
    public void delete(Ingredient ingredient) {
        em.remove(ingredient);
    }
}
