package com.project.seoulmarket.main.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.seoulmarket.R;
import com.project.seoulmarket.main.model.MarketData;
import com.project.seoulmarket.main.view.MainView;

import java.util.ArrayList;

/**
 * Created by kh on 2016. 10. 5..
 */

public class CardViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<MarketData> itemDatas;
    MainView myView;

    public CardViewAdapter(ArrayList<MarketData> itemDatas, MainView myView){
        this.itemDatas = itemDatas;
        this.myView = myView;
    }

    //ViewHolder 생성
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_cardview_basic, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView,myView);

        return viewHolder;

    }

    //ListView의 getView()랑 동일
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
        holder.mImageView.setImageResource(R.drawable.tempimg);


    }


    @Override
    public int getItemCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }
}
