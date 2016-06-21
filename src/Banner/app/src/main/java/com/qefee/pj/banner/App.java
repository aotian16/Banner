package com.qefee.pj.banner;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * App.
 * (16/6/13)
 *
 * @author tongjin
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this); // 初始化Fresco
    }
}
