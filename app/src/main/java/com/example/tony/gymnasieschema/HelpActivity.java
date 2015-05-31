package com.example.tony.gymnasieschema;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

/**
 * Created by Tony on 2015-05-10.
 */
public class HelpActivity extends ActionBarActivity{

    @SuppressWarnings("deprecated")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView textView1 = (TextView)findViewById(R.id.textView);
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        TextView textView3 = (TextView)findViewById(R.id.textView3);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Thin.ttf");

        textView1.setTypeface(typeFace);
        textView2.setTypeface(typeFace);
        textView3.setTypeface(typeFace);

        ShimmerFrameLayout container =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.setAutoStart(true);
        container.setDuration(3000);
        container.startShimmerAnimation();
    }

    public void closeHelp(View v) {
        finish();
    }
}
