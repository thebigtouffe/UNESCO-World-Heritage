package fr.thebigtouffe.unescoworldheritage;

import fr.thebigtouffe.unescoworldheritage.UNESCO.Site;
import fr.thebigtouffe.unescoworldheritage.UNESCO.Database;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class List extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Database unescoDB;
    private ListView mListSites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
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

        populateSiteListView("default");

    }

    public void populateSiteListView(String option) {
        ArrayList<String> site_names = new ArrayList<String>();
        unescoDB = new Database(this);

        // Add sites to list view
        ArrayList<Site> sites = unescoDB.getAllSites(option);
        for(int i=0;i<sites.size(); i++) {
            site_names.add(sites.get(i).getName());
        }

        mListSites = (ListView) findViewById(R.id.listSites);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(List.this,
                android.R.layout.simple_list_item_1, site_names);
        mListSites.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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


        // Handle click on category
        if (id == R.id.nav_cultural) {
            populateSiteListView("cultural");
        } else if (id == R.id.nav_natural) {
            populateSiteListView("natural");
        } else if (id == R.id.nav_mixed) {
            populateSiteListView("mixed");
        }

        // Handle click on region
        else if (id == R.id.nav_africa) {
            populateSiteListView("africa");
        } else if (id == R.id.nav_arab) {
            populateSiteListView("arab");
        } else if (id == R.id.nav_asia_pacific) {
            populateSiteListView("asia");
        } else if (id == R.id.nav_europe_north_america) {
            populateSiteListView("europe");
        } else if (id == R.id.nav_latin_america) {
            populateSiteListView("latin");
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
