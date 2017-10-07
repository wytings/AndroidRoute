package com.wytings.route.activity;

import android.os.Bundle;
import android.view.View;

import com.wytings.annotation.AutoValue;
import com.wytings.annotation.Route;
import com.wytings.route.api.RouteManager;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */

@Route("activityC")
public class ActivityC extends BaseActivity {


    @AutoValue
    String name = "shenzhen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouteManager.getInstance().autoValue(this);
        textView.setText("CCCCCCCCCC");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText("name = " + name);
            }
        });
    }
}
