package com.wytings.route.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wytings.annotation.AutoValue;
import com.wytings.annotation.RouteModule;
import com.wytings.route.api.RouteManager;
import com.wytings.route.module.ModuleParcelable;
import com.wytings.route.module.ModuleSerializable;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */

@RouteModule("activityD")
public class ActivityD extends BaseActivity {

    @AutoValue("f")
    float floatNumber = -1;

    @AutoValue("l")
    long longNumber = -1;

    @AutoValue("double")
    double doubleNumber = -1;

    @AutoValue("module")
    ModuleParcelable moduleParcelable = new ModuleParcelable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouteManager.getInstance().autoValue(this);

        textView.setText("DDDDDDDDDD");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText("floatNumber= " + floatNumber
                        + "\nlongNumber=" + longNumber
                        + "\ndoubleNumber=" + doubleNumber
                        + "\nmoduleParcelable=" + moduleParcelable);
            }
        });
    }
}
