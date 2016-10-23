package com.project.seoulmarket.main.presenter;

import com.project.seoulmarket.application.GlobalApplication;
import com.project.seoulmarket.main.view.MainView;
import com.project.seoulmarket.service.NetworkService;

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
    public void requestMainData() {

    }
}
