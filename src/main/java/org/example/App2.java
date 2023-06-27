package org.example;

import fr.digi.off.Ingredient;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App2 {
  public static void main(String[] args) {
    String newString = "Purée de pommes 76%, purée de coings 20%, sucre de canne roux 4%, antioxydant  acide ascorbique. Issus de l'agriculture biologique.";
    Pattern pattern4 = Pattern.compile("[\\d.%]+");
    Matcher matcher4 = pattern4.matcher(newString);
    String newString2 = matcher4.replaceAll( "");
    String[] ingredients = newString2.split(",");

    //System.out.println(newString2);
    Set<Ingredient> ingredientTab = new HashSet<>();
    for(String ingredient : ingredients){
      Ingredient newIngredient = new Ingredient(ingredient);
      ingredientTab.add(newIngredient);
    }
    System.out.println(ingredientTab);
  }


}
