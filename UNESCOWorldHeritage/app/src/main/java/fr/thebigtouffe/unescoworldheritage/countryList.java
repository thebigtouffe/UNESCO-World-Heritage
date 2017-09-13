package fr.thebigtouffe.unescoworldheritage;

import fr.thebigtouffe.unescoworldheritage.UNESCO.Country;
import fr.thebigtouffe.unescoworldheritage.UNESCO.Database;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class countryList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Database unescoDB;
    private ListView mListCountries;
    private Boolean isDefaultView = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        populateCountryListView("default");

        mListCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Country clickedCountry = (Country) mListCountries.getItemAtPosition(position);
                Intent intent = new Intent(countryList.this, siteList.class);
                intent.putExtra("countryId", ""+clickedCountry.getId());
                startActivity(intent);
            }
        });

    }

    public void populateCountryListView(String option) {
        unescoDB = new Database(this);

        // Add countries to list view
        ArrayList<Country> countries = unescoDB.getCountries(option);
        mListCountries = (ListView) findViewById(R.id.listCountries);
        final countryRowAdapter adapter = new countryRowAdapter(countryList.this, countries);
        mListCountries.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (isDefaultView) {
                super.onBackPressed();
            }
            else {
                isDefaultView = true;
                populateCountryListView("default");
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Handle click on region
        if (id == R.id.nav_africa) {
            isDefaultView = false;
            populateCountryListView("africa");
        } else if (id == R.id.nav_arab) {
            isDefaultView = false;
            populateCountryListView("arab");
        } else if (id == R.id.nav_asia_pacific) {
            isDefaultView = false;
            populateCountryListView("asia");
        } else if (id == R.id.nav_europe_north_america) {
            isDefaultView = false;
            populateCountryListView("europe");
        } else if (id == R.id.nav_latin_america) {
            isDefaultView = false;
            populateCountryListView("latin");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
