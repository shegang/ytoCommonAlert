package com.yto.common.notice.api;




import com.yto.common.notice.BuildConfig;
import com.yto.common.notice.NoticeConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitUtil {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private volatile static RetrofitUtil sInstance;
    private Retrofit mRetrofit;
    private ApiService apiService;
    private int timeout = 30;
    private String testBaseUrl = "http://192.168.207.21:18768/";//测试环境  18768
    private String releaseBaseUrl = "http://ann.yto.net.cn:18768/";//生产环境

//    private final String baseUrl = "http://192.168.201.67:8081/steward/app/";

    private RetrofitUtil() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(httpInterceptor);
        httpClientBuilder.connectTimeout(timeout, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(timeout, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(timeout, TimeUnit.SECONDS);
        OkHttpClient httpClient = httpClientBuilder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(NoticeConfig.isIsRelease() ?releaseBaseUrl:testBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();
        apiService = mRetrofit.create(ApiService.class);
    }

    public static RetrofitUtil getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitUtil.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitUtil();
                }
            }
        }
        return sInstance;
    }


    public ApiService getApiService() {
        return apiService;
    }

    private Interceptor httpInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json; charset=UTF-8")
                    .build();

            //打印请求信息
            if (BuildConfig.DEBUG && request != null) {
                String strRequestInfo = String.format("Method:%s\nHeaders:%s\nUrl:%s\nBody:%s", request.method(), request.headers(), request.url(), getParam(request.body()));
//                LogUtils.log("NetRequest:",strRequestInfo);
            }
            return chain.proceed(request);
        }
    };

    public void requestMode(Call<ResponseBody> call, final DataCallBack dataCallBack) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null) {
                    try {
                        if (response.body() == null) {
                            dataCallBack.fail("", response.code());
                            return;
                        }
                        String result = response.body().string();
//                        LogUtils.log("NetResponse",result);//调试接口用
                        if (BuildConfig.DEBUG) {
//                            LogUtils.log("NetResponse:",result);
                        }
                        try {
                            JSONObject resultJson = new JSONObject(result);
                            if (resultJson.getInt("status") == 0) {
                                if (resultJson.has("data")) {
                                    dataCallBack.success(resultJson.getString("message"), resultJson.getString("data"));
                                }
                                else{
                                    dataCallBack.success(resultJson.getString("message"), "");
                                }
                                //给网络请求增加status为1时也回调sucess bq
                            }
                        } catch (JSONException e) {
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error = t.toString();
            }
        });
    }

    private String getParam(RequestBody requestBody) {
        if(requestBody !=null) {
            Buffer buffer = new Buffer();
            String logparm;
            try {
                requestBody.writeTo(buffer);
                logparm = buffer.readUtf8();
                logparm = URLDecoder.decode(logparm, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
            return logparm;
        }
        return "";
    }
}
