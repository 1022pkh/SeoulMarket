package com.project.seoulmarket.service;

import com.project.seoulmarket.detail.model.DetailResultData;
import com.project.seoulmarket.detail.model.FavoriteResult;
import com.project.seoulmarket.login.model.Token;
import com.project.seoulmarket.main.model.ResultData;
import com.project.seoulmarket.main.model.ResultFilter;
import com.project.seoulmarket.mypage.model.LikeResult;
import com.project.seoulmarket.mypage.model.RecruitResult;
import com.project.seoulmarket.mypage.model.ReportResult;
import com.project.seoulmarket.splash.model.ConnectResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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

    //메인페이지 데이터
    @GET("/main")
    Call<ResultData> getMainData(@Query("currentPage") String currentPage);

    //메인페이지  - 이름 검색 데이터
    @GET("/main/searchname/{name}")
    Call<ResultFilter> getNameFilterData(@Path("name") String name, @Query("currentPage") String currentPage);

    //상세페이지 데이터
    @GET("/main/{id}")
    Call<DetailResultData> getDetailData(@Path("id") String id);

    //좋아요
    @PUT("/main/{id}")
    Call<FavoriteResult> requestLikeFavorite(@Path("id") String id);
    //좋아요
    @DELETE("/main/{id}")
    Call<FavoriteResult> requestDeleteFavorite(@Path("id") String id);


    //나의 공간 - 내가 좋아하는 마켓
    @GET("/me/market/good")
    Call<LikeResult> getMyLikeMarketData();

    //나의 공간 - 내가 제보한 마켓
    @GET("/me/market")
    Call<ReportResult> getMyReportMarketData();

    //나의 공간 - 셀러모집
    @GET("/me/saller")
    Call<RecruitResult> getMyRecruitSellerData();

}
