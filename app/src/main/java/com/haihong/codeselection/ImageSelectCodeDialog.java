package com.haihong.codeselection;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.squareup.picasso.Target;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageSelectCodeDialog extends Dialog {

    private static final int DURATION = 700;
    protected long mDuration = DURATION;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_button)
    TextView tvButton;
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.v_main)
    RelativeLayout vMain;
    @BindView(R.id.img_select)
    PictureTagLayout imgSelect;
    @BindView(R.id.text_hint)
    TextView textHint;
    @BindView(R.id.tv_changeone)
    TextView tvChangeone;

    private Context mContext;
    private ImageSelectBean.Data mCode;
    private ImageCodeDialogListener mListener;
    private boolean mIsLoad;
    private Dialog m_Dialog;
    private String vaild_position;
    private String msg;

    public interface ImageCodeDialogListener {
        void onConfirm(ImageSelectBean.Data code);
    }

    public void setImageCodeDialogListener(ImageCodeDialogListener listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((WindowManager.LayoutParams) params);

        //注册事件
        EventBus.getDefault().register(this);
    }

    //正式环境下用的应该是网络图片,此时只需要在此传入参数再进行初始化即可
    public ImageSelectCodeDialog(final @NonNull Context context) {
        super(context, R.style.dialog_untran);
        this.setContentView(R.layout.dialog_imageselect);
        ButterKnife.bind(this);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
        this.setOwnerActivity((Activity) context);
        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                showEnter();
            }
        });
        this.mContext = context;

        initView(mCode);
        this.show();
    }

    private void initView(ImageSelectBean.Data data) {
        //初始化网络图片
//        imgSelect.removeAllViews();
//        imgSelect.setImage(mContext, 0);
//        Picasso.with(mContext).load(data.getUrl()).into(new Target() {
//
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    imgSelect.setBackground(new BitmapDrawable(mContext.getResources(), bitmap));
//                }
//            }
//
//            @Override
//            public void onBitmapFailed(final Drawable errorDrawable) {
//            }
//
//            @Override
//            public void onPrepareLoad(final Drawable placeHolderDrawable) {
//            }
//        });

        Log.e("Picasso >>>", "Picasso");
    }

    private void showEnter() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(vMain, "translationY", -1500, 0, -200, 0, -100, 0, -40, 0).setDuration(mDuration),
                ObjectAnimator.ofFloat(vMain, "alpha", 0, 1).setDuration(mDuration / 2)
        );
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }

    private void showOut() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(vMain, "translationY", 0, -1000),
                ObjectAnimator.ofFloat(vMain, "alpha", 1, 0)
        );
        set.setDuration(mDuration / 2);
        set.setInterpolator(new AnticipateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                disMissDialog();
            }
        });
        set.start();
    }

    private void disMissDialog() {
        if (this != null) {
            this.dismiss();
            this.cancel();
        }
    }

    @OnClick({R.id.tv_button, R.id.img_close, R.id.v_main, R.id.tv_changeone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_changeone:

                break;
            case R.id.tv_button:

                break;
            case R.id.img_close:

                showOut();

                break;
            case R.id.v_main:

                showOut();

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void VaildCode(String vaild_position) {
        //在此向后台验证
        Toast.makeText(mContext, vaild_position, Toast.LENGTH_LONG).show();
        imgSelect.removeAllViews();
    }

}