package fr.thebigtouffe.unescoworldheritage.UNESCO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Database extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "unesco.db";
    private static final int DATABASE_VERSION = 2;

    private Map<String, Country> countries = new HashMap<>();

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void getCountries() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from app_country", null);

        while (c.moveToNext()) {
            Integer id = c.getInt(c.getColumnIndex("id"));
            String name = c.getString(c.getColumnIndex("name"));
            String name_fr = c.getString(c.getColumnIndex("name_fr"));
            String iso = c.getString(c.getColumnIndex("iso"));

            Country country = new Country(name, name_fr, iso);

            countries.put(id.toString(), country);
        }
        c.close();
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

    public Integer getCategoryIdByName(String name) {
        Integer id = -1;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from app_category WHERE name = ?", new String[] {name});

        if (c.moveToFirst()) {
            id = c.getInt(c.getColumnIndex("id"));
        }
        c.close();
        return id;
    }

    public ArrayList<Site> getAllSites(String option) {
        ArrayList<Site> sites = new ArrayList<Site>();

        getCountries();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT number, name, name_fr, year_inscribed, country_id " +
                "FROM app_site JOIN app_site_country ON number = site_id ";

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

        // Category option

        else if (option.equals("cultural")) {
            query += "WHERE category_id =" + getCategoryIdByName("Cultural");
        } else if (option.equals("mixed")) {
            query += "WHERE category_id =" + getCategoryIdByName("Mixed");
        } else if (option.equals("natural")) {
            query += "WHERE category_id =" + getCategoryIdByName("Natural");
        }

        // Group sites by country
        query += " ORDER BY country_id";

        // Retrieve results
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            int number = c.getInt(c.getColumnIndex("number"));
            String name = c.getString(c.getColumnIndex("name"));
            String name_fr = c.getString(c.getColumnIndex("name_fr"));
            Integer yearInscribed = c.getInt(c.getColumnIndex("year_inscribed"));
            Integer countryId = c.getInt(c.getColumnIndex("country_id"));
            Country country = countries.get(countryId.toString());

            Site site = new Site(number, name, name_fr, null, country,
                                 null, null, yearInscribed,  null, null, null, null,
                                 null, null, null, null, null, null);

            sites.add(site);
        }
        c.close();
        return sites;
    }

}
