package com.example.developserg.geekbitmap;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    static final String CAT_EXTRA = "CAT_EXTRA";

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private final int DEFAULT_ZOOM = 10;

    private ArrayList<Cat> catsList = new ArrayList<>();
    private final int catsCount = 5;
    private final String catsLatLng[] = {"38.961814, -77.036347", "38.923592, -76.975781", "38.910040, -76.971516",
            "38.897782, -77.017332", "38.904939, -77.037914"};
    private final String[] catsNames = {"Барсик", "Мурзик", "Бегемот", "Рыжик", "Матроскин"};
    private final String[] catsType = {"Бразильская короткошерстная", "Тонкинская кошка",
            "Минскин", "Бурмилла длинношерстная", "Охос азулес"};
    private final String[] catsDescriptions = {"Мурлычет когда глядят", "Сильно храпит", "Много ест",
            "Громко мяукает", "Супер-активный"};
    private final int[] catsImagesId = {R.drawable.brazilian_shorthair, R.drawable.tonkin_cat, R.drawable.minskin,
            R.drawable.burmilla_longhair, R.drawable.ojos_azules};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        putDataOnDB();
        initCats();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void putDataOnDB() {
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        if (c.getCount() == 0) {
            ContentValues cv = new ContentValues();
            for (int i = 0; i < catsCount; i++) {
                cv.put("latlng", catsLatLng[i]);
                cv.put("name", catsNames[i]);
                cv.put("description", catsDescriptions[i]);
                cv.put("imageId", catsImagesId[i]);
                cv.put("type", catsType[i]);
                db.insert("mytable", null, cv);
            }
        }
        c.close();
    }

    private void initCats() {
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int latlng = c.getColumnIndex("latlng");
            int name = c.getColumnIndex("name");
            int type = c.getColumnIndex("type");
            int description = c.getColumnIndex("description");
            int fotoId = c.getColumnIndex("imageId");
            for (int i = 0; i < catsCount; i++) {
                Cat cat = new Cat();
                cat.setName(c.getString(name));
                cat.setDescription(c.getString(description));
                cat.setType(c.getString(type));
                cat.setFoto(c.getInt(fotoId));
                cat.setLatLng(c.getString(latlng));
                catsList.add(cat);
                c.moveToNext();
            }
        }
        c.close();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng washington = new LatLng(38.897652, -77.036545);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(washington, DEFAULT_ZOOM));

        for (int i = 0; i < catsList.size(); i++) {
            String[] latLng = catsList.get(i).getLatLng().split(",");
            double latitude = Double.parseDouble(latLng[0]);
            double longitude = Double.parseDouble(latLng[1]);
            LatLng catLatLng = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(catLatLng).title(String.valueOf(i)));
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String title = marker.getTitle();
                Intent showCatProfile = new Intent(getApplicationContext(), AboutCatActivity.class);
                Cat cat = getCat(Integer.valueOf(title));
                showCatProfile.putExtra(CAT_EXTRA, cat);
                startActivity(showCatProfile);
                return true;
            }
        });
    }

    public Cat getCat(int position) {
        return catsList.get(position);
    }
}
