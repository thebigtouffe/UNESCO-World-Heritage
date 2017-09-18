package fr.thebigtouffe.unescoworldheritage;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.text.Normalizer;
import java.util.ArrayList;

import fr.thebigtouffe.unescoworldheritage.UNESCO.Country;
import fr.thebigtouffe.unescoworldheritage.UNESCO.Database;
import fr.thebigtouffe.unescoworldheritage.UNESCO.Site;

import static java.lang.Math.min;

public class Search extends AppCompatActivity {

    private Database unescoDB;

    private ListView mListSites;
    private ListView mListCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mListCountries = (ListView) findViewById(R.id.country_results_list);
        mListSites = (ListView) findViewById(R.id.site_results_list);

        // Create database connection
        unescoDB = new Database(this);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            searchCountries(stripAccents(query.toLowerCase()));
            searchSites(stripAccents(query.toLowerCase()));
        }

        // Handle click on site
        mListSites.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Site clickedSite = (Site) mListSites.getItemAtPosition(position);
                Intent intent = new Intent(Search.this, siteView.class);
                intent.putExtra("siteId", ""+clickedSite.getNumber());
                startActivity(intent);
            }
        });

        // Handle click on country
        mListCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Country clickedCountry = (Country) mListCountries.getItemAtPosition(position);
                Intent intent = new Intent(Search.this, siteList.class);
                intent.putExtra("countryId", ""+clickedCountry.getId());
                startActivity(intent);
            }
        });

    }

    private void searchCountries(String query) {

        ArrayList<Country> results = unescoDB.searchCountry(query);

        // Truncate list of countries
        ArrayList<Country> resultsTruncated = new ArrayList<>();
        for (int i=0; i< min(2, results.size()); i++ ) {
            resultsTruncated.add(results.get(i));
        }

        if (results.size() > 0) {
            View countryResultsContainer = findViewById(R.id.country_results);
            countryResultsContainer.setVisibility(RelativeLayout.VISIBLE);

            final countryRowAdapter adapter = new countryRowAdapter(Search.this, resultsTruncated);
            mListCountries.setAdapter(adapter);
        }

    }

    private void searchSites(String query) {

        ArrayList<Site> results = unescoDB.searchSite(query);

        if (results.size() > 0) {
            View siteResultsContainer = findViewById(R.id.site_results);
            siteResultsContainer.setVisibility(RelativeLayout.VISIBLE);

            final siteRowAdapter adapter = new siteRowAdapter(Search.this, results);
            mListSites.setAdapter(adapter);
        }
    }

    public static String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
}
