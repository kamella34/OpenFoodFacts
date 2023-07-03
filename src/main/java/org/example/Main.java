package org.example;

import fr.digi.off.dao.*;
import fr.digi.off.dao.jpa.*;
import fr.digi.off.entite.*;
import jakarta.persistence.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("src/main/resources/open-food-facts.csv");
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
                Set<Additif> setAdditifs = createAdditifs(additifLine, additifDAO);
                for (Additif additif : setAdditifs) {
                    additifDAO.save(additif);
                }

                //Allergènes
                String allergeneLine = traiter(columns[28]);
                Set<Allergene> setAllergenes = createAllergenes(allergeneLine, allergeneDAO);
                for (Allergene allergene: setAllergenes) {
                    allergeneDAO.save(allergene);
                }

                // Ingrédients
                String ingredientLine = traiter(columns[4]);
                Set<Ingredient> setIngredients = createIngredients(ingredientLine, ingredientDAO);
                for (Ingredient ingredient: setIngredients) {
                    ingredientDAO.save(ingredient);
                }

                // Categorie
                Categorie categorie = createCategorie(columns[0],categorieDao);
                categorieDao.save(categorie);

                // Marque
                String marqueName = traiter(columns[1]);
                Marque marque = createMarque(marqueName,marqueDao);
                if (marque != null) {
                    marqueDao.save(marque);
                }

                //Produit
                Produit produit = new Produit(columns[2], joule, graisse, NutriScore.getNutriScoreByLettre(columns[3].toUpperCase()),categorie , marque);
                produit.setIngredients(setIngredients);
                produit.setAllergenes(setAllergenes);
                produit.setAdditifs(setAdditifs);
                produitDAO.save(produit);
                et.commit();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Metodes
    /**
     * On transforme la chaine de caractère en double et si on ne peut pas on retroune 0.0.
     * @param string
     * @return Double
     */
    public static double isDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Enlever les caractères parasite.
     * @param string
     * @return String
     */
    public static String traiter(String string) {
        // Vérifier si la propriété ingredients est null
        if (string == null) {
            string = "";
        } else {
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
     * Création d'une marque.
     * @param marqueName
     * @param dao
     * @return Marque
     */
    public static Marque createMarque(String marqueName,MarqueDao dao) {
            Marque marque = null;
        marqueName = marqueName.trim();
        if (!marqueName.isEmpty()){
            try {
                marque = dao.findByNom(marqueName);
            } catch (RuntimeException e) {
                marque = new Marque(marqueName);
            }
        }
        return marque;
    }

    /**
     * Création d'une catégorie.
     * @param categorieName
     * @param dao
     * @return Categorie
     */
    public static Categorie createCategorie(String categorieName, CategorieDao dao) {
        Categorie categorie;
        try {
            categorie = dao.findByNom(categorieName);
            if (categorie == null) {
                categorie = new Categorie(categorieName);
            }
        } catch (NoResultException e) {
            categorie = new Categorie(categorieName);
        }
        return categorie;
    }

    /**
     * Création des ingrédients.
     * @param ingredientLine
     * @param dao
     * @return Set<Ingredient>
     */
    public static Set<Ingredient> createIngredients(String ingredientLine, IngredientDao dao) {
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
                    setIngredients.add(ingredient);
                }
            }
        }
        return setIngredients;
    }

    /**
     * Création des allergenes.
     * @param allergeneLine
     * @param dao
     * @return Set<Allergene>
     */
    public static Set<Allergene> createAllergenes(String allergeneLine, AllergeneDao dao) {
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
                    setAllergene.add(allergene);
                }
            }
        }
        return setAllergene;
    }

    /**
     * Création des additifs.
     * @param additifsLine
     * @param dao
     * @return Set<Additif>
     */
    public static Set<Additif> createAdditifs(String additifsLine, AdditifDao dao) {
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
                    setAdditifs.add(additif);
                }
            }
        }
        return setAdditifs;
    }
}
