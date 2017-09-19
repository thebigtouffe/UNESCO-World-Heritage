package fr.thebigtouffe.heritage;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fr.thebigtouffe.heritage.UNESCO.Database;
import fr.thebigtouffe.heritage.UNESCO.Site;

public class seenSites extends AppCompatActivity {

    private userManager userManager;
    private Database unescoDB;

    private ListView mListSeenSites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seen_sites);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Load database
        userManager = new userManager(this);
        unescoDB = new Database(this);

        // Display seen sites
        showSeenSites();

        // Handle click on site
        mListSeenSites.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Site clickedSite = (Site) mListSeenSites.getItemAtPosition(position);
                Intent intent = new Intent(seenSites.this, siteView.class);
                intent.putExtra("siteId", ""+clickedSite.getNumber());
                startActivity(intent);
            }
        });
    }

    private void showSeenSites() {
        String sqlList = userManager.getSeenSitesIDs().toString();
        sqlList = sqlList.replace("[","(").replace("]",")");

        ArrayList<Site> seenSites = unescoDB.getSeenSites(sqlList);

        mListSeenSites = (ListView) findViewById(R.id.listSeenSites);
        final siteRowAdapter adapter = new siteRowAdapter(seenSites.this, seenSites);
        mListSeenSites.setAdapter(adapter);

    }
}
