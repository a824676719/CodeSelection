package com.haihong.codeselection;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;


/**
 * 带左右方向
 * */
public class PictureTagView extends RelativeLayout implements OnEditorActionListener {

    private Context context;
    private View view;
    private TextView tvNum;

    public enum Direction {Left, Right, Measure}

    private Direction direction;
    private InputMethodManager imm;
    private String type;
    private String share;

    public PictureTagView(Context context) {
        super(context);

    }

    public PictureTagView(Context context, Direction direction, String share) {
        super(context);
        this.context = context;
        this.direction = direction;
        this.share = share;
        initViews();
        init();
    }

    /**
     * 初始化视图
     **/
    protected void initViews() {
        view = LayoutInflater.from(context).inflate(R.layout.picturetagview, this, true);

        tvNum = (TextView) view.findViewById(R.id.tvNum);
        tvNum.setText(share);
    }

    /**
     * 初始化
     **/
    protected void init() {
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

}