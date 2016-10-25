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
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.util.KakaoParameterException;
import com.project.seoulmarket.R;
import com.project.seoulmarket.application.GlobalApplication;
import com.project.seoulmarket.detail.DetailActivity;
import com.project.seoulmarket.main.view.MainTabActivity;
import com.project.seoulmarket.mypage.model.LikeDetailData;
import com.project.seoulmarket.mypage.model.RecruitDetailData;
import com.project.seoulmarket.mypage.model.ReportDetailData;
import com.project.seoulmarket.mypage.presenter.MyPageAdapter;
import com.project.seoulmarket.mypage.presenter.MyPagePresenter;
import com.project.seoulmarket.mypage.presenter.MyPagePresenterImpl;
import com.project.seoulmarket.mypage.presenter.MyPageRecruitAdapter;
import com.project.seoulmarket.mypage.presenter.MyPageReportAdapter;
import com.project.seoulmarket.mypage.presenter.MyPageViewPagerAdapter;
import com.project.seoulmarket.recruit.view.RecruitDetailActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyPageActivity extends AppCompatActivity implements MyPageView{
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.ntb)
    NavigationTabBar navigationTabBar;

    MyPageAdapter mLikeAdapter;

    ArrayList<LikeDetailData> likeItemDatas;
    LinearLayoutManager mLikeLayoutManager;
    MyPageReportAdapter mReportAdapter;

    ArrayList<ReportDetailData> reportItemDatas;
    LinearLayoutManager mReportLayoutManager;
    MyPageRecruitAdapter mRecruitAdapter;

    ArrayList<RecruitDetailData> recruitItemDatas;
    LinearLayoutManager mRecruitLayoutManager;

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



        likeItemDatas = new ArrayList<LikeDetailData>();
        reportItemDatas = new ArrayList<ReportDetailData>();
        recruitItemDatas = new ArrayList<RecruitDetailData>();

        /**
         * viewpager
         */

        MyPageViewPagerAdapter adapter = new MyPageViewPagerAdapter(getSupportFragmentManager(),this);
        pager.setAdapter(adapter);


        /**
         *
         */

//        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        final String[] colors = getResources().getStringArray(R.array.default_preview);

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[0])
                ).title("Heart")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1])
                ).title("Cup")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2])
                ).title("Diploma")
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[3])
                ).title("Flag")
                        .badgeTitle("icon")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(pager, 0);

        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
        navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.BOTTOM);
        navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.CENTER);
        navigationTabBar.setIsBadged(true);
        navigationTabBar.setIsTitled(true);
        navigationTabBar.setIsTinted(true);
        navigationTabBar.setIsBadgeUseTypeface(true);
        navigationTabBar.setBadgeBgColor(Color.RED);
        navigationTabBar.setBadgeTitleColor(Color.WHITE);
        navigationTabBar.setIsSwiped(true);
        navigationTabBar.setBgColor(Color.WHITE);
        navigationTabBar.setBadgeSize(10);
        navigationTabBar.setTitleSize(10);
        navigationTabBar.setIconSizeFraction((float) 0.5);


        presenter.getMyLikeMarketData();
        presenter.getMyReportMarketData();
        presenter.getMyRecruitSellerData();

    }

    @Override
    protected void onResume(){
        super.onResume();
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

        mLikeAdapter = new MyPageAdapter(likeItemDatas, this);
        recyclerView.setAdapter(mLikeAdapter);

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


        mReportAdapter = new MyPageReportAdapter(reportItemDatas, this);
        recyclerView.setAdapter(mReportAdapter);


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

        mRecruitAdapter = new MyPageRecruitAdapter(recruitItemDatas, this);
        recyclerView.setAdapter(mRecruitAdapter);


//        recruitItemDatas.add(new RecruitDetailData("12","프리마켓1 셀러모집","","nick","2016.10.22","1"));

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

    @Override
    public void dataNull() {
        Toast.makeText(getApplicationContext(),"데이터가 없습니다.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setLikeData(ArrayList<LikeDetailData> getDatas) {
        likeItemDatas.addAll(getDatas);
        mLikeAdapter.notifyDataSetChanged();
    }

    @Override
    public void setReportData(ArrayList<ReportDetailData> getDatas) {
        reportItemDatas.addAll(getDatas);
    }

    @Override
    public void setRecruitData(ArrayList<RecruitDetailData> getDatas) {
        recruitItemDatas.addAll(getDatas);
//        mRecruitAdapter.notifyDataSetChanged();
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
    public void moveDetailPage(String mId) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("market_id",mId);
        startActivity(intent);
    }

    @Override
    public void moveRecruitPage(String mId) {
        Intent intent = new Intent(getApplicationContext(), RecruitDetailActivity.class);
        intent.putExtra("recruitId",mId);
        startActivity(intent);
    }

    @Override
    public void deleteReport(String mId) {
        Toast.makeText(getApplicationContext(),"삭제 구현예정",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteRecruit(String mId) {

        Toast.makeText(getApplicationContext(),"삭제 구현예정",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void sendKakao(String marketId) {
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

