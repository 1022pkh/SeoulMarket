package com.project.seoulmarket.mypage.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.util.KakaoParameterException;
import com.matthewtamlin.sliding_intro_screen_library.DotIndicator;
import com.project.seoulmarket.R;
import com.project.seoulmarket.application.GlobalApplication;
import com.project.seoulmarket.detail.DetailActivity;
import com.project.seoulmarket.main.model.MarketData;
import com.project.seoulmarket.main.view.MainTabActivity;
import com.project.seoulmarket.mypage.model.RecruitSeller;
import com.project.seoulmarket.mypage.presenter.MyPageAdapter;
import com.project.seoulmarket.mypage.presenter.MyPagePresenter;
import com.project.seoulmarket.mypage.presenter.MyPagePresenterImpl;
import com.project.seoulmarket.mypage.presenter.MyPageRecruitAdapter;
import com.project.seoulmarket.mypage.presenter.MyPageReportAdapter;
import com.project.seoulmarket.mypage.presenter.MyPageViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyPageActivity extends AppCompatActivity implements MyPageView{
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.pager_indicator)
    DotIndicator indicator;

    MyPagePresenter presenter;

    MyPageAdapter mLikeAdapter;
    ArrayList<MarketData> likeItemDatas;
    LinearLayoutManager mLikeLayoutManager;

    MyPageReportAdapter mReportAdapter;
    ArrayList<MarketData> reportItemDatas;
    LinearLayoutManager mReportLayoutManager;

    MyPageRecruitAdapter mRecruitAdapter;
    ArrayList<RecruitSeller> recruitItemDatas;
    LinearLayoutManager mRecruitLayoutManager;


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
         * viewpager
         */

        MyPageViewPagerAdapter adapter = new MyPageViewPagerAdapter(getSupportFragmentManager(),this);
        pager.setAdapter(adapter);


        /**
         * 도트 색 지정
         */
        indicator.setSelectedDotColor( Color.parseColor( "#F96332" ) );
        indicator.setUnselectedDotColor( Color.parseColor( "#CFCFCF" ) );

        /**
         * indicator 초기화
         */
        indicator.setNumberOfItems(4);


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

    @Override
    public void makeLikeView(LinearLayout view) {

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.likeMarketList);
        TextView nickNameTitle = (TextView)view.findViewById(R.id.userNickName);

        nickNameTitle.setText(GlobalApplication.loginInfo.getString("nickname", ""));

        /**
         * recyclerview
         */
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        // layoutManager 설정
        mLikeLayoutManager = new LinearLayoutManager(MyPageActivity.this);
        mLikeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLikeLayoutManager);

        //TODO adpater 설정
        likeItemDatas = new ArrayList<MarketData>();
        mLikeAdapter = new MyPageAdapter(likeItemDatas, this);
        recyclerView.setAdapter(mLikeAdapter);


        /**
         * 임시로 데이터 삽입
         * todo 서버에서 데이터 받아오기 ( 아직 서버 구축 전 )
         */
        //MarketData(int id, String name, String location, String imgUrl, String date)
        likeItemDatas.add(new MarketData(12,"프리마켓1","건대입구역","imgUrl","2016-10-04\n~ 2016-10-10"));
        likeItemDatas.add(new MarketData(22,"프리마켓2","건대입구역","imgUrl","2016-10-04\n~ 2016-10-10"));
        likeItemDatas.add(new MarketData(323,"프리마켓3","건대입구역","imgUrl","2016-10-04\n~ 2016-10-10"));
        likeItemDatas.add(new MarketData(44,"프리마켓4","건대입구역","imgUrl","2016-10-04\n~ 2016-10-10"));
        likeItemDatas.add(new MarketData(51,"프리마켓5","건대입구역","imgUrl","2016-10-04\n~ 2016-10-10"));
    }


    @Override
    public void makeReportView(LinearLayout view) {

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.reportMarketList);
        TextView nickNameTitle = (TextView)view.findViewById(R.id.userNickName);

        nickNameTitle.setText(GlobalApplication.loginInfo.getString("nickname", ""));

        /**
         * recyclerview
         */
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        // layoutManager 설정
        mReportLayoutManager = new LinearLayoutManager(MyPageActivity.this);
        mReportLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mReportLayoutManager);

        //TODO adpater 설정
        reportItemDatas = new ArrayList<MarketData>();
        mReportAdapter = new MyPageReportAdapter(reportItemDatas, this);
        recyclerView.setAdapter(mReportAdapter);


        /**
         * 임시로 데이터 삽입
         * todo 서버에서 데이터 받아오기 ( 아직 서버 구축 전 )
         */
        //MarketData(int id, String name, String location, String imgUrl, String date)
        reportItemDatas.add(new MarketData(12,"프리마켓1","건대입구역","imgUrl","2016-10-04\n~ 2016-10-10"));
        reportItemDatas.add(new MarketData(22,"프리마켓2","건대입구역","imgUrl","2016-10-04\n~ 2016-10-10"));
        reportItemDatas.add(new MarketData(323,"프리마켓3","건대입구역","imgUrl","2016-10-04\n~ 2016-10-10"));
        reportItemDatas.add(new MarketData(44,"프리마켓4","건대입구역","imgUrl","2016-10-04\n~ 2016-10-10"));
        reportItemDatas.add(new MarketData(51,"프리마켓5","건대입구역","imgUrl","2016-10-04\n~ 2016-10-10"));
    }

    @Override
    public void makeRecruitView(LinearLayout view) {

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recruitMarketList);
        TextView nickNameTitle = (TextView)view.findViewById(R.id.userNickName);

        nickNameTitle.setText(GlobalApplication.loginInfo.getString("nickname", ""));

        /**
         * recyclerview
         */
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        // layoutManager 설정
        mRecruitLayoutManager = new LinearLayoutManager(MyPageActivity.this);
        mRecruitLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mRecruitLayoutManager);

        //TODO adpater 설정
        recruitItemDatas = new ArrayList<RecruitSeller>();
        mRecruitAdapter = new MyPageRecruitAdapter(recruitItemDatas, this);
        recyclerView.setAdapter(mRecruitAdapter);


        /**
         * 임시로 데이터 삽입
         * todo 서버에서 데이터 받아오기 ( 아직 서버 구축 전 )
         */
        //MarketData(int id, String name, String location, String imgUrl, String date)
        recruitItemDatas.add(new RecruitSeller(12,"프리마켓1 셀러모집","2016-10-22","1"));
        recruitItemDatas.add(new RecruitSeller(22,"프리마켓2 셀러모집","2016-10-22","411"));
        recruitItemDatas.add(new RecruitSeller(323,"프리마켓3 셀러모집","2016-10-22","24"));
        recruitItemDatas.add(new RecruitSeller(44,"프리마켓4 셀러모집","2016-10-22","31"));
        recruitItemDatas.add(new RecruitSeller(51,"프리마켓5 셀러모집","2016-10-22","0"));

    }

    @Override
    public void makeInfoView(LinearLayout view) {
        CircleImageView thumbnail =  (CircleImageView)view.findViewById(R.id.profile_image);
        TextView loginMethod = (TextView)view.findViewById(R.id.loginMethod);
        Button logoutBtn = (Button)view.findViewById(R.id.logoutBtn);

        /**
         * 사용자 프로필이미지 적용
         */
        Glide.with(this)
                .load(GlobalApplication.loginInfo.getString("thumbnail", ""))
                .into(thumbnail);

        loginMethod.setText(GlobalApplication.loginInfo.getString("method", ""));
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutEvent();
            }
        });
    }

    public void logoutEvent(){
        /**
         * 로그인 방법에 따른 로그아웃 메소드/방식이 다름
         */
        if(GlobalApplication.loginInfo.getString("method", "").equals("kakao")){
            //kakao 로그아웃
            UserManagement.requestLogout(new LogoutResponseCallback() {
                @Override
                public void onCompleteLogout() {
                }
            });
        }
        else{
            LoginManager.getInstance().logOut();
        }

        GlobalApplication.editor.putBoolean("Login_check", false);
        GlobalApplication.editor.commit();

        /**
         * 성공시 메인페이지로 이동한다.
         */
        Intent intent = new Intent(this, MainTabActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }


    @Override
    public void moveDetailPage(int mId) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("marketId",mId);
        startActivity(intent);
    }

    @Override
    public void deleteReport(int mId) {
        Toast.makeText(getApplicationContext(),"삭제 구현예정",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteRecruit(int mId) {

        Toast.makeText(getApplicationContext(),"삭제 구현예정",Toast.LENGTH_SHORT).show();
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
//                    .addWebButton("마켓 정보 홈페이지", "http://www.kakao.com/services/8")
                    .build();

            //메시지 전달
            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, this);
        } catch (KakaoParameterException e) {
            e.printStackTrace();
            Log.i("myTag", String.valueOf(e));
        }


    }


}

