package com.project.seoulmarket.recruit.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.seoulmarket.R;
import com.project.seoulmarket.mypage.model.RecruitDetailData;
import com.project.seoulmarket.recruit.presenter.RecruitAdapter;
import com.project.seoulmarket.recruit.presenter.RecruitPresenter;
import com.project.seoulmarket.recruit.presenter.RecruitPresenterImpl;
import com.project.seoulmarket.recruit.register.RecruitRegisterActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecruitActivity extends AppCompatActivity implements RecruitView{

    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.addSeller)
    TextView addSellerBtn;

    RecruitAdapter recruitAdapter;
    private ArrayList<RecruitDetailData> itemDatas = null;

    RecruitPresenter presenter;
    boolean lastitemVisibleFlag = false;

    int currentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit);
        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#F6D03F"));
        }

        ButterKnife.bind(this);


        /**
         * actionbar 설정
         */

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
        presenter = new RecruitPresenterImpl(this);


        itemDatas = new ArrayList<RecruitDetailData>();


        recruitAdapter = new RecruitAdapter(itemDatas,getApplicationContext());
        listView.setAdapter(recruitAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),RecruitDetailActivity.class);
                intent.putExtra("recruitId",String.valueOf(itemDatas.get(position).recruitment_idx));
                startActivity(intent);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
                lastitemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
                //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag) {
                    //TODO 화면이 바닦에 닿을때 처리
                    presenter.getRecruitListDatas(String.valueOf(currentPage++));
                }
            }

        });


        presenter.getRecruitListDatas(String.valueOf(currentPage++));
    }

    @OnClick(R.id.addSeller)
    public void moveAddSeller(){
        Intent intent = new Intent(getApplicationContext(), RecruitRegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void setRecruitData(ArrayList<RecruitDetailData> getDatas) {
        itemDatas.addAll(getDatas);
        recruitAdapter.notifyDataSetChanged();
    }

    @Override
    public void DataNull() {
        Toast.makeText(getApplicationContext(),"데이터가 없습니다.",Toast.LENGTH_SHORT).show();
    }
}
