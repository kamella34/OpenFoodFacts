package fr.digi.off.entite;


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

    public static NutriScore getNutriScoreByLettre(String lettre) {
        for (NutriScore nutriScore : NutriScore.values()) {
            if (nutriScore.getLettreNutriScore().equals(lettre)) {
                return nutriScore;
            }
        }
        return null;
    }
}
