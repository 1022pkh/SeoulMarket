package com.project.seoulmarket.recruit.register;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.seoulmarket.R;
import com.project.seoulmarket.dialog.DialogCancel;
import com.project.seoulmarket.dialog.DialogRegister;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecruitRegisterActivity extends AppCompatActivity {

    @BindView(R.id.inputTitleEdit)
    EditText inputTitleEdit;
    @BindView(R.id.inputContentEdit)
    EditText inputContentEdit;

    DialogCancel dialogCancel;
    DialogRegister dialog_Register;

    Boolean emptyTitleCheck = false;
    Boolean emptyContentCheck = false;

    String currentDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit_register);
        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#F6D03F"));
        }

        ButterKnife.bind(this);


        /**
         * actionbar 설정
         */

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // ActionBar의 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));

        getSupportActionBar().setElevation(0); // 그림자 없애기

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.actionbar_close_layout, null);

        ImageView closeBtn = (ImageView) mCustomView.findViewById(R.id.closeBtn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warningOut();
            }
        });


        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        /**
         *
         */

        // 시스템으로부터 현재시간(ms) 가져오기
        long now = System.currentTimeMillis();
        // Data 객체에 시간을 저장한다.
        Date date = new Date(now);
        // 각자 사용할 포맷을 정하고 문자열로 만든다.
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        currentDate = dateFormat.format(date);

    }

    @OnClick(R.id.inputContentArea)
    public void focusContentArea(){
        inputContentEdit.hasFocus();
    }

    @OnClick(R.id.completeRecruit)
    public void compelteRecruit(){
        titleEmptyCheck();
        contentEmptyCheck();

        if(emptyTitleCheck && emptyContentCheck){

            Log.i("myTag",inputTitleEdit.getText().toString());
            Log.i("myTag",inputContentEdit.getText().toString());
            Log.i("myTag",currentDate);

            WindowManager.LayoutParams registerParams;
            dialog_Register = new DialogRegister(RecruitRegisterActivity.this, registerEvent,registerCancelEvent);

            registerParams = dialog_Register.getWindow().getAttributes();

            // Dialog 사이즈 조절 하기
            registerParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            registerParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            dialog_Register.getWindow().setAttributes(registerParams);

            dialog_Register.show();
        }
    }

    private View.OnClickListener registerEvent = new View.OnClickListener() {
        public void onClick(View v) {
            dialog_Register.dismiss();
            Toast.makeText(getApplicationContext(),"셀러 모집 등록 완료!",Toast.LENGTH_SHORT).show();
            /**
             * 성공시 돌아간다.
             */
            finish();
        }

    };

    private View.OnClickListener registerCancelEvent = new View.OnClickListener() {
        public void onClick(View v) {
            dialog_Register.dismiss();
        }

    };


    @Override
    public void onBackPressed() {
        warningOut();
    }

    public void warningOut(){
        WindowManager.LayoutParams loginParams;
        dialogCancel = new DialogCancel(RecruitRegisterActivity.this, moveRecruitPage, remainPageEvent);

        loginParams = dialogCancel.getWindow().getAttributes();

        // Dialog 사이즈 조절 하기
        loginParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        loginParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogCancel.getWindow().setAttributes(loginParams);
        dialogCancel.show();
    }

    private View.OnClickListener moveRecruitPage = new View.OnClickListener() {
        public void onClick(View v) {
            dialogCancel.dismiss();
            finish();
        }

    };

    private View.OnClickListener remainPageEvent = new View.OnClickListener() {
        public void onClick(View v) {
            dialogCancel.dismiss();
        }

    };

    public void titleEmptyCheck(){
        if(inputTitleEdit.length() == 0) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(inputTitleEdit.getWindowToken(), 0);
            inputTitleEdit.setError(getString(R.string.error_field_required));
            emptyTitleCheck = false;
        }
        else
            emptyTitleCheck = true;
    }

    public void contentEmptyCheck(){
        if(inputContentEdit.length() == 0) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(inputContentEdit.getWindowToken(), 0);
            inputContentEdit.setError(getString(R.string.error_field_required));
            emptyContentCheck = false;
        }
        else
            emptyContentCheck = true;

    }


}
