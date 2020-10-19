package com.yto.common.notice.api;




import com.yto.common.notice.BuildConfig;

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
    private String baseUrl = "https://api.apiopen.top/";

//    private final String baseUrl = "http://192.168.201.67:8081/steward/app/";
    private RetrofitUtil() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(httpInterceptor);
        httpClientBuilder.connectTimeout(timeout, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(timeout, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(timeout, TimeUnit.SECONDS);
        OkHttpClient httpClient = httpClientBuilder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
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
                    .addHeader("Authorization", "")
                    .build();

            //打印请求信息
            if (BuildConfig.DEBUG && request != null) {
                String strRequestInfo = String.format("Method:%s\nHeaders:%s\nUrl:%s\nBody:%s", request.method(), request.headers(), request.url(), getParam(request.body()));
//                LogUtils.log("NetRequest:",strRequestInfo);
            }
            //https://blog.csdn.net/zhang_110326/article/details/89217373参考
//            okhttp3.Response response = chain.proceed(request);//这个不能在这里调用，因为会导致接口请求出现两次
//            //返回信息
//            ResponseBody responseBody = response.body();
//            BufferedSource source = responseBody.source();
//            source.request(Long.MAX_VALUE); // Buffer the entire body.
//            Buffer buffer = source.buffer();
//            Charset charset = UTF8;
//            MediaType contentType = responseBody.contentType();
//            if (contentType != null) {
//                charset = contentType.charset(UTF8);
//            }
//            //打印响应结果
//            if (BuildConfig.DEBUG) {
//                String bodyString = buffer.clone().readString(charset);
//                Log.d("NetResponse:",bodyString);
//            }

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
                            if (resultJson.getInt("code") == 200) {
                                if (resultJson.has("result")) {
                                    dataCallBack.success(resultJson.getString("message"), resultJson.getString("result"));
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
