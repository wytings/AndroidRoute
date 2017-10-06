package com.wytings.route.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.wytings.route.api.RouteManager;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */

public class SchemeFilterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        Log.i("wytings", "uri = " + uri);

        String scheme = uri.getScheme();
        if ("http".equals(scheme) || "https".equals(scheme)) {
            String routeModule = uri.getLastPathSegment();
            if (!TextUtils.isEmpty(routeModule)) {
                RouteManager.getInstance().build(routeModule + "?" + uri.getQuery()).go(this);
            }
        } else {
            RouteManager.getInstance().build(uri.toString()).go(this);
        }

        finish();
    }

}
