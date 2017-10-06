package com.wytings.route.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wytings.route.module.ModuleParcelable;
import com.wytings.route.module.ModuleSerializable;
import com.wytings.route.R;
import com.wytings.route.api.RouteManager;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.openWeb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplication(), WebViewActivity.class).putExtra("url", "file:///android_asset/h5-scheme-test.html"));
            }
        });

        findViewById(R.id.activityA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteManager.getInstance().build("activityA?name=hongkong2&age=150")
                        .autoProceed(true)
                        .next("activityD?f=1.123&&double=22.2233")
                        .with("module", new ModuleParcelable("haha", 1.1111f))
                        .go(getActivity());
            }
        });

        findViewById(R.id.activityAA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteManager.getInstance().build("activityA?name=hongkong1&age=150000")
                        .with("module", new ModuleSerializable("SerializableName", 1.1111f))
                        .autoProceed(false)
                        .next("activityD?l=987654321")
                        .with("module", new ModuleParcelable("ParcelableName", 99999))
                        .go(getActivity());
            }
        });

        findViewById(R.id.activityC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteManager.getInstance().build("activityC?name=testName").go(getActivity());
            }
        });
    }
}
