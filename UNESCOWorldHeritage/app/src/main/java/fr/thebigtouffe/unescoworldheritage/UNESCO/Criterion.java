package fr.thebigtouffe.unescoworldheritage.UNESCO;

public class Criterion {
    private int number;
    private String desription;

    public Criterion(int number, String desription) {
        this.number = number;
        this.desription = desription;
    }

    public int getNumber() {
        return number;
    }

    public String getDesription() {
        return desription;
    }

}
