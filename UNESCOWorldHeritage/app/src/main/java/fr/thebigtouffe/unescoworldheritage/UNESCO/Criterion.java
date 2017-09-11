package fr.thebigtouffe.unescoworldheritage.UNESCO;

public class Criterion {
    private int number;
    private String desription;
    private String description_fr;

    public Criterion(int number, String desription, String description_fr) {
        this.number = number;
        this.desription = desription;
        this.description_fr = description_fr;
    }

    public int getNumber() {
        return number;
    }

    public String getDesription() {
        return desription;
    }

    public String getDescription_fr() {
        return description_fr;
    }

}
