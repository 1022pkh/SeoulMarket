package com.project.seoulmarket.main.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.seoulmarket.R;
import com.project.seoulmarket.main.model.MarketFilterData;
import com.project.seoulmarket.main.view.MainView;
import com.project.seoulmarket.mypage.presenter.FooterViewHolder;

import java.util.ArrayList;

/**
 * Created by kh on 2016. 10. 25..
 */
public class FilterViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MarketFilterData> itemDatas;

    MainView myView;
    private View itemView;
    private ViewGroup parent;
    private static final int FOOTER_VIEW = 1;

    public FilterViewAdapter(ArrayList<MarketFilterData> itemDatas, MainView myView){
        this.itemDatas = itemDatas;
        this.myView = myView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;

        if (viewType == FOOTER_VIEW) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_cardview_footer, parent, false);

            FooterViewHolder vh = new FooterViewHolder(itemView);

            return vh;
        }

        this.itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_cardview_filter, parent,false);

        FilterViewHolder viewHolder = new FilterViewHolder(itemView,myView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            if (holder instanceof FilterViewHolder) {
                FilterViewHolder vh = (FilterViewHolder) holder;

                vh.mId = itemDatas.get(position).market_idx;
                vh.mName.setText(itemDatas.get(position).market_name);
                vh.mLocation.setText(itemDatas.get(position).market_address);

                String startTemp = itemDatas.get(position).market_startdate.replace("-",".");
                String endTemp = itemDatas.get(position).market_enddate.replace("-",".");

                vh.mDate.setText(startTemp + "~" + endTemp);
                vh.mLike.setText(itemDatas.get(position).market_count);

                /**
                 * state > 1 : 남은날자
                 * state = 0 : 당일
                 * state < 0 만료
                 */

                int state = Integer.valueOf(itemDatas.get(position).market_state);

                if( state > 0){
                    vh.mProgress.setText("D-" + state);
                    vh.mProgress.setBackgroundResource(R.drawable.progress_background);
                }
                else if(state == 0){
                    vh.mProgress.setText("D-day");
                    vh.mProgress.setBackgroundResource(R.drawable.progress_background);
                }
                else{
                    vh.mProgress.setText("만료");
                    vh.mProgress.setBackgroundResource(R.drawable.progress_background);
                }

                ImageView imageView = (ImageView)itemView.findViewById(R.id.image);

                Glide.with(parent.getContext())
                        .load(itemDatas.get(position).image_url)
                        .thumbnail(0.3f)
                        .error(R.drawable.ic_default)
                        .into(vh.getImageView());


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
