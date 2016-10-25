package com.project.seoulmarket.main.presenter;

import com.project.seoulmarket.application.GlobalApplication;
import com.project.seoulmarket.main.model.MarketFilterData;
import com.project.seoulmarket.main.model.MarketFirstData;
import com.project.seoulmarket.main.model.ResultData;
import com.project.seoulmarket.main.model.ResultFilter;
import com.project.seoulmarket.main.view.MainView;
import com.project.seoulmarket.service.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by kh on 2016. 10. 23..
 */
public class MainPresenterImpl implements MainPresenter {

    MainView view;
    NetworkService networkService;

    public MainPresenterImpl(MainView view) {
        this.view = view;
        networkService = GlobalApplication.getInstance().getNetworkService();
    }

    @Override
    public void requestMainData(String pageNum) {

        Call<ResultData> getFirstData = networkService.getMainData(pageNum);
        getFirstData.enqueue(new Callback<ResultData>() {
            @Override
            public void onResponse(Call<ResultData> call, Response<ResultData> response) {

                if (response.isSuccessful()){
                    //result[0],[1].....
                    ArrayList<MarketFirstData> getDatas = response.body().result;

                    if(getDatas.size() > 0 )
                        view.firstSetData(getDatas);
                    else
                        view.DataNull();
                }

            }

            @Override
            public void onFailure(Call<ResultData> call, Throwable t) {

            }
        });

    }

    @Override
    public void requestNameFilterData(final String mName, String pageNum) {
        Call<ResultFilter> getFilterData = networkService.getNameFilterData(mName,pageNum);
        getFilterData.enqueue(new Callback<ResultFilter>() {
            @Override
            public void onResponse(Call<ResultFilter> call, Response<ResultFilter> response) {
                if (response.isSuccessful()){
                    //result[0],[1].....
                    ArrayList<MarketFilterData> getDatas = response.body().result;

                    if(getDatas.size() > 0 )
                        view.filterSetData(mName,getDatas);
                    else
                        view.DataNull();
                }
            }

            @Override
            public void onFailure(Call<ResultFilter> call, Throwable t) {

            }
        });
    }
}
