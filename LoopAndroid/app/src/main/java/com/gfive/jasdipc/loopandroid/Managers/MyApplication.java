package com.gfive.jasdipc.loopandroid.Managers;

import android.app.Application;
import android.content.res.Configuration;

import com.gfive.jasdipc.loopandroid.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by JasdipC on 2016-12-01.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/AvenirLTStd-Book.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
