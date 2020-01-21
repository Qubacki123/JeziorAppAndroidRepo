package com.example.drawer_gnss2.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.drawer_gnss2.MainActivity.fab;
import static com.example.drawer_gnss2.MainActivity.lokalizacja_uzytkownika;
import static com.example.drawer_gnss2.MainActivity.showOverflowMenu;

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
    public Marker mPrzystanLipowyOstrow;
    public Marker mZatokaLipowyOstrow1;
    public Marker mZatokaLipowyOstrow2;
    public Marker mZatokaLipowyOstrow3;
    public Marker mZatokaLipowyOstrow4;
    public Marker mZatokaLipowyOstrow5;
    public Marker mMarinaSiemiany;
    public Marker mKurkaWodna;
    public Marker mPrzystanZeglarskaSiemiany;
    public Marker mLakowa;
    public Marker mGierczakMaly2;
    public Marker mJerzwald;
    public Marker mZatokaBukowiec;
    public Marker mGublawki;
    public Marker mMatyty;
    public Marker mWejscieKanal;

    //mielizny
    public Marker mMielTor;
    public Marker mMielJazdz;
    public Marker mMielMalyGierczak;
    public Marker mMielDuzyGierczak;
    public Marker mLowiskoSiemiany;
    public Marker mCzaplak1;
    public Marker mCzaplak2;


    public Marker mLiniaWys;

    static public List<Marker> lista_poi;

    @SuppressLint("RestrictedApi")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Przywrócenie widoczności przycisku lokalizacji
        fab.setVisibility(View.VISIBLE);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Log.e("HomeFragment", "On Create");

        showOverflowMenu(true);


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

        float zoom = 12;

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



        setMapLongClick(mMap); // Set a long click listener for the map;

        setPoiClick(mMap); // Set a click listener for points of interest.

        //lista poi
        lista_poi = new ArrayList<Marker>();

        //marker Pod Omegą
        ObiektyPOI PodOmega = new ObiektyPOI(true,false,false,true,true,true,true,false,false,true,true,true,true,53.600198, 19.547083);
        LatLng PodOmegaLatLng = new LatLng(PodOmega.latitude,PodOmega.longitude);
        mPodOmega = mMap.addMarker(new MarkerOptions()
                    .position(PodOmegaLatLng)
                    .title("Pod Omegą")
                    .snippet(getSnippet(PodOmega))
                    .icon(BitmapDescriptorFactory.defaultMarker
                            (BitmapDescriptorFactory.HUE_AZURE)));
        lista_poi.add(mPodOmega);

        //marker Port Iława
        ObiektyPOI PortIlawa = new ObiektyPOI(true,false,false,true,true,true,true,false,false,true,false,true,true,53.599951, 19.553358);
        LatLng PortIlawaLatLng = new LatLng(PortIlawa.latitude,PortIlawa.longitude);
        mPortIlawa = mMap.addMarker(new MarkerOptions()
                .position(PortIlawaLatLng)
                .title("Port Iława")
                .snippet(getSnippet(PortIlawa))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));
        lista_poi.add(mPortIlawa);


        //marker Ekomarina
        ObiektyPOI Ekomarina = new ObiektyPOI(false,true,false,true,false,true,true,false,false,true,false,true,true,53.604218, 19.558014);
        LatLng EkomarinaLatLng = new LatLng(Ekomarina.latitude,Ekomarina.longitude);
        mEkomarina = mMap.addMarker(new MarkerOptions()
                .position(EkomarinaLatLng)
                .title("Ekomarina")
                .snippet(getSnippet(Ekomarina))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));
        lista_poi.add(mEkomarina);

        //marker Biskaje
        ObiektyPOI Biskaje = new ObiektyPOI(true,false,false,true,false,true,true,false,false,true,false,true,false,53.615033, 19.563197);
        LatLng BiskajeLatLng = new LatLng(Biskaje.latitude,Biskaje.longitude);
        mBiskaje = mMap.addMarker(new MarkerOptions()
                .position(BiskajeLatLng)
                .title("Biskaje")
                .snippet(getSnippet(Biskaje))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));
        lista_poi.add(mBiskaje);

        //marker Przystań Skarbek
        ObiektyPOI PrzystanSkarbek = new ObiektyPOI(true,false,false,true,true,true,true,false,false,true,false,true,false,53.616857, 19.563474);
        LatLng PrzystanSkarbekLatLng = new LatLng(PrzystanSkarbek.latitude,PrzystanSkarbek.longitude);
        mPrzystanSkarbek = mMap.addMarker(new MarkerOptions()
                .position(PrzystanSkarbekLatLng)
                .title("Przystań Skarbek")
                .snippet(getSnippet(PrzystanSkarbek))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));
        lista_poi.add(mPrzystanSkarbek);

        //marker Maribo
        ObiektyPOI Maribo = new ObiektyPOI(true,false,false,true,false,false,true,false,false,true,false,true,false,53.643445, 19.573208);
        LatLng MariboLatLng = new LatLng(Maribo.latitude,Maribo.longitude);
        mMaribo = mMap.addMarker(new MarkerOptions()
                .position(MariboLatLng)
                .title("Maribo")
                .snippet(getSnippet(Maribo))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));
        lista_poi.add(mMaribo);

        //marker Pomost Krzywy Róg
        ObiektyPOI PomostKrzywyRog = new ObiektyPOI(false,true,false,false,false,false,false,true,false,false,true,true,false,53.632064, 19.556638);
        LatLng PomostKrzywyRogLatLng = new LatLng(PomostKrzywyRog.latitude,PomostKrzywyRog.longitude);
        mPomostKrzywyRog = mMap.addMarker(new MarkerOptions()
                .position(PomostKrzywyRogLatLng)
                .title("Pomost Krzywy Róg")
                .snippet(getSnippet(PomostKrzywyRog))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mPomostKrzywyRog);


        //marker Zatoka Przy Wyspie Miłości
        ObiektyPOI ZatWyspMil = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.628367, 19.554406);
        LatLng ZatWyspMilLatLng = new LatLng(ZatWyspMil.latitude,ZatWyspMil.longitude);
        mZatWyspMil = mMap.addMarker(new MarkerOptions()
                .position(ZatWyspMilLatLng)
                .title("Zatoka Przy Wyspie Miłości")
                .snippet(getSnippet(ZatWyspMil))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mZatWyspMil);


        //marker Przystań Indyjski Półwysep
        ObiektyPOI PrzystIndPol = new ObiektyPOI(false,true,false,false,false,false,false,true,false,false,true,true,false,53.650759, 19.581258);
        LatLng PrzystIndPolLatLng = new LatLng(PrzystIndPol.latitude,PrzystIndPol.longitude);
        mPrzystIndPol = mMap.addMarker(new MarkerOptions()
                .position(PrzystIndPolLatLng)
                .title("Przystań Indyjski Półwysep")
                .snippet(getSnippet(PrzystIndPol))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mPrzystIndPol);

        //marker Sarnówek
        ObiektyPOI Sarnowek = new ObiektyPOI(false,true,false,true,false,false,true,false,false,true,false,true,false,53.658618, 19.589955);
        LatLng SarnowekLatLng = new LatLng(Sarnowek.latitude,Sarnowek.longitude);
        mSarnowek = mMap.addMarker(new MarkerOptions()
                .position(SarnowekLatLng)
                .title("Sarnówek")
                .snippet(getSnippet(Sarnowek))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));
        lista_poi.add(mSarnowek);

        //marker Makowo
        ObiektyPOI Makowo = new ObiektyPOI(false,false,true,true,false,true,true,false,false,true,false,true,false,53.682125, 19.644059);
        LatLng MakowoLatLng = new LatLng(Makowo.latitude,Makowo.longitude);
        mMakowo = mMap.addMarker(new MarkerOptions()
                .position(MakowoLatLng)
                .title("Makowo")
                .snippet(getSnippet(Makowo))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));
        lista_poi.add(mMakowo);

        //marker Zatoka Makowo#1
        ObiektyPOI ZatokaMakowo1 = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.678385, 19.633059);
        LatLng ZatokaMakowo1LatLng = new LatLng(ZatokaMakowo1.latitude,ZatokaMakowo1.longitude);
        mZatokaMakowo1 = mMap.addMarker(new MarkerOptions()
                .position(ZatokaMakowo1LatLng)
                .title("Zatoka Makowo #1")
                .snippet(getSnippet(ZatokaMakowo1))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mZatokaMakowo1);

        //marker Zatoka Makowo#2
        ObiektyPOI ZatokaMakowo2 = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.681885, 19.634695);
        LatLng ZatokaMakowo2LatLng = new LatLng(ZatokaMakowo2.latitude,ZatokaMakowo2.longitude);
        mZatokaMakowo2 = mMap.addMarker(new MarkerOptions()
                .position(ZatokaMakowo2LatLng)
                .title("Zatoka Makowo #2")
                .snippet(getSnippet(ZatokaMakowo2))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mZatokaMakowo2);

        //marker Dodzia Camp
        ObiektyPOI DodziaCamp = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.698063, 19.643175);
        LatLng DodziaCampLatLng = new LatLng(DodziaCamp.latitude,DodziaCamp.longitude);
        mDodziaCamp = mMap.addMarker(new MarkerOptions()
                .position(DodziaCampLatLng)
                .title("Dodzia Camp")
                .snippet(getSnippet(DodziaCamp))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mDodziaCamp);

        //marker Zatoka Piratów
        ObiektyPOI ZatokaPiratow = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.702170, 19.627328);
        LatLng ZatokaPiratowLatLng = new LatLng(ZatokaPiratow.latitude,ZatokaPiratow.longitude);
        mZatokaPiratow = mMap.addMarker(new MarkerOptions()
                .position(ZatokaPiratowLatLng)
                .title("Zatoka Piratów")
                .snippet(getSnippet(ZatokaPiratow))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mZatokaPiratow);

        //marker Obelisk
        ObiektyPOI Obelisk = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.701782, 19.623862);
        LatLng ObeliskLatLng = new LatLng(Obelisk.latitude,Obelisk.longitude);
        mObelisk = mMap.addMarker(new MarkerOptions()
                .position(ObeliskLatLng)
                .title("Obelisk")
                .snippet(getSnippet(Obelisk))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mObelisk);

        //marker Gierczak Mały
        ObiektyPOI GierczakMaly = new ObiektyPOI(false,true,false,false,false,false,false,false,true,false,true,false,false,53.710819, 19.629494);
        LatLng GierczakMalyLatLng = new LatLng(GierczakMaly.latitude,GierczakMaly.longitude);
        mGierczakMaly = mMap.addMarker(new MarkerOptions()
                .position(GierczakMalyLatLng)
                .title("Gierczak Mały")
                .snippet(getSnippet(GierczakMaly))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mGierczakMaly);

        //marker Gierczak Mały
        ObiektyPOI GierczakMaly2 = new ObiektyPOI(false,true,false,false,false,false,false,false,true,false,true,false,false,53.711336, 19.631966);
        LatLng GierczakMaly2LatLng = new LatLng(GierczakMaly2.latitude,GierczakMaly2.longitude);
        mGierczakMaly2 = mMap.addMarker(new MarkerOptions()
                .position(GierczakMaly2LatLng)
                .title("Gierczak Mały #2")
                .snippet(getSnippet(GierczakMaly2))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mGierczakMaly2);

        //marker Gierczak Duży
        ObiektyPOI GierczakDuzy = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.717282, 19.636532);
        LatLng GierczakDuzyLatLng = new LatLng(GierczakDuzy.latitude,GierczakDuzy.longitude);
        mGierczakDuzy = mMap.addMarker(new MarkerOptions()
                .position(GierczakDuzyLatLng)
                .title("Gierczak Duży")
                .snippet(getSnippet(GierczakDuzy))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mGierczakDuzy);

        //marker Binduga Przy Gierczaku
        ObiektyPOI BindugaGierczak1 = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.714304, 19.625954);
        LatLng BindugaGierczak1LatLng = new LatLng(BindugaGierczak1.latitude,BindugaGierczak1.longitude);
        mBindugaGierczak1 = mMap.addMarker(new MarkerOptions()
                .position(BindugaGierczak1LatLng)
                .title("Binduga Gierczak #1")
                .snippet(getSnippet(BindugaGierczak1))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mBindugaGierczak1);

        //marker Przystań Lipowy Ostrów
        ObiektyPOI PrzystanLipowyOstrow = new ObiektyPOI(false,true,false,false,false,false,false,false,true,false,true,false,false,53.730279, 19.604237);
        LatLng PrzystanLipowyOstrowLatLng = new LatLng(PrzystanLipowyOstrow.latitude,PrzystanLipowyOstrow.longitude);
        mPrzystanLipowyOstrow = mMap.addMarker(new MarkerOptions()
                .position(PrzystanLipowyOstrowLatLng)
                .title("Przystań Lipowy Ostrów")
                .snippet(getSnippet(PrzystanLipowyOstrow))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mPrzystanLipowyOstrow);

        //marker Zatoka Lipowy Ostrów #1
        ObiektyPOI ZatokaLipowyOstrow1 = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.734379, 19.599806);
        LatLng ZatokaLipowyOstrow1LatLng = new LatLng(ZatokaLipowyOstrow1.latitude,ZatokaLipowyOstrow1.longitude);
        mZatokaLipowyOstrow1 = mMap.addMarker(new MarkerOptions()
                .position(ZatokaLipowyOstrow1LatLng)
                .title("Zatoka Lipowy Ostrów #1")
                .snippet(getSnippet(ZatokaLipowyOstrow1))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mZatokaLipowyOstrow1);

        //marker Zatoka Lipowy Ostrów #2
        ObiektyPOI ZatokaLipowyOstrow2 = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.729915, 19.600368);
        LatLng ZatokaLipowyOstrow2LatLng = new LatLng(ZatokaLipowyOstrow2.latitude,ZatokaLipowyOstrow2.longitude);
        mZatokaLipowyOstrow2 = mMap.addMarker(new MarkerOptions()
                .position(ZatokaLipowyOstrow2LatLng)
                .title("Zatoka Lipowy Ostrów #2")
                .snippet(getSnippet(ZatokaLipowyOstrow2))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mZatokaLipowyOstrow2);

        //marker Zatoka Lipowy Ostrów #3
        ObiektyPOI ZatokaLipowyOstrow3 = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.732145, 19.601242);
        LatLng ZatokaLipowyOstrow3LatLng = new LatLng(ZatokaLipowyOstrow3.latitude,ZatokaLipowyOstrow3.longitude);
        mZatokaLipowyOstrow3 = mMap.addMarker(new MarkerOptions()
                .position(ZatokaLipowyOstrow3LatLng)
                .title("Zatoka Lipowy Ostrów #3")
                .snippet(getSnippet(ZatokaLipowyOstrow3))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mZatokaLipowyOstrow3);

        //marker Zatoka Lipowy Ostrów #4
        ObiektyPOI ZatokaLipowyOstrow4 = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.730733, 19.602566);
        LatLng ZatokaLipowyOstrow4LatLng = new LatLng(ZatokaLipowyOstrow4.latitude,ZatokaLipowyOstrow4.longitude);
        mZatokaLipowyOstrow4 = mMap.addMarker(new MarkerOptions()
                .position(ZatokaLipowyOstrow4LatLng)
                .title("Zatoka Lipowy Ostrów #4")
                .snippet(getSnippet(ZatokaLipowyOstrow4))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mZatokaLipowyOstrow4);

        //marker Zatoka Lipowy Ostrów #5
        ObiektyPOI ZatokaLipowyOstrow5 = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.733113, 19.601898);
        LatLng ZatokaLipowyOstrow5LatLng = new LatLng(ZatokaLipowyOstrow5.latitude,ZatokaLipowyOstrow5.longitude);
        mZatokaLipowyOstrow5 = mMap.addMarker(new MarkerOptions()
                .position(ZatokaLipowyOstrow5LatLng)
                .title("Zatoka Lipowy Ostrów #5")
                .snippet(getSnippet(ZatokaLipowyOstrow5))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mZatokaLipowyOstrow5);

        //marker Marina Żeglarska Siemiany
        ObiektyPOI MarinaSiemiany = new ObiektyPOI(false,true,false,true,true,true,true,false,false,true,true,true,true,53.738182, 19.587825);
        LatLng MarinaSiemianyLatLng = new LatLng(MarinaSiemiany.latitude,MarinaSiemiany.longitude);
        mMarinaSiemiany = mMap.addMarker(new MarkerOptions()
                .position(MarinaSiemianyLatLng)
                .title("Marina Żeglarska Siemiany")
                .snippet(getSnippet(MarinaSiemiany))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));
        lista_poi.add(mMarinaSiemiany);

        //marker Kurka Wodna
        ObiektyPOI KurkaWodna = new ObiektyPOI(false,true,false,true,true,true,true,false,false,true,false,true,true,53.737090, 19.587104);
        LatLng KurkaWodnaLatLng = new LatLng(KurkaWodna.latitude,KurkaWodna.longitude);
        mKurkaWodna = mMap.addMarker(new MarkerOptions()
                .position(KurkaWodnaLatLng)
                .title("Kurka Wodna")
                .snippet(getSnippet(KurkaWodna))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));
        lista_poi.add(mKurkaWodna);

        //marker Przystań Żeglarska Siemiany
        ObiektyPOI PrzystanZeglarskaSiemiany = new ObiektyPOI(false,false,true,true,true,true,false,true,false,true,false,true,false,53.731746, 19.587028);
        LatLng PrzystanZeglarskaSiemianyLatLng = new LatLng(PrzystanZeglarskaSiemiany.latitude,PrzystanZeglarskaSiemiany.longitude);
        mPrzystanZeglarskaSiemiany = mMap.addMarker(new MarkerOptions()
                .position(PrzystanZeglarskaSiemianyLatLng)
                .title("Przystań Żeglarska Siemiany")
                .snippet(getSnippet(PrzystanZeglarskaSiemiany))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));
        lista_poi.add(mPrzystanZeglarskaSiemiany);

        //marker Łąkowa
        ObiektyPOI Lakowa = new ObiektyPOI(false,true,false,false,false,false,false,false,true,false,true,false,false,53.729685, 19.628712);
        LatLng LakowaLatLng = new LatLng(Lakowa.latitude,Lakowa.longitude);
        mLakowa = mMap.addMarker(new MarkerOptions()
                .position(LakowaLatLng)
                .title("Przystań Łąkowa")
                .snippet(getSnippet(Lakowa))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mLakowa);

        //marker Jerzwałd
        ObiektyPOI Jerzwald = new ObiektyPOI(false,true,false,true,true,true,true,false,false,true,false,true,true,53.777941, 19.530287);
        LatLng JerzwaldLatLng = new LatLng(Jerzwald.latitude,Jerzwald.longitude);
        mJerzwald = mMap.addMarker(new MarkerOptions()
                .position(JerzwaldLatLng)
                .title("Jerzwałd")
                .snippet(getSnippet(Jerzwald))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));
        lista_poi.add(mJerzwald);

        //marker Zatoka Bukowiec
        ObiektyPOI ZatokaBukowiec = new ObiektyPOI(false,false,true,false,false,false,false,false,true,false,true,false,false,53.756991, 19.581213);
        LatLng ZatokaBukowiecLatLng = new LatLng(ZatokaBukowiec.latitude,ZatokaBukowiec.longitude);
        mZatokaBukowiec = mMap.addMarker(new MarkerOptions()
                .position(ZatokaBukowiecLatLng)
                .title("Zatoka Bukowiec")
                .snippet(getSnippet(ZatokaBukowiec))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_GREEN)));
        lista_poi.add(mZatokaBukowiec);

        //marker Matyty
        ObiektyPOI Matyty = new ObiektyPOI(false,true,false,true,false,true,true,false,false,true,false,true,true,53.788362, 19.585864);
        LatLng MatytyLatLng = new LatLng(Matyty.latitude,Matyty.longitude);
        mMatyty = mMap.addMarker(new MarkerOptions()
                .position(MatytyLatLng)
                .title("Matyty")
                .snippet(getSnippet(Matyty))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));
        lista_poi.add(mMatyty);

        //marker Gubławki
        ObiektyPOI Gublawki = new ObiektyPOI(false,false,true,false,false,true,true,false,false,true,false,true,false,53.739782, 19.645910);
        LatLng GublawkiLatLng = new LatLng(Gublawki.latitude,Gublawki.longitude);
        mGublawki = mMap.addMarker(new MarkerOptions()
                .position(GublawkiLatLng)
                .title("Gubławki")
                .snippet(getSnippet(Gublawki))
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_AZURE)));
        lista_poi.add(mGublawki);




        /**
         * MIELIZNY
         *
         */
        BitmapDescriptor icon1 = BitmapDescriptorFactory.fromResource(R.mipmap.danger_small);

        BitmapDescriptor icon2 = BitmapDescriptorFactory.fromResource(R.mipmap.electricity_small);

        BitmapDescriptor icon3 = BitmapDescriptorFactory.fromResource(R.mipmap.dam_small);

        //mielizna przy torze
        LatLng MielTor = new LatLng(53.618983, 19.548221);
        mMielTor = mMap.addMarker(new MarkerOptions()
                .position(MielTor)
                .title("Mielizna")
                .icon(icon1))  ;

        //mielizna przy Jazdzowkach
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

        //mielizna przy jeziorze Płaskim
        LatLng Plaskie = new LatLng(53.763629, 19.567231);
        mMielDuzyGierczak = mMap.addMarker(new MarkerOptions()
                .position(Plaskie)
                .title("Mielizna")
                .icon(icon1))  ;

        //mielizna przy Czaplaku #1
        LatLng Czaplak1 = new LatLng(53.772987, 19.604339);
        mCzaplak1 = mMap.addMarker(new MarkerOptions()
                .position(Czaplak1)
                .title("Mielizna")
                .icon(icon1))  ;

        //mielizna przy Czaplaku #2
        LatLng Czaplak2 = new LatLng(53.768854, 19.601177);
        mCzaplak2 = mMap.addMarker(new MarkerOptions()
                .position(Czaplak2)
                .title("Mielizna")
                .icon(icon1))  ;

        //mielizna przy łowisku Siemiany
        LatLng LowiskoSiemiany = new LatLng(53.743239, 19.597505);
        mLowiskoSiemiany = mMap.addMarker(new MarkerOptions()
                .position(LowiskoSiemiany)
                .title("Mielizna")
                .icon(icon1))  ;

        /**
         * INNE OBIEKTY MAPY
         *
         */

        //wejscie do Kanału Elbląskiego
        LatLng WejscieKanal = new LatLng(53.752886, 19.691161);
        mWejscieKanal = mMap.addMarker(new MarkerOptions()
                .position(WejscieKanal)
                .title("Wejście Kanału Elbląskiego")
                .anchor(0.5F,0.5F)
                .icon(icon3))  ;


        // Add polylines to the map.
        // Polylines are useful to show a route or some other connection between points.
        Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(53.604047, 19.539689),
                        new LatLng(53.61697, 19.54636))
                .clickable(true)
                .width(3));
        polyline1.setTag("Tor wioślarski");

       // Polylines are useful to show a route or some other connection between points.
        Polyline polyline2 = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(53.753499, 19.574327),
                        new LatLng(53.754991, 19.575723))
                .clickable(true)
                .color(0xffffff00)
                .width(6));
        polyline2.setTag("Linia wysokiego napięcia");

        // Polylines are useful to show a route or some other connection between points.
        Polyline polyline3 = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(53.597355, 19.554563),
                        new LatLng(53.597640, 19.556304))
                .clickable(true)
                .color(0xffff0000)
                .width(8));
        polyline3.setTag("Most drogowy w Iławie");

        //ikona ponad linią napięcia
        LatLng liniaNapiecia = new LatLng((53.753499+53.754991)/2, (19.574327+19.575723)/2);
        mLiniaWys = mMap.addMarker(new MarkerOptions()
                .position(liniaNapiecia)
                .anchor(0.5F,0.5F)
                .title("Linia wysokiego napięcia")
                .icon(icon2))  ;

        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener()
        {
            @Override
            public void onPolylineClick(Polyline polyline)
            {
                //double latitude0 = polyline.getPoints().get(0).latitude;
                //double longitude0 = polyline.getPoints().get(0).longitude;
                //double latitude1 = polyline.getPoints().get(1).latitude;
                //double longitude1 = polyline.getPoints().get(1).longitude;
                //LatLng srodek = new LatLng((latitude0+latitude1)/2,(longitude0+longitude1)/2);
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
                if (!marker.getTitle().equals("Mielizna")) {
                info.addView(snippet); }

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
            snippet = snippet + "\n\n\uD83D\uDEBF Prysznic";}
        if (poi.paliwo) {
            snippet = snippet + "\n\n⛽ Paliwo";}
        if (poi.sklep) {
            snippet = snippet + "\n\n\uD83D\uDED2 Sklep";}

        if (poi.kupa_w_krzaku) {
            snippet = snippet + "\n\n\uD83D\uDEBD Kupa w lesie ( ͡° ͜ʖ ͡°)";}
        else if(poi.toitoi) {
            snippet = snippet + "\n\n\uD83D\uDEBD ToiToi";}
        else if (poi.toaleta){
            snippet = snippet + "\n\n\uD83D\uDEBD Toaleta";}

        if (poi.woda_pitna) {
            snippet = snippet + "\n\n\uD83D\uDEB0 Woda pitna";}
        if (poi.ognisko) {
            snippet = snippet + "\n\n\uD83D\uDD25 Ognisko";}
        if (poi.smietnik) {
            snippet = snippet + "\n\n\uD83D\uDDD1 Śmietnik";}
        if (poi.nocleg) {
            snippet = snippet + "\n\n\uD83D\uDECC Nocleg";}

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