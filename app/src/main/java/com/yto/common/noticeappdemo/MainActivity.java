package com.yto.common.noticeappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yto.common.notice.marqueeview.MarqueeFactory;
import com.yto.common.notice.marqueeview.MarqueeView;
import com.yto.common.notice.marqueeview.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

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
}