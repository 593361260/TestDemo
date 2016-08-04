package com.wcl.uustore.baseapplication;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.wcl.uustore.tool.AppConfig;


/**
 * Created by DoctorY on 2016/4/8.
 */
public class UUApplication extends Application {

    @Override
    public void onCreate() {
//        Vitamio.initialize(this);
        super.onCreate();
        Fresco.initialize(this);
        AppConfig.currenttime = System.currentTimeMillis();
    }
}
