package com.example.drawer_gnss2.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.drawer_gnss2.MainActivity;
import com.example.drawer_gnss2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements  OnMapReadyCallback {
    private HomeViewModel homeViewModel;

    public static GoogleMap mMap;
    public static GoogleMap map;
    public Marker mPodOmega;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

         */

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Log.e("HomeFragment", "On Create");


        return root;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        map = googleMap;

        float zoom = 11;

        // Add a marker over Jeziorak and move the camera
        LatLng jeziorak = new LatLng(53.7222, 19.6065);
        //mMap.addMarker(new MarkerOptions().position(jeziorak).title("Jezioro Jeziorak"));
        //move camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jeziorak, zoom));

        // Add a ground overlay 100 meters in width to the home location.
        GroundOverlayOptions homeOverlay = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.android))
                .position(jeziorak, 1000);

        //mMap.addGroundOverlay(homeOverlay);

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        setMapLongClick(mMap); // Set a long click listener for the map;

        setPoiClick(mMap); // Set a click listener for points of interest.

        //marker podomega
        ObiektyPOI PodOmega = new ObiektyPOI(true,false,false,true,true,true,true,true,53.600198, 19.547083);
        LatLng PodOmegaLatLng = new LatLng(PodOmega.latitude,PodOmega.longitude);
        mPodOmega = mMap.addMarker(new MarkerOptions()
                    .position(PodOmegaLatLng)
                    .title("Pod Omegą")
                    .snippet(getSnippet(PodOmega))
                    .icon(BitmapDescriptorFactory.defaultMarker
                            (BitmapDescriptorFactory.HUE_AZURE)));
        mPodOmega.setTag(0);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Context mContext = getContext(); //or getActivity(), YourActivity.this, etc.
                Toast.makeText(mContext, "Wybrano przystań " + marker.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                Context mContext = getContext(); //or getActivity(), YourActivity.this, etc.

                LinearLayout info = new LinearLayout(mContext);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(mContext);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(mContext);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }

    public String getSnippet (ObiektyPOI poi) {
        String snippet = "";
        snippet = snippet + "Cumowanie: ";
        if (poi.cumowanie_kotwica == true) {
            snippet = snippet + "kotwica\n";}
        if (poi.cumowanie_bojka == true) {
            snippet = snippet + "bojka\n";}
        if (poi.cumowanie_ybom == true) {
            snippet = snippet + "y-bom\n";}

        return snippet;
    }


    /**
     * Adds a blue marker to the map when the user long clicks on it.
     *
     * @param map The GoogleMap to attach the listener to.
     */
    private void setMapLongClick(final GoogleMap map) {

        // Add a blue marker to the map when the user performs a long click.
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                String snippet = String.format(Locale.getDefault(),
                        getString(R.string.lat_long_snippet),
                        latLng.latitude,
                        latLng.longitude);

                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(getString(R.string.dropped_pin))
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker
                                (BitmapDescriptorFactory.HUE_BLUE)));
            }
        });
    }


    /**
     * Adds a marker when a place of interest (POI) is clicked with the name of
     * the POI and immediately shows the info window.
     *
     * @param map The GoogleMap to attach the listener to.
     */
    private void setPoiClick(final GoogleMap map) {
        map.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest poi) {
                Marker poiMarker = map.addMarker(new MarkerOptions()
                        .position(poi.latLng)
                        .title(poi.name));
                poiMarker.showInfoWindow();
                poiMarker.setTag(getString(R.string.poi));
            }
        });
    }


    public class ObiektyPOI {
        boolean cumowanie_bojka,cumowanie_ybom,cumowanie_kotwica;
        boolean prysznic,paliwo,sklep,toaleta,woda_pitna;
        double latitude,longitude;

        public ObiektyPOI(boolean cum_b,boolean cum_y,boolean cum_k,boolean prysz,boolean pal,boolean skl,boolean toa,boolean wod,double lat,double lng) {
            cumowanie_bojka = cum_b;
            cumowanie_ybom = cum_y;
            cumowanie_kotwica = cum_k;
            prysznic = prysz;
            paliwo = pal;
            sklep = skl;
            toaleta = toa;
            woda_pitna = wod;
            latitude = lat;
            longitude = lng;
        }
    }



}