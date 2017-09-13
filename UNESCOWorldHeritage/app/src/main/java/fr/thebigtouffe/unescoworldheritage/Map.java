package fr.thebigtouffe.unescoworldheritage;

import android.app.Activity;
import android.util.Log;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.modules.OfflineTileProvider;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class Map {

    private Double latitude;
    private Double longitude;
    private MapView map;
    private Activity activity;

    private Double XHDPI_SCALING = 2.0;
    private Double XXHDPI_SCALING = 3.0;
    private Double XXXHDPI_SCALING = 4.0;

    private Double epsilon = 0.01;

    public Map(Double latitude, Double longitude, MapView map, Activity activity) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.map = map;
        this.activity = activity;

        // Options de la carte
        map.setUseDataConnection(false);
        map.setBuiltInZoomControls(false);
        map.setMaxZoomLevel(5);
        map.setMinZoomLevel(2);
        map.setMultiTouchControls(true);

        double boundNorth = 84.0;
        double boundSouth = -70.0;
        double boundEast = longitude + 40;
        double boundWest = longitude - 40;
        if (boundEast > 180) boundEast = boundEast - 360;
        if (boundWest < -180) boundWest = 360 + boundWest;

        BoundingBox maxScrollableLimit = new BoundingBox(boundNorth, boundEast, boundSouth, boundWest);
        map.setScrollableAreaLimitDouble(maxScrollableLimit);

        // Charge la carte depuis les assets
        File file = null;
        try {
            file = getFileFromAssets("world_map.mbtiles");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.exists()) {
            File[] fileTab = new File[1];
            fileTab[0] = file;
            OfflineTileProvider tileProvider = null;
            try {
                tileProvider = new OfflineTileProvider(new SimpleRegisterReceiver(activity), fileTab);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.setTileProvider(tileProvider);

            // Place la position de la carte
            IMapController mapController = map.getController();
            GeoPoint point = new GeoPoint(latitude, longitude);
            mapController.setCenter(point);

            Float density = activity.getResources().getDisplayMetrics().density;
            if (density < XHDPI_SCALING + epsilon) {
                mapController.setZoom(4);
            }
            else {
                mapController.setZoom(5);
            }

            ArrayList<OverlayItem> overlayItemArray = new ArrayList<OverlayItem>();
            OverlayItem overlayItem = new OverlayItem("Linkoping", "Sweden", point);
            overlayItemArray.add(overlayItem);

            // Add the Array to the IconOverlay
            ItemizedIconOverlay<OverlayItem> itemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(activity, overlayItemArray, null);

            // Add the overlay to the MapView
            map.getOverlays().add(itemizedIconOverlay);
        }
    }

    public File getFileFromAssets(String fileName) throws IOException {
        File cacheFile = new File(activity.getCacheDir(), fileName);
        try {
            InputStream inputStream = activity.getAssets().open(fileName);
            try {
                FileOutputStream outputStream = new FileOutputStream(cacheFile);
                try {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                } finally {
                    outputStream.close();
                }
            } finally {
                inputStream.close();
            }
        } catch (IOException e) {
            throw new IOException("Could not open "+fileName, e);
        }
        return cacheFile;
    }
}
