package com.project.seoulmarket.main.view;

import android.content.Intent;
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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.project.seoulmarket.R;
import com.project.seoulmarket.application.GlobalApplication;
import com.project.seoulmarket.detail.DetailActivity;
import com.project.seoulmarket.dialog.DialogDate;
import com.project.seoulmarket.dialog.DialogLocation;
import com.project.seoulmarket.dialog.DialogLogin;
import com.project.seoulmarket.dialog.DialogName;
import com.project.seoulmarket.login.LoginActivity;
import com.project.seoulmarket.main.model.MarketFirstData;
import com.project.seoulmarket.main.presenter.CardViewAdapter;
import com.project.seoulmarket.main.presenter.MainPresenter;
import com.project.seoulmarket.main.presenter.MainPresenterImpl;
import com.project.seoulmarket.mypage.view.MyPageActivity;
import com.project.seoulmarket.recruit.view.RecruitActivity;
import com.project.seoulmarket.report.view.ReportMarketActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainTabActivity extends AppCompatActivity implements MainView{


    @BindView(R.id.my_recyclerView)
    RecyclerView recyclerView;


    RecyclerView.Adapter mAdapter;
    ArrayList<MarketFirstData> itemDatas;
    LinearLayoutManager mLayoutManager;

    DialogLocation dialog_location;
    DialogDate dialog_date;
    DialogName dialog_name;
    DialogLogin dialog_login;

    //Back 키 두번 클릭 여부 확인
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    //요청한 검색 값
    String chooseAddress = "";
    String startDate = "";
    String endDate = "";

    //페이지 카운터
    int currentPage = 0;


    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#F6D03F"));
        }

        ButterKnife.bind(this);


        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.actionbar_layout);

        // ActionBar의 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));

        getSupportActionBar().setElevation(0); // 그림자 없애기

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);

        ImageView findNameBtn = (ImageView) mCustomView.findViewById(R.id.findNameBtn);

        findNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findName();
            }
        });


        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);


        /**
         * presenter 초기화
         */

        presenter = new MainPresenterImpl(this);

        /**
         * 로그인 유무 체크
         */
        //loginCheck();

        /**
         * FCM
         */

        //추가한 라인
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();



        /**
         * recyclerview
         */

        itemDatas = new ArrayList<MarketFirstData>();
        mAdapter = new CardViewAdapter(itemDatas,this);
        recyclerView.setAdapter(mAdapter);

        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int scrollOffset = recyclerView.computeVerticalScrollOffset();
                int scrollExtend = recyclerView.computeVerticalScrollExtent();
                int scrollRange = recyclerView.computeVerticalScrollRange();

                if (scrollOffset + scrollExtend == scrollRange || scrollOffset + scrollExtend - 1 == scrollRange) {

                    presenter.requestMainData(String.valueOf(currentPage++));

                }
            }
        });



        /**
         * 데이터 삽입
         */
        //String idx; String address; String state; String image; String marketname;
        presenter.requestMainData(String.valueOf(currentPage++));

    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }


    //나의 공간
    @OnClick(R.id.myPage)
    public void moveMyPage(){

        if(GlobalApplication.loginInfo.getBoolean("Login_check", false)) {

            Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else{
            WindowManager.LayoutParams loginParams;
            dialog_login = new DialogLogin(MainTabActivity.this, loginEvent,loginCancelEvent);

            loginParams = dialog_login.getWindow().getAttributes();

            // Dialog 사이즈 조절 하기
            loginParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            loginParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            dialog_login.getWindow().setAttributes(loginParams);

            dialog_login.show();

        }


    }

    //마켓 제보
    @OnClick(R.id.noitfyMarket)
    public void moveNoitfyMarket(){

        if(GlobalApplication.loginInfo.getBoolean("Login_check", false)) {

            Intent intent = new Intent(getApplicationContext(), ReportMarketActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else{
            WindowManager.LayoutParams loginParams;
            dialog_login = new DialogLogin(MainTabActivity.this, loginEvent,loginCancelEvent);

            loginParams = dialog_login.getWindow().getAttributes();

            // Dialog 사이즈 조절 하기
            loginParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            loginParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            dialog_login.getWindow().setAttributes(loginParams);

            dialog_login.show();

        }

    }

    //셀러 모집
    @OnClick(R.id.recruitSeller)
    public void moveRecruitSeller(){
        Intent intent = new Intent(getApplicationContext(), RecruitActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public void findName(){
        WindowManager.LayoutParams params;

        dialog_name = new DialogName(MainTabActivity.this, getNameEvent);

        params = dialog_name.getWindow().getAttributes();

        // Dialog 사이즈 조절 하기
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;


        dialog_name.getWindow().setAttributes(params);

        dialog_name.show();

    }

    @OnClick(R.id.findLocationBtn)
    public void findLocation(){

        WindowManager.LayoutParams params;

        dialog_location = new DialogLocation(MainTabActivity.this, getLocationEvent);

        params = dialog_location.getWindow().getAttributes();

        // Dialog 사이즈 조절 하기
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog_location.getWindow().setAttributes(params);

        dialog_location.show();
    }

    @OnClick(R.id.findDateBtn)
    public void findDate(){

        WindowManager.LayoutParams params;

        dialog_date = new DialogDate(MainTabActivity.this,this,getDateEvent);

        params = dialog_date.getWindow().getAttributes();

        // Dialog 사이즈 조절 하기
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog_date.getWindow().setAttributes(params);

        dialog_date.show();
    }

    private View.OnClickListener getLocationEvent = new View.OnClickListener() {
        public void onClick(View v) {

            chooseAddress = dialog_location.giveAddress();
//
            if(chooseAddress == "null") {
                Toast.makeText(getApplicationContext(),"지역을 선택해주세요.",Toast.LENGTH_SHORT).show();
            }
            else {

                Toast.makeText(getApplicationContext(),chooseAddress,Toast.LENGTH_SHORT).show();
                dialog_location.dismiss();

            }
        }

    };

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


    private View.OnClickListener getDateEvent = new View.OnClickListener() {
        public void onClick(View v) {

            startDate = dialog_date.getStartDate();
            endDate = dialog_date.getEndDate();

            Toast.makeText(getApplicationContext(),startDate + " ~ "+endDate,Toast.LENGTH_SHORT).show();
            dialog_date.dismiss();

        }

    };

    private View.OnClickListener getNameEvent = new View.OnClickListener() {
        public void onClick(View v) {

            Toast.makeText(getApplicationContext(),"검색 : " + dialog_name.getName(),Toast.LENGTH_SHORT).show();
            dialog_name.dismiss();

        }

    };

    @Override
    public void onBackPressed() {
        long tempTime        = System.currentTimeMillis();
        long intervalTime    = tempTime - backPressedTime;

            /**
             * Back키 두번 연속 클릭 시 앱 종료
             */
            if ( 0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime ) {
                super.onBackPressed();
            }
            else {
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(),"뒤로 가기 키을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show();
            }


    }


    @Override
    public void checkDate() {
        Toast.makeText(getApplicationContext(),"날짜를 다시 선택해주세요",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void prevousStart() {
        Toast.makeText(getApplicationContext(),"선택 날짜는 현재 날짜보다 과거일 수 없습니다.",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void prevousEnd() {
        Toast.makeText(getApplicationContext(),"마지막 날짜는 시작 날짜보다 과거일 수 없습니다.",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void nextEnd() {
        Toast.makeText(getApplicationContext(),"시작 날짜는 마지막 날짜보다 미래일 수 없습니다.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveDetailPage(String id) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("market_id",id);
        startActivity(intent);
    }

    @Override
    public void firstSetData(ArrayList<MarketFirstData> getDatas) {
        Log.i("myTag",String.valueOf(getDatas.size()));

        itemDatas.addAll(getDatas);
        mAdapter.notifyDataSetChanged();
    }

}
