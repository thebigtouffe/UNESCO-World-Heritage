package fr.thebigtouffe.heritage;

import fr.thebigtouffe.heritage.UNESCO.Country;
import fr.thebigtouffe.heritage.UNESCO.Site;
import fr.thebigtouffe.heritage.UNESCO.Database;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class siteList extends AppCompatActivity {

    private ListView mListSites;
    private Database unescoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_list);

        Intent intent = getIntent();
        String countryId = intent.getStringExtra("countryId");

        unescoDB = new Database(this);
        Country country = unescoDB.getCountryById(Integer.parseInt(countryId));

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(country.getName());
        actionBar.setDisplayHomeAsUpEnabled(true);

        populateSiteListView(country);

        mListSites.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Site clickedSite = (Site) mListSites.getItemAtPosition(position);
                Intent intent = new Intent(siteList.this, siteView.class);
                intent.putExtra("siteId", ""+clickedSite.getNumber());
                startActivity(intent);
            }
        });
    }

    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }

    public void populateSiteListView(Country country) {

        // Add sites to list view
        ArrayList<Site> sites = unescoDB.getSitesByCountry(country);
        mListSites = (ListView) findViewById(R.id.listSites);
        final siteRowAdapter adapter = new siteRowAdapter(siteList.this, sites);
        mListSites.setAdapter(adapter);
    }
}
