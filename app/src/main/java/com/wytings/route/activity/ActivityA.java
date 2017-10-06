package com.wytings.route.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wytings.annotation.AutoValue;
import com.wytings.annotation.RouteModule;
import com.wytings.route.api.RouteManager;
import com.wytings.route.module.ModuleSerializable;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */

@RouteModule("activityA")
public class ActivityA extends BaseActivity {

    @AutoValue
    String name = "shenzhen";

    @AutoValue
    int age = -200;

    @AutoValue
    ModuleSerializable module = new ModuleSerializable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouteManager.getInstance().autoValue(this);

        textView.setText("AAAAA( click to D)");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteManager.getInstance().proceed(getActivity());
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText("name = " + name
                        + "\nage = " + age
                        + "\nmodule = " + module);
            }
        });
    }
}
