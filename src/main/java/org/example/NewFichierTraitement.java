package org.example;

import fr.digi.off.*;
import fr.digi.off.dao.*;
import fr.digi.off.dao.jpa.*;
import jakarta.persistence.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class NewFichierTraitement {
    public static void main(String[] args) {
        Path path = Paths.get("src/main/resources/open-food-facts.csv");
        List<Produit> produits = new ArrayList<>();
        Map<String, Marque> marques = new HashMap<>();
        Map<String, Categorie> categories = new HashMap<>();
        Map<String, Additif> additifs = new HashMap<>();
        Map<String, Allergene> allergenes = new HashMap<>();
        Map<String, Ingredient> ingredients = new HashMap<>();
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("openfoodfactsdev");
             EntityManager em = emf.createEntityManager();) {
            EntityTransaction et = em.getTransaction();
            final ProduitDao produitDAO = new ProduitDAOImpl(em);
            final AdditifDao additifDAO = new AdditifDAOImpl(em);
            final AllergeneDao allergeneDAO = new AllergeneDAOImpl(em);
            final IngredientDao ingredientDAO = new IngredientDAOImpl(em);
            final CategorieDao categorieDao = new CategorieDAOImpl(em);
            final MarqueDao marqueDao = new MarqueDAOImpl(em);

            //Traitement fichier

            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (int i = 1; i < lines.size(); i++) {
                et.begin();
                String[] columns = lines.get(i).split("\\|", 31);

                //Graisse
                double graisse = isDouble(columns[6]);

                //Joule
                double joule = isDouble(columns[5]);

                //Additifs
                String additifLine = traiter(columns[29]);
                Set<Additif> setAdditifs = createAdditifs(additifLine, additifs, additifDAO);
                for (Additif additif : setAdditifs) {
                    additifDAO.save(additif);
                }

                //Allergènes
                String allergeneLine = traiter(columns[28]);
                Set<Allergene> setAllergenes = createAllergenes(allergeneLine, allergenes, allergeneDAO);
                for (Allergene allergene: setAllergenes) {
                    allergeneDAO.save(allergene);
                }

                // Ingrédients
                String ingredientLine = traiter(columns[4]);
                Set<Ingredient> setIngredients = createIngredients(ingredientLine, ingredients, ingredientDAO);
                for (Ingredient ingredient: setIngredients) {
                    ingredientDAO.save(ingredient);
                }

                // Categorie
                Categorie categorie = createCategorie(columns[0], categories,categorieDao);
                categorieDao.save(categorie);

                // Marque
                String marqueName = traiter(columns[1]);
                Marque marque = createMarque(marqueName, marques,marqueDao);
                if (marque != null) {
                    marqueDao.save(marque);
                }

                //Produit
                Produit produit = new Produit(columns[2], joule, graisse, NutriScore.getNutriScoreByLettre(columns[3].toUpperCase()),categorie , marque);
                produit.setIngredients(setIngredients);
                produit.setAllergenes(setAllergenes);
                produit.setAdditifs(setAdditifs);
                produitDAO.save(produit);

                produits.add(produit);
                et.commit();
            }
            /*
            et.begin();
            for (Categorie categorie: categories.values()) {
                em.persist(categorie);
            }
            et.commit();
            et.begin();
            for (Marque marque: marques.values()) {
                em.persist(marque);
            }
            et.commit();
            et.begin();
            for (Additif additif: additifs.values()) {
                em.persist(additif);
            }
            et.commit();
            et.begin();
            for (Allergene allergene: allergenes.values()) {
                em.persist(allergene);
            }
            et.commit();
            et.begin();
            for (Ingredient ingredient: ingredients.values()) {
                em.persist(ingredient);
            }
            et.commit();
            et.begin();
            for (Produit produit : produits){
                em.persist(produit);
            }
            et.commit();*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Metodes
    /**
     *
     * @param string
     * @return
     */
    public static double isDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     *
     * @param string
     * @return
     */
    public static String traiter(String string) {
        // Vérifier si la propriété ingredients est null
        if (string == null) {
            string = ""; // Ou une autre valeur par défaut
        } else {
            // Le reste du traitement...
            string = string.replaceAll("\\*", "")
                    .replaceAll("_", "")
                    .replaceAll("\\s*\\([^\\)]*\\)\\s*", "")
                    .replaceAll("\\d+%?", "")
                    .replaceAll(",", ";")
                    .replaceAll("%", "")
                    .replaceAll("[.:\\-,]", ";")
                    .replaceAll("[()]", "")
                    .replaceAll("\\b(\\p{L})(?!\\p{L})\\b", "")
                    .trim();
        }
        return string;
    }

    /**
     *
     * @param marqueName
     * @param dao
     * @return
     */
    public static Marque createMarque(String marqueName, Map<String, Marque> marques,MarqueDao dao) {
        Marque marque = null;
        marqueName = marqueName.trim();
        if (!marqueName.isEmpty()){
            try {
                marque = dao.findByNom(marqueName);
            } catch (RuntimeException e) {
                marque = new Marque(marqueName);
                marques.put(marqueName, marque);
            }
        }
        return marque;
    }

    /**
     *
     * @param categorieName
     * @param dao
     * @return
     */
    public static Categorie createCategorie(String categorieName, Map<String, Categorie> categories, CategorieDao dao) {
        Categorie categorie;
        if (categories.containsKey(categorieName)) {
            categorie = categories.get(categorieName);
        } else {
            try {
                categorie = dao.findByNom(categorieName);
                if (categorie == null) {
                    categorie = new Categorie(categorieName);
                    categories.put(categorieName, categorie);
                }
            } catch (NoResultException e) {
                categorie = new Categorie(categorieName);
                categories.put(categorieName, categorie);
            }
        }
        return categorie;
    }

    /**
     *
     * @param ingredientLine
     * @param ingredients
     * @param dao
     * @return
     */
    public static Set<Ingredient> createIngredients(String ingredientLine, Map<String, Ingredient> ingredients, IngredientDao dao) {
        Set<Ingredient> setIngredients = new HashSet<>();
        String[] ingredientsTab = ingredientLine.split(";");
        for (String ingredientName : ingredientsTab) {
            ingredientName = ingredientName.trim();
            if (!ingredientName.isEmpty()) {
                try {
                    Ingredient ingredient = dao.findByNom(ingredientName);
                    setIngredients.add(ingredient);
                } catch (RuntimeException e) {
                    Ingredient ingredient = new Ingredient(ingredientName);
                    ingredients.put(ingredientName, ingredient);
                    setIngredients.add(ingredient);
                }
            }
        }
        return setIngredients;
    }

    /**
     *
     * @param allergeneLine
     * @param allergenes
     * @param dao
     * @return
     */
    public static Set<Allergene> createAllergenes(String allergeneLine, Map<String, Allergene> allergenes, AllergeneDao dao) {
        Set<Allergene> setAllergene = new HashSet<>();
        String[] allergenesTab = allergeneLine.split(";");
        for (String allergeneName : allergenesTab) {
            allergeneName = allergeneName.trim();
            if (!allergeneName.isEmpty()){
                try {
                    Allergene allergene = dao.findByNom(allergeneName);
                    setAllergene.add(allergene);
                } catch (RuntimeException e) {
                    Allergene allergene = new Allergene(allergeneName);
                    allergenes.put(allergeneName, allergene);
                    setAllergene.add(allergene);
                }
            }
        }
        return setAllergene;
    }

    /**
     *
     * @param additifsLine
     * @param additifs
     * @param dao
     * @return
     */
    public static Set<Additif> createAdditifs(String additifsLine, Map<String, Additif> additifs, AdditifDao dao) {
        Set<Additif> setAdditifs = new HashSet<>();
        String[] additifsTab = additifsLine.split(";");
        for (String additifName : additifsTab) {
            additifName = additifName.trim();
            if (!additifName.isEmpty()) {
                try {
                    Additif additif = dao.findByNom(additifName);
                    setAdditifs.add(additif);
                } catch (RuntimeException e) {
                    Additif additif = new Additif(additifName);
                    additifs.put(additifName, additif);
                    setAdditifs.add(additif);
                }
            }
        }
        return setAdditifs;
    }
}
