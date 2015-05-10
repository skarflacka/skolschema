package com.example.tony.gymnasieschema;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.method.Touch;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Tony on 2015-05-05.
 */

public class Fragment1 extends android.support.v4.app.Fragment {
    private View rootView;
    private TouchImageView touchImageView;
    private String url;
    private int week;

    public static Fragment1 newInstance (int pos) {
        Fragment1 fragment1 = new Fragment1();

        Bundle args = new Bundle();
        args.putInt("week", pos);
        fragment1.setArguments(args);

        return fragment1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment1, container, false);

        touchImageView = (TouchImageView)rootView.findViewById(R.id.touchImageView);

        url = getResources().getString(R.string.url);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String school = sharedPreferences.getString("SchoolKey", "00000");
        String personnummer = sharedPreferences.getString("PersonnummerKey", "00000");
        String width = sharedPreferences.getString("WidthKey", "0");
        String height = sharedPreferences.getString("HeightKey", "0");

        week = getArguments().getInt("week", 0);
        String weekString = String.valueOf(week);

        String urlFormatted = url.format(url, school, personnummer, width, height, weekString);

        Picasso.with(touchImageView.getContext()).load(urlFormatted).into(touchImageView);

        return rootView;
    }


}
