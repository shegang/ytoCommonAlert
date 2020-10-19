package com.yto.common.notice.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface  ApiService {
    @GET("videoHomeTab")
    Call<ResponseBody> getVideoTab();
}
