package com.yto.common.noticeappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yto.common.notice.api.DataCallBack;
import com.yto.common.notice.api.RetrofitUtil;
import com.yto.common.notice.api.requestparameter.RequestParameter;
import com.yto.common.notice.marqueeview.MarqueeFactory;
import com.yto.common.notice.marqueeview.MarqueeView;
import com.yto.common.notice.marqueeview.NoticeManager;
import com.yto.common.notice.marqueeview.SimpleMF;
import com.yto.common.notice.marqueeview.SimpleMarqueeView;
import com.yto.common.notice.marqueeview.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dialog.DialogManager;

public class MainActivity extends AppCompatActivity {
    private MarqueeView<RelativeLayout, ComplexItemEntity> marqueeView;
    private SimpleMarqueeView marqueeView2;

    private NoticeManager noticeManager;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        marqueeView = findViewById(R.id.marqueeView);
        marqueeView2 = findViewById(R.id.marqueeView2);
        noticeManager = new NoticeManager.Builder(this)
                .init(marqueeView2)
                .create();
        initMarqueeView();
        RequestParameter parameter = new RequestParameter();
        parameter.setAppCode("688d1c03959c4553b48e2a524273d52f");
        parameter.setAppSecret("d729fdcdc8");
//        parameter.setUserCode("1367916926"+Math.random()*9);
        parameter.setUserCode("13679169261");
        parameter.setUserName("zc");
        new DialogManager.Builder(getSupportFragmentManager(), this)
                .setParameter(parameter)
                .setTitleColor("#FFC107")
                .setTitleBarBgColor("#F62D2D")
                .setBackImageColor("#FFC107")
                .create();

    }

    private void initMarqueeView() {
        List<ComplexItemEntity> complexDatas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            complexDatas.add(new ComplexItemEntity("标题 " + i, "副标题 " + i, "时间 " + i));
        }
        MarqueeFactory<RelativeLayout, ComplexItemEntity> marqueeFactory = new ComplexViewMF(MainActivity.this);

        marqueeFactory.setData(complexDatas);
        marqueeView.setOnItemClickListener(new OnItemClickListener<RelativeLayout, ComplexItemEntity>() {
            @Override
            public void onItemClickListener(RelativeLayout mView, ComplexItemEntity mData, int mPosition) {
                Toast.makeText(MainActivity.this, String.format("mPosition:%s,mData:%s,mView:%s,.", mPosition, mData, mView), Toast.LENGTH_SHORT).show();
            }
        });
        marqueeView.setInAndOutAnim(R.anim.in_top, R.anim.out_bottom);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();
    }

    public void homeBannerDialog(View view) {
        RequestParameter parameter = new RequestParameter();
        parameter.setAppCode("688d1c03959c4553b48e2a524273d52f");
        parameter.setAppSecret("d729fdcdc8");

//        parameter.setUserCode("1367916926"+Math.random()*9);
        parameter.setUserCode("13679169261");
        parameter.setUserName("zc");
        new DialogManager.Builder(getSupportFragmentManager(), this)
                .setParameter(parameter)
                .setTitleColor("#FFC107")
                .setTitleBarBgColor("#F62D2D")
                .setBackImageColor("#FFC107")
                .create();
    }


}