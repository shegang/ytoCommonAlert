package dialog;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.yto.common.notice.R;
import com.yto.common.notice.api.DataCallBack;
import com.yto.common.notice.api.RetrofitUtil;
import com.yto.common.notice.api.requestparameter.RequestParameter;
import com.yto.common.notice.entity.PopAnnounceData;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class DialogManager {

    private String htmlText="<p>美国《国会山报》11月5日消息，特朗普和拜登竞选团队于当地时间周三向支持者发起募捐请求，双方竞选团队的募捐方式“截然不同”。</p><p>特朗普竞选团队发出一连串的电子邮件和短信，敦促收件人捐款，同时指责民主党试图窃取选举。一封邮件的主题是“他们将试图窃取选举”。另一封邮件的主题是“就像我预测的那样”，其中包含以下信息：“我需要你们的帮助，以确保我们有资源保护选举结果。我们不能让左翼暴徒破坏我们的选举。我要求我最激烈和最忠诚的捍卫者，比如你们，展开反击！”</p><p>拜登的募捐主题则是“确保每一张选票都被计算在内”。拜登早些时候在推特发文称：“特朗普和我都不能决定这次选举的结果。结果由美国人民决定。这就是为什么我们发起了‘拜登斗争基金’——确保每一张选票都被计算在内。”</p>";

    private FragmentManager fragmentManager;
    private Context context;

    private RequestParameter parameter;

    public DialogManager(FragmentManager fragmentManager,Context context) {
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    public void init(final Context context){
//        requestData(context);

    }

    private void requestData() {
//        RequestParameter parameter = new RequestParameter();
//        parameter.setAppCode("bc7c151dbe8c45c8a3ce486d64d76bd7");
//        parameter.setAppSecret("2690d6b105");
//        parameter.setUserCode("01653893");
//        parameter.setUserName("曾超");
        Call<ResponseBody> call = RetrofitUtil.getInstance().getApiService().getPopAnnounceList(parameter);
        RetrofitUtil.getInstance().requestMode(call, new DataCallBack() {
            @Override
            public void success(String msg, String result) {
                Gson gson = new Gson();
                final PopAnnounceData popAnnounceData = gson.fromJson(result,PopAnnounceData.class);

                new SGDialog.Builder(fragmentManager)
                        .setLayoutRes(R.layout.dialog_home_ad)
                        .setScreenHeightAspect(context, 0.7f)
                        .setScreenWidthAspect(context, 0.8f)
                        .setCancelableOutside(false)
                        .setOnBindViewListener(new OnBindViewListener() {
                            @Override
                            public void bindView(BindViewHolder viewHolder) {
                               RelativeLayout view =  viewHolder.getView(R.id.rl);
                               TextView textViewContent = view.findViewById(R.id.tv_a_content);
                               TextView textViewTitle = view.findViewById(R.id.tv_title);
                                textViewContent.setText(Html.fromHtml(htmlText));
                               if(popAnnounceData != null && popAnnounceData.getAnnounce() != null){
                                   textViewTitle.setText(popAnnounceData.getAnnounce().getAnnounceName());
                                   htmlText = popAnnounceData.getAnnounce().getContent()==null?"":popAnnounceData.getAnnounce().getContent();
//                                   textView.setText(Html.fromHtml(popAnnounceData.getAnnounce().getContent()==null?"":popAnnounceData.getAnnounce().getContent()));
                                   textViewContent.setText(Html.fromHtml(htmlText));
                               }

                            }
                        })
                        .addOnClickListener(R.id.iv_close,R.id.iv_icon)
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, SGDialog tDialog) {
                                if(view.getId() == R.id.iv_icon){
                                    //可对图片进行修改
                                    Toast.makeText(context,"点击了图片",Toast.LENGTH_LONG).show();
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

    public static class Builder{
        DialogManager dialogManager;
        public Builder(FragmentManager fragmentManager,Context context){
            dialogManager = new DialogManager(fragmentManager,context);
        }

        public Builder setParameter(RequestParameter parameter){
            dialogManager.parameter = parameter;
            return this;
        }

        public DialogManager create(){
            dialogManager.requestData();
            return dialogManager;
        }
    }
}
