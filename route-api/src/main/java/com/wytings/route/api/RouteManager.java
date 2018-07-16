package com.wytings.route.api;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.wytings.route.api.InternalConst.AUTO_VALUE_HELPER;
import static com.wytings.route.api.InternalConst.CLASS_ROUTE_MAP_LOADER;
import static com.wytings.route.api.RouteConst.NEXT_INTENT;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */


public class RouteManager {
    @SuppressLint("StaticFieldLeak")
    private static final RouteManager ROUTER_MANAGER = new RouteManager();
    private final Map<String, Class<?>> routeMap = new HashMap<>();
    private final Map<String, IAutoValueHelper> autoValueMap = new HashMap<>();
    private Context application;

    public static RouteManager getInstance() {
        return ROUTER_MANAGER;
    }

    private RouteManager() {

    }

    public void autoValue(Object object) {
        Log.i("wytings", "object = " + object);
        String clazz = object.getClass().getCanonicalName() + AUTO_VALUE_HELPER;
        IAutoValueHelper helper = autoValueMap.get(clazz);
        try {
            if (helper == null) {
                helper = (IAutoValueHelper) Class.forName(clazz).newInstance();
                autoValueMap.put(clazz, helper);
            }
            helper.autoValue(object);
        } catch (Exception e) {
            Log.i("wytings", Log.getStackTraceString(e));
        }
    }

    public void initialize(Context context) {
        application = context.getApplicationContext();
        try {
            @SuppressWarnings("unchecked")
            Class<IRouteMapLoader> mapLoaderClass = (Class<IRouteMapLoader>) Class.forName(CLASS_ROUTE_MAP_LOADER);
            IRouteMapLoader loader = mapLoaderClass.getConstructor().newInstance();
            routeMap.clear();
            routeMap.putAll(loader.load());
        } catch (Exception e) {
            Log.e("wytings", Log.getStackTraceString(e));
        }
        Log.i("wytings", "loading route map from generate file");
    }

    public RouteMeta build(String modulePath) {
        return new RouteMeta(modulePath);
    }

    public void navigate(Context context, RouteMeta routeMeta) {
        if (routeMeta == null) {
            return;
        }

        Intent intent = makeRouteIntent(routeMeta);
        if (intent == null) {
            return;
        }

        Context routeContext = application;
        boolean needNewTask = true;
        if (context instanceof Activity) {
            needNewTask = false;
            routeContext = context;
        }

        Intent nextIntent = intent;
        List<Intent> list = new ArrayList<>(3);
        if (needNewTask) {
            nextIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        list.add(nextIntent);
        while (nextIntent.getBooleanExtra(RouteConst.IS_AUTO_PROCEED, false)) {
            nextIntent = nextIntent.getParcelableExtra(NEXT_INTENT);
            if (nextIntent == null) {
                break;
            }
            if (needNewTask) {
                nextIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            list.add(nextIntent);
        }

        if (list.size() == 1) {
            routeContext.startActivity(list.get(0));
        } else if (list.size() > 1) {
            routeContext.startActivities(list.toArray(new Intent[0]));
        }
    }

    private Intent makeRouteIntent(RouteMeta routeMeta) {

        if (routeMeta == null || TextUtils.isEmpty(routeMeta.intentPath)) {
            return null;
        }

        try {
            RouteMeta meta = routeMeta;
            List<Intent> reverseIntentList = new ArrayList<>(3);
            while (meta != null) {
                Uri uri = Uri.parse(meta.intentPath);
                String moduleName = uri.getPath();
                if (TextUtils.isEmpty(moduleName)) {
                    moduleName = uri.getHost();
                }

                Class<?> dest = routeMap.get(moduleName);
                if (dest == null) {
                    break;
                }

                Intent intent = new Intent(application, dest);
                intent.putExtras(meta.bundle);
                Set<String> nameSet = uri.getQueryParameterNames();
                for (String name : nameSet) {
                    intent.putExtra(name, uri.getQueryParameter(name));
                }
                reverseIntentList.add(intent);
                meta = meta.getSource();
            }

            if (reverseIntentList.isEmpty()) {
                return null;
            }
            int size = reverseIntentList.size();
            Intent routeIntent = reverseIntentList.get(size - 1);
            for (int i = size - 2; i >= 0; i--) {
                routeIntent.putExtra(NEXT_INTENT, reverseIntentList.get(i));
            }
            return routeIntent;

        } catch (Exception e) {
            Log.i("wytings", Log.getStackTraceString(e));
        }
        return null;
    }

    public boolean proceed(Activity activity) {
        if (activity == null) {
            return false;
        }
        Intent callingIntent = activity.getIntent();
        Intent next = callingIntent.getParcelableExtra(NEXT_INTENT);
        if (next == null) {
            return false;
        }
        activity.startActivity(next);
        return true;
    }
}
