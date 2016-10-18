package com.project.seoulmarket.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.project.seoulmarket.R;
import com.project.seoulmarket.application.GlobalApplication;
import com.project.seoulmarket.join.JoinActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private SessionCallback callback;      //콜백 선언
    CallbackManager mFacebookCallbackManager;

    @BindView(R.id.login_with_facebook)
    LoginButton facebookBtn;

    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }

        //kakao login
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();




        //Facebook
        facebookBtn.setReadPermissions("public_profile","email");
        // If using in a fragment
//        facebookBtn.setFragment(this);
        // Other app specific specialization

        mFacebookCallbackManager = CallbackManager.Factory.create();

        // Callback registration
        facebookBtn.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.i("myTag","face-success");
                Log.i("myTag", "token"+String.valueOf(loginResult.getAccessToken().getToken()));

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                try {

                                    String id = (String) response.getJSONObject().get("id");//페이스북 아이디값
                                    String name = (String) response.getJSONObject().get("name");//페이스북 이름

                                    String thumnailImg = "http://graph.facebook.com/"+ id +"/picture?type=large";

//                                    Log.i("myTag",id);
//                                    Log.i("myTag",name);

                                    /**
                                     * 페이스북 로그인 성공에 따른 정보 업데이트
                                     */
                                    GlobalApplication.editor.putBoolean("Login_check", true);
                                    GlobalApplication.editor.putString("method", "facebook");
                                    GlobalApplication.editor.putString("nickname", name);
                                    GlobalApplication.editor.putString("thumbnail", thumnailImg);
                                    GlobalApplication.editor.commit();


                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                // new joinTask().execute(); //자신의 서버에서 로그인 처리를 해줍니다

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();


                redirectJoinActivity();
            }

            @Override
            public void onCancel() {
                // App code
                Log.i("myTag","face-cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i("myTag","face-error");
            }
        });


    }
    @OnClick(R.id.facelogout)
    public void logoutFace(){
        LoginManager.getInstance().logOut();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            redirectSignupActivity();  // 세션 연결성공 시 redirectSignupActivity() 호출
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
            Log.i("myTag", String.valueOf(exception));

            setContentView(R.layout.activity_login); // 세션 연결이 실패했을때
        }                                            // 로그인화면을 다시 불러옴
    }

    protected void redirectSignupActivity() {       //세션 연결 성공 시 SignupActivity로 넘김
        final Intent intent = new Intent(this, KakaoSignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    private void redirectJoinActivity() {
        /**
         * 연동을 성공하는 순간, 회원가입은 된 것.
         * 앱에서 사용할 닉네임만 따로 수정할 수 있도록 이동하는 것
         */

        Intent intent = new Intent(this, JoinActivity.class);
        intent.putExtra("login","facebook");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
