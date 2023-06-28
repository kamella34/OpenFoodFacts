package org.example;

import fr.digi.off.Categorie;
import fr.digi.off.Produit;
import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

import static java.lang.String.valueOf;

public class App2 {
    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("openfoodfacts");
             EntityManager em = emf.createEntityManager();) {

            EntityTransaction et = em.getTransaction();
            et.begin();
            String menu = """
                    1 - Rechercher les meilleurs produits d'une Marque.
                    2 - Rechercher les meilleurs produits d'une Catégorie.
                    3 - Rechercher les meilleurs produits par Marque et par Catégorie.
                    4 - Afficher les ingrédients les plus courants avec le nombre de produits dans lesquels ils apparaissent.
                    5 - Afficher les allergènes les plus courants avec le nombre de produits dans lesquels ils apparaissent.
                    6 - Afficher les additifs les plus courants avec le nombre de produits dans lesquels ils apparaissent.
                    7 - Exit.
                    """;
            System.out.println(menu);

            Scanner scanner = new Scanner(System.in);
            int choix = scanner.nextInt();
            switch (choix) {
                case 1:
                    System.out.println("1 - Rechercher les meilleurs produits d'une Marque:");
                    System.out.println("Entrez le nom de la Marque.");
                    String marque = scanner.next();
                    System.out.println("Entrez le lettre Nutriscore.");
                    String nutriScore = scanner.next();
                    System.out.println("Vous avez selectionner la marque "+marque+" et le nutriscore "+"'"+nutriScore+"'" + " Voici le resultat :");

                    Query query = em.createQuery("SELECT p from Produit p  where p.marques = :marque AND p.nutriScore = :nutriScore", Produit.class);
                    query.setParameter("marques",marque);
                    query.setParameter("nutriscore",nutriScore);
                    List<Produit> prod = query.getResultList();
                    System.out.println(prod);

                    break;
                case 2:
                    System.out.println("Rechercher les N meilleurs produits pour une Catégorie donnée. La catégorie et la valeur de N sont demandées à l’utilisateur");
                    break;
                case 3:
                    System.out.println("Rechercher les N meilleurs produits par Marque et par Catégorie. La marque, la catégorie et la valeur de N sont demandées à l’utilisateur");

                    break;
                case 4:
                    System.out.println("Afficher les N ingrédients les plus courants avec le nb de produits dans lesquels ils apparaissent. La valeur de N est demandée à l’utilisateur");

                    break;
                case 5:
                    System.out.println("Afficher les N allergènes les plus courants avec le nb de produits dans lesquels ils apparaissent. La valeur de N est demandée à l’utilisateur");

                    break;
                case 6:
                    System.out.println("Afficher les N additifs les plus courants avec le nb de produits dans lesquels ils apparaissent. La valeur de N est demandée à l’utilisateur");
                    break;
                case 7:
                    System.out.println("Fin de l'application");

                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix invalide");
                    break;
            }


            et.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}