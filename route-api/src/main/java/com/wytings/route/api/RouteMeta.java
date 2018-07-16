package com.wytings.route.api;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

import static com.wytings.route.api.RouteConst.IS_AUTO_PROCEED;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */


public class RouteMeta {

    public final String intentPath;
    public final Bundle bundle;
    private RouteMeta source;

    private RouteMeta(RouteMeta source, String intentPath) {
        this.intentPath = intentPath;
        this.bundle = new Bundle();
        this.source = source;
    }

    public RouteMeta(String intentPath) {
        this.intentPath = intentPath;
        this.bundle = new Bundle();
    }

    public RouteMeta with(String key, long value) {
        bundle.putString(key, String.valueOf(value));
        return this;
    }

    public RouteMeta with(String key, String value) {
        bundle.putString(key, value);
        return this;
    }

    public RouteMeta with(String key, boolean value) {
        bundle.putString(key, String.valueOf(value));
        return this;
    }

    public RouteMeta autoProceed(boolean isAutoProceed) {
        bundle.putBoolean(IS_AUTO_PROCEED, isAutoProceed);
        return this;
    }

    public RouteMeta with(String key, Serializable value) {
        bundle.putSerializable(key, value);
        return this;
    }

    public RouteMeta with(String key, Parcelable value) {
        bundle.putParcelable(key, value);
        return this;
    }

    public RouteMeta getSource() {
        return source;
    }

    public RouteMeta next(String nextPath) {
        return new RouteMeta(this, nextPath);
    }

    public void go(Context context) {
        RouteManager.getInstance().navigate(context, this);
    }
}
