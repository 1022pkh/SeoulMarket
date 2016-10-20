package com.project.seoulmarket.main.presenter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.seoulmarket.R;
import com.project.seoulmarket.detail.DetailActivity;

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
//        Log.i("myTag", String.valueOf(mId));
        Intent intent = new Intent(v.getContext(), DetailActivity.class);
        intent.putExtra("marketId",mId);
        v.getContext().startActivity(intent);
    }
}