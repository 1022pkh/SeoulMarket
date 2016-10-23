package com.project.seoulmarket.recruit.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.project.seoulmarket.R;
import com.project.seoulmarket.recruit.model.RecruitReviewDetailData;
import com.project.seoulmarket.recruit.presenter.RecruitReviewAdapter;

import java.util.ArrayList;

public class RecruitReviewActivity extends AppCompatActivity {

    ListView listview;
    RecruitReviewAdapter adapter;
    ArrayList<RecruitReviewDetailData> reviewDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_review);

        /**
         *
         */
        Intent intent = getIntent();
        String id = intent.getExtras().getString("recruitId");

        Log.i("myTag",id);

        //어뎁터 생성

        //리스트뷰 참조 및 어뎁터 담기
        listview = (ListView)findViewById(R.id.listview_comment);

        reviewDatas = new ArrayList<RecruitReviewDetailData>();

        RecruitReviewDetailData data = new RecruitReviewDetailData("박경현", "2016.10.22 17:11","댓글1");
        reviewDatas.add(data);
        data = new RecruitReviewDetailData("이지희", "2016.10.22 17:11","댓글1");
        reviewDatas.add(data);
        data = new RecruitReviewDetailData("정나영", "2016.10.22 17:11","댓글1");
        reviewDatas.add(data);

        adapter = new RecruitReviewAdapter(reviewDatas,getApplicationContext());
        listview.setAdapter(adapter);
    }
}
