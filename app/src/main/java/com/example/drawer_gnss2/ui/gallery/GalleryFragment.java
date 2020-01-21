package com.example.drawer_gnss2.ui.gallery;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.drawer_gnss2.R;

import static com.example.drawer_gnss2.MainActivity.fab;
import static com.example.drawer_gnss2.MainActivity.showOverflowMenu;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    @SuppressLint("RestrictedApi")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        /*
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */
        ImageView image_mapa = root.findViewById(R.id.imageView3);
        ImageView image_dane = root.findViewById(R.id.imageView4);

        image_mapa.setImageBitmap(getBitmapFromResources(getResources(), R.mipmap.jeziorak));
        image_dane.setImageBitmap(getBitmapFromResources(getResources(), R.mipmap.jeziorak2));

        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        p.setAnchorId(View.NO_ID);
        fab.setLayoutParams(p);

        //Wyłączenie widoczności przycisku lokalizacji
        fab.setVisibility(View.GONE);

        showOverflowMenu(false);


        return root;
    }

    public static Bitmap getBitmapFromResources(Resources resources, int resImage) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inSampleSize = 1;
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeResource(resources, resImage, options);
    }


}