package fr.digi.off;

public enum NutriScore {
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F");

    String lettreNutriScore;

    NutriScore(String lettreNutriScore) {
        this.lettreNutriScore = lettreNutriScore;
    }

    public String getLettreNutriScore() {
        return lettreNutriScore;
    }

    public void setLettreNutriScore(String lettreNutriScore) {
        this.lettreNutriScore = lettreNutriScore;
    }
}
