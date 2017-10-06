package com.wytings.route;

import android.app.Application;

import com.wytings.route.api.RouteManager;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RouteManager.getInstance().initialize(this);
    }
}
