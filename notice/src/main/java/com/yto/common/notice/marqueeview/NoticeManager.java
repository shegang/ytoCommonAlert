package com.yto.common.notice.marqueeview;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yto.common.notice.api.DataCallBack;
import com.yto.common.notice.api.requestparameter.RequestParameter;
import com.yto.common.notice.api.RetrofitUtil;
import com.yto.common.notice.entity.AnnounceData;
import com.yto.common.notice.entity.RollAnnounceDataList;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class NoticeManager {
    private MarqueeView marqueeView;
    private Context context;

    private NoticeManager(Context context) {
        this.context = context;
    }

    private void requestData() {
        RequestParameter parameter = new RequestParameter();
        parameter.setAppCode("bc7c151dbe8c45c8a3ce486d64d76bd7");
        parameter.setAppSecret("2690d6b105");
        parameter.setUserCode("01524106");
        parameter.setUserName("曾超");
        Call<ResponseBody> call = RetrofitUtil.getInstance().getApiService().getRollAnnounceList(parameter);
        RetrofitUtil.getInstance().requestMode(call, new DataCallBack() {
            @Override
            public void success(String msg, String result) {
                Gson gson = new Gson();
//                List<ComplexItemEntity> list = gson.fromJson(result, new TypeToken<List<ComplexItemEntity>>() {
//                }.getType());
                RollAnnounceDataList rollAnnounceDataList = gson.fromJson(result,RollAnnounceDataList.class);
                List<AnnounceData> list = rollAnnounceDataList.getAnnounceList();
                List<String> data = new ArrayList<>();
                for (AnnounceData entity : list) {
                    data.add(entity.getAnnounceName() + ":" + entity.getAnnounceLabel());
                }
                SimpleMF simpleMF = new SimpleMF(context);
                simpleMF.setData(data);
                if(marqueeView != null){
                    marqueeView.setMarqueeFactory(simpleMF);
                    marqueeView.startFlipping();
                }
            }

            @Override
            public void fail(String msg, int errorCode) {
                Log.e("error", msg);
            }
        });
    }

    public static class Builder {
        private NoticeManager manager;

        public Builder(Context context) {
            manager = new NoticeManager(context);
        }

        public Builder init(MarqueeView marqueeView) {
            manager.marqueeView = marqueeView;
            return this;
        }

        public NoticeManager create() {
            manager.requestData();
            return manager;
        }
    }
}
