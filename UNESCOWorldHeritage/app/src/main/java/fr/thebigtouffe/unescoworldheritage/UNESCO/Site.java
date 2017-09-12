package fr.thebigtouffe.unescoworldheritage.UNESCO;

import java.util.ArrayList;

public class Site {

    private int number;
    private String name;
    private String name_fr;

    private Category category;
    private Zone zone;
    private ArrayList<Country> countries;

    private ArrayList<Criterion> criteria;
    private Boolean endangered;
    private Integer yearInscribed;

    private Double latitude;
    private Double longitude;

    private String long_description;
    private String long_description_fr;
    private String short_description;
    private String short_description_fr;
    private String justification;
    private String justification_fr;
    private String historical_description;
    private String historical_description_fr;

    public Site(int number, String name, String name_fr, Category category, Zone zone,
                ArrayList<Country> countries,
                ArrayList<Criterion> criteria, Boolean endangered, Integer yearInscribed,
                Double latitude, Double longitude,
                String long_description, String long_description_fr,
                String short_description, String short_description_fr,
                String justification, String justification_fr,
                String historical_description, String historical_description_fr) {
        this.number = number;
        this.name = name;
        this.name_fr = name_fr;
        this.category = category;
        this.zone = zone;
        this.countries = countries;
        this.criteria = criteria;
        this.endangered = endangered;
        this.yearInscribed = yearInscribed;
        this.latitude = latitude;
        this.longitude = longitude;
        this.long_description = long_description;
        this.long_description_fr = long_description_fr;
        this.short_description = short_description;
        this.short_description_fr = short_description_fr;
        this.justification = justification;
        this.justification_fr = justification_fr;
        this.historical_description = historical_description;
        this.historical_description_fr = historical_description_fr;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getName_fr() {
        return name_fr;
    }

    public Category getCategory() {
        return category;
    }

    public Zone getZone() {
        return zone;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public ArrayList<Criterion> getCriteria() {
        return criteria;
    }

    public Boolean getEndangered() {
        return endangered;
    }

    public Integer getYearInscribed() {
        return yearInscribed;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getLong_description() {
        return long_description;
    }

    public String getLong_description_fr() {
        return long_description_fr;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getShort_description_fr() {
        return short_description_fr;
    }

    public String getJustification() {
        return justification;
    }

    public String getJustification_fr() {
        return justification_fr;
    }

    public String getHistorical_description() {
        return historical_description;
    }

    public String getHistorical_description_fr() {
        return historical_description_fr;
    }

    @Override
    public String toString() {
        return "Site{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", name_fr='" + name_fr + '\'' +
                ", category=" + category +
                ", zone=" + zone +
                ", countries=" + countries +
                ", criteria=" + criteria +
                ", endangered=" + endangered +
                ", yearInscribed=" + yearInscribed +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", long_description='" + long_description + '\'' +
                ", long_description_fr='" + long_description_fr + '\'' +
                ", short_description='" + short_description + '\'' +
                ", short_description_fr='" + short_description_fr + '\'' +
                ", justification='" + justification + '\'' +
                ", justification_fr='" + justification_fr + '\'' +
                ", historical_description='" + historical_description + '\'' +
                ", historical_description_fr='" + historical_description_fr + '\'' +
                '}';
    }
}
