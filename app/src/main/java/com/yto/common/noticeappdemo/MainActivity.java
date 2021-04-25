package com.yto.common.noticeappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yto.common.notice.NoticeConfig;
import com.yto.common.notice.api.requestparameter.RequestParameter;
import com.yto.common.notice.marqueeview.MarqueeFactory;
import com.yto.common.notice.marqueeview.MarqueeView;
import com.yto.common.notice.marqueeview.NoticeManager;
import com.yto.common.notice.marqueeview.SimpleMarqueeView;
import com.yto.common.notice.marqueeview.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

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

        NoticeConfig.setIsRelease(false);

        noticeManager = new NoticeManager.Builder(this)
                .init(marqueeView2)
                .create();
        initMarqueeView();

        //appCode=26e80fd95baa4256aa2360514f606bb4，AppSecret=446c771e62  生产环境易拣通
        RequestParameter parameter = new RequestParameter();
//        parameter.setAppCode("688d1c03959c4553b48e2a524273d52f");
//        parameter.setAppSecret("d729fdcdc8");

        //appCode：688d1c03959c4553b48e2a524273d52f
        //AppSecret：P220115035
        parameter.setAppCode("9f3e2da25f69447e99effec111c0bbb2");
        parameter.setAppSecret("0062565893");
        parameter.setSource("PORTAL");
        parameter.setToken("Portal-eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJAdHlwZVwiOlwiY24ueXRvLnBvcnRhbC5hdXRob3JpdHkudm8uVXNlclZvXCIsXCJiZWxvbmdPcmdDb2RlXCI6XCIyMTAwNDVcIixcImJlbG9uZ09yZ05hbWVcIjpcIuS4iua1t-W4gumdmeWuieWMuumdmeWuiemXqFwiLFwiYmVsb25nT3JnVHlwZVwiOlwiQlJBTkNIXCIsXCJjZWxscGhvbmVcIjpcIjEzOTM2OTQ2MTQ2XCIsXCJvcmdDb2RlXCI6XCIyMTAwNDVcIixcIm9yZ05hbWVcIjpcIuS4iua1t-W4gumdmeWuieWMuumdmeWuiemXqFwiLFwib3JnVHlwZVwiOlwiQlJBTkNIXCIsXCJwYXNzd29yZFwiOlwicjkwTFN0THNGeXhZYmlGUWR3Ky9uZz09XCIsXCJwYXNzd29yZFRlcm1pbmFsXCI6XCIwSmNIRkhWM2crYlBGN0p2dU9JcGp3PT1cIixcInVzZXJDb2RlXCI6XCIwMDAwMzUyMFwiLFwidXNlck5hbWVcIjpcIuael-eIseWFsHRlc3RcIn0iLCJqdGkiOiJhOTc5NTYxZC0xNjE2LTQ5MTctYjMzOS01NzRmMDg2NTAyMjQiLCJpYXQiOjE2MTkzNDE3NjMsImV4cCI6MTYxOTM1NjE2M30.-OdMj67W1UDOgMAmuBB49ksS3YwoDQJg_8MNp2sONEgmsCc3CwPG1ePajzpy7KPKsHfzM-2BXtJiTb5jFXymDA");

//        parameter.setUserCode("01653893"+Math.random()*9);
        parameter.setUserCode("01653893");
//        parameter.setUserCode("13679169261");
        parameter.setUserName("sg");

        new DialogManager.Builder(getSupportFragmentManager(), this)
                .setParameter(parameter)
                .setTitleColor("#FFC107")//webview的标题字体颜色
                .setTitleBarBgColor("#F62D2D")//webview的导航栏背景颜色
                .setBackImageColor("#FFC107")//返回按钮颜色
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
        parameter.setAppCode("26e80fd95baa4256aa2360514f606bb4");
        parameter.setAppSecret("446c771e62");

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