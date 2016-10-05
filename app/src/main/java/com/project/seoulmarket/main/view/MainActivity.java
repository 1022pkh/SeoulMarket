package com.project.seoulmarket.main.view;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.project.seoulmarket.R;
import com.project.seoulmarket.dialog.DialogLocation;
import com.project.seoulmarket.main.model.MarketData;
import com.project.seoulmarket.main.presenter.CardViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.main_toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.my_recyclerView)
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    ArrayList<MarketData> itemDatas;
    LinearLayoutManager mLayoutManager;

    DialogLocation dialog_location;

    //Back 키 두번 클릭 여부 확인
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    //요청한 검색 값
    String chooseAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

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



        //MarketData(int id, String name, String location, String imgUrl, String date)
        itemDatas.add(new MarketData(1,"프리마켓1","건대입구역","imgUrl","D-10"));
        itemDatas.add(new MarketData(2,"프리마켓2","건대입구역","imgUrl","D-20"));
        itemDatas.add(new MarketData(323,"프리마켓3","건대입구역","imgUrl","D-30"));
        itemDatas.add(new MarketData(44,"프리마켓4","건대입구역","imgUrl","D-40"));
        itemDatas.add(new MarketData(5,"프리마켓5","건대입구역","imgUrl","D-50"));
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
