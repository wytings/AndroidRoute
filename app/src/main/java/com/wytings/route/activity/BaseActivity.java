package com.wytings.route.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wytings.route.R;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */


public abstract class BaseActivity extends Activity {

    protected TextView textView;
    protected Button button;

    public Activity getActivity() {
        return this;
    }

    protected void showText(String msg) {
        Toast.makeText(this, getLocalClassName() + "\n" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        textView = findViewById(R.id.text);
        button = findViewById(R.id.button);

        Log.i("wytings", this.getLocalClassName() + " ----- onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("wytings", this.getLocalClassName() + " ----- onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("wytings", this.getLocalClassName() + " ----- onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("wytings", this.getLocalClassName() + " ----- onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("wytings", this.getLocalClassName() + " ----- onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("wytings", this.getLocalClassName() + " ----- onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("wytings", this.getLocalClassName() + " ----- onDestroy");
    }
}
