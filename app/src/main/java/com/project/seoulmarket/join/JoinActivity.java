package com.project.seoulmarket.join;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;
import com.project.seoulmarket.R;
import com.project.seoulmarket.application.GlobalApplication;
import com.project.seoulmarket.main.view.MainTabActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class JoinActivity extends AppCompatActivity {

    @BindView(R.id.profile_image)
    CircleImageView profile;
    @BindView(R.id.userNickname)
    EditText nickNameArea;

    private String usrToken;
    String loginMethod;
    Boolean nickNameCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        ButterKnife.bind(this);

        Log.i("myTag","In Join");

        Intent intent = getIntent();
        loginMethod =  intent.getExtras().getString("login");


        Log.i("myTag",loginMethod);

        /**
         * kakao를 통해 로그인했을 경우
         */
        if(loginMethod.equals("kakao")){
            kakaoRequestMe();
        }
        else if(loginMethod.equals("facebook")) {
            facebookRequestMe();
        }
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this

        // 여기서 부터는 알림창의 속성 설정
        builder.setTitle("회원 가입을 취소하시겠습니까?")        // 메세지 설정
                //  .setMessage("취소 시 연동된 앱의 닉네임으로 설정됩니다.")
                .setCancelable(true)        // 뒤로 버튼 클릭시 취소 가능 설정
                .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    // 확인 버튼 클릭시 설정
                    public void onClick(DialogInterface dialog, int whichButton){

                        if(GlobalApplication.loginInfo.getString("method", "").equals("kakao")){
                            //kakao 로그아웃
                            UserManagement.requestLogout(new LogoutResponseCallback() {
                                @Override
                                public void onCompleteLogout() {
                                }
                            });
                        }
                        else{
                            LoginManager.getInstance().logOut();
                        }

                        GlobalApplication.editor.putBoolean("Login_check", false);
                        GlobalApplication.editor.commit();

                        finish();

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                    // 취소 버튼 클릭시 설정
                    public void onClick(DialogInterface dialog, int whichButton){
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();    // 알림창 객체 생성
        dialog.show();    // 알림창 띄우기
    }

    /**
     * facebook 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void facebookRequestMe(){

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        try {

                            String id = (String) response.getJSONObject().get("id");//페이스북 아이디값
                            String name = (String) response.getJSONObject().get("name");//페이스북 이름
//                            String email = (String) response.getJSONObject().get("email");//이메일

//                            Log.i("myTag",id);
//                            Log.i("myTag",name);
//                            Log.i("myTag", String.valueOf(response));


                            nickNameArea.setText(name);

                            String thumnailImg = "http://graph.facebook.com/"+ id +"/picture?type=large";
                            Glide.with(JoinActivity.this)
                                    .load(thumnailImg)
                                    .into(profile);


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }


    /**
     * kakao 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void kakaoRequestMe() { //유저의 정보를 받아오는 함수
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                Log.i("myTag","error2");

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.i("myTag","error3");
            }

            @Override
            public void onNotSignedUp() {} // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

            @Override
            public void onSuccess(UserProfile userProfile) {  //성공 시 userProfile 형태로 반환

//                Log.i("myTag", String.valueOf(userProfile.getId()));
//                Log.i("myTag", String.valueOf(userProfile.getNickname()));
//                Logger.d("UserProfile : " + userProfile);

                usrToken = String.valueOf(userProfile.getId());
                String thumnailImg = userProfile.getThumbnailImagePath();

//                Log.i("myTag", thumnailImg);

                nickNameArea.setText(userProfile.getNickname());

                /**
                 * 받아온 User 데이터의 profileUrl을 Glide 라이브러리를 이용하여 이미지뷰에 삽입하는 코드입니다.
                 * Glide의 가장 기본적인 사용방법이니 익혀두시면 편합니다.
                 */
                Glide.with(JoinActivity.this)
                        .load(thumnailImg)
                        .into(profile);

            }
        });
    }

    @OnClick(R.id.doubleCheck)
    public void nickNameCheck(){
        Toast.makeText(getApplicationContext(),nickNameArea.getText(),Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.completeNickname)
    public void complete(){
        // TODO: 2016. 10. 18. 서버로 닉네임 업데이트 해줘야 함.
        GlobalApplication.editor.putString("nickname", String.valueOf(nickNameArea.getText()));
        GlobalApplication.editor.commit();

        Toast.makeText(getApplicationContext(),"회원가입 성공!",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainTabActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

}
