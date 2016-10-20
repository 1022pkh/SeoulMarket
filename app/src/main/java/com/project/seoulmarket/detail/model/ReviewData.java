package com.project.seoulmarket.detail.model;

/**
 * Created by kh on 2016. 10. 5..
 */
public class ReviewData {
    public int id;
    public String nickName;
    public String content;
    public String date;

    public ReviewData(int id, String nickName, String content, String date) {
        this.id = id;
        this.nickName = nickName;
        this.content = content;
        this.date = date;
    }

}
