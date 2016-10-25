package com.project.seoulmarket.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.matthewtamlin.sliding_intro_screen_library.DotIndicator;
import com.project.seoulmarket.R;
import com.project.seoulmarket.application.GlobalApplication;
import com.project.seoulmarket.detail.maps.MapsActivity;
import com.project.seoulmarket.detail.model.ReviewData;
import com.project.seoulmarket.detail.model.Result;
import com.project.seoulmarket.detail.presenter.DetailPresenter;
import com.project.seoulmarket.detail.presenter.DetailPresenterImpl;
import com.project.seoulmarket.detail.presenter.ViewpagerAdapter;
import com.project.seoulmarket.detail.review.RegisterReviewActivity;
import com.project.seoulmarket.dialog.DialogLogin;
import com.project.seoulmarket.login.LoginActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity implements DetailView{

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.pager_indicator)
    DotIndicator indicator;
    @BindView(R.id.basicInfo)
    LinearLayout basicInfoArea;
    @BindView(R.id.reviewInfo)
    LinearLayout reviewInfoArea;
    @BindView(R.id.contentArea)
    LinearLayout inflatedLayout;
    @BindView(R.id.likehHeart)
    ImageView likeHeart;
    @BindView(R.id.marketTag)
    TextView marketTag;

    @BindView(R.id.marketName)
    TextView marketName;
    @BindView(R.id.finderName)
    TextView finderName;
    //tag정보
    @BindView(R.id.marketLocation)
    TextView marketLocation;
    @BindView(R.id.likeCount)
    TextView likeCount;


    Boolean heartCheck = false;
    ArrayList<String> imgUrl;
    DialogLogin dialog_login;
    DetailPresenter presenter;

    String marketId = "";

    String mName="";
    String mTag = "";
    String mStartTime="";
    String mEndTime="";
    String mStartDate="";
    String mEndDate="";
    String mContent="";
    String mHost="";
    String mAddress="";
    String longitude="";
    String latitude="";
    String mFavorite="";
    int heartStat = -1;
    String mURL="";

    ArrayList<ReviewData> reviewDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#F6D03F"));
        }


        presenter = new DetailPresenterImpl(this);
        imgUrl = new ArrayList<String>();
        /**
         * get id
         */

        Intent intent = getIntent();
        marketId = intent.getExtras().getString("market_id");

        Log.i("myTag",marketId);


        /**
         * getData
         */
        presenter.getDetail(marketId);

    }

    @OnClick(R.id.likehHeart)
    public void likeHeartEvent(){

        if(GlobalApplication.loginInfo.getBoolean("Login_check", false)) {

            if(heartCheck == false) {
                Log.i("myTag","request like");
                presenter.requestLikeFavorite(marketId);
            }
            else {
                Log.i("myTag","request delete");
                presenter.requestDeleteFavorite(marketId);
            }
        }
        else{
            WindowManager.LayoutParams loginParams;
            dialog_login = new DialogLogin(DetailActivity.this, loginEvent,loginCancelEvent);

            loginParams = dialog_login.getWindow().getAttributes();

            // Dialog 사이즈 조절 하기
            loginParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            loginParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            dialog_login.getWindow().setAttributes(loginParams);

            dialog_login.show();
        }


    }

    @OnClick(R.id.showLocation)
    public void moveLocationPage(){
        /**
         * 넘길때
         * 마켓이름 , 주소 , 위도 , 경도 보내야함
         */

        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        intent.putExtra("name",mName);
        intent.putExtra("address",mAddress);
        intent.putExtra("longitude",longitude);
        intent.putExtra("latitude",latitude);

        startActivity(intent);
    }

    @OnClick(R.id.marketReview)
    public void moveRigisterReview(){

        if(GlobalApplication.loginInfo.getBoolean("Login_check", false)) {
            Intent intent = new Intent(getApplicationContext(), RegisterReviewActivity.class);
            startActivity(intent);
        }
        else{
            WindowManager.LayoutParams loginParams;
            dialog_login = new DialogLogin(DetailActivity.this, loginEvent,loginCancelEvent);

            loginParams = dialog_login.getWindow().getAttributes();

            // Dialog 사이즈 조절 하기
            loginParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            loginParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            dialog_login.getWindow().setAttributes(loginParams);

            dialog_login.show();
        }


    }

    private View.OnClickListener loginEvent = new View.OnClickListener() {
        public void onClick(View v) {
            dialog_login.dismiss();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

    };

    private View.OnClickListener loginCancelEvent = new View.OnClickListener() {
        public void onClick(View v) {
            dialog_login.dismiss();
        }

    };

    @OnClick(R.id.basicInfo)
    public void changeBasicArea(){
        basicInfoArea.setBackgroundColor(Color.rgb(64, 84, 178));
        reviewInfoArea.setBackgroundColor(Color.rgb(226, 202, 174));


        inflatedLayout.removeAllViews();

        /**
         * 미리 정보를 받아와서 객체로 저장해놓고 있어야함!
         */
        LayoutInflater inflater =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflated_Layout.xml로 구성한 레이아웃을 inflatedLayout 영역으로 확장
        inflater.inflate(R.layout.content_basicinfo, inflatedLayout);

        TextView progressGroup =  (TextView)findViewById(R.id.progressGroup);
        TextView progressDate = (TextView)findViewById(R.id.progressDate);
        TextView progressTime =  (TextView)findViewById(R.id.progressTime);
        TextView marketKind = (TextView)findViewById(R.id.marketKind);
        TextView marketContent =  (TextView)findViewById(R.id.marketContent);
        TextView marketURL = (TextView)findViewById(R.id.marketURL);

        progressGroup.setText(mHost);
        progressDate.setText(mStartDate+"~"+mEndDate);
        progressTime.setText(mStartTime+"~"+mEndTime);
        marketKind.setText("오픈마켓 형식");
        marketContent.setText(mContent);
        marketURL.setText(mURL);


        marketURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent page = new Intent(Intent.ACTION_VIEW, Uri.parse(mURL));
                startActivity(page);
            }
        });

    }



    @OnClick(R.id.reviewInfo)
    public void changeReviewArea(){
        reviewInfoArea.setBackgroundColor(Color.rgb(64, 84, 178));
        basicInfoArea.setBackgroundColor(Color.rgb(226, 202, 174));

        inflatedLayout.removeAllViews();

        /**
         * 미리 정보를 받아와서 객체로 저장해놓고 있어야함!
         */

        LayoutInflater inflater =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflated_Layout.xml로 구성한 레이아웃을 inflatedLayout 영역으로 확장
        inflater.inflate(R.layout.content_reviewinfo, inflatedLayout);

        //부모 뷰
        LinearLayout listview = (LinearLayout)findViewById(R.id.reviewList);

        LayoutInflater child;
        LinearLayout childLayout;

        TextView reviewNickname;
        TextView reviewDate;
        TextView reviewContent;

        for(int i=0; i<reviewDatas.size();i++){

            child = (LayoutInflater) getSystemService (Context.LAYOUT_INFLATER_SERVICE);
            childLayout = (LinearLayout) child.inflate(R.layout.detail_list_review_item, null);

            reviewNickname =  (TextView)childLayout.findViewById(R.id.reviewNickname);
            reviewDate = (TextView)childLayout.findViewById(R.id.reviewDate);
            reviewContent =  (TextView)childLayout.findViewById(R.id.reviewContent);

            reviewNickname.setText(reviewDatas.get(i).user_nickname);
            reviewDate.setText(reviewDatas.get(i).review_uploadtime);
            reviewContent.setText(reviewDatas.get(i).review_contents);

            listview.addView(childLayout);

        }

    }

    @Override
    public void setDetailData(Result itemDatas) {
        marketName.setText(itemDatas.market_name);

        marketTag.setText(itemDatas.market_tag.replace(","," "));

        finderName.setText(itemDatas.user_nickname);
        marketLocation.setText(itemDatas.market_address);
        likeCount.setText(itemDatas.market_count);

        for(int i=0;i<itemDatas.image.size();i++){
            imgUrl.add(itemDatas.image.get(i).img_url);
//                Log.i("myTag", itemDatas.image.get(i).img_url);
        }

        ViewpagerAdapter adapter= new ViewpagerAdapter(getLayoutInflater(),imgUrl);

        //ViewPager에 Adapter 설정
        pager.setAdapter(adapter);


        /**
         * 도트 색 지정
         */
        indicator.setSelectedDotColor( Color.parseColor( "#F96332" ) );
        indicator.setUnselectedDotColor( Color.parseColor( "#CFCFCF" ) );

        /**
         * indicator 초기화
         */
        indicator.setNumberOfItems( imgUrl.size());


        /**
         * 스크롤 등으로 다음 페이지로 넘어갈 때 도트도 옮김
         */
        pager.addOnPageChangeListener( new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels )
            {

            }

            @Override
            public void onPageSelected( int position )
            {
                indicator.setSelectedItem( pager.getCurrentItem(), true );
            }

            @Override
            public void onPageScrollStateChanged( int state )
            {

            }
        } );




        mName = itemDatas.market_name;
        mTag = itemDatas.market_tag;
        mStartTime = itemDatas.market_openTime;
        mEndTime = itemDatas.market_endTime;
        mStartDate = itemDatas.market_startdate;
        mEndDate = itemDatas.market_enddate;
        mContent = itemDatas.market_contents;
        mHost = itemDatas.market_host;
        mAddress = itemDatas.market_address;
        longitude = itemDatas.market_longitude;
        latitude = itemDatas.market_latitude;
        mURL = itemDatas.market_url;

        reviewDatas = new ArrayList<ReviewData>();
        reviewDatas.addAll(itemDatas.review);

        mFavorite = itemDatas.favorite;
        heartStat =  Integer.valueOf(mFavorite);

        if(heartStat == -1 || heartStat == 0) {
            likeHeart.setImageResource(R.drawable.ic_heart_big_blank);
            heartCheck = false;
        }
        else{
            likeHeart.setImageResource(R.drawable.ic_heart_big);
            heartCheck = true;
        }

        changeBasicArea();

    }

    @Override
    public void setLikeHeart() {
        likeHeart.setImageResource(R.drawable.ic_heart_big);
        int temp = Integer.valueOf(likeCount.getText().toString());
        likeCount.setText(String.valueOf(++temp));

        heartCheck = true;
    }
    @Override
    public void setDeleteHeart() {
        likeHeart.setImageResource(R.drawable.ic_heart_big_blank);
        int temp = Integer.valueOf(likeCount.getText().toString());
        likeCount.setText(String.valueOf(--temp));
        heartCheck = false;
    }
}
