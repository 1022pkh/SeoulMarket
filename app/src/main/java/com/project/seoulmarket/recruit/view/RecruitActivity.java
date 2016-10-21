package com.project.seoulmarket.recruit.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.seoulmarket.R;
import com.project.seoulmarket.recruit.model.RecruitData;
import com.project.seoulmarket.recruit.presenter.RecruitAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecruitActivity extends AppCompatActivity {

    @BindView(R.id.listview)
    ListView listView;

    RecruitAdapter recruitAdapter;
    private ArrayList<RecruitData> itemDatas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit);
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
        View mCustomView = mInflater.inflate(R.layout.actionbar_back_layout, null);

        TextView actionbarTitle = (TextView)mCustomView.findViewById(R.id.mytext);
        actionbarTitle.setText("셀러 모집");
        ImageView backBtn = (ImageView) mCustomView.findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        /**
         *
         */

        itemDatas = new ArrayList<RecruitData>();
        // itemDatas 들어갈 자료를 추가

        for(int i=0;i<15;i++){
            String userNickname = "pkh1022";
            String Cotent = "내가 만든 마켓이다 설명이 필요없다";
            String date = "2016-10-17";

            RecruitData listViewItem = new RecruitData(0,userNickname,Cotent,date);

            itemDatas.add(listViewItem);
        }

        // 들어갈 자료를 ListView에 지정
        recruitAdapter = new RecruitAdapter(itemDatas,getApplicationContext());
        listView.setAdapter(recruitAdapter);
    }
}
