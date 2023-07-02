package org.example;

import fr.digi.off.Categorie;
import fr.digi.off.Marque;
import fr.digi.off.Produit;
import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("openfoodfacts1");
             EntityManager em = emf.createEntityManager();) {
            EntityTransaction et = em.getTransaction();

            et.begin();
            Cache cache = emf.getCache();
            boolean isInCache = cache.contains(Categorie.class, 1);
            System.out.println(isInCache);

            Categorie cat = em.find(Categorie.class,1);
            et.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                break;
            case 5:
                // Afficher les N allergènes les plus courants avec le nb de produits dans lesquels ils apparaissent. La valeur de N est demandée à l’utilisateur
                break;
            case 6:
                // Afficher les N additifs les plus courants avec le nb de produits dans lesquels ils apparaissent. La valeur de N est demandée à l’utilisateur
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

    private static void produitParMarque(int nombre, String marque) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("openfoodfacts");
             EntityManager em = emf.createEntityManager();) {
            String jpqlMarque = "SELECT m FROM Marque m WHERE m.nom = :nom";
            TypedQuery<Marque> queryMarque = em.createQuery(jpqlMarque, Marque.class);
            queryMarque.setParameter("nom", marque);
            queryMarque.setMaxResults(1);
            Marque marqueObj = queryMarque.getSingleResult();
            String jpqlProduit = "SELECT p FROM Produit p, Marque m WHERE p.categorie = :marque ORDER BY p.nutriscore ASC";
            TypedQuery<Produit> queryProduit = em.createQuery(jpqlProduit, Produit.class);
            queryProduit.setParameter("marque", marqueObj);
            queryProduit.setMaxResults(nombre);
            List<Produit> produits = queryProduit.getResultList();
            for (Produit produit: produits) {
                System.out.println("produit = " + produit);
            }
        }
    }
    private static void produitParCategorie(int nombre, String categorie) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("openfoodfacts");
             EntityManager em = emf.createEntityManager();) {
            System.out.println("categorie = " + categorie);
            String jpqlCategorie = "SELECT c FROM Categorie c WHERE c.nom = :nom";
            TypedQuery<Categorie> queryCategory = em.createQuery(jpqlCategorie, Categorie.class);
            queryCategory.setParameter("nom", categorie);
            queryCategory.setMaxResults(1);
            Categorie categorieObj = queryCategory.getSingleResult();
            String jpqlProduit = "SELECT p FROM Produit p, Categorie c WHERE p.categorie = :categorie ORDER BY p.nutriscore ASC";
            TypedQuery<Produit> queryProduit = em.createQuery(jpqlProduit, Produit.class);
            queryProduit.setParameter("categorie", categorieObj);
            queryProduit.setMaxResults(nombre);
            List<Produit> produits = queryProduit.getResultList();
            for (Produit produit: produits) {
                System.out.println("produit = " + produit);
            }
        }
    }
}
