package com.project.seoulmarket.mypage.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.seoulmarket.R;
import com.project.seoulmarket.main.model.MarketData;
import com.project.seoulmarket.mypage.view.MyPageView;

import java.util.ArrayList;

/**
 * Created by kh on 2016. 10. 5..
 */

public class MyPageAdapter extends RecyclerView.Adapter<MyPageViewHolder> {

    private ArrayList<MarketData> itemDatas;
    private View itemView;
    private ViewGroup parent;

    private MyPageView myView;

    public MyPageAdapter(ArrayList<MarketData> itemDatas, MyPageView myView){
        this.itemDatas = itemDatas;
        this.myView = myView;
    }

    //ViewHolder 생성
    @Override
    public MyPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.parent = parent;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_cardview_like, parent,false);
        MyPageViewHolder viewHolder = new MyPageViewHolder(itemView, myView);

        return viewHolder;

    }

    //ListView의 getView()랑 동일
    @Override
    public void onBindViewHolder(MyPageViewHolder holder, int position) {
        /**
         *
         int id;
         String name;
         String location;
         String imgUrl;
         String date; // 날짜를 받아와서 진행상태를 표시하기로
         */
        holder.mId = itemDatas.get(position).id;
        holder.mName.setText(itemDatas.get(position).name);
        holder.mLocation.setText(itemDatas.get(position).location);

        // TODO: 2016. 10. 5. 아직 데이터가 없으므로 임시로 넣어주기로.
        holder.mProgress.setText(itemDatas.get(position).date);

        String tempUrl = "http://www.samsungfundblog.com/wp-content/uploads/2014/04/%ED%94%84%EB%A6%AC%EB%A7%88%EC%BC%93.jpg";

        ImageView imageView = (ImageView)itemView.findViewById(R.id.image);

        Glide.with(parent.getContext())
                .load(tempUrl)
                .into(imageView);


    }


    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }
}
