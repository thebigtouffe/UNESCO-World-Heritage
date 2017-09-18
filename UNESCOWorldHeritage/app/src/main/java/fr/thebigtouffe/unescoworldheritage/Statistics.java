package fr.thebigtouffe.unescoworldheritage;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

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
        ArrayList<Integer> seenSitesIDs = userManager.getSeenSitesIDs();

        // Animate percentage of seen sites
        TextView seenSitesCount = (TextView) findViewById(R.id.count_visited_site);
        float percentage = ((float) 100*seenSitesIDs.size() / unescoDB.getNumberSites());
        animateTextView(0, percentage, seenSitesCount);

        // Display visited sites by zone
        PieChart visitedZonesChart = (PieChart) findViewById(R.id.visited_zones);
        String seenSitesString = seenSitesIDs.toString();
        seenSitesString = seenSitesString.replace("[","(").replace("]",")");
        List<PieEntry> visitedZonesEntries = unescoDB.getStatsByZone(seenSitesString);

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

    public void animateTextView(float initialValue, float finalValue, final TextView textview) {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialValue, finalValue);
        valueAnimator.setDuration(1500);

        // Display decimals depending on the value of the number
        String format = "%.1f";
        if (finalValue < 1)
            format = "%.2f";
        final String FORMAT = format;

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                String value = String.format(FORMAT, valueAnimator.getAnimatedValue());
                textview.setText(value);

            }
        });
        valueAnimator.start();

    }
}
