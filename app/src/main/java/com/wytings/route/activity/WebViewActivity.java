package com.wytings.route.activity;

import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */

public class WebViewActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        setContentView(webView);
        webView.loadUrl(getIntent().getStringExtra("url"));
    }
}
