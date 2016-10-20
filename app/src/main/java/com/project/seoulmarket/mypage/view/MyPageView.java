package com.project.seoulmarket.mypage.view;

import android.widget.LinearLayout;

/**
 * Created by kh on 2016. 10. 21..
 */
public interface MyPageView {
    void makeLikeView(LinearLayout view);
    void makeReportView(LinearLayout view);
    void makeRecruitView(LinearLayout view);
    void deleteReport(int mId);
    void deleteRecruit(int mId);
    void sendKakao(int mId);
}
