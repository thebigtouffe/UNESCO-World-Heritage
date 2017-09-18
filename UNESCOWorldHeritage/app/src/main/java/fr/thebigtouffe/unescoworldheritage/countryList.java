package fr.thebigtouffe.unescoworldheritage;

import fr.thebigtouffe.unescoworldheritage.UNESCO.Country;
import fr.thebigtouffe.unescoworldheritage.UNESCO.Database;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.SearchView;
import android.view.MenuInflater;
import android.view.SubMenu;
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_drawer);
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

                // Deselect drawer item
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_drawer);
                uncheckAllMenuItems(navigationView);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.search_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MenuItem searchMenuItem = menu.findItem( R.id.menu_search ); // get my MenuItem with placeholder submenu
                searchMenuItem.expandActionView(); // Expand the search menu item in order to show by default the query
            }
        });

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
        else if (id == R.id.action_random_site) {
            int randomId = unescoDB.getRandomSiteId();
            Intent intent = new Intent(countryList.this, siteView.class);
            intent.putExtra("siteId", ""+randomId);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Handle click on first part of drawer
        if (id == R.id.nav_seen) {
            Intent intent = new Intent(countryList.this, seenSites.class);
            startActivity(intent);
        } else if (id == R.id.nav_stats) {
            Intent intent = new Intent(countryList.this, Statistics.class);
            startActivity(intent);
        }

        // Handle click on region
        else if (id == R.id.nav_africa) {
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

    private void uncheckAllMenuItems(NavigationView navigationView) {
        final Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.hasSubMenu()) {
                SubMenu subMenu = item.getSubMenu();
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    subMenuItem.setChecked(false);
                }
            } else {
                item.setChecked(false);
            }
        }
    }
}
