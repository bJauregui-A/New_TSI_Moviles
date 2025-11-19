package com.example.new_tsi_moviles.controller;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import com.example.new_tsi_moviles.R;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

public class MapaController extends AppCompatActivity {

    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue(getPackageName());

        setContentView(R.layout.mapa);

        map = findViewById(R.id.osmMap);
        map.setMultiTouchControls(true);

        GeoPoint destino = new GeoPoint(-36.828228, -73.049698);

        map.getController().animateTo(destino);
        map.getController().setZoom(19.0);

        Marker marker = new Marker(map);
        marker.setPosition(destino);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setTitle("Los Aguilera 319, Barrio Universitario, Concepción, Chile");

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.pin);
        Bitmap original = ((BitmapDrawable) drawable).getBitmap();

        int width = 70;
        int height = 70;
        Bitmap resized = Bitmap.createScaledBitmap(original, width, height, true);
        marker.setIcon(new BitmapDrawable(getResources(), resized));
        map.getOverlays().add(marker);

        map.invalidate();
    }


}
