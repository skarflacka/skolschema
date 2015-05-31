package com.example.tony.gymnasieschema;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.PushService;

/**
 * Created by Tony on 2015-05-31.
 */
public class ParsePushApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "WFxl4YX0QdaJkFDQriHsodt57QKFlKLfqHiuY78K", "bx4Wk9jvhTTeYZ5gtxqxp8SiquOAv2j5jbGWMAlJ");
        ParseInstallation parseInstallation = ParseInstallation.getCurrentInstallation();
        parseInstallation.saveInBackground();

        ParsePush.subscribeInBackground("dummy1048596");

    }
}
