package fr.thebigtouffe.heritage.UNESCO;

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

    private byte[] thumb;
    private String image1;
    private String image1_license;

    public Site(int number, String name) {
        this.number = number;
        this.name = name;
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

    public byte[] getThumb() { return thumb; }

    public String getImage1() {
        return image1;
    }

    public String getImage1_license() {
        return image1_license;
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

    public void setNumber(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public void setCriteria(ArrayList<Criterion> criteria) {
        this.criteria = criteria;
    }

    public void setEndangered(Boolean endangered) {
        this.endangered = endangered;
    }

    public void setYearInscribed(Integer yearInscribed) {
        this.yearInscribed = yearInscribed;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public void setHistorical_description(String historical_description) {
        this.historical_description = historical_description;
    }

    public void setThumb(byte[] thumb) {
        this.thumb = thumb;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public void setImage1_license(String image1_license) {
        this.image1_license = image1_license;
    }
}
