package com.project.seoulmarket.mypage.presenter;

import com.project.seoulmarket.mypage.view.MyPageView;

/**
 * Created by kh on 2016. 10. 21..
 */
public class MyPagePresenterImpl implements MyPagePresenter{
    MyPageView view;

    public MyPagePresenterImpl(MyPageView view) {
        this.view = view;
    }
}
