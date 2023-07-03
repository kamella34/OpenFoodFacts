package org.example;

import fr.digi.off.entite.Additif;
import fr.digi.off.entite.Allergene;
import fr.digi.off.entite.Ingredient;
import fr.digi.off.entite.Produit;
import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        while(true){
            String menu = """
                    \n
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
                    // Rechercher les N meilleurs produits pour une Marque donnée. La marque et la valeur de N sont demandées à l’utilisateur
                    System.out.println("Nombre de produits a afficher :");
                    int nb = scanner.nextInt();
                    System.out.println("Marque :");
                    String marque = scanner.next();
                    produitParMarque(nb, marque);
                    break;
                case 2:
                    // Rechercher les N meilleurs produits pour une Catégorie donnée. La catégorie et la valeur de N sont demandées à l’utilisateur
                    System.out.println("Nombre de produits a afficher :");
                    nb = scanner.nextInt();
                    System.out.println("Catégorie :");
                    String categorie = scanner.next();
                    produitParCategorie(nb, categorie);
                    break;
                case 3:
                    // Rechercher les N meilleurs produits par Marque et par Catégorie. La marque, la catégorie et la valeur de N sont demandées à l’utilisateur
                    System.out.println("Nombre de produits a afficher :");
                    nb = scanner.nextInt();
                    System.out.println("Marque :");
                    marque = scanner.next();
                    System.out.println("Catégorie :");
                    categorie = scanner.next();
                    produitParMarqueEtCategorie(nb, categorie, marque);
                    break;
                case 4:
                    // Afficher les N ingrédients les plus courants avec le nb de produits dans lesquels ils apparaissent. La valeur de N est demandée à l’utilisateur
                    System.out.println("Nombre d'ingredient à afficher :");
                    nb = scanner.nextInt();
                    ingredientEtNbProduit(nb);
                    break;
                case 5:
                    // Afficher les N allergènes les plus courants avec le nb de produits dans lesquels ils apparaissent. La valeur de N est demandée à l’utilisateur
                    System.out.println("Nombre d'allergènes à afficher :");
                    nb = scanner.nextInt();
                    allergeneEtNbProduit(nb);
                    break;
                case 6:
                    // Afficher les N additifs les plus courants avec le nb de produits dans lesquels ils apparaissent. La valeur de N est demandée à l’utilisateur
                    System.out.println("Nombre d'additif a afficher :");
                    int nbAdd = scanner.nextInt();
                    additifEtNbProduit(nbAdd);
                    break;
                case 7:
                    // Fin de l'application
                    System.out.println("Fin de l'application");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix invalide");
                    break;
            }
        }
    }

    /**
     * Chercher et afficher les additifs les plus courants avec le nombre de produits dans lesquels ils apparaissent.
     * @param nb
     */
    private static void additifEtNbProduit(int nb){

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("openfoodfacts");
             EntityManager em = emf.createEntityManager();) {
            Query query = em.createQuery("SELECT a, COUNT(p) AS nombreProduit FROM Additif a JOIN a.produits p GROUP BY a ORDER BY nombreProduit DESC");
            query.setMaxResults(nb);
            List<Object[]> results = query.getResultList();
            for (Object[] result: results) {
                Additif additif = (Additif) result[0];
                Long nombreProduit = (Long) result[1];
                System.out.println("Additif = " + additif);
                System.out.println("nombreProduit = " + nombreProduit);
            }
        }
    }

    /**
     * Chercher et afficher les allergènes les plus courants avec le nombre de produits dans lesquels ils apparaissent.
     * @param nb
     */
    private static void allergeneEtNbProduit(int nb) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("openfoodfacts");
             EntityManager em = emf.createEntityManager();) {
            Query query = em.createQuery("SELECT a, COUNT(p) AS nombreProduit FROM Allergene a JOIN a.produits p GROUP BY a ORDER BY nombreProduit DESC");
            query.setMaxResults(nb);
            List<Object[]> results = query.getResultList();
            for (Object[] result: results) {
                Allergene allergene = (Allergene) result[0];
                Long nombreProduit = (Long) result[1];
                System.out.println("allergene = " + allergene);
                System.out.println("nombreProduit = " + nombreProduit);
            }
        }
    }

    /**
     * Chercher et afficher les ingrédients les plus courants avec le nombre de produits dans lesquels ils apparaissent.
     * @param nb
     */
    private static void ingredientEtNbProduit(int nb) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("openfoodfacts");
             EntityManager em = emf.createEntityManager();) {
            Query query = em.createQuery("SELECT i, COUNT(p) AS nombreProduit FROM Ingredient i JOIN i.produits p GROUP BY i ORDER BY nombreProduit DESC");
            query.setMaxResults(nb);
            List<Object[]> results = query.getResultList();
            for (Object[] result: results) {
                Ingredient ingredient = (Ingredient) result[0];
                Long nombreProduit = (Long) result[1];
                System.out.println("ingredient = " + ingredient);
                System.out.println("nombreProduit = " + nombreProduit);
            }
        }
    }

    /**
     * Chercher et afficher les meilleurs produits par Marque et par Catégorie.
     * @param nb
     * @param categorie
     * @param marque
     */
    private static void produitParMarqueEtCategorie(int nb, String categorie, String marque) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("openfoodfacts");
             EntityManager em = emf.createEntityManager();) {
            Query query = em.createQuery("SELECT p FROM Produit p WHERE p.categorie.nom = :categorie AND p.marque.nom = :marque ORDER BY p.nutriscore");
            query.setParameter("categorie", categorie);
            query.setParameter("marque", marque);
            query.setMaxResults(nb);
            List<Produit> produits = query.getResultList();
            for (Produit produit : produits) {
                System.out.println(produit);
            }
        }
    }

    /**
     * Chercher et afficher les meilleurs produits pour une Marque donnée.
     * @param nombre
     * @param marque
     */
    private static void produitParMarque(int nombre, String marque) {
        try(EntityManagerFactory emf = Persistence.createEntityManagerFactory("openfoodfacts");
            EntityManager em = emf.createEntityManager();) {
            String jpqlProduit = "SELECT p FROM Produit p, Marque m WHERE p.marque.nom = :marque ORDER BY p.nutriscore ASC";
            TypedQuery<Produit> queryProduit = em.createQuery(jpqlProduit, Produit.class);
            queryProduit.setParameter("marque", marque);
            queryProduit.setMaxResults(nombre);
            List<Produit> produits = queryProduit.getResultList();
            for (Produit produit: produits) {
                System.out.println("produit = " + produit);
            }
        }
    }

    /**
     * Chercher et afficher les meilleurs produits pour une Catégorie donnée.
     * @param nombre
     * @param categorie
     */
    private static void produitParCategorie(int nombre, String categorie) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("openfoodfacts");
             EntityManager em = emf.createEntityManager();) {
            String jpqlProduit = "SELECT p FROM Produit p, Categorie c WHERE p.categorie.nom = :categorie ORDER BY p.nutriscore ASC";
            TypedQuery<Produit> queryProduit = em.createQuery(jpqlProduit, Produit.class);
            queryProduit.setParameter("categorie", categorie);
            queryProduit.setMaxResults(nombre);
            List<Produit> produits = queryProduit.getResultList();
            for (Produit produit: produits) {
                System.out.println("produit = " + produit);
            }
        }
    }
}