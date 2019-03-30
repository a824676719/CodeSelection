package com.haihong.codeselection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_login)
    TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                //这里的bean实体只是之前正式项目的一个数据,暂时不把他去掉了
                ImageSelectCodeDialog dialog = new ImageSelectCodeDialog(MainActivity.this);
                dialog.setImageCodeDialogListener(new ImageSelectCodeDialog.ImageCodeDialogListener() {
                    @Override
                    public void onConfirm(ImageSelectBean.Data code) {
                        //回调操作
                    }
                });

                break;
        }
    }

}
