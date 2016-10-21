package com.project.seoulmarket.splash.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.project.seoulmarket.R;
import com.project.seoulmarket.main.view.MainTabActivity;
import com.project.seoulmarket.splash.model.ErrorController;
import com.project.seoulmarket.splash.presenter.SplashPresenterImpl;

public class SplashAcitivty extends AppCompatActivity implements SplashView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_acitivty);

        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#F6D03F"));
        }

        final SplashPresenterImpl splashPresenterImpl = new SplashPresenterImpl(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Log.i("myTag","start");
                splashPresenterImpl.connectServer();
            }
        }, 2000);

    }

    @Override
    public void connectingSucceed(int statusCode) {
        Intent intent;
        if(statusCode == 200){
            intent = new Intent(getApplicationContext(), MainTabActivity.class);
        }
        else if(statusCode == 401){
            intent = new Intent(getApplicationContext(), MainTabActivity.class);
        }
        else {
            return;
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void networkError() {
        ErrorController errorController = new ErrorController(getApplicationContext());
        errorController.notifyNetworkError();
    }
}
