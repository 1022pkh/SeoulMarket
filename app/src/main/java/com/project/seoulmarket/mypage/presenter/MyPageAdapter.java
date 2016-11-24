package com.project.seoulmarket.mypage.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.seoulmarket.R;
import com.project.seoulmarket.mypage.model.LikeDetailData;
import com.project.seoulmarket.mypage.view.MyPageView;

import java.util.ArrayList;

/**
 * Created by kh on 2016. 10. 5..
 */

public class MyPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<LikeDetailData> itemDatas;
    private View itemView;
    private ViewGroup parent;

    private MyPageView myView;

    private static final int FOOTER_VIEW = 1;

    public MyPageAdapter(ArrayList<LikeDetailData> itemDatas, MyPageView myView){
        this.itemDatas = itemDatas;
        this.myView = myView;
    }

    //ViewHolder 생성
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


//        Log.i("myTag", String.valueOf(viewType));

        if (viewType == FOOTER_VIEW) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_cardview_footer, parent, false);

            FooterViewHolder vh = new FooterViewHolder(itemView);

            return vh;
        }

        this.parent = parent;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_cardview_like, parent,false);
        MyPageViewHolder viewHolder = new MyPageViewHolder(itemView, myView);

        return viewHolder;

    }

    //ListView의 getView()랑 동일
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        try {
            if (holder instanceof MyPageViewHolder) {
                MyPageViewHolder vh = (MyPageViewHolder) holder;

                vh.mId = itemDatas.get(position).idx;
                vh.mName.setText(itemDatas.get(position).marketname);
                vh.mLocation.setText(itemDatas.get(position).address);

                int state = Integer.valueOf(itemDatas.get(position).state);

                if( state > 0){
                    vh.mProgress.setText("D-" + state);
                    vh.mProgress.setBackgroundResource(R.drawable.progress_background);
                }
                else if(state == 0){
                    vh.mProgress.setText("진행중");
                    vh.mProgress.setBackgroundResource(R.drawable.progress_background);
                }
                else{
                    vh.mProgress.setText("만료");
                    vh.mProgress.setBackgroundResource(R.drawable.progress_background);
                }

                String startTemp = itemDatas.get(position).market_startdate.replace("-",".");
                String endTemp = itemDatas.get(position).market_enddate.replace("-",".");

                vh.mDate.setText(startTemp +"~" + endTemp);

                ImageView imageView = (ImageView)itemView.findViewById(R.id.image);

                Glide.with(parent.getContext())
                        .load(itemDatas.get(position).image)
                        .thumbnail(0.3f)
                        .error(R.drawable.ic_default)
                        .into(vh.getImageView());

//        Glide.clear(imageView);

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
