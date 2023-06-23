package fr.digi.off;

public class Produits {

 private Integer id;

 private String nom;
 private Double JoulePrCent;
 private Double GraissePrCent;

 public Produits() {
 }

 public Integer getId() {
  return id;
 }

 public void setId(Integer id) {
  this.id = id;
 }

 public String getNom() {
  return nom;
 }

 public void setNom(String nom) {
  this.nom = nom;
 }

 public Double getJoulePrCent() {
  return JoulePrCent;
 }

 public void setJoulePrCent(Double joulePrCent) {
  JoulePrCent = joulePrCent;
 }

 public Double getGraissePrCent() {
  return GraissePrCent;
 }

 public void setGraissePrCent(Double graissePrCent) {
  GraissePrCent = graissePrCent;
 }
}
