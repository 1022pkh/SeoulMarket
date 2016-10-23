package com.project.seoulmarket.mypage.view;

import android.widget.LinearLayout;

/**
 * Created by kh on 2016. 10. 21..
 */
public interface MyPageView {
    void makeLikeView(LinearLayout view);
    void makeReportView(LinearLayout view);
    void makeRecruitView(LinearLayout view);
    void makeInfoView(LinearLayout view);

    void moveDetailPage(String mId);
    void moveRecruitPage(String mId);
    void deleteReport(String mId);
    void deleteRecruit(String mId);
    void sendKakao(String mId);
}
