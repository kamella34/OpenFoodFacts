package org.example;

import fr.digi.off.Produit;

import java.util.regex.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Path path1 = Paths.get("C:/Users/theol/Documents/GitHub/Open-Food-Facts/src/main/resources/open-food-facts.csv");
        Path path2 = Paths.get("C:/Users/theol/Documents/GitHub/Open-Food-Facts/src/main/resources/open-food-facts-new.csv");
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
                System.out.println("replacedText = " + replacedText);
                line.split("|");
                Produit produit = new Produit();
            }
        } catch (Exception e) {
            System.err.println("ERROR:" + e.getMessage());
        }
    }
}