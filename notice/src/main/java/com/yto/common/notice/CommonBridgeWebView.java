package com.yto.common.notice;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.DefaultHandler;

public class CommonBridgeWebView extends AppCompatActivity {

    protected BridgeWebView webView;
    protected WebChromeClient chromeClient;
    protected WebViewClient webViewClient;
    protected ProgressBar progressBar;
    protected RelativeLayout rl_loading_faild;
    protected ImageView iv_back;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        webView = (BridgeWebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        iv_back = findViewById(R.id.iv_back);
        rl_loading_faild = (RelativeLayout) findViewById(R.id.rl_loading_faild);
        if (webView != null) {
            initWebView();
            initWebChromeClient();
        }
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });
    }

    protected void againLoad() {
        if (webView != null) {
            webView.setVisibility(View.VISIBLE);
        }
        rl_loading_faild.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initWebView() {
        WebSettings setting = webView.getSettings();

        //开启自动化测试
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }

//        //自定义UA
//        String userAgent = setting.getUserAgentString();
//        userAgent = userAgent + "WebViewDemo";
//        setting.setUserAgentString(userAgent);

        /**
         *  Webview在安卓5.0之前默认允许其加载混合网络协议内容
         *  在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //自动播放音频autoplay
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setting.setMediaPlaybackRequiresUserGesture(false);
        }

        setting.setJavaScriptEnabled(true);//设置WebView是否允许执行JavaScript脚本,默认false
        setting.setSupportZoom(true);//WebView是否支持使用屏幕上的缩放控件和手势进行缩放,默认值true
        setting.setBuiltInZoomControls(true);//是否使用内置的缩放机制
        setting.setDisplayZoomControls(false);//使用内置的缩放机制时是否展示缩放控件,默认值true

        setting.setUseWideViewPort(true);//是否支持HTML的“viewport”标签或者使用wide viewport
        setting.setLoadWithOverviewMode(true);//是否允许WebView度超出以概览的方式载入页面,默认false
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//设置布局,会引起WebView的重新布局(relayout),默认值NARROW_COLUMNS

        setting.setRenderPriority(WebSettings.RenderPriority.HIGH);//线程优先级(在API18以上已废弃。不建议调整线程优先级，未来版本不会支持这样做)
        setting.setEnableSmoothTransition(true);//已废弃,将来会成为空操作（no-op）,设置当panning或者缩放或者持有当前WebView的window没有焦点时是否允许其光滑过渡,若为true,WebView会选择一个性能最大化的解决方案。例如过渡时WebView的内容可能不更新。若为false,WebView会保持精度（fidelity）,默认值false。
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);//重写使用缓存的方式，默认值LOAD_DEFAULT
        setting.setPluginState(WebSettings.PluginState.ON);//在API18以上已废弃。未来将不支持插件,不要使用
        setting.setJavaScriptCanOpenWindowsAutomatically(true);//让JavaScript自动打开窗口,默认false

        //webview 中localStorage无效的解决方法
        setting.setDomStorageEnabled(true);//DOM存储API是否可用,默认false
        setting.setAppCacheMaxSize(1024 * 1024 * 8);//设置应用缓存内容的最大值
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        setting.setAppCachePath(appCachePath);//设置应用缓存文件的路径
        setting.setAllowFileAccess(true);//是否允许访问文件,默认允许
        setting.setAppCacheEnabled(true);//应用缓存API是否可用,默认值false,结合setAppCachePath(String)使用


        //支持文件下载
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                try {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        webView.setDefaultHandler(new DefaultHandler());
    }

    protected void initWebChromeClient() {
        chromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (progressBar != null) {
                    if (newProgress == 100) {
                        progressBar.setVisibility(View.GONE);
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(newProgress);
                    }
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        };
        webView.setWebChromeClient(chromeClient);
    }

}
