package fr.thebigtouffe.unescoworldheritage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import fr.thebigtouffe.unescoworldheritage.UNESCO.Site;
import fr.thebigtouffe.unescoworldheritage.UNESCO.Database;

public class siteView extends AppCompatActivity {

    private Database unescoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_view);
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

        Intent intent = getIntent();
        String siteId = intent.getStringExtra("siteId");

        unescoDB = new Database(this);
        Site site = unescoDB.getSiteById(Integer.parseInt(siteId));

        CollapsingToolbarLayout toolbarTitle = (CollapsingToolbarLayout) findViewById(R.id.toolbar_title);
        toolbarTitle.setTitle(site.getName());

    }
}
