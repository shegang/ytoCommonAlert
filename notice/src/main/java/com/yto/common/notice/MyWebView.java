package com.yto.common.notice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MyWebView extends CommonBridgeWebView {
    public static final String KEY_URL = "key_url";
    public static final String KEY_TITLE = "key_title";
    public static final String KEY_TITLE_COLOR = "key_title_color";
    public static final String KEY_BAR_COLOR = "key_titleBarColor";
    public static final String KEY_BACK_COLOR = "key_backColor";

    private String url;
    private String titleBarColor;
    private String titleColor;
    private String backColor;
    private String title;

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, MyWebView.class);
        Bundle bundle = new Bundle();
//        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_URL, url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String url, String title,String titleColor,String titleBarColor, String backColor) {
        Intent intent = new Intent(context, MyWebView.class);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_TITLE_COLOR, titleColor);
        bundle.putString(KEY_URL, url);
        bundle.putString(KEY_BAR_COLOR, titleBarColor);
        bundle.putString(KEY_BACK_COLOR, backColor);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_bridge_webview);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString(KEY_TITLE);
            url = bundle.getString(KEY_URL);
            titleColor = bundle.getString(KEY_TITLE_COLOR);
            titleBarColor = bundle.getString(KEY_BAR_COLOR);
            backColor = bundle.getString(KEY_BACK_COLOR);
        }
        tv_title.setText(title);
        if(!TextUtils.isEmpty(titleColor)){
            tv_title.setTextColor(Color.parseColor(titleColor));
        }
        if (!TextUtils.isEmpty(titleBarColor)) {
            rl_title.setBackgroundColor(Color.parseColor(titleBarColor));
        }
        if (!TextUtils.isEmpty(backColor)) {
            iv_back.setColorFilter(Color.parseColor(backColor));
        }

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
