package dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.yto.common.notice.MyWebView;
import com.yto.common.notice.R;
import com.yto.common.notice.api.DataCallBack;
import com.yto.common.notice.api.DataSignatureUtils;
import com.yto.common.notice.api.RetrofitUtil;
import com.yto.common.notice.api.requestparameter.CommonParameter;
import com.yto.common.notice.api.requestparameter.LogisticsInterfaceParameter;
import com.yto.common.notice.api.requestparameter.RequestParameter;
import com.yto.common.notice.entity.PopAnnounceData;
import com.yto.common.notice.util.DateUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class DialogManager {

    private FragmentManager fragmentManager;
    private Context context;

    private RequestParameter parameter;

    private String titleBarColor;
    private String titleColor;
    private String backImageColor;
    private boolean isRelease = true;

    public DialogManager(FragmentManager fragmentManager, Context context) {
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    public void init(final Context context) {
//        requestData(context);

    }

    private void requestData() {
//        RequestParameter parameter = new RequestParameter();
//        parameter.setAppCode("bc7c151dbe8c45c8a3ce486d64d76bd7");
//        parameter.setAppSecret("2690d6b105");
//        parameter.setUserCode("01653893");
//        parameter.setUserName("曾超");
        LogisticsInterfaceParameter logisticsInterfaceParameter = new LogisticsInterfaceParameter();
        logisticsInterfaceParameter.setUserCode(parameter.getUserCode());
        logisticsInterfaceParameter.setToken(parameter.getToken());
        logisticsInterfaceParameter.setSource(parameter.getSource());
        Gson gson = new Gson();
        String jsonStr = gson.toJson(logisticsInterfaceParameter);
        String currentDate = DateUtil.getStringByFormat(System.currentTimeMillis(),"yyyyMMddHHmmss");
//        String requestParameter = jsonStr+parameter.getAppSecret()+System.currentTimeMillis();
        String dataDigest = DataSignatureUtils.getDataSignature(jsonStr, parameter.getAppSecret(), currentDate);
        Log.i("requestParameter", dataDigest);
        CommonParameter commonParameter = new CommonParameter();
        commonParameter.setAppCode(parameter.getAppCode());
        commonParameter.setCurrentDate(currentDate);
        commonParameter.setDataDigest(dataDigest);
//        commonParameter.setCurrentDate("20210425162319");
//        commonParameter.setDataDigest("vAj/rV9I5vjQj8vnvkPSbg==");
        commonParameter.setLogisticsInterface(jsonStr);

        Call<ResponseBody> call = RetrofitUtil.getInstance().getApiService().getPopAnnounceList(commonParameter);
        RetrofitUtil.getInstance().requestMode(call, new DataCallBack() {
            @Override
            public void success(String msg, String result) {
                Gson gson = new Gson();
                final PopAnnounceData popAnnounceData = gson.fromJson(result, PopAnnounceData.class);
                if (popAnnounceData == null || popAnnounceData.getAnnounce() == null) {
                    return;
                }
                new SGDialog.Builder(fragmentManager)
                        .setLayoutRes(R.layout.dialog_announce)
//                        .setScreenHeightAspect(context, 0.7f)
                        .setScreenWidthAspect(context, 0.8f)
                        .setCancelableOutside(false)
                        .setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    if (popAnnounceData.getAnnounce().getCheckChoice().equals("1")) {//checkChoice查看选项1强制0不强制',
//                                        Toast.makeText(context, "强制按返回键无效", Toast.LENGTH_LONG).show();
                                        return true;
                                    }
                                }
                                return false;
                            }
                        })
                        .setOnBindViewListener(new OnBindViewListener() {
                            @Override
                            public void bindView(BindViewHolder viewHolder) {
                                LinearLayout view = viewHolder.getView(R.id.ll);
//                               TextView textViewContent = view.findViewById(R.id.tv_a_content);
                                TextView textViewTitle = view.findViewById(R.id.tv_title);
                                ImageView iv_close = viewHolder.bindView.findViewById(R.id.iv_close);
//                                textViewContent.setText(Html.fromHtml(htmlText));
                                if (popAnnounceData != null && popAnnounceData.getAnnounce() != null) {
                                    textViewTitle.setText(popAnnounceData.getAnnounce().getAnnounceName());
//                                   htmlText = popAnnounceData.getAnnounce().getContent()==null?"":popAnnounceData.getAnnounce().getContent();
//                                   textView.setText(Html.fromHtml(popAnnounceData.getAnnounce().getContent()==null?"":popAnnounceData.getAnnounce().getContent()));
//                                   textViewContent.setText(Html.fromHtml(htmlText));
                                    if (popAnnounceData.getAnnounce().getCheckChoice().equals("1")) {//checkChoice查看选项1强制0不强制',
                                        iv_close.setVisibility(View.GONE);
                                    } else {
                                        iv_close.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        })
                        .addOnClickListener(R.id.iv_close, R.id.bt_check)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, SGDialog tDialog) {
                                if (view.getId() == R.id.bt_check) {
                                    if (popAnnounceData != null) {
                                        if (popAnnounceData.getAnnounce() != null && !TextUtils.isEmpty(popAnnounceData.getAnnounce().getAnnounceName())) {
                                            MyWebView.startActivity(context, popAnnounceData.getDetailUrl(), popAnnounceData.getAnnounce().getAnnounceName(),
                                                    titleColor, titleBarColor, backImageColor);
                                        } else {
                                            MyWebView.startActivity(context, popAnnounceData.getDetailUrl(), "", titleColor, titleBarColor, backImageColor);
                                        }

                                    }

                                }
                                tDialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }

            @Override
            public void fail(String msg, int errorCode) {
                Log.e("error", msg);
            }
        });
    }

    public static class Builder {
        DialogManager dialogManager;

        public Builder(FragmentManager fragmentManager, Context context) {
            dialogManager = new DialogManager(fragmentManager, context);
        }

        public Builder setParameter(RequestParameter parameter) {
            dialogManager.parameter = parameter;
            return this;
        }

        public Builder setTitleBarBgColor(String color) {
            dialogManager.titleBarColor = color;
            return this;
        }

        public Builder setTitleColor(String color) {
            dialogManager.titleColor = color;
            return this;
        }

        public Builder setBackImageColor(String color) {
            dialogManager.backImageColor = color;
            return this;
        }

        public DialogManager create() {
            dialogManager.requestData();
            return dialogManager;
        }
    }
}
