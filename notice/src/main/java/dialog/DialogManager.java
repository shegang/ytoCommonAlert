package dialog;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.yto.common.notice.R;

public class DialogManager {
    private FragmentManager fragmentManager;

    public DialogManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void init(final Context context){
        new SGDialog.Builder(fragmentManager)
                .setLayoutRes(R.layout.dialog_home_ad)
                .setScreenHeightAspect(context, 0.7f)
                .setScreenWidthAspect(context, 0.8f)
                .setOnBindViewListener(new OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder viewHolder) {

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
}
