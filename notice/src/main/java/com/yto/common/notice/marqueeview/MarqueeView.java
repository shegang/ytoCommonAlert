package com.yto.common.notice.marqueeview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;
import android.widget.ViewFlipper;


import androidx.annotation.AnimRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yto.common.notice.MyWebView;
import com.yto.common.notice.R;
import com.yto.common.notice.api.DataCallBack;
import com.yto.common.notice.api.RetrofitUtil;
import com.yto.common.notice.entity.ComplexItemEntity;
import com.yto.common.notice.marqueeview.util.AnimationListenerAdapter;
import com.yto.common.notice.marqueeview.util.OnItemClickListener;
import com.yto.common.notice.marqueeview.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import dialog.BindViewHolder;
import dialog.OnBindViewListener;
import dialog.OnViewClickListener;
import dialog.SGDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by GongWen on 16/12/20.
 * 属性
 * ViewAnimator_inAnimation
 * ViewAnimator_outAnimation
 * ViewAnimator_animateFirstView
 * ViewFlipper_flipInterval
 * ViewFlipper_autoStart
 * MarqueeView_marqueeAnimDuration
 * <p>
 * 注意：
 * interval 必须大于 animDuration，否则视图将会重叠
 */

public class MarqueeView<T extends View, E> extends ViewFlipper implements Observer {
    protected MarqueeFactory<T, E> factory;
    private final int DEFAULT_ANIM_RES_IN = R.anim.in_bottom;
    private final int DEFAULT_ANIM_RES_OUT = R.anim.out_top;

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (getInAnimation() == null || getOutAnimation() == null) {
            setInAnimation(getContext(), DEFAULT_ANIM_RES_IN);
            setOutAnimation(getContext(), DEFAULT_ANIM_RES_OUT);
        }
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MarqueeView);
        if (a.hasValue(R.styleable.MarqueeView_marqueeAnimDuration)) {
            int animDuration = a.getInt(R.styleable.MarqueeView_marqueeAnimDuration, -1);
            getInAnimation().setDuration(animDuration);
            getOutAnimation().setDuration(animDuration);
        }

        a.recycle();
        setPadding(30,15,32,15);

        if(getBackground() == null){
            this.setBackgroundResource(R.drawable.bg_large_oval_radius);
        }
        setOnClickListener(onClickListener);
    }

    public void setAnimDuration(long animDuration) {
        if (getInAnimation() != null) {
            getInAnimation().setDuration(animDuration);
        }
        if (getOutAnimation() != null) {
            getOutAnimation().setDuration(animDuration);
        }
    }

    public void setInAndOutAnim(Animation inAnimation, Animation outAnimation) {
        setInAnimation(inAnimation);
        setOutAnimation(outAnimation);
    }

    public void setInAndOutAnim(@AnimRes int inResId, @AnimRes int outResId) {
        setInAnimation(getContext(), inResId);
        setOutAnimation(getContext(), outResId);
    }

    public void setMarqueeFactory(MarqueeFactory<T, E> factory) {
        this.factory = factory;
        factory.attachedToMarqueeView(this);
        refreshChildViews();
    }

    protected void refreshChildViews() {
        if (getChildCount() > 0) {
            removeAllViews();
        }
        List<T> mViews = factory.getMarqueeViews();
        for (int i = 0; i < mViews.size(); i++) {
            addView(mViews.get(i));
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == null) return;
        if (MarqueeFactory.COMMAND_UPDATE_DATA.equals(arg.toString())) {
            Animation animation = getInAnimation();
            if (animation != null && animation.hasStarted()) {
                animation.setAnimationListener(new AnimationListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        refreshChildViews();
                        if (animation != null) {
                            animation.setAnimationListener(null);
                        }
                    }
                });
            } else {
                refreshChildViews();
            }
        }
    }
    // <editor-fold desc="点击事件模块">

    private OnItemClickListener<T, E> onItemClickListener;

    private boolean isJustOnceFlag = true;
    private final OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            MyWebView.startActivity(v.getContext(), "https://www.baidu.com");
            if (onItemClickListener != null) {
                if (factory == null || Util.isEmpty(factory.getData()) || getChildCount() == 0) {
                    onItemClickListener.onItemClickListener(null, null, -1);
                    return;
                }
                final int displayedChild = getDisplayedChild();
                final E mData = factory.getData().get(displayedChild);
                onItemClickListener.onItemClickListener((T) getCurrentView(), mData, displayedChild);
            }
        }
    };

    @Override
    public void setOnClickListener(@Nullable View.OnClickListener l) {
        if (isJustOnceFlag) {
            super.setOnClickListener(l);
            isJustOnceFlag = false;
        } else {
            throw new UnsupportedOperationException("The setOnClickListener method is not supported,please use setOnItemClickListener method.");
        }
    }

    public void setOnItemClickListener(OnItemClickListener<T, E> mOnItemClickListener) {
        this.onItemClickListener = mOnItemClickListener;
    }
    // </editor-fold>
}
