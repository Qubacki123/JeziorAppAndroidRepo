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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements  OnMapReadyCallback {
    private HomeViewModel homeViewModel;

    public static GoogleMap mMap;
    public static GoogleMap map;
    public Marker mPodOmega;
    public Marker mPortIlawa;
    public Marker mEkomarina;
    public Marker mBiskaje;
    public Marker mPrzystanSkarbek;
    public Marker mMaribo;
    public Marker mPomostKrzywyRog;
    public Marker mZatWyspMil;

    public Marker mMielTor;

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

        //marker Pod Omegą
        ObiektyPOI PodOmega = new ObiektyPOI(true,false,false,true,true,true,true,false,false,true,true,true,true,53.600198, 19.547083);
        LatLng PodOmegaLatLng = new LatLng(PodOmega.latitude,PodOmega.longitude);
        mPodOmega = mMap.addMarker(new MarkerOptions()
                    .position(PodOmegaLatLng)
                    .title("Pod Omegą")
                    .snippet(getSnippet(PodOmega))
                    .icon(BitmapDescriptorFactory.defaultMarker
                            (BitmapDescriptorFactory.HUE_AZURE)));

        //marker Port Iława
        ObiektyPOI PortIlawa = new ObiektyPOI(true,false,false,true,true,true,true,false,false,true,false,true,true,53.599951, 19.553358);
        LatLng PortIlawaLatLng = new LatLng(PortIlawa.latitude,PortIlawa.longitude);
        mPortIlawa = mMap.addMarker(new MarkerOptions()
                .position(PortIlawaLatLng)
                .title("Port Iława")
                .snippet(getSnippet(PortIlawa))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));

        //marker Ekomarina
        ObiektyPOI Ekomarina = new ObiektyPOI(false,true,false,true,false,true,true,false,false,true,false,true,true,53.604218, 19.558014);
        LatLng EkomarinaLatLng = new LatLng(Ekomarina.latitude,Ekomarina.longitude);
        mEkomarina = mMap.addMarker(new MarkerOptions()
                .position(EkomarinaLatLng)
                .title("Ekomarina")
                .snippet(getSnippet(Ekomarina))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));

        //marker Biskaje
        ObiektyPOI Biskaje = new ObiektyPOI(true,false,false,true,false,true,true,false,false,true,false,true,false,53.615033, 19.563197);
        LatLng BiskajeLatLng = new LatLng(Biskaje.latitude,Biskaje.longitude);
        mBiskaje = mMap.addMarker(new MarkerOptions()
                .position(BiskajeLatLng)
                .title("Biskaje")
                .snippet(getSnippet(Biskaje))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));

        //marker Przystań Skarbek
        ObiektyPOI PrzystanSkarbek = new ObiektyPOI(true,false,false,true,true,true,true,false,false,true,false,true,false,53.616857, 19.563474);
        LatLng PrzystanSkarbekLatLng = new LatLng(PrzystanSkarbek.latitude,PrzystanSkarbek.longitude);
        mPrzystanSkarbek = mMap.addMarker(new MarkerOptions()
                .position(PrzystanSkarbekLatLng)
                .title("Przystań Skarbek")
                .snippet(getSnippet(PrzystanSkarbek))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));

        //marker Maribo
        ObiektyPOI Maribo = new ObiektyPOI(true,false,false,true,false,false,true,false,false,true,false,true,false,53.643445, 19.573208);
        LatLng MariboLatLng = new LatLng(Maribo.latitude,Maribo.longitude);
        mMaribo = mMap.addMarker(new MarkerOptions()
                .position(MariboLatLng)
                .title("Maribo")
                .snippet(getSnippet(Maribo))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));

        //marker Pomost Krzywy Róg
        ObiektyPOI PomostKrzywyRog = new ObiektyPOI(false,true,false,false,false,false,false,true,false,false,true,true,false,53.632064, 19.556638);
        LatLng PomostKrzywyRogLatLng = new LatLng(PomostKrzywyRog.latitude,PomostKrzywyRog.longitude);
        mPomostKrzywyRog = mMap.addMarker(new MarkerOptions()
                .position(PomostKrzywyRogLatLng)
                .title("Pomost Krzywy Róg")
                .snippet(getSnippet(PomostKrzywyRog))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));

        //marker Zatoka Przy Wyspie Miłości
        ObiektyPOI ZatWyspMil = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.628367, 19.554406);
        LatLng ZatWyspMilLatLng = new LatLng(ZatWyspMil.latitude,ZatWyspMil.longitude);
        mZatWyspMil = mMap.addMarker(new MarkerOptions()
                .position(ZatWyspMilLatLng)
                .title("Zatoka Przy Wyspie Miłości")
                .snippet(getSnippet(ZatWyspMil))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));

        //marker Przystań Indyjski Półwysep
        ObiektyPOI PrzystIndPol = new ObiektyPOI(false,true,false,false,false,false,false,true,false,false,true,true,false,53.650759, 19.581258);
        LatLng PrzystIndPolLatLng = new LatLng(PrzystIndPol.latitude,PrzystIndPol.longitude);
        mPrzystIndPol = mMap.addMarker(new MarkerOptions()
                .position(PrzystIndPolLatLng)
                .title("Przystań Indyjski Półwysep")
                .snippet(getSnippet(PrzystIndPol))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));

        /**
         * MIELIZNY
         *
         */
        BitmapDescriptor icon1 = BitmapDescriptorFactory.fromResource(R.mipmap.danger_small);

        //mielizna przy torze
        LatLng MielTor = new LatLng(53.618983, 19.548221);
        mMielTor = mMap.addMarker(new MarkerOptions()
                .position(MielTor)
                .title("Mielizna")
                .icon(icon1))  ;


        /**
         * INNE OBIEKTY MAPY
         *
         */


        // Add polylines to the map.
        // Polylines are useful to show a route or some other connection between points.
        Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(53.604047, 19.539689),
                        new LatLng(53.61697, 19.54636))
                .clickable(true)
                .width(6));
        polyline1.setTag("Tor wioślarski");

        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener()
        {
            @Override
            public void onPolylineClick(Polyline polyline)
            {
                double latitude0 = polyline.getPoints().get(0).latitude;
                double longitude0 = polyline.getPoints().get(0).longitude;
                double latitude1 = polyline.getPoints().get(1).latitude;
                double longitude1 = polyline.getPoints().get(1).longitude;
                LatLng srodek = new LatLng((latitude0+latitude1)/2,(longitude0+longitude1)/2);
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(srodek, 14));
                Context mContext = getContext(); //or getActivity(), YourActivity.this, etc.
                Toast.makeText(mContext, polyline.getTag().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Context mContext = getContext(); //or getActivity(), YourActivity.this, etc.
                if (marker.getTitle().equals("Mielizna") ){
                    Toast.makeText(mContext, "Wybrano mieliznę", Toast.LENGTH_SHORT).show();}
                else {
                Toast.makeText(mContext, "Wybrano przystań " + marker.getTitle(), Toast.LENGTH_SHORT).show();}
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
        /*
        int emoji_sklep_int = 0x1F6D2;
        String emoji_sklep = getEmojiByUnicode(emoji_sklep_int);

        int emoji_shower_int = 0x1F6BF;
        String emoji_shower = getEmojiByUnicode(emoji_shower_int);

         */

        snippet = snippet + "⚓ Cumowanie: ";
        if (poi.cumowanie_kotwica) {
            snippet = snippet + "kotwica\n";}
        if (poi.cumowanie_bojka) {
            snippet = snippet + "bojka\n";}
        if (poi.cumowanie_ybom) {
            snippet = snippet + "y-bom\n";}
        if (poi.prysznic) {
            snippet = snippet + "\uD83D\uDEBF Prysznic\n";}
        if (poi.paliwo) {
            snippet = snippet + "⛽ Paliwo\n";}
        if (poi.sklep) {
            snippet = snippet + "\uD83D\uDED2 Sklep\n";}

        if (poi.kupa_w_krzaku) {
            snippet = snippet + "\uD83D\uDEBD Kupa w lesie ( ͡° ͜ʖ ͡°)";}
        else if(poi.toitoi) {
            snippet = snippet + "\uD83D\uDEBD ToiToi";}
        else if (poi.toaleta){
            snippet = snippet + "\uD83D\uDEBD Toaleta";}

        snippet = snippet + "\n";

        if (poi.woda_pitna) {
            snippet = snippet + "\uD83D\uDEB0 Woda pitna\n";}
        if (poi.ognisko) {
            snippet = snippet + "\uD83D\uDD25 Ognisko\n";}
        if (poi.smietnik) {
            snippet = snippet + "\uD83D\uDDD1 Śmietnik";}
        if (poi.nocleg) {
            snippet = snippet + "\n\uD83D\uDECC Nocleg";}

        return snippet;
    }


    /**
     * Pozyskanie emoji
     *
     * @param unicode emoji podane w int
     */
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
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
        boolean prysznic,paliwo,sklep,toaleta,toitoi,kupa_w_krzaku,woda_pitna,ognisko,smietnik,nocleg;
        double latitude,longitude;


        public ObiektyPOI(boolean cum_b,boolean cum_y,boolean cum_k,
                          boolean prysz,boolean pal,boolean skl,boolean toa,
                          boolean toi,boolean las,boolean wod,boolean ogn,boolean smiet,
                          boolean noc,double lat,double lng) {
            cumowanie_bojka = cum_b;
            cumowanie_ybom = cum_y;
            cumowanie_kotwica = cum_k;
            prysznic = prysz;
            paliwo = pal;
            sklep = skl;
            toaleta = toa;
            toitoi = toi;
            kupa_w_krzaku = las;
            woda_pitna = wod;
            ognisko = ogn;
            smietnik = smiet;
            nocleg = noc;
            latitude = lat;
            longitude = lng;
        }
    }



}