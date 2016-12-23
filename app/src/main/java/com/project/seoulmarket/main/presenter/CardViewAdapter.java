package com.project.seoulmarket.main.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.seoulmarket.R;
import com.project.seoulmarket.main.model.MarketFirstData;
import com.project.seoulmarket.main.view.MainView;

import java.util.ArrayList;

/**
 * Created by kh on 2016. 10. 5..
 */

public class CardViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MarketFirstData> itemDatas;
    MainView myView;
    private View itemView;
    private ViewGroup parent;

    private static final int FOOTER_VIEW = 1;

    public CardViewAdapter(ArrayList<MarketFirstData> itemDatas, MainView myView){
        this.itemDatas = itemDatas;
        this.myView = myView;
    }

    public void refreshData(ArrayList<MarketFirstData> itemDatas){
        this.itemDatas = itemDatas;
        notifyDataSetChanged();
    }


    //ViewHolder 생성
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.parent = parent;

        if (viewType == FOOTER_VIEW) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_footer, parent, false);

            FooterViewHolder vh = new FooterViewHolder(itemView);

            return vh;
        }


        this.itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_cardview_basic, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView,myView);

        return viewHolder;

    }

    //ListView의 getView()랑 동일
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        try {
            if (holder instanceof ViewHolder) {
                ViewHolder vh = (ViewHolder) holder;

                vh.mId = itemDatas.get(position).idx;
                vh.mName.setText(itemDatas.get(position).marketname);
                vh.mLocation.setText(itemDatas.get(position).address);

                /**
                 * state > 1 : 남은날자
                 * state = 0 : 진행중
                 * state < 0 만료
                 */

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

                ImageView imageView = (ImageView)itemView.findViewById(R.id.image);

                Glide.with(parent.getContext())
                        .load(itemDatas.get(position).image)
                        .thumbnail(0.3f)
                        .error(R.drawable.ic_default)
                        .into(vh.getImageView());

            } else if (holder instanceof FooterViewHolder) {
                FooterViewHolder vh = (FooterViewHolder) holder;

                if(itemDatas.size() % 10 == 0 ){
                    Glide.with(parent.getContext())
                            .load(R.drawable.gif_loading)
                            .into(vh.getGifLoadingView());
                }
                else {
                    vh.getGifLoadingView().setImageResource(0);
                    vh.getRelativeLayout().setVisibility(View.GONE);
                }

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
