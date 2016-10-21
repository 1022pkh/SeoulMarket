package com.project.seoulmarket.service;

import com.project.seoulmarket.login.model.Token;
import com.project.seoulmarket.splash.model.ConnectResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by kh on 2016. 8. 25..
 */
public interface NetworkService {

    //연결확인
    @GET("/connect")
    Call<ConnectResult> getConnection();

    // 페이스북 로그인 요청
    @POST("/auth/facebook/token")
    Call<ConnectResult> requestFacebookLogin(@Body Token token);

    // 카카오톡 로그인요청
    @GET("/auth/kakao/token")
    Call<ConnectResult> requestKakaoLogin(@Query("access_token") String access_token);


    // 닉네임 중복체크
    @GET("/me")
    Call<ConnectResult> nickNameDoubleCheck(@Query("nickname") String nickname);

}
