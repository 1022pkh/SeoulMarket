package com.project.seoulmarket.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.seoulmarket.R;
import com.project.seoulmarket.mypage.view.MyPageView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by KyoungHyun on 16. 5. 1..
 */
public class DialogOther extends Dialog {

    @BindView(R.id.completeBtn)
    Button completeBtn;
    @BindView(R.id.cancelBtn)
    Button cancelBtn;
    @BindView(R.id.msgTitile)
    TextView msgTitile;

    MyPageView view;
    int position;
    String mId;

    private View.OnClickListener completeBtnEvent;
    private View.OnClickListener cancelBtnEvnet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_other);

        ButterKnife.bind(this);
        completeBtn.setOnClickListener(completeBtnEvent);
        cancelBtn.setOnClickListener(cancelBtnEvnet);


    }

    public void onBackPressed() {
//        Log.i("myTag","cancel");
        view.cancelDialog();
    }

    public DialogOther(Context context, View.OnClickListener complete, View.OnClickListener cancel, MyPageView view, int position, String mId) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.completeBtnEvent = complete;
        this.cancelBtnEvnet = cancel;
        this.position = position;
        this.mId = mId;

        this.view = view;

    }

}

