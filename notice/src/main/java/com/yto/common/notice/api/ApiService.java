package com.yto.common.notice.api;

import com.yto.common.notice.api.requestparameter.CommonParameter;
import com.yto.common.notice.api.requestparameter.RequestParameter;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("videoHomeTab")
    Call<ResponseBody> getVideoTab();

    @POST("announce/announceAppPc/appRollingAnnounceList")
    Call<ResponseBody> getRollAnnounceList(@Body RequestParameter parameter);

    @POST("announce/announceSurface/appPopAnnounces")
    Call<ResponseBody> getPopAnnounceList(@Body CommonParameter parameter);


}
