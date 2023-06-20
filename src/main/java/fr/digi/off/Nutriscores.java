package fr.digi.off;

public enum Nutriscores {
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F");

    String lettreNutriScore;

    private Nutriscores(String lettreNutriScore) {
        this.lettreNutriScore = lettreNutriScore;
    }

    public String getLettreNutriScore() {
        return lettreNutriScore;
    }
}
