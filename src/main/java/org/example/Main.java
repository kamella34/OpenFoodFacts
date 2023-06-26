package org.example;

import fr.digi.off.*;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Path path1 = Paths.get("src/main/resources/open-food-facts.csv");
        Path path2 = Paths.get("src/main/resources/open-food-facts-new.csv");
        try {
            List<String> lines = Files.readAllLines(path1, StandardCharsets.UTF_8);
            for (String line : lines) {
                Pattern pattern = Pattern.compile("(\\|)+");
                Matcher matcher = pattern.matcher(line);
                StringBuffer stringBuffer = new StringBuffer();
                while (matcher.find()) {
                    matcher.appendReplacement(stringBuffer, "|");
                }
                matcher.appendTail(stringBuffer);
                String replacedText = stringBuffer.toString();
                replacedText = replacedText.replaceAll("_", "");
                //System.out.println("replacedText = " + replacedText);

                //Séparation des élément
                String[] token = line.split("|");

                //Catégorie
                Categorie categorie = new Categorie(token[0]);

                //Marque
                Marque marque = new Marque(token[1]);

                //Joule
                String joule = token[5];
                joule = joule.replaceAll("o", "0");

                //Graisse
                String graisse = token[6];
                graisse = graisse.replaceAll("o", "0");

                //Produit
                Produit produit = new Produit(token[0], Double.parseDouble(joule), Double.parseDouble(graisse), NutriScore.getNutriScoreByLettre(token[3]) , categorie, marque);

                //Ingredients
                String[] ingredients = token[4].split(",");
                Set<Ingredient> ingredientTab = new HashSet<>();
                for (String ingredient: ingredients) {
                    Ingredient ingredient1 = new Ingredient(ingredient);
                    ingredientTab.add(ingredient1);
                }
                produit.setIngredients(ingredientTab);

                //Additif
                if (token[29] != null) {
                    String[] additifs = token[29].split(",");
                    Set<Additif> additifTab = new HashSet<>();
                    for (String additif: additifs) {
                        Additif additif1 = new Additif(additif);
                        additifTab.add(additif1);
                    }
                    produit.setAdditifs(additifTab);
                }else {
                    produit.setAdditifs(null);
                }


                //Allergenes
                if (token[28] != null) {
                    String[] allergenes = token[28].split(",");
                    Set<Allergene> allergeneTab = new HashSet<>();
                    for (String allergene: allergenes) {
                        Allergene allergene1 = new Allergene(allergene);
                        allergeneTab.add(allergene1);
                    }
                    produit.setAllergenes(allergeneTab);
                }else {
                    produit.setAllergenes(null);
                }
                System.out.println("produit = " + produit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}