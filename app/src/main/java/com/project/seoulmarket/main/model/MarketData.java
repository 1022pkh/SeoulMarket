package com.project.seoulmarket.main.model;

/**
 * Created by kh on 2016. 10. 5..
 */
public class MarketData {
    public String id;
    public String name;
    public String location;
    public String imgUrl;
    public String date; // 날짜를 받아와서 진행상태를 표시하기로

    public MarketData(String id, String name, String location, String imgUrl, String date) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.imgUrl = imgUrl;
        this.date = date;
    }

}
