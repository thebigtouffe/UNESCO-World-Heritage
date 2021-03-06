package fr.thebigtouffe.heritage.UNESCO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.mikephil.charting.data.PieEntry;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Database extends SQLiteAssetHelper {

    private Boolean isFrench = Locale.getDefault().getLanguage().equals("fr");

    private static final String DATABASE_NAME = "unesco.db";
    private static final int DATABASE_VERSION = 20170918;

    private ArrayList<Country> countries = new ArrayList<>();

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

    public ArrayList<Country> getCountries(String option) {

        countries = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT app_country.id, app_country.name, app_country.name_fr, " +
                "count(site_id) AS count, app_country.iso " +
                "FROM app_country JOIN app_site_country ON app_country.id=country_id " +
                "JOIN app_site ON app_site.number = app_site_country.site_id ";

        query = addOptionsToQuery(query, option);
        query += " GROUP BY app_country.id ";

        // Order countries by names
        if (isFrench) {
            query += "ORDER BY app_country.name_fr COLLATE UNICODE";
        }
        else {
            query += "ORDER BY app_country.name COLLATE UNICODE";
        }

        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            Integer id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String iso = c.getString(c.getColumnIndex("iso"));

            if (isFrench)
                name = c.getString(c.getColumnIndex("name_fr"));

            Integer count = c.getInt(c.getColumnIndex("count"));

            // Exclude countries without any registered site
            if (count > 0) {
                Country country = new Country(id, name, iso);
                countries.add(country);
            }
        }
        c.close();

        setNumberSitesByCountry("Cultural");
        setNumberSitesByCountry("Natural");
        setNumberSitesByCountry("Mixed");

        return countries;
    }

    private void setNumberSitesByCountry(String category) {

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT country_id, count(*) AS count FROM app_site " +
                "JOIN app_site_country ON site_id=number " +
                "JOIN app_country ON country_id = app_country.id " +
                "JOIN app_category ON app_category.id = app_site.category_id " +
                "WHERE app_category.name = ? " +
                "GROUP BY country_id, category_id ";
        Cursor c = db.rawQuery(query, new String[] {category});

        while (c.moveToNext()) {
            Integer countryId = c.getInt(c.getColumnIndex("country_id"));
            Integer count = c.getInt(c.getColumnIndex("count"));

            Country country = getCountryById(countryId);
            if (country != null) {
                if (category.equals("Cultural"))
                    country.setNumberCulturalSites(count);
                if (category.equals("Natural"))
                    country.setNumberNaturalSites(count);
                if (category.equals("Mixed"))
                    country.setNumberMixedSites(count);
            }
        }
        c.close();
    }

    public Country getCountryById(int id) {
        if (countries.size() < 1) {
            getCountries("default");
        }

        for(int i=0; i < countries.size();i++) {
            Country country = countries.get(i);
            if (country.getId() == id) {
                return country;
            }
        }
        return null;
    }

    public Integer getZoneIdByName(String name) {
        Integer id = -1;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from app_zone WHERE name = ?", new String[] {name});

        if (c.moveToFirst()) {
            id = c.getInt(c.getColumnIndex("id"));
        }
        c.close();
        return id;
    }

    private String addOptionsToQuery(String query, String option) {

        // Zone option
        if (option.equals("africa")) {
            query += "WHERE zone_id =" + getZoneIdByName("Africa");
        }
        else if (option.equals("asia")) {
            query += "WHERE zone_id =" + getZoneIdByName("Asia and the Pacific");
        }
        else if (option.equals("europe")) {
            query += "WHERE zone_id =" + getZoneIdByName("Europe and North America");
        }
        else if (option.equals("arab")) {
            query += "WHERE zone_id =" + getZoneIdByName("Arab States");
        }
        else if (option.equals("latin")) {
            query += "WHERE zone_id =" + getZoneIdByName("Latin America and the Caribbean");
        }

        return query;
    }


    public ArrayList<Site> getSitesByCountry(Country country) {
        ArrayList<Site> sites = new ArrayList<Site>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT number, app_site.name, app_site.name_fr, year_inscribed, " +
                "app_category.name AS category, app_category.name_fr AS category_fr, thumb " +
                "FROM app_site JOIN app_site_country ON number = site_id " +
                "JOIN app_category ON app_category.id = app_site.category_id " +
                "WHERE country_id = ? ";


        // Group sites by names
        if (isFrench) {
            query += "ORDER BY app_site.name_fr COLLATE UNICODE";
        }
        else {
            query += "ORDER BY app_site.name COLLATE UNICODE";
        }

        // Retrieve results
        Cursor c = db.rawQuery(query, new String[] {""+country.getId()});
        while (c.moveToNext()) {
            int number = c.getInt(c.getColumnIndex("number"));
            String name = c.getString(c.getColumnIndex("name"));
            String categoryName = c.getString(c.getColumnIndex("category"));

            if (isFrench) {
                name = c.getString(c.getColumnIndex("name_fr"));
                categoryName = c.getString(c.getColumnIndex("category_fr"));
            }

            Category category = new Category(categoryName);
            Integer yearInscribed = c.getInt(c.getColumnIndex("year_inscribed"));
            byte[] thumb = c.getBlob(c.getColumnIndex("thumb"));

            Site site = new Site(number, name);
            site.setYearInscribed(yearInscribed);
            site.setCategory(category);
            site.setThumb(thumb);

            sites.add(site);
        }
        c.close();
        return sites;
    }

    public ArrayList<Country> getCountriesBySiteID(int id) {
        ArrayList<Country> countries = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String countriesQuery = "SELECT app_country.id, name, name_fr " +
                "FROM app_country JOIN app_site_country ON country_id=app_country.id " +
                "WHERE site_id = ?";
        Cursor c = db.rawQuery(countriesQuery, new String[] {""+id});

        while (c.moveToNext()) {
            Integer country_id = c.getInt(c.getColumnIndex("id"));
            String country_name = c.getString(c.getColumnIndex("name"));

            if (isFrench)
                country_name = c.getString(c.getColumnIndex("name_fr"));

            countries.add(new Country(country_id, country_name, null));
        }

        return countries;
    }

    public Site getSiteById(int id) {
        String name = "";
        Integer yearInscribed = 0;
        Boolean endangered = false;
        Double latitude = null;
        Double longitude = null;
        String long_description = "";
        String short_description = "";
        String justification = "";
        String historical_description = "";
        String image1 = "";
        String image1_license = "";

        Category category = new Category(null);
        Zone zone = new Zone(null);
        ArrayList<Criterion> criteria = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String querySite = "SELECT * FROM app_site WHERE number = ?";
        Cursor c = db.rawQuery(querySite, new String[] {""+id});
        while (c.moveToNext()) {
            name = c.getString(c.getColumnIndex("name"));
            yearInscribed = c.getInt(c.getColumnIndex("year_inscribed"));
            endangered = c.getInt(c.getColumnIndex("endangered")) > 0;
            latitude = c.getDouble(c.getColumnIndex("latitude"));
            longitude = c.getDouble(c.getColumnIndex("longitude"));

            short_description = c.getString(c.getColumnIndex("short_description"));
            long_description = c.getString(c.getColumnIndex("long_description"));
            historical_description = c.getString(c.getColumnIndex("historical_description"));
            justification = c.getString(c.getColumnIndex("justification"));

            try {
                image1 = c.getString(c.getColumnIndex("image1_url"));
                image1_license = c.getString(c.getColumnIndex("image1_description"));
            } catch (Exception e){ }


            if (isFrench) {
                name = c.getString(c.getColumnIndex("name_fr"));
                short_description = c.getString(c.getColumnIndex("short_description_fr"));
                long_description = c.getString(c.getColumnIndex("long_description_fr"));
                historical_description = c.getString(c.getColumnIndex("historical_description_fr"));
                justification = c.getString(c.getColumnIndex("justification_fr"));
            }
        }

        String categoryQuery = "SELECT app_category.name, app_category.name_fr " +
                "FROM app_category JOIN app_site ON app_site.category_id = app_category.id " +
                "WHERE app_site.number = ?";
        Cursor c2 = db.rawQuery(categoryQuery, new String[] {""+id});
        while (c2.moveToNext()) {
            String category_name = c2.getString(c2.getColumnIndex("name"));

            if (isFrench)
                category_name = c2.getString(c2.getColumnIndex("name_fr"));

            category = new Category(category_name);
        }

        String zoneQuery = "SELECT app_zone.name, app_zone.name_fr " +
                "FROM app_zone JOIN app_site ON app_site.zone_id = app_zone.id " +
                "WHERE app_site.number = ?";
        Cursor c3 = db.rawQuery(zoneQuery, new String[] {""+id});
        while (c3.moveToNext()) {
            String zone_name = c3.getString(c3.getColumnIndex("name"));
            if (isFrench)
                zone_name = c3.getString(c3.getColumnIndex("name_fr"));

            zone = new Zone(zone_name);
        }

        ArrayList<Country> countries = getCountriesBySiteID(id);

        String criteriaQuery = "SELECT app_criterion.number, description, description_fr " +
                "FROM app_criterion JOIN app_site_criteria ON criterion_id=app_criterion.number " +
                "WHERE site_id = ?";
        Cursor c5 = db.rawQuery(criteriaQuery, new String[] {""+id});
        while (c5.moveToNext()) {
            Integer criterion_id = c5.getInt(c5.getColumnIndex("number"));
            String criterion_description = c5.getString(c5.getColumnIndex("description"));

            if (isFrench)
                criterion_description = c5.getString(c5.getColumnIndex("description_fr"));

            criteria.add(new Criterion(criterion_id, criterion_description));
        }


        Site site = new Site(id, name);
        site.setYearInscribed(yearInscribed);
        site.setCriteria(criteria);
        site.setShort_description(short_description);
        site.setHistorical_description(historical_description);
        site.setLong_description(long_description);
        site.setEndangered(endangered);
        site.setLongitude(longitude);
        site.setLatitude(latitude);
        site.setJustification(justification);
        site.setImage1(image1);
        site.setImage1_license(image1_license);

        return site;
    }

    public int getRandomSiteId() {
        int id = 1;

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT number FROM app_site ORDER BY RANDOM() LIMIT 1";
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            id = c.getInt(c.getColumnIndex("number"));
        }
        c.close();
        return id;
    }

    public ArrayList<Site> getSeenSites(String list) {

        ArrayList<Site> seenSites = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT app_site.number, name, name_fr, thumb " +
                "FROM app_site " +
                "WHERE number IN " + list;

        // Order sites by names
        if (isFrench) {
            query += " ORDER BY name_fr COLLATE UNICODE";
        }
        else {
            query += " ORDER BY name COLLATE UNICODE";
        }

        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            int number = c.getInt(c.getColumnIndex("number"));
            String name = c.getString(c.getColumnIndex("name"));
            byte[] thumb = c.getBlob(c.getColumnIndex("thumb"));

            if (isFrench) {
                name = c.getString(c.getColumnIndex("name_fr"));
            }

            Site site = new Site(number, name);
            site.setThumb(thumb);
            site.setCountries(getCountriesBySiteID(number));
            seenSites.add(site);
        }
        c.close();

        return seenSites;
    }

    public List<PieEntry> getStatsByZone(String seenSites) {
        List<PieEntry> visitedZonesEntries = new ArrayList<PieEntry>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT app_zone.name, app_zone.name_fr, count(*) AS count " +
                "FROM app_zone JOIN app_site ON app_zone.id = app_site.zone_id " +
                "WHERE app_site.number IN " + seenSites + " " +
                "GROUP BY app_zone.name";
        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            String name = c.getString(c.getColumnIndex("name"));
            if (isFrench)
                name = c.getString(c.getColumnIndex("name_fr"));

            Integer siteSeenCount = c.getInt(c.getColumnIndex("count"));

            visitedZonesEntries.add(new PieEntry((float) siteSeenCount, name));
        }
        c.close();

        return visitedZonesEntries;
    }

    public Integer getNumberSites() {
        Integer count = 0;

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT count(*) AS count FROM app_site";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            count = c.getInt(c.getColumnIndex("count"));
        }
        c.close();

        return count;
    }

    public ArrayList<Country> searchCountry(String userQuery) {

        countries = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT id, name, name_fr, iso FROM app_country ";

        if (isFrench)
            query += "WHERE searchable_name_fr LIKE ?";
        else
            query += "WHERE searchable_name LIKE ?";

        Cursor c = db.rawQuery(query, new String[] {"%"+userQuery+"%"});
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String iso = c.getString(c.getColumnIndex("iso"));

            if (isFrench)
                name = c.getString(c.getColumnIndex("name_fr"));

            countries.add(new Country(id, name, iso));
        }
        c.close();

        if (countries.size() > 0) {
            setNumberSitesByCountry("Cultural");
            setNumberSitesByCountry("Natural");
            setNumberSitesByCountry("Mixed");
        }

        return removeCountriesWithNoSites(countries);
    }

    public ArrayList<Country> removeCountriesWithNoSites(ArrayList<Country> countries) {

        // Remove countries with no sites
        ArrayList<Country> countriesTruncated = new ArrayList<>();
        for (int i=0;i<countries.size();i++) {
            Country country = countries.get(i);
            if (country.getNumberCulturalSites() > 0 ||
                    country.getNumberMixedSites() > 0 ||
                    country.getNumberNaturalSites() > 0) {
                countriesTruncated.add(country);
            }
        }
        return countriesTruncated;

    }

    public ArrayList<Site> searchSite(String userQuery) {

        ArrayList<Site> sites = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT number, name, name_fr, thumb FROM app_site ";

        if (isFrench)
            query += "WHERE searchable_name_fr LIKE ?";
        else
            query += "WHERE searchable_name LIKE ?";

        Cursor c = db.rawQuery(query, new String[] {"%"+userQuery+"%"});
        while (c.moveToNext()) {
            int number = c.getInt(c.getColumnIndex("number"));
            String name = c.getString(c.getColumnIndex("name"));
            byte[] thumb = c.getBlob(c.getColumnIndex("thumb"));

            if (isFrench)
                name = c.getString(c.getColumnIndex("name_fr"));

            Site site = new Site(number, name);
            site.setThumb(thumb);
            site.setCountries(getCountriesBySiteID(number));
            sites.add(site);
        }
        c.close();

        return sites;

    }



}
