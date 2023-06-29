package org.example;

import fr.digi.off.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {


    public static void main(String[] args) {
        Path path1 = Paths.get("src/main/resources/open-food-facts.csv");
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("openfoodfactsdev"); EntityManager em = emf.createEntityManager();) {
            EntityTransaction et = em.getTransaction();
            List<String> lines = Files.readAllLines(path1, StandardCharsets.UTF_8);
            for (int i = 1; i < lines.size(); i++) {
                System.out.println(i);
                String line = lines.get(i);
                line = line.substring(0, line.length() - 1);
                line = line.replaceAll("[*_:]", "");

                String[] charsTab = line.split("");
                int pipeCount = 0;
                int pipeIngredient = 4;
                int pipeAttendue = 29;
                for (int ii = 0; ii < charsTab.length; ii++) {
                    //Compte le nombre de pipe apres ingradient
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
                line = stringBuilder.toString();
                System.out.println("LINE" + line);

                //Séparation des élément
                String[] token = line.split("\\|");
                while (token.length < 30) {
                    String[] newToken = new String[token.length + 1];
                    System.arraycopy(token, 0, newToken, 0, token.length);
                    newToken[token.length] = null;
                    token = newToken;
                }
                System.out.println("\n---------------------------------------------------------------------------------------------------------------\n");
                for (int ii = 0; ii < token.length; ii++) {
                    if (token[ii] == "") {
                        token[ii] = null;
                    }
                }

                //Catégorie
                Categorie categorie = new Categorie(token[0]);

                //Marque
                Marque marque = new Marque(token[1]);

                //Joule
                String joule = token[5];
                if (joule == null) {
                    joule = "0";
                }

                //Graisse
                String graisse = token[6];
                if (graisse == null) {
                    graisse = "0";
                }

                //Produit
                Produit produit = new Produit(token[2], Double.parseDouble(joule), Double.parseDouble(graisse), NutriScore.getNutriScoreByLettre(token[3].toUpperCase()), categorie, marque);
                System.out.println("PRODUIT"+produit);

                //Ingredients
                String[] ingredients = token[4].split(",");
                for (String ingredient : ingredients) {
                    Ingredient ingredient1 = new Ingredient(ingredient.replaceAll("[\\d.%]+", ""));
                    em.persist(ingredient1);
                    produit.getIngredients().add(ingredient1);
                }

                //Additif
                if (token[29] != null) {
                    String[] additifs = token[29].split(",");
                    for (String additif: additifs) {
                        Additif additif1 = new Additif(additif);
                        // Enregistrer l'instance Additif dans la base de données
                        produit.getAdditifs().add(additif1);
                        em.persist(additif1);
                    }
                }else {
                    produit.getAdditifs().add(null);
                }

                //Allergenes
                if (token[28] != null) {
                    String[] allergenes = token[28].split(",");
                    for (String allergene : allergenes) {
                        Allergene allergene1 = new Allergene(allergene);
                        produit.getAllergenes().add(allergene1);
                        em.persist(allergene1);
                    }
                } else {
                    produit.setAllergenes(null);
                }

                et.begin();
                em.persist(produit);
                et.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}