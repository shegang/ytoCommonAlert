package com.yto.common.notice.marqueeview;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yto.common.notice.api.DataCallBack;
import com.yto.common.notice.api.RetrofitUtil;
import com.yto.common.notice.entity.ComplexItemEntity;

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
        Call<ResponseBody> call = RetrofitUtil.getInstance().getApiService().getVideoTab();
        RetrofitUtil.getInstance().requestMode(call, new DataCallBack() {
            @Override
            public void success(String msg, String result) {
                Gson gson = new Gson();
                List<ComplexItemEntity> list = gson.fromJson(result, new TypeToken<List<ComplexItemEntity>>() {
                }.getType());
                List<String> data = new ArrayList<>();
                for (ComplexItemEntity entity : list) {
                    data.add(entity.getName() + ":" + entity.getApiUrl());
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
