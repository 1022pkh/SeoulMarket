package com.project.seoulmarket.main.presenter;

/**
 * Created by kh on 2016. 10. 23..
 */
public interface MainPresenter {
    void requestMainData(String pageNum);
    void requestMainData(String pageNum, Boolean refresh);

    void requestNameFilterData(String mName,String pageNum);

    void requestLocationFilterData(String address,String startDate,String endDate,String pageNum);
    void requestDateFilterData(String address,String startDate,String endDate,String pageNum);
}
