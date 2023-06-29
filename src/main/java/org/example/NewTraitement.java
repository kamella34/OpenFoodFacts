package org.example;

import fr.digi.off.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewTraitement {
    public static void main(String[] args) {
        List<Produit> produits = new ArrayList<>();
        Path path1 = Paths.get("src/main/resources/open-food-facts.csv");
        try{
            List<String> lines = Files.readAllLines(path1, StandardCharsets.UTF_8);
            for (int i = 1; i < lines.size(); i++) {
                String[] elements = formaté(lines.get(i));

                // Traitement des caractères parasites, pourcentages et informations entre parenthèses
                //Nom
                String nom = traiter(elements[2]);

                //Graisse
                String graisseString = elements[6];
                double graisse = 0.0;
                if (!graisseString.isEmpty()) {
                    graisse = Double.parseDouble(graisseString);
                }

                //Joule
                String jouleString = elements[5];
                double joule = 0.0;
                if (!jouleString.isEmpty()) {
                    joule = Double.parseDouble(jouleString);
                }

                //Catégorie
                Categorie categorie = new Categorie(traiter(elements[0]));

                //Marque
                Marque marque = new Marque(traiter(elements[1]));

                //Nutriscore
                String nutriscore = elements[3];

                //Produit
                Produit produit = new Produit(nom, joule, graisse, NutriScore.getNutriScoreByLettre(nutriscore.toUpperCase()),categorie , marque);

                //Ingrédients
                String ingredients = traiter(elements[4]);
                for (Ingredient ingredient: createIngredients(ingredients)) {
                    produit.getIngredients().add(ingredient);
                }
                
                //Alergènes
                String allergenes = traiter(elements[28]);
                for (Allergene allergene: createAllergenes(allergenes)) {
                    produit.getAllergenes().add(allergene);
                }

                //Additifs
                String additifs = traiter(elements[29]);
                for (Additif additif: createAdditifs(additifs)) {
                    produit.getAdditifs().add(additif);
                }
                produits.add(produit);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("openfoodfactsdev"); EntityManager em = emf.createEntityManager();) {
            EntityTransaction et = em.getTransaction();
            et.begin();
            // Boucle pour persister chaque produit dans la base de données
            // Persistez les ingrédients
            for (Produit produit : produits) {
                // Persistez les ingrédients
                for (Ingredient ingredient : produit.getIngredients()) {
                    em.persist(ingredient);
                }

                // Persistez les allergènes
                for (Allergene allergene : produit.getAllergenes()) {
                    em.persist(allergene);
                }

                // Persistez les additifs
                for (Additif additif : produit.getAdditifs()) {
                    em.persist(additif);
                }
            }
            // Puis persistez les produits
            for (Produit produit : produits) {
                em.persist(produit);
            }
            // Validation de la transaction
            et.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Enlève les caractères parasite
     * @param elements
     * @return elements
     */
    public static String traiter(String elements) {
        // Vérifier si la propriété ingredients est null
        if (elements == null) {
            elements = ""; // Ou une autre valeur par défaut
        } else {
            // Le reste du traitement...
            elements = elements.replaceAll("\\*", "")
                    .replaceAll("_", "")
                    .replaceAll("\\s*\\([^\\)]*\\)\\s*", "")
                    .replaceAll("\\d+%?", "")
                    .replaceAll(",", "")
                    .replaceAll("%", "")
                    .replaceAll("[.:\\-,]", ";")
                    .replaceAll("[()]", "")
                    .trim();
        }
        return elements;
    }

    /**
     * Formaté le fichier
     * @param elements
     * @return elementsTab
     */
    public static String[] formaté(String elements){
        elements = elements.substring(0, elements.length() - 1);
        String[] charsTab = elements.split("");
        int pipeCount = 0;
        int pipeIngredient = 4;
        int pipeAttendue = 29;
        for (int ii = 0; ii < charsTab.length; ii++) {
            //Compte le nombre de pipe apres ingrédient
            if (charsTab[ii].equals("|")) {
                pipeCount++;
            }
        }
        if (pipeAttendue < pipeCount) {
            int pipeSurplus = pipeCount - pipeAttendue;
            int pipeCount2 = 0;
            //on boucle sur la ligne
            for (int j = 0; j < charsTab.length; j++) {
                //Si on croise un pipe alors pipeCount2++
                if (charsTab[j].equals("|")) {
                    pipeCount2++;
                }
                if (pipeIngredient < pipeCount2) {
                    if (pipeSurplus != 0) {
                        charsTab[j] = "l";
                    }
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String el : charsTab) {
            stringBuilder.append(el);
        }
        elements = stringBuilder.toString();
        String[] elementsTab = elements.split("\\|");
        if (elementsTab.length < 30) {
            String[] newToken = new String[30]; // Créer un tableau avec 30 éléments
            System.arraycopy(elementsTab, 0, newToken, 0, elementsTab.length);
            for (int j = elementsTab.length; j < 30; j++) {
                newToken[j] = ""; // Remplir les éléments manquants avec une valeur par défaut (chaîne vide)
            }
            elementsTab = newToken;
        }
        return elementsTab;
    }

    /**
     * Création des ingrédients
     * @param ingredients
     * @return ingredientsTab
     */
    public static Set<Ingredient> createIngredients(String ingredients){
        Set<Ingredient> ingredientsTab = new HashSet<>();
        String[] listIngredients = ingredients.split(";");
        for (String ingredient: listIngredients) {
            if (ingredient != null || ingredient != "") {
                Ingredient objectIngredient = new Ingredient(ingredient.toLowerCase());
                ingredientsTab.add(objectIngredient);
            }
        }
        return ingredientsTab;
    }

    /**
     * Création des allergènes
     * @param allergenes
     * @return allergenesTab
     */
    public static Set<Allergene> createAllergenes(String allergenes){
        Set<Allergene> allergenesTab = new HashSet<>();
        String[] listAllergenes = allergenes.split(";");
        for (String allergene: listAllergenes) {
            if (allergene != null || allergene != "") {
                Allergene objectAllergene = new Allergene(allergene.toLowerCase());
                allergenesTab.add(objectAllergene);
            }
        }
        return allergenesTab;
    }

    /**
     * Création des additifs
     * @param additifs
     * @return additifsTab
     */
    public static Set<Additif> createAdditifs(String additifs){
        Set<Additif> additifsTab = new HashSet<>();
        String[] listAdditifs = additifs.split(";");
        for (String additif: listAdditifs) {
            if (additif != null || additif != "") {
                Additif objectAdditif = new Additif(additif.toLowerCase());
                additifsTab.add(objectAdditif);
            }
        }
        return additifsTab;
    }
}
