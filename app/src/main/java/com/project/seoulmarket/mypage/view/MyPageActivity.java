package com.project.seoulmarket.mypage.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;
import com.project.seoulmarket.R;
import com.project.seoulmarket.main.model.MarketData;
import com.project.seoulmarket.mypage.presenter.MyPageAdapter;
import com.project.seoulmarket.mypage.presenter.MyPagePresenter;
import com.project.seoulmarket.mypage.presenter.MyPagePresenterImpl;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPageActivity extends AppCompatActivity implements MyPageView{

    @BindView(R.id.likeMarketList)
    RecyclerView recyclerView;

    MyPageAdapter mAdapter;
    ArrayList<MarketData> itemDatas;
    LinearLayoutManager mLayoutManager;


    MyPagePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#F6D03F"));
        }

        ButterKnife.bind(this);

        presenter = new MyPagePresenterImpl(this);


        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // ActionBar의 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));

        getSupportActionBar().setElevation(0); // 그림자 없애기

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.actionbar_back_layout, null);

        TextView actionbarTitle = (TextView)mCustomView.findViewById(R.id.mytext);
        actionbarTitle.setText("나의 공간");
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
         * recyclerview
         */
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        //TODO adpater 설정
        itemDatas = new ArrayList<MarketData>();
        mAdapter = new MyPageAdapter(itemDatas,this);
        recyclerView.setAdapter(mAdapter);


        /**
         * 임시로 데이터 삽입
         * todo 서버에서 데이터 받아오기 ( 아직 서버 구축 전 )
         */
        //MarketData(int id, String name, String location, String imgUrl, String date)
        itemDatas.add(new MarketData(12,"프리마켓1","건대입구역","imgUrl","2016-10-04\n~ 2016-10-10"));
        itemDatas.add(new MarketData(22,"프리마켓2","건대입구역","imgUrl","2016-10-04\n~ 2016-10-10"));
        itemDatas.add(new MarketData(323,"프리마켓3","건대입구역","imgUrl","2016-10-04\n~ 2016-10-10"));
        itemDatas.add(new MarketData(44,"프리마켓4","건대입구역","imgUrl","2016-10-04\n~ 2016-10-10"));
        itemDatas.add(new MarketData(51,"프리마켓5","건대입구역","imgUrl","2016-10-04\n~ 2016-10-10"));

    }


    @Override
    public void sendKakao(int marketId) {
        Toast.makeText(getApplicationContext(),"kakao " +marketId,Toast.LENGTH_SHORT).show();

        /**
         * 해당하는 market 에 대한 정보 리턴!
         */

        try {
            final KakaoLink kakaoLink = KakaoLink.getKakaoLink(getApplicationContext());
            final KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();


            String text = "건대프리마켓";
            String imageSrc = "https://tv.pstatic.net/thm?size=120x150&quality=9&q=http://sstatic.naver.net/people/80/201306051741063951.jpg";
            int width = 150;
            int height = 150;

            kakaoTalkLinkMessageBuilder.addText(text)
                    .addImage(imageSrc, width, height)
                    .addWebButton("마켓 정보 홈페이지", "http://www.kakao.com/services/8")
                    .build();

            //메시지 전달
            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, this);
        } catch (KakaoParameterException e) {
            e.printStackTrace();
            Log.i("myTag", String.valueOf(e));
        }


    }
}
