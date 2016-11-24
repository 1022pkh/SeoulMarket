package com.project.seoulmarket.mypage.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.seoulmarket.R;
import com.project.seoulmarket.mypage.model.RecruitDetailData;
import com.project.seoulmarket.mypage.view.MyPageView;

import java.util.ArrayList;

/**
 * Created by kh on 2016. 10. 5..
 */

public class MyPageRecruitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<RecruitDetailData> itemDatas;
    private View itemView;
    private ViewGroup parent;

    private MyPageView myView;

    private static final int FOOTER_VIEW = 1;

    public MyPageRecruitAdapter(ArrayList<RecruitDetailData> itemDatas, MyPageView myView){
        this.itemDatas = itemDatas;
        this.myView = myView;
    }

    //ViewHolder 생성
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.parent = parent;

        if (viewType == FOOTER_VIEW) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_cardview_footer, parent, false);

            FooterViewHolder vh = new FooterViewHolder(itemView);

            return vh;
        }


        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_cardview_recruit, parent,false);
        MyPageRecruitViewHolder viewHolder = new MyPageRecruitViewHolder(itemView, myView);




        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        try {
            if (holder instanceof MyPageRecruitViewHolder) {
                MyPageRecruitViewHolder vh = (MyPageRecruitViewHolder) holder;

                vh.position = position;
                vh.mId = itemDatas.get(position).recruitment_idx;
                vh.mTitle.setText(itemDatas.get(position).recruitment_title);
                vh.mDate.setText(itemDatas.get(position).recruitment_uploadtime);
                vh.mCount.setText(itemDatas.get(position).count);

            } else if (holder instanceof FooterViewHolder) {
                FooterViewHolder vh = (FooterViewHolder) holder;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        if (itemDatas == null) {
            return 0;
        }

        if (itemDatas.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return itemDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == itemDatas.size()) {
            // This is where we'll add footer.
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }
}
