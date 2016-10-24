package com.project.seoulmarket.main.view;

import com.project.seoulmarket.main.model.MarketFirstData;

import java.util.ArrayList;

/**
 * Created by kh on 2016. 10. 6..
 */
public interface MainView {
    void checkDate();
    void prevousStart();
    void prevousEnd();
    void nextEnd();
    void moveDetailPage(String id);
    void firstSetData(ArrayList<MarketFirstData> getDatas);
}
