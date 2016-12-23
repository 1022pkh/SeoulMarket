package com.project.seoulmarket.main.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.project.seoulmarket.R;

/**
 * Created by kh on 2016. 10. 5..
 */
public class FooterViewHolder extends RecyclerView.ViewHolder {


    public ImageView gifLoadingView;
    public RelativeLayout relativeLayout;

    public ImageView getGifLoadingView() {
        return gifLoadingView;
    }

    public RelativeLayout getRelativeLayout() {
        return relativeLayout;
    }

    public FooterViewHolder(View itemView) {
        super(itemView);

        gifLoadingView = (ImageView)itemView.findViewById(R.id.gifLoadingView);
        relativeLayout = (RelativeLayout)itemView.findViewById(R.id.cardLinear);

    }


}