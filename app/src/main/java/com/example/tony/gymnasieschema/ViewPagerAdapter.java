package com.example.tony.gymnasieschema;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Tony on 2015-05-05.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int week = new GregorianCalendar().get(Calendar.WEEK_OF_YEAR);

    private String tabTitles[] = new String[] {
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23",
            "24",
            "25",
            "26",
            "27",
            "28",
            "29",
            "30",
            "31",
            "32",
            "33",
            "34",
            "35",
            "36",
            "37",
            "38",
            "39",
            "40",
            "41",
            "42",
            "43",
            "44",
            "45",
            "46",
            "47",
            "48",
            "49",
            "50",
            "51",
            "52"
    };
    private Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int pos) {
        if(pos+1 == week) {
            return new Fragment2().newInstance(pos+1);
        } else if (pos < 53) {
            return new Fragment1().newInstance(pos+1);
        } else {
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int pos) {
        return "Vecka " + (tabTitles[pos]);
    }

    @Override
    public int getCount() {
        return 52;
    }
}
