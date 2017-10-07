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


@Route("activityB")
public class ActivityB extends BaseActivity {

    @AutoValue
    boolean show = false;

    @AutoValue
    byte flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouteManager.getInstance().autoValue(this);
        textView.setText("BBBBBBBBBB");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText("show = " + show
                        + "\n flag = " + flag);
            }
        });
    }

}
