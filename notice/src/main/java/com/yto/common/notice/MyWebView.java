package com.yto.common.notice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MyWebView extends CommonBridgeWebView {
    public static final String KEY_URL = "KEY_URL";

    private String url;
    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, MyWebView.class);
        Bundle bundle = new Bundle();
//        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_URL, url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_bridge_webview);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
//            title = bundle.getString(KEY_TITLE);
            url = bundle.getString(KEY_URL);
        }
//        setTitle(title);
        webView.loadUrl(url);

        //测试加载失败点击重新加载逻辑
//        loadingFailed();
    }

    @Override
    protected void againLoad() {
        super.againLoad();
        webView.loadUrl(url);
    }
}
