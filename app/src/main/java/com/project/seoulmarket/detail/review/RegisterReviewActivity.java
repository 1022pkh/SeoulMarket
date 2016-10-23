package com.project.seoulmarket.detail.review;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.seoulmarket.R;
import com.project.seoulmarket.dialog.DialogCancel;
import com.project.seoulmarket.dialog.DialogRegister;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterReviewActivity extends AppCompatActivity {

    @BindView(R.id.addImgBtn)
    Button addImgBtn;
    @BindView(R.id.imgName)
    TextView imgName;


    DialogCancel dialogCancel;
    DialogRegister dialog_Register;

    final int REQ_CODE_SELECT_IMAGE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_review);

        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#F6D03F"));
        }

        ButterKnife.bind(this);


        /**
         * actionbar 설정
         */

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // ActionBar의 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));

        getSupportActionBar().setElevation(0); // 그림자 없애기

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.actionbar_close_layout, null);

        ImageView closeBtn = (ImageView) mCustomView.findViewById(R.id.closeBtn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warningOut();
            }
        });


        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);


    }

    @Override
    public void onBackPressed() {
        warningOut();
    }

    public void warningOut(){
        WindowManager.LayoutParams loginParams;
        dialogCancel = new DialogCancel(RegisterReviewActivity.this, moveDetailPage, remainPageEvent);

        loginParams = dialogCancel.getWindow().getAttributes();

        // Dialog 사이즈 조절 하기
        loginParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        loginParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogCancel.getWindow().setAttributes(loginParams);
        dialogCancel.show();
    }

    private View.OnClickListener moveDetailPage = new View.OnClickListener() {
        public void onClick(View v) {
            dialogCancel.dismiss();
            finish();
        }

    };

    private View.OnClickListener remainPageEvent = new View.OnClickListener() {
        public void onClick(View v) {
            dialogCancel.dismiss();
        }

    };

    @OnClick(R.id.addImgBtn)
    public void getImg(){
        // 사진 갤러리 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
    }

    // 선택된 이미지 가져오기
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Toast.makeText(getBaseContext(), "resultCode : "+resultCode,Toast.LENGTH_SHORT).show();

        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    String name_Str = getImageNameToUri(data.getData());

                    Log.i("myTag",name_Str);

                    imgName.setText(name_Str);
                    //이미지 데이터를 비트맵으로 받아온다.
//                    Bitmap image_bitmap 	= MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
//                    ImageView image = (ImageView)findViewById(R.id.imageView1);

                    //배치해놓은 ImageView에 set
//                    image.setImageBitmap(image_bitmap);

                    //Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();

                }
//                catch (FileNotFoundException e) { 		e.printStackTrace(); 			}
//                catch (IOException e)                 {		e.printStackTrace(); 			}
                catch (Exception e)		         {             e.printStackTrace();			}
            }
        }
    }

    // 선택된 이미지 파일명 가져오기
    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);


        return imgName;
    }

    @OnClick(R.id.registerReviewBtn)
    public void completeReview(){
        WindowManager.LayoutParams registerParams;
        dialog_Register = new DialogRegister(RegisterReviewActivity.this, registerEvent,registerCancelEvent);

        registerParams = dialog_Register.getWindow().getAttributes();

        // Dialog 사이즈 조절 하기
        registerParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        registerParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog_Register.getWindow().setAttributes(registerParams);

        dialog_Register.show();
    }

    private View.OnClickListener registerEvent = new View.OnClickListener() {
        public void onClick(View v) {
            dialog_Register.dismiss();
            Toast.makeText(getApplicationContext(),"후기 등록 완료!",Toast.LENGTH_SHORT).show();
            /**
             * 성공시 돌아간다.
             */
            finish();
        }

    };

    private View.OnClickListener registerCancelEvent = new View.OnClickListener() {
        public void onClick(View v) {
            dialog_Register.dismiss();
        }

    };
}
