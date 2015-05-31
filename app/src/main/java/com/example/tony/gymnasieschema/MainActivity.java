package com.example.tony.gymnasieschema;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.tony.gymnasieschema.sTab.SlidingTabLayout;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends ActionBarActivity {

    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private android.support.v7.widget.Toolbar toolbar;
    private TouchImageView touchImageView;
    private String url;
    private ImageLoader imageLoader;

    private int dag = new GregorianCalendar().get(Calendar.DAY_OF_WEEK);
    private int week = new GregorianCalendar().get(Calendar.WEEK_OF_YEAR);
    private static final int SETTINGS_INFO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //First Start

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.commit();

            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivityForResult(intent, SETTINGS_INFO);
        }

        //Tabbed down color and icon. Only for post 20 api.
        if (Build.VERSION.SDK_INT > 20) {
            Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.skolschema);
            ActivityManager.TaskDescription taskDesc = new ActivityManager.TaskDescription(getString(R.string.app_name), icon, getResources().getColor(R.color.ColorPrimary));
            ((Activity) this).setTaskDescription(taskDesc);
        }

        /* Day view. Deprecated.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean dag = sharedPreferences.getBoolean("DagKey", false);*/

        //ViewPager setup

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), MainActivity.this));
        viewPager.setCurrentItem(week-1);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingTabs);

        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.teal);
            }

            @Override
            public int getDividerColor(int position) {
                return 0;
            }
        });

        slidingTabLayout.setViewPager(viewPager);

    }

    //Start HelpActivity, called first in onCreate.
    public void showHelp() {
        Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivityForResult(intent, SETTINGS_INFO);

            return true;
        } if (id == R.id.action_refresh) {
            displayImageUni();

            return true;
        } if (id == R.id.action_credits) {
            Dialog dialog = dialogCredits();
            dialog.show();

            return true;
        }
        return super.onOptionsItemSelected(item);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SETTINGS_INFO) {
            displayImage();
        }
    }

    public Dialog dialogCredits() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.credits_text)
                .setTitle(R.string.action_credits2).setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();

        return  dialog;
    }

    public void weekList() {
        String[] week_list = getResources().getStringArray(R.array.week_array);
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, week_list);

        ListView listView = (ListView)findViewById(R.id.week_list);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void displayImage() {
        touchImageView = (TouchImageView)findViewById(R.id.touchImageView);

        url = getResources().getString(R.string.url);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String school = sharedPreferences.getString("SchoolKey", "00000");
        String personnummer = sharedPreferences.getString("PersonnummerKey", "00000");
        String width = sharedPreferences.getString("WidthKey", "0");
        String height = sharedPreferences.getString("HeightKey", "0");

        String weekString = String.valueOf(week);

        String urlFormatted = url.format(url, school, personnummer, width, height, weekString);

        Picasso.with(touchImageView.getContext()).load(urlFormatted).into(touchImageView);
    }

    public void displayImageUni() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(options)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024)
                .build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP

        imageLoader = ImageLoader.getInstance();

        touchImageView = (TouchImageView)findViewById(R.id.touchImageView);

        url = getResources().getString(R.string.url);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String school = sharedPreferences.getString("SchoolKey", "00000");
        String personnummer = sharedPreferences.getString("PersonnummerKey", "00000");
        String width = sharedPreferences.getString("WidthKey", "0");
        String height = sharedPreferences.getString("HeightKey", "0");

        String weekString = String.valueOf(week);

        String urlFormatted = url.format(url, school, personnummer, width, height, weekString);


        imageLoader.displayImage(urlFormatted, touchImageView, options);
    }
}
