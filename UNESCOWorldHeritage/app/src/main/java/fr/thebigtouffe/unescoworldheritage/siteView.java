package fr.thebigtouffe.unescoworldheritage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;
import org.osmdroid.views.MapView;

import fr.thebigtouffe.unescoworldheritage.UNESCO.Database;
import fr.thebigtouffe.unescoworldheritage.UNESCO.Site;

public class siteView extends AppCompatActivity {

    private Database unescoDB;
    private String customHtml;
    private String cssStyle = "<style>" +
            "body {" +
            "text-align: justify;" +
            "padding: 0.6em;" +
            "line-height: 160%;" +
            "}" +
            "</style>";

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


        // Get ID of the site to be displayed
        Intent intent = getIntent();
        String siteId = intent.getStringExtra("siteId");

        // Get site info
        unescoDB = new Database(this);
        Site site = unescoDB.getSiteById(Integer.parseInt(siteId));

        // Display site name
        CollapsingToolbarLayout toolbarTitle = (CollapsingToolbarLayout) findViewById(R.id.toolbar_title);
        toolbarTitle.setTitle(site.getName());

        // Display year
        TextView year = (TextView) findViewById(R.id.year);
        year.setText(getResources().getString(R.string.inscribed_in) + " " + site.getYearInscribed());

        // Display description using a WebView (because data countains XML tags)
        customHtml = "<html><body>" + cssStyle;
        customHtml += site.getShort_description();
        customHtml += "</body></html>";
        WebView descriptionView = (WebView) findViewById(R.id.description_webview);
        descriptionView.loadData(customHtml, "text/html; charset=UTF-8", null);

        // Display long description
        customHtml = "<html><body>" + cssStyle;
        customHtml += site.getLong_description();
        customHtml += "</body></html>";
        WebView longDescriptionView = (WebView) findViewById(R.id.long_description_webview);
        longDescriptionView.loadData(customHtml, "text/html; charset=UTF-8", null);

        // Display map
        Map map = new Map(site.getLatitude(), site.getLongitude(), (MapView) findViewById(R.id.map), this);
    }


}
