package com.project.seoulmarket.detail.presenter;

import android.util.Log;

import com.project.seoulmarket.application.GlobalApplication;
import com.project.seoulmarket.detail.DetailView;
import com.project.seoulmarket.detail.model.DetailResultData;
import com.project.seoulmarket.detail.model.Result;
import com.project.seoulmarket.service.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kh on 2016. 10. 24..
 */
public class DetailPresenterImpl implements DetailPresenter {

    DetailView view;
    NetworkService networkService;

    public DetailPresenterImpl(DetailView view) {
        this.view = view;
        networkService = GlobalApplication.getInstance().getNetworkService();
    }

    @Override
    public void getDetail(String id) {
        Call<DetailResultData> detailData = networkService.getDetailData(id);
        detailData.enqueue(new Callback<DetailResultData>() {
            @Override
            public void onResponse(Call<DetailResultData> call, Response<DetailResultData> response) {
                if(response.isSuccessful()){
                    Result getDatas = response.body().result;
                    view.setDetailData(getDatas);
//                    Log.i("myTag", String.valueOf(getDatas.image.get(i).img_url));
                }
            }

            @Override
            public void onFailure(Call<DetailResultData> call, Throwable t) {
                Log.i("myTag fail",t.toString());
            }
        });

    }
}
