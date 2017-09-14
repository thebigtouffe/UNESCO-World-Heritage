package fr.thebigtouffe.unescoworldheritage;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;
import org.osmdroid.views.MapView;

import java.util.ArrayList;

import fr.thebigtouffe.unescoworldheritage.UNESCO.Criterion;
import fr.thebigtouffe.unescoworldheritage.UNESCO.Database;
import fr.thebigtouffe.unescoworldheritage.UNESCO.Site;
import fr.thebigtouffe.unescoworldheritage.Utils.Map;
import fr.thebigtouffe.unescoworldheritage.Utils.RomanNumber;

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

        final FloatingActionButton seeButton = (FloatingActionButton) findViewById(R.id.see_button);
        seeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSeen(seeButton, view);
            }
        });

        // Get ID of the site to be displayed
        Intent intent = getIntent();
        String siteId = intent.getStringExtra("siteId");

        // Get site info
        unescoDB = new Database(this);
        Site site = unescoDB.getSiteById(Integer.parseInt(siteId));

        // Display picture
        ImageView imageView = (ImageView) findViewById(R.id.navbar_picture);
        String imageUrl = site.getImage1();
        Picasso.with(getApplicationContext()).load(imageUrl).into(imageView);

        // Display site name
        CollapsingToolbarLayout toolbarTitle = (CollapsingToolbarLayout) findViewById(R.id.toolbar_title);
        toolbarTitle.setTitle(site.getName());

        // Display year
        TextView year = (TextView) findViewById(R.id.year);
        year.setText(getResources().getString(R.string.inscribed_in) + " " + site.getYearInscribed());

        // Display warning sign if site is endangered
        ImageView warning = (ImageView) findViewById(R.id.warning);
        if (site.getEndangered())
            warning.setVisibility(ImageView.VISIBLE);

        // Display description using a WebView (because data contains XML tags)
        customHtml = "<html><body>" + cssStyle;
        customHtml += site.getShort_description();
        customHtml += "</body></html>";
        WebView descriptionView = (WebView) findViewById(R.id.description_webview);
        descriptionView.loadData(customHtml, "text/html; charset=UTF-8", null);

        // Display long description
        String longDescription = site.getLong_description();
        if (longDescription.length() > 10) {
            customHtml = "<html><body>" + cssStyle;
            customHtml += longDescription;
            customHtml += "</body></html>";
            WebView longDescriptionView = (WebView) findViewById(R.id.long_description_webview);
            longDescriptionView.loadData(customHtml, "text/html; charset=UTF-8", null);

            View longDescriptionContainer = findViewById(R.id.long_description);
            longDescriptionContainer.setVisibility(RelativeLayout.VISIBLE);
        }

        // Display criteria
        ArrayList<Criterion> criteria = site.getCriteria();
        String justification = site.getJustification();
        customHtml = "<html><body>" + cssStyle;

        if (justification.length() > 10) {
            customHtml += justification;
        } else {
            // Fallback if no justification is given
            for (int i = 0; i < criteria.size(); i++) {
                Criterion criterion = criteria.get(i);
                String criterionRoman = RomanNumber.toRoman(criterion.getNumber());
                customHtml += "<h3>" + getResources().getString(R.string.criterion) + " " + criterionRoman + "</h3>";
                customHtml += "<p>" + criterion.getDesription() + "</p>";
            }
        }

        customHtml += "</body></html>";
        WebView criteriaView = (WebView) findViewById(R.id.criteria_webview);
        criteriaView.loadData(customHtml, "text/html; charset=UTF-8", null);

        // Display map
        Double latitude = site.getLatitude();
        Double longitude = site.getLongitude();
        MapView mapView = (MapView) findViewById(R.id.map);
        if (!longitude.equals(0.0)) {
            Map map = new Map(latitude, longitude, mapView, this);
            mapView.setVisibility(MapView.VISIBLE);
        }

    }


    public void toggleSeen(FloatingActionButton seeButton, View view) {
        int newColor = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);
        seeButton.setBackgroundTintList(ColorStateList.valueOf(newColor));
        Snackbar.make(view, getResources().getString(R.string.add_to_seen), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


}
