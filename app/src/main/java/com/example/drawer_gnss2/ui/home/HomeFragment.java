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
    public Marker mPrzystIndPol;
    public Marker mSarnowek;
    public Marker mMakowo;
    public Marker mZatokaMakowo1;
    public Marker mZatokaMakowo2;
    public Marker mDodziaCamp;
    public Marker mZatokaPiratow;
    public Marker mObelisk;
    public Marker mGierczakMaly;
    public Marker mGierczakDuzy;
    public Marker mBindugaGierczak1;

    public Marker mMielTor;
    public Marker mMielJazdz;
    public Marker mMielMalyGierczak;
    public Marker mMielDuzyGierczak;

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

        //marker Sarnówek
        ObiektyPOI Sarnowek = new ObiektyPOI(false,true,false,true,false,false,true,false,false,true,false,true,false,53.658618, 19.589955);
        LatLng SarnowekLatLng = new LatLng(Sarnowek.latitude,Sarnowek.longitude);
        mSarnowek = mMap.addMarker(new MarkerOptions()
                .position(SarnowekLatLng)
                .title("Sarnówek")
                .snippet(getSnippet(Sarnowek))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));

        //marker Makowo
        ObiektyPOI Makowo = new ObiektyPOI(false,false,true,true,false,true,true,false,false,true,false,true,false,53.682125, 19.644059);
        LatLng MakowoLatLng = new LatLng(Makowo.latitude,Makowo.longitude);
        mMakowo = mMap.addMarker(new MarkerOptions()
                .position(MakowoLatLng)
                .title("Makowo")
                .snippet(getSnippet(Makowo))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));

        //marker Zatoka Makowo#1
        ObiektyPOI ZatokaMakowo1 = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.678385, 19.633059);
        LatLng ZatokaMakowo1LatLng = new LatLng(ZatokaMakowo1.latitude,ZatokaMakowo1.longitude);
        mZatokaMakowo1 = mMap.addMarker(new MarkerOptions()
                .position(ZatokaMakowo1LatLng)
                .title("Zatoka Makowo#1")
                .snippet(getSnippet(ZatokaMakowo1))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));

        //marker Zatoka Makowo#2
        ObiektyPOI ZatokaMakowo2 = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.681885, 19.634695);
        LatLng ZatokaMakowo2LatLng = new LatLng(ZatokaMakowo2.latitude,ZatokaMakowo2.longitude);
        mZatokaMakowo2 = mMap.addMarker(new MarkerOptions()
                .position(ZatokaMakowo2LatLng)
                .title("Zatoka Makowo#2")
                .snippet(getSnippet(ZatokaMakowo2))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));

        //marker Dodzia Camp
        ObiektyPOI DodziaCamp = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.698063, 19.643175);
        LatLng DodziaCampLatLng = new LatLng(DodziaCamp.latitude,DodziaCamp.longitude);
        mDodziaCamp = mMap.addMarker(new MarkerOptions()
                .position(DodziaCampLatLng)
                .title("Dodzia Camp")
                .snippet(getSnippet(DodziaCamp))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));

        //marker Zatoka Piratów
        ObiektyPOI ZatokaPiratow = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.702170, 19.627328);
        LatLng ZatokaPiratowLatLng = new LatLng(ZatokaPiratow.latitude,ZatokaPiratow.longitude);
        mZatokaPiratow = mMap.addMarker(new MarkerOptions()
                .position(ZatokaPiratowLatLng)
                .title("Zatoka Piratów")
                .snippet(getSnippet(ZatokaPiratow))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));

        //marker Obelisk
        ObiektyPOI Obelisk = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.701782, 19.623862);
        LatLng ObeliskLatLng = new LatLng(Obelisk.latitude,Obelisk.longitude);
        mObelisk = mMap.addMarker(new MarkerOptions()
                .position(ObeliskLatLng)
                .title("Obelisk")
                .snippet(getSnippet(Obelisk))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));

        //marker Gierczak Mały
        ObiektyPOI GierczakMaly = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.701782, 19.623862);
        LatLng GierczakMalyLatLng = new LatLng(GierczakMaly.latitude,GierczakMaly.longitude);
        mGierczakMaly = mMap.addMarker(new MarkerOptions()
                .position(GierczakMalyLatLng)
                .title("Gierczak Mały")
                .snippet(getSnippet(GierczakMaly))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));

        //marker Gierczak Duży
        ObiektyPOI GierczakDuzy = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.717282, 19.636532);
        LatLng GierczakDuzyLatLng = new LatLng(GierczakDuzy.latitude,GierczakDuzy.longitude);
        mGierczakDuzy = mMap.addMarker(new MarkerOptions()
                .position(GierczakDuzyLatLng)
                .title("Gierczak Duży")
                .snippet(getSnippet(GierczakDuzy))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));

        //marker Binduga Przy Gierczaku
        ObiektyPOI BindugaGierczak1 = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.714304, 19.625954);
        LatLng BindugaGierczak1LatLng = new LatLng(BindugaGierczak1.latitude,BindugaGierczak1.longitude);
        mBindugaGierczak1 = mMap.addMarker(new MarkerOptions()
                .position(BindugaGierczak1LatLng)
                .title("Binduga Gierczak #1")
                .snippet(getSnippet(BindugaGierczak1))
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

        //mielizna przy torze
        LatLng MielJazdz = new LatLng(53.673303, 19.618766);
        mMielJazdz = mMap.addMarker(new MarkerOptions()
                .position(MielJazdz)
                .title("Mielizna")
                .icon(icon1))  ;

        //mielizna przy Gierczaku Małym
        LatLng MalyGierczak = new LatLng(53.712067, 19.629935);
        mMielMalyGierczak = mMap.addMarker(new MarkerOptions()
                .position(MalyGierczak)
                .title("Mielizna")
                .icon(icon1))  ;

        //mielizna przy Gierczaku Dużym
        LatLng DuzyGierczak = new LatLng(53.716900, 19.644295);
        mMielDuzyGierczak = mMap.addMarker(new MarkerOptions()
                .position(DuzyGierczak)
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
            snippet = snippet + "kotwica";}
        if (poi.cumowanie_bojka) {
            snippet = snippet + "bojka";}
        if (poi.cumowanie_ybom) {
            snippet = snippet + "y-bom";}
        if (poi.prysznic) {
            snippet = snippet + "\n\uD83D\uDEBF Prysznic";}
        if (poi.paliwo) {
            snippet = snippet + "\n⛽ Paliwo";}
        if (poi.sklep) {
            snippet = snippet + "\n\uD83D\uDED2 Sklep";}

        if (poi.kupa_w_krzaku) {
            snippet = snippet + "\n\uD83D\uDEBD Kupa w lesie ( ͡° ͜ʖ ͡°)";}
        else if(poi.toitoi) {
            snippet = snippet + "\n\uD83D\uDEBD ToiToi";}
        else if (poi.toaleta){
            snippet = snippet + "\n\uD83D\uDEBD Toaleta";}

        if (poi.woda_pitna) {
            snippet = snippet + "\n\uD83D\uDEB0 Woda pitna";}
        if (poi.ognisko) {
            snippet = snippet + "\n\uD83D\uDD25 Ognisko";}
        if (poi.smietnik) {
            snippet = snippet + "\n\uD83D\uDDD1 Śmietnik";}
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