package com.project.seoulmarket.detail;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.matthewtamlin.sliding_intro_screen_library.DotIndicator;
import com.project.seoulmarket.R;
import com.project.seoulmarket.detail.presenter.ViewpagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.pager_indicator)
    DotIndicator indicator;

    ArrayList<String> imgUrl;

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

    }


}
