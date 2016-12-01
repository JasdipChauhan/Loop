package com.gfive.jasdipc.loopandroid.Managers;

import android.app.Application;

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
}
