package com.example.drawer_gnss2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.example.drawer_gnss2.ui.home.HomeFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.view.Menu;
import android.widget.Toast;

import static com.example.drawer_gnss2.ui.home.HomeFragment.lista_poi;
import static com.example.drawer_gnss2.ui.home.HomeFragment.mMap;
import static com.example.drawer_gnss2.ui.home.HomeFragment.map;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private LocationManager lm;
    private LocationListener listener;

    public static Location lokalizacja_uzytkownika;
    public static FloatingActionButton fab;
    public static Menu menu_mapy;

    LocationManager locationManager;
    String provider;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new MyListener();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showOverflowMenu(true);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lokalizacja_uzytkownika == null) {
                    Snackbar.make(view, "Czekam na określenie lokalizacji", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                //znajdz najblizszy
                else {
                    Snackbar.make(view, "Szukam najbliższej przystani...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Marker bliskie_poi = findNearestMarker(lokalizacja_uzytkownika);
                    LatLng bliskie_poi_latlng = new LatLng(bliskie_poi.getPosition().latitude, bliskie_poi.getPosition().longitude);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bliskie_poi_latlng, 16));

                    Context mContext = getApplicationContext(); //or getActivity(), YourActivity.this, etc.
                    Toast.makeText(mContext, "Znaleziono " + bliskie_poi.getTitle(), Toast.LENGTH_SHORT).show();

                    Log.e("Bliskie POI", bliskie_poi.getTitle() + " pos: " + bliskie_poi.getPosition().toString());
                    bliskie_poi.showInfoWindow();
                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Log.e("MainActivity", "On Create");


        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)   != PackageManager.PERMISSION_GRANTED &&ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
            }
            return;
        }




    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults)
    {
       registerListener();
    }



    void registerListener() {
              lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
    }




    private class MyListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location location)
        {
            przetwarzajLokalizacje(location);
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

        }

        @Override
        public void onProviderEnabled(String provider)
        {

        }

        @Override
        public void onProviderDisabled(String provider)
        {

        }
    }

    Location prevLocation = null;
    private void przetwarzajLokalizacje(Location location)
    {
        lokalizacja_uzytkownika = location;
        prevLocation = location;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu_mapy = menu;
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public static void showOverflowMenu(boolean showMenu){
        if(menu_mapy == null)
            return;
        menu_mapy.setGroupVisible(R.id.main_menu_group, showMenu);
    }

    static Marker findNearestMarker(Location location){
        Marker najblizszy = null;
        Double odleglosc = 0.0;
        for(int i = 0; i < lista_poi.size(); i++) {
            if (najblizszy == null) {
                najblizszy = lista_poi.get(i);
                odleglosc = dist(location.getLongitude(),location.getLatitude(),
                        lista_poi.get(i).getPosition().longitude,lista_poi.get(i).getPosition().latitude);
                continue;
            }
            Double tmp = dist(location.getLongitude(),location.getLatitude(),
                    lista_poi.get(i).getPosition().longitude,lista_poi.get(i).getPosition().latitude);
            if (tmp < odleglosc) {
                odleglosc = tmp;
                najblizszy = lista_poi.get(i);
                continue;
            }
        }
        return najblizszy;
    }

    /**
     * degrees to radians
     *
     * @param degrees The GoogleMap to attach the listener to.
     */
    static Double degreesToRadians(Double degrees) {
        return degrees * Math.PI / 180;
    }


    /**
     * calculate distance between 2 points
     *
     */
    static Double dist(double long1, double lat1, double long2, double lat2) {

        Double earthRadiusKm = 6371.0;

        Double dLat = degreesToRadians(lat2 - lat1);
        Double dLon = degreesToRadians(long2 - long1);

        lat1 = degreesToRadians(lat1);
        lat2 = degreesToRadians(lat2);

        Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadiusKm * c;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.action_normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.action_hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.action_satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.action_terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
