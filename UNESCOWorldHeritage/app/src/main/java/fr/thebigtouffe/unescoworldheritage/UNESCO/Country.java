package fr.thebigtouffe.heritage.UNESCO;

public class Country {
    private Integer id;
    private String name;
    private String iso;
    private Integer numberCulturalSites = 0;
    private Integer numberMixedSites = 0;
    private Integer numberNaturalSites = 0;

    public Country(Integer id, String name, String iso) {
        this.name = name;
        this.id = id;
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    public Integer getNumberCulturalSites() {
        return numberCulturalSites;
    }

    public void setNumberCulturalSites(Integer numberCulturalSites) {
        this.numberCulturalSites = numberCulturalSites;
    }

    public Integer getNumberMixedSites() {
        return numberMixedSites;
    }

    public void setNumberMixedSites(Integer numberMixedSites) {
        this.numberMixedSites = numberMixedSites;
    }

    public Integer getNumberNaturalSites() {
        return numberNaturalSites;
    }

    public void setNumberNaturalSites(Integer numberNaturalSites) {
        this.numberNaturalSites = numberNaturalSites;
    }

    public String getIso() {
        return iso;
    }
}
