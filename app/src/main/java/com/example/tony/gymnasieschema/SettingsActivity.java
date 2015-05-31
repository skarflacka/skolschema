package com.example.tony.gymnasieschema;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Tony on 2015-05-05.
 */
public class SettingsActivity extends ActionBarActivity {

    private android.support.v7.widget.Toolbar toolbar_settings;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getFragmentManager().beginTransaction()
                .replace(R.id.preference_container, new SettingsFragment())
                .commit();

        toolbar_settings = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_settings);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);

            Preference preference = findPreference("SchoolKey");
            Preference clearCachePreference = findPreference("ClearCacheKey");

            clearCachePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

                @Override
                public boolean onPreferenceClick(Preference preference) {
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    imageLoader.clearDiskCache();
                    imageLoader.clearMemoryCache();

                    Toast.makeText(getActivity().getApplicationContext(), "Cache cleared.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), SchoolSearchActivity.class);
                    startActivity(intent);

                    return false;
                }
            });
        }
    }

    /*

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.toolbar_settings, root, false);
        root.addView(bar, 0); // insert at top
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }*/

}