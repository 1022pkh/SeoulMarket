package com.project.seoulmarket.mypage.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.seoulmarket.R;
import com.project.seoulmarket.mypage.model.RecruitSeller;
import com.project.seoulmarket.mypage.view.MyPageView;

import java.util.ArrayList;

/**
 * Created by kh on 2016. 10. 5..
 */

public class MyPageRecruitAdapter extends RecyclerView.Adapter<MyPageRecruitViewHolder> {

    private ArrayList<RecruitSeller> itemDatas;
    private View itemView;
    private ViewGroup parent;

    private MyPageView myView;

    public MyPageRecruitAdapter(ArrayList<RecruitSeller> itemDatas, MyPageView myView){
        this.itemDatas = itemDatas;
        this.myView = myView;
    }

    //ViewHolder 생성
    @Override
    public MyPageRecruitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.parent = parent;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_cardview_recruit, parent,false);
        MyPageRecruitViewHolder viewHolder = new MyPageRecruitViewHolder(itemView, myView);

        return viewHolder;

    }

    //ListView의 getView()랑 동일
    @Override
    public void onBindViewHolder(MyPageRecruitViewHolder holder, int position) {

        holder.mId = itemDatas.get(position).id;
        holder.mTitle.setText(itemDatas.get(position).title);
        holder.mDate.setText(itemDatas.get(position).date);
        holder.mCount.setText(itemDatas.get(position).count);

    }


    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }
}
