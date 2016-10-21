package com.project.seoulmarket.main.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.project.seoulmarket.R;
import com.project.seoulmarket.application.GlobalApplication;
import com.project.seoulmarket.detail.DetailActivity;
import com.project.seoulmarket.detail.review.RegisterReviewActivity;
import com.project.seoulmarket.dialog.DialogDate;
import com.project.seoulmarket.dialog.DialogLocation;
import com.project.seoulmarket.dialog.DialogName;
import com.project.seoulmarket.login.LoginActivity;
import com.project.seoulmarket.main.model.MarketData;
import com.project.seoulmarket.main.presenter.CardViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.main_toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.nav_myPage)
    LinearLayout myPageNav;
    @BindView(R.id.nav_noitfyMarket)
    LinearLayout notifyMarketNav;
    @BindView(R.id.nav_recruitSeller)
    LinearLayout recruitSellerNav;
    @BindView(R.id.my_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.profile_image)
    CircleImageView profile;
    @BindView(R.id.userNickName)
    TextView userNickName;


    RecyclerView.Adapter mAdapter;
    ArrayList<MarketData> itemDatas;
    LinearLayoutManager mLayoutManager;

    DialogLocation dialog_location;
    DialogDate dialog_date;
    DialogName dialog_name;


    //Back 키 두번 클릭 여부 확인
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    //요청한 검색 값
    String chooseAddress = "";
    String startDate = "";
    String endDate = "";

    Boolean searchNameArea = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#F6D03F"));
        }

        ButterKnife.bind(this);


        /**
         * toolbar 설정
         */
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        //타이틀 설정
        toolbar_title.setText("SeoulMarket");

        ActionBarDrawerToggle toggle
                = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        /**
         * 로그인 유무 체크
         */
        loginCheck();

        /**
         * FCM
         */

        //추가한 라인
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();



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
        mAdapter = new CardViewAdapter(itemDatas);
        recyclerView.setAdapter(mAdapter);


        /**
         * 임시로 데이터 삽입
         * todo 서버에서 데이터 받아오기 ( 아직 서버 구축 전 )
         */
        //MarketData(int id, String name, String location, String imgUrl, String date)
        itemDatas.add(new MarketData(1,"프리마켓1","건대입구역","imgUrl","D-10"));
        itemDatas.add(new MarketData(2,"프리마켓2","건대입구역","imgUrl","D-20"));
        itemDatas.add(new MarketData(323,"프리마켓3","건대입구역","imgUrl","D-30"));
        itemDatas.add(new MarketData(44,"프리마켓4","건대입구역","imgUrl","D-40"));
        itemDatas.add(new MarketData(5,"프리마켓5","건대입구역","imgUrl","D-50"));



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loginCheck();
    }


    @OnClick(R.id.nav_myPage)
    public void moveMyPage(){
//        Toast.makeText(getApplicationContext(),"나의 마켓 관리",Toast.LENGTH_SHORT).show();

        if(GlobalApplication.loginInfo.getBoolean("Login_check", false)) {
            Toast.makeText(getApplicationContext(),"현재 로그인 상태",Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }




        drawer.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.nav_noitfyMarket)
    public void moveNoitfyMarket(){



        if(GlobalApplication.loginInfo.getBoolean("Login_check", false)) {

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

            loginCheck();

        }
        else{
            Toast.makeText(getApplicationContext(),"현재 로그인 아웃 상태",Toast.LENGTH_SHORT).show();
        }



//    Toast.makeText(getApplicationContext(),"마켓 제보",Toast.LENGTH_SHORT).show();
        drawer.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.nav_recruitSeller)
    public void moveRecruitSeller(){
        Intent intent = new Intent(getApplicationContext(), RegisterReviewActivity.class);
        startActivity(intent);

        Toast.makeText(getApplicationContext(),"셀러 모집",Toast.LENGTH_SHORT).show();
        drawer.closeDrawer(GravityCompat.START);
    }



    @OnClick(R.id.findLocationBtn)
    public void findLocation(){

        WindowManager.LayoutParams params;

        dialog_location = new DialogLocation(MainActivity.this, getLocationEvent);

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

        dialog_date = new DialogDate(MainActivity.this,this,getDateEvent);

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

    private View.OnClickListener getDateEvent = new View.OnClickListener() {
        public void onClick(View v) {

//            startDate = dialog_date.getStartDate();
//            endDate = dialog_date.getEndDate();
//
//            Toast.makeText(getApplicationContext(),startDate + " ~ "+endDate,Toast.LENGTH_SHORT).show();
            dialog_date.dismiss();

        }

    };

    private View.OnClickListener getNameEvent = new View.OnClickListener() {
        public void onClick(View v) {

            Toast.makeText(getApplicationContext(),"검색 : " + dialog_name.getName(),Toast.LENGTH_SHORT).show();
            dialog_name.dismiss();

        }

    };


    public void loginCheck(){
        //login
        if(GlobalApplication.loginInfo.getBoolean("Login_check", false)){

            Glide.with(this)
                    .load(GlobalApplication.loginInfo.getString("thumbnail", ""))
                    .into(profile);

            profile.setVisibility(View.VISIBLE);
            userNickName.setText(GlobalApplication.loginInfo.getString("nickname", ""));

        }
        else{
            profile.setVisibility(View.INVISIBLE);
            userNickName.setText("로그인 후 이용해 주세요");
        }
    }

    @Override
    public void onBackPressed() {
        long tempTime        = System.currentTimeMillis();
        long intervalTime    = tempTime - backPressedTime;

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.findNameBtn) {
            Log.i("myTag","search");


//            if(searchNameArea == false){
//                searchNameArea = true;

                WindowManager.LayoutParams params;

                dialog_name = new DialogName(MainActivity.this, getNameEvent);

                params = dialog_name.getWindow().getAttributes();

                // Dialog 사이즈 조절 하기
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.MATCH_PARENT;


                dialog_name.getWindow().setAttributes(params);

                dialog_name.show();
//            }
//            else{
//
//                searchNameArea = false;
//                dialog_name.dismiss();
//            }



            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
    public void moveDetailPage(int id) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("marketId",id);
        startActivity(intent);
    }
}
