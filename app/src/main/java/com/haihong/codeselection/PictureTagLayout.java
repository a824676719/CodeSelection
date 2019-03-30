package com.haihong.codeselection;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;


/*
 * 加左右
 *
 * */
@SuppressLint("NewApi")
public class PictureTagLayout extends RelativeLayout implements OnTouchListener, View.OnClickListener {
    int startX;
    int startY;
    int startTouchViewLeft = 0;
    int startTouchViewTop = 0;
    private View touchView, clickView;
    private Context context;
    private int height;
    private int width;

    float xDown, yDown, xUp;
    private int mNum;
    private String user_position = "";

    public PictureTagLayout(Context context) {
        super(context, null);
    }

    public PictureTagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        init();
    }

    private void init() {
        this.setOnTouchListener(this);
    }

    //用作更新图片时操作
    public void setImage(Context context, int num) {
        this.context = context;
        this.mNum = num;
        user_position = "";
    }

    //开始的位置小于结束的位置 向左滑动
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getX();
                yDown = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:

                touchView = null;
                if (clickView != null) {
                    clickView = null;
                }
                startX = (int) event.getX();
                startY = (int) event.getY();
                if (hasView(startX, startY)) {//如果点击位置已经有了
                    startTouchViewLeft = touchView.getLeft();
                    startTouchViewTop = touchView.getTop();
                } else {
                    showPopup();
                }
                Log.i("******点击的位置--x-", startX + "*----y" + startY);
                break;

        }
        return true;
    }

    public void showPopup() {
        mNum++;
        addData(mNum);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    public void addData(int num) {
        if (hasView(startX, startY)) {//有view 就不添加到集合里
            startTouchViewLeft = touchView.getLeft();
            startTouchViewTop = touchView.getTop();
        } else {
            if (num < 5) {
                Log.i("addItem   点击的位置--x-", startX + "*----y" + startY);
                addItem(startX, startY, num + "");

                //完成的时候 返给主界面回调
                if (num == 4) {
                    //咱们再布局中的PictureTagLayout宽高为
                    //android:layout_width="600px"
                    //android:layout_height="300px"
                    //为了精确的拿到正确的坐标:
                    //第一个600为图片本身像素宽,第二个600为布局中设置的像素宽
                    //第一个300为图片本身像素宽,第二个300为布局中设置的像素宽
                    //有人会问为什么需要600/600呢不是刚好就是1吗?  其实这里只是为了表达,
                    //假如你布局当中设置的宽不是600的话,那就需要这么设置了,不然无法得到精确的坐标与后台匹配
                    user_position += startX * 600 / 600 + "," + startY * 300 / 300;


                    //因为这里没有走网络耗时操作,所以你们可能看不多第四个点击的效果,单机版谅解下
                    EventBus.getDefault().postSticky(user_position);
                    mNum = 0;
                    user_position = "";

                } else {
                    user_position += startX * 300 / 600 + "," + startY * 200 / 400 + "|";
                }

            }
        }
    }

    private void addItem(int x, int y, String share) {
        View view = null;
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        //第一次点击添加标签是  PictureTagView.Direction.Measure 让TagView自己测量方向
        view = new PictureTagView(getContext(), PictureTagView.Direction.Right, share);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        int w = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        height = view.getMeasuredHeight();
        width = view.getMeasuredWidth();
        //标签在右面 点击位置 x-标签宽度   右边的标签并不是以圆点开始的  而是以左边的wei
        params.leftMargin = x - width + 10;
        params.topMargin = y - height / 2;

        //上下位置在视图内
        if (params.topMargin <= 0) {
            params.topMargin = 0;
        } else if ((params.topMargin + height) > getHeight()) {
            params.topMargin = getHeight() - height;
        }
        if (params.leftMargin <= 0) {
            params.leftMargin = 0;
        }
        if (params.rightMargin >= getWidth()) {
            params.rightMargin = getWidth();
        }
        this.addView(view, params);
    }

    private boolean hasView(int x, int y) {
        //循环获取子view，判断xy是否在子view上，即判断是否按住了子view
        for (int index = 0; index < this.getChildCount(); index++) {
            View view = this.getChildAt(index);
            int left = (int) view.getX();
            int top = (int) view.getY();
            int right = view.getRight();
            int bottom = view.getBottom();
//			Toast.makeText(context, "已经有的---"+((PictureTagView)view).getShare()+"-x-"+left+"--y--"+top, Toast.LENGTH_SHORT).show();
            Rect rect = new Rect(left, top, right, bottom);
            boolean contains = rect.contains(x, y);
            //如果是与子view重叠则返回真,表示已经有了view不需要添加新view了
            if (contains) {
                touchView = view;
                touchView.bringToFront();
                return true;
            }

        }

        touchView = null;
        return false;
    }

}
