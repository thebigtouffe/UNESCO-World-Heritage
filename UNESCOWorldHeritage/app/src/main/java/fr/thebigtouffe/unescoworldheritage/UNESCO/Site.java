package fr.thebigtouffe.unescoworldheritage.UNESCO;

import java.util.ArrayList;

public class Site {

    private int number;
    private String name;

    private Category category;
    private Zone zone;
    private ArrayList<Country> countries;

    private ArrayList<Criterion> criteria;
    private Boolean endangered;
    private Integer yearInscribed;

    private Double latitude;
    private Double longitude;

    private String long_description;
    private String short_description;
    private String justification;
    private String historical_description;

    public Site(int number, String name, Category category, Zone zone,
                ArrayList<Country> countries,
                ArrayList<Criterion> criteria, Boolean endangered, Integer yearInscribed,
                Double latitude, Double longitude,
                String long_description,
                String short_description,
                String justification,
                String historical_description) {
        this.number = number;
        this.name = name;
        this.category = category;
        this.zone = zone;
        this.countries = countries;
        this.criteria = criteria;
        this.endangered = endangered;
        this.yearInscribed = yearInscribed;
        this.latitude = latitude;
        this.longitude = longitude;
        this.long_description = long_description;
        this.short_description = short_description;
        this.justification = justification;
        this.historical_description = historical_description;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
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

    public String getShort_description() {
        return short_description;
    }

    public String getJustification() {
        return justification;
    }

    public String getHistorical_description() {
        return historical_description;
    }

    @Override
    public String toString() {
        return "Site{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", zone=" + zone +
                ", countries=" + countries +
                ", criteria=" + criteria +
                ", endangered=" + endangered +
                ", yearInscribed=" + yearInscribed +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", long_description='" + long_description + '\'' +
                ", short_description='" + short_description + '\'' +
                ", justification='" + justification + '\'' +
                ", historical_description='" + historical_description + '\'' +
                '}';
    }
}
