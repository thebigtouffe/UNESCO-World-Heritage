package fr.thebigtouffe.unescoworldheritage.UNESCO;

public class Zone {
    private String name;
    private String name_fr;

    public Zone(String name, String name_fr) {
        this.name = name;
        this.name_fr = name_fr;
    }

    public String getName() {
        return name;
    }

    public String getName_fr() {
        return name_fr;
    }

}
