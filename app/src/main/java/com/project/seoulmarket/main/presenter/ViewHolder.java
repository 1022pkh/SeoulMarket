package com.project.seoulmarket.main.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.seoulmarket.R;

/**
 * Created by kh on 2016. 10. 5..
 */
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public int mId;
    public TextView mName;
    public TextView mLocation;
    public TextView mProgress;
    public ImageView mImageView;

    public ViewHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView)itemView.findViewById(R.id.image);
        mName = (TextView)itemView.findViewById(R.id.marketName);
        mLocation = (TextView)itemView.findViewById(R.id.marketLocation);
        mProgress = (TextView)itemView.findViewById(R.id.progressRate);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Log.i("myTag", String.valueOf(mId));
    }
}