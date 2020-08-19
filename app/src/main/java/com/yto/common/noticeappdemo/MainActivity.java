package com.yto.common.noticeappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yto.common.notice.marqueeview.MarqueeFactory;
import com.yto.common.notice.marqueeview.MarqueeView;
import com.yto.common.notice.marqueeview.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import dialog.BindViewHolder;
import dialog.OnBindViewListener;
import dialog.OnViewClickListener;
import dialog.SGDialog;

public class MainActivity extends AppCompatActivity {
    private MarqueeView<RelativeLayout, ComplexItemEntity> marqueeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        marqueeView = findViewById(R.id.marqueeView);
        initMarqueeView();
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
        new SGDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_home_ad)
                .setScreenHeightAspect(this, 0.7f)
                .setScreenWidthAspect(this, 0.8f)
                .setOnBindViewListener(new OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder viewHolder) {

                    }
                })
                .addOnClickListener(R.id.iv_close,R.id.iv_icon)
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, SGDialog tDialog) {
                       if(view.getId() == R.id.iv_icon){
                           //可对图片进行修改
                           Toast.makeText(MainActivity.this,"点击了图片",Toast.LENGTH_LONG).show();
                       }
                        tDialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}