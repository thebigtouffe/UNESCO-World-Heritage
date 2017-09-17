package fr.thebigtouffe.unescoworldheritage;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import fr.thebigtouffe.unescoworldheritage.UNESCO.Database;

public class Statistics extends AppCompatActivity {

    public static final int[] MY_COLORS = {
            Color.parseColor("#EC407A"),
            Color.parseColor("#03A9F4"),
            Color.parseColor("#FFA726"),
            Color.parseColor("#8BC34A"),
            Color.parseColor("#FFEB3B"),
    };

    private Database unescoDB;
    private userManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        // Load database
        userManager = new userManager(this);
        unescoDB = new Database(this);

        // Get seen sites
        String seenSites = userManager.getSeenSitesIDs().toString();
        seenSites = seenSites.replace("[","(").replace("]",")");

        // Display visited sites by zone
        PieChart visitedZonesChart = (PieChart) findViewById(R.id.visited_zones);
        List<PieEntry> visitedZonesEntries = unescoDB.getStatsByZone(seenSites);

        PieDataSet visitedZonesDataSet = new PieDataSet(visitedZonesEntries, "");
        visitedZonesDataSet.setColors(MY_COLORS);
        PieData visitedZonesData = new PieData(visitedZonesDataSet);
        visitedZonesChart.setData(visitedZonesData);

        Legend l = visitedZonesChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setWordWrapEnabled(true);
        l.setDrawInside(false);
        l.setTextSize(14f);
        visitedZonesChart.getDescription().setEnabled(false);
        visitedZonesChart.setDrawEntryLabels(false);

    }
}
