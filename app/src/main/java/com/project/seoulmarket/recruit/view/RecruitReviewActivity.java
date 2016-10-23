package com.project.seoulmarket.recruit.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;
import com.project.seoulmarket.R;
import com.project.seoulmarket.recruit.model.RecruitReviewDetailData;
import com.project.seoulmarket.recruit.presenter.RecruitReviewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecruitReviewActivity extends AppCompatActivity {

    @BindView(R.id.reviewAddBtn)
    Button reviewAddBtn;
    @BindView(R.id.inputReviewAddEdit)
    EditText inputReviewAddEdit;
    @BindView(R.id.reviewList)
    LinearLayout listview;
    @BindView(R.id.reviewTitle)
    TextView reviewTitle;
    @BindView(R.id.sticky_scroll)
    StickyScrollView sticky_scroll;

    InputMethodManager imm;
    RecruitReviewAdapter adapter;
    ArrayList<RecruitReviewDetailData> reviewDatas;

    String nickname="pkh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit_detail);

        /**
         *
         */

        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#F6D03F"));
        }

        ButterKnife.bind(this);


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

        Intent intent = getIntent();
        String recruitId = intent.getExtras().getString("recruitId");

        Log.i("myTag",recruitId);


        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);


        reviewDatas = new ArrayList<RecruitReviewDetailData>();

        RecruitReviewDetailData data = new RecruitReviewDetailData("박경현", "2016.10.22 17:11","댓글1");
        reviewDatas.add(data);
        data = new RecruitReviewDetailData("박경현", "2016.10.22 17:11","댓글1");
        reviewDatas.add(data);
        data = new RecruitReviewDetailData("박경현", "2016.10.22 17:11","댓글1");
        reviewDatas.add(data);
        data = new RecruitReviewDetailData("박경현", "2016.10.22 17:11","댓글1");
        reviewDatas.add(data);
        data = new RecruitReviewDetailData("박경현", "2016.10.22 17:11","댓글1");
        reviewDatas.add(data);
        data = new RecruitReviewDetailData("박경현", "2016.10.22 17:11","댓글1");
        reviewDatas.add(data);
        data = new RecruitReviewDetailData("박경현", "2016.10.22 17:11","댓글1");
        reviewDatas.add(data);
        data = new RecruitReviewDetailData("박경현", "2016.10.22 17:11","댓글1");
        reviewDatas.add(data);

//
//        ListView listView = (ListView)findViewById(R.id.reviewList);
//        adapter = new RecruitReviewAdapter(reviewDatas,getApplicationContext());
//        listView.setAdapter(adapter);

        /**
         * 미리 정보를 받아와서 객체로 저장해놓고 있어야함!
         */

        //부모 뷰
        LinearLayout listview = (LinearLayout)findViewById(R.id.reviewList);

        LayoutInflater child;
        LinearLayout childLayout;

        TextView reviewNickname;
        TextView reviewContent;
        TextView reviewDate;

        for(int i=0; i<reviewDatas.size();i++){

            child = (LayoutInflater) getSystemService (Context.LAYOUT_INFLATER_SERVICE);
            childLayout = (LinearLayout) child.inflate(R.layout.review_detail_list_review_item, null);

            reviewNickname =  (TextView)childLayout.findViewById(R.id.reviewNickname);
            reviewDate = (TextView)childLayout.findViewById(R.id.reviewDate);
            reviewContent =  (TextView)childLayout.findViewById(R.id.reviewContent);

            reviewNickname.setText(reviewDatas.get(i).name);
            reviewDate.setText(reviewDatas.get(i).date);
            reviewContent.setText(reviewDatas.get(i).content);

            listview.addView(childLayout);

        }

    }

    @OnClick(R.id.reviewAddBtn)
    public void addReview(){


        if(inputReviewAddEdit.getText().length() != 0) {


            // 시스템으로부터 현재시간(ms) 가져오기
            long now = System.currentTimeMillis();
            // Data 객체에 시간을 저장한다.
            Date date = new Date(now);
            // 각자 사용할 포맷을 정하고 문자열로 만든다.
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            String currentDate = dateFormat.format(date);

            //부모 뷰
//            LinearLayout listview = (LinearLayout) findViewById(R.id.reviewList);

            LayoutInflater child;
            LinearLayout childLayout;

            TextView reviewNickname;
            TextView reviewContent;
            TextView reviewDate;

            child = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            childLayout = (LinearLayout) child.inflate(R.layout.review_detail_list_review_item, null);

            reviewNickname = (TextView) childLayout.findViewById(R.id.reviewNickname);
            reviewDate = (TextView) childLayout.findViewById(R.id.reviewDate);
            reviewContent = (TextView) childLayout.findViewById(R.id.reviewContent);

            reviewNickname.setText(nickname);
            reviewDate.setText(currentDate);
            reviewContent.setText(inputReviewAddEdit.getText().toString());

            listview.addView(childLayout);

            inputReviewAddEdit.setText("");
            imm.hideSoftInputFromWindow(inputReviewAddEdit.getWindowToken(), 0);

            sticky_scroll.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }
}
