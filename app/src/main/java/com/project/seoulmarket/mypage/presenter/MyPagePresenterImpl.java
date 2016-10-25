package com.project.seoulmarket.mypage.presenter;

import android.util.Log;

import com.project.seoulmarket.application.GlobalApplication;
import com.project.seoulmarket.mypage.model.LikeDetailData;
import com.project.seoulmarket.mypage.model.LikeResult;
import com.project.seoulmarket.mypage.model.RecruitDetailData;
import com.project.seoulmarket.mypage.model.RecruitResult;
import com.project.seoulmarket.mypage.model.ReportDetailData;
import com.project.seoulmarket.mypage.model.ReportResult;
import com.project.seoulmarket.mypage.view.MyPageView;
import com.project.seoulmarket.service.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kh on 2016. 10. 21..
 */
public class MyPagePresenterImpl implements MyPagePresenter{
    MyPageView view;
    NetworkService networkService;

    public MyPagePresenterImpl(MyPageView view) {
        this.view = view;
        networkService = GlobalApplication.getInstance().getNetworkService();
    }

    @Override
    public void getMyLikeMarketData() {

        Call<LikeResult> getLikeData = networkService.getMyLikeMarketData();

        getLikeData.enqueue(new Callback<LikeResult>() {
            @Override
            public void onResponse(Call<LikeResult> call, Response<LikeResult> response) {

                Log.i("myTag","like");

                if (response.isSuccessful()){
                    ArrayList<LikeDetailData> getDatas = response.body().result;

//                    Log.i("myTag",String.valueOf(getDatas));

                    if(getDatas.size() > 0 )
                        view.setLikeData(getDatas);
                    else
                        view.dataNull();
                }
            }

            @Override
            public void onFailure(Call<LikeResult> call, Throwable t) {

            }
        });
    }

    @Override
    public void getMyReportMarketData() {
        Call<ReportResult> getReportData = networkService.getMyReportMarketData();
        getReportData.enqueue(new Callback<ReportResult>() {
            @Override
            public void onResponse(Call<ReportResult> call, Response<ReportResult> response) {
                Log.i("myTag","report");

                if (response.isSuccessful()){
                    ArrayList<ReportDetailData> getDatas = response.body().result;

//                    Log.i("myTag",String.valueOf(getDatas));

                    if(getDatas.size() > 0 )
                        view.setReportData(getDatas);
                    else
                        view.dataNull();
                }
            }

            @Override
            public void onFailure(Call<ReportResult> call, Throwable t) {

            }
        });
    }

    @Override
    public void getMyRecruitSellerData() {
        Call<RecruitResult> getRecruitData = networkService.getMyRecruitSellerData();
        getRecruitData.enqueue(new Callback<RecruitResult>() {
            @Override
            public void onResponse(Call<RecruitResult> call, Response<RecruitResult> response) {
                Log.i("myTag","recruit");

                if (response.isSuccessful()){
                    ArrayList<RecruitDetailData> getDatas = response.body().result;

                    Log.i("myTag idx",String.valueOf(getDatas.get(0).recruitment_idx));

                    if(getDatas.size() > 0 )
                        view.setRecruitData(getDatas);
                    else
                        view.dataNull();
                }
            }

            @Override
            public void onFailure(Call<RecruitResult> call, Throwable t) {

            }
        });
    }
}
