package com.project.seoulmarket.mypage.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.seoulmarket.R;
import com.project.seoulmarket.mypage.model.ReportDetailData;
import com.project.seoulmarket.mypage.view.MyPageView;

import java.util.ArrayList;

/**
 * Created by kh on 2016. 10. 5..
 */

public class MyPageReportAdapter extends RecyclerView.Adapter<MyPageReportViewHolder> {

    private ArrayList<ReportDetailData> itemDatas;
    private View itemView;
    private ViewGroup parent;

    private MyPageView myView;

    public MyPageReportAdapter(ArrayList<ReportDetailData> itemDatas, MyPageView myView){
        this.itemDatas = itemDatas;
        this.myView = myView;
    }

    //ViewHolder 생성
    @Override
    public MyPageReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.parent = parent;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_cardview_report, parent,false);
        MyPageReportViewHolder viewHolder = new MyPageReportViewHolder(itemView, myView);

        return viewHolder;

    }

    //ListView의 getView()랑 동일
    @Override
    public void onBindViewHolder(MyPageReportViewHolder holder, int position) {
        holder.mId = itemDatas.get(position).idx;
        holder.mName.setText(itemDatas.get(position).marketname);
        holder.mLocation.setText(itemDatas.get(position).address);

        int state = Integer.valueOf(itemDatas.get(position).state);

        if( state > 0){
            holder.mProgress.setText("D-" + state);
        }
        else if(state == 0){
            holder.mProgress.setText("D-day");
        }
        else{
            holder.mProgress.setText("만료");
        }

        holder.mDate.setText(itemDatas.get(position).market_startdate +"\n~"+itemDatas.get(position).market_enddate);

        ImageView imageView = (ImageView)itemView.findViewById(R.id.image);

        Glide.with(parent.getContext())
                .load(itemDatas.get(position).image)
                .into(imageView);


    }


    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }
}
