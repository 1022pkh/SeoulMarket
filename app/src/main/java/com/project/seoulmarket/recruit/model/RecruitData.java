package com.project.seoulmarket.recruit.model;

/**
 * Created by kh on 2016. 10. 22..
 */
public class RecruitData {
    public int id;
    public String nickName;
    public String content;
    public String date;

    public RecruitData(int id, String nickName, String content, String date) {
        this.id = id;
        this.nickName = nickName;
        this.content = content;
        this.date = date;
    }

}
