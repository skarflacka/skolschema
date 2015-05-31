package com.example.tony.gymnasieschema;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by Tony on 2015-05-05.
 */

public class Fragment2 extends android.support.v4.app.Fragment {
    private View rootView;
    private TouchImageView touchImageView;
    private String url;
    private int week;

    public static Fragment2 newInstance (int pos) {
        Fragment2 fragment2 = new Fragment2();

        Bundle args = new Bundle();
        args.putInt("week", pos);
        fragment2.setArguments(args);

        return fragment2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment1, container, false);

        displayImageUni();

        return rootView;
    }

    public void displayImageUni() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity().getApplicationContext())
                .defaultDisplayImageOptions(options)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024)
                .build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP

        ImageLoader imageLoader = ImageLoader.getInstance();

        touchImageView = (TouchImageView)rootView.findViewById(R.id.touchImageView);

        url = getResources().getString(R.string.url);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String school = sharedPreferences.getString("SchoolKey", "00000");
        String personnummer = sharedPreferences.getString("PersonnummerKey", "00000");
        String width = sharedPreferences.getString("WidthKey", "0");
        String height = sharedPreferences.getString("HeightKey", "0");

        week = getArguments().getInt("week", 0);
        String weekString = String.valueOf(week);

        String urlFormatted = url.format(url, school, personnummer, width, height, weekString);

        imageLoader.displayImage(urlFormatted, touchImageView, options);
    }

}
