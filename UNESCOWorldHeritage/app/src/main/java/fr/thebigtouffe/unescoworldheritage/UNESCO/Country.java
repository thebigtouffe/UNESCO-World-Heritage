package fr.thebigtouffe.unescoworldheritage.UNESCO;

public class Country {
    private String name;
    private String name_fr;
    private String iso;

    public Country(String name, String name_fr, String iso) {
        this.name = name;
        this.name_fr = name_fr;
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public String getName_fr() {
        return name_fr;
    }

    public String getIso() {
        return iso;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                '}';
    }
}
