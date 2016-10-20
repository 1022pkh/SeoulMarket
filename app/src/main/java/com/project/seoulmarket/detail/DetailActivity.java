package com.project.seoulmarket.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.matthewtamlin.sliding_intro_screen_library.DotIndicator;
import com.project.seoulmarket.R;
import com.project.seoulmarket.detail.maps.MapsActivity;
import com.project.seoulmarket.detail.model.ReviewData;
import com.project.seoulmarket.detail.presenter.ViewpagerAdapter;
import com.project.seoulmarket.detail.review.RegisterReviewActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {

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


    ArrayList<String> imgUrl;

    ArrayList<ReviewData> reviewDatas = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#F6D03F"));
        }



        /**
         * 서버에서 받아온 이미지를 리스트에 넣어준다.
         */

        imgUrl = new ArrayList<String>();

        imgUrl.add("http://www.samsungfundblog.com/wp-content/uploads/2014/04/%ED%94%84%EB%A6%AC%EB%A7%88%EC%BC%93.jpg");
        imgUrl.add("http://www.samsungfundblog.com/wp-content/uploads/2014/04/IMG_0665-701x525.jpg");
        imgUrl.add("http://www.samsungfundblog.com/wp-content/uploads/2014/04/%EC%9A%B0%EC%82%AC%EB%8B%A8-%EB%A7%88%EC%9D%84.jpg");

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


        changeBasicArea();

        /**
         * review 탭은 바로 보이는게 아니기때문에 페이지 정보를 받아온 후
         * 그 다음 받아온다.
         */

        reviewDatas = new ArrayList<ReviewData>();

        /**
         * 임시로 데이터 넣어줌
         */
        ReviewData data = new ReviewData(0,"pkh1022","마켓에 다양한 물품이 많았어요~!!!","10/22 17:00");

        reviewDatas.add(data);
        data = new ReviewData(0,"pkh1022","마켓에 다양한 물품이 많았어요~!!!","10/22 17:00");
        reviewDatas.add(data);
        data = new ReviewData(1,"rudgus1022","마켓에 다양한 물품이 많았어요~!!!","10/22 17:00");
        reviewDatas.add(data);
        data = new ReviewData(2,"1022pkh","마켓에 다양한 물품이 많았어요~!!!","10/22 17:00");
        reviewDatas.add(data);
        data = new ReviewData(3,"cafe","마켓에 다양한 물품이 많았어요~!!!","10/22 17:00");
        reviewDatas.add(data);
        data = new ReviewData(4,"sejong","마켓에 다양한 물품이 많았어요~!!!","10/22 17:00");
        reviewDatas.add(data);
        data = new ReviewData(5,"sopt","마켓에 다양한 물품이 많았어요~!!!","10/22 17:00");
        reviewDatas.add(data);
        data = new ReviewData(6,"good6","마켓에 다양한 물품이 많았어요~!!!","10/22 17:00");
        reviewDatas.add(data);
        data = new ReviewData(7,"good7","마켓에 다양한 물품이 많았어요~!!!","10/22 17:00");
        reviewDatas.add(data);
        data = new ReviewData(8,"good8","마켓에 다양한 물품이 많았어요~!!!","10/22 17:00");
        reviewDatas.add(data);
        data = new ReviewData(9,"good9","마켓에 다양한 물품이 많았어요~!!!","10/22 17:00");
        reviewDatas.add(data);
        data = new ReviewData(10,"good10","마켓에 다양한 물품이 많았어요~!!!","10/22 17:00");
        reviewDatas.add(data);


    }

    @OnClick(R.id.showLocation)
    public void moveLocationPage(){
        /**
         * 넘길때
         * 마켓이름 , 주소 , 위도 , 경도 보내야함
         */

        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        intent.putExtra("name","건대프리마켓");
        intent.putExtra("address","서울특별시 광진구 능동로 120");
        intent.putExtra("longitude","127.0793428");
        intent.putExtra("latitude","37.5407625");

        startActivity(intent);
    }

    @OnClick(R.id.marketReview)
    public void moveRigisterReview(){
        Intent intent = new Intent(getApplicationContext(), RegisterReviewActivity.class);
        startActivity(intent);
    }



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

        progressGroup.setText("생활협동조합");
        progressDate.setText("2016-10-17 ~ 2016-10-23");
        progressTime.setText("10:00 ~ 17:30");
        marketKind.setText("오픈마켓 형식");
        marketContent.setText("내가 만든 마켓이다 설명이 필요없다\\n내가 만든 마켓이다 설명이 필요없다\\n내가 만든 마켓이다 설명이 필요없다");

        final String url = "http:www.naver.com";
        marketURL.setText(url);


        marketURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent page = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
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

            reviewNickname.setText(reviewDatas.get(i).nickName);
            reviewDate.setText(reviewDatas.get(i).date);
            reviewContent.setText(reviewDatas.get(i).content);

            listview.addView(childLayout);

        }

    }

}
