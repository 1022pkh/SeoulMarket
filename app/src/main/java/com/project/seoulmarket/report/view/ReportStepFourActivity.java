package com.project.seoulmarket.report.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.seoulmarket.R;
import com.project.seoulmarket.dialog.DialogRegister;
import com.project.seoulmarket.main.view.MainTabActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportStepFourActivity extends AppCompatActivity {

    @BindView(R.id.itemImg1)
    ImageView itemImg1;
    @BindView(R.id.itemImg2)
    ImageView itemImg2;
    @BindView(R.id.itemImg3)
    ImageView itemImg3;
    @BindView(R.id.itemImg4)
    ImageView itemImg4;
    @BindView(R.id.itemImg5)
    ImageView itemImg5;
    @BindView(R.id.itemImg6)
    ImageView itemImg6;
    @BindView(R.id.imgArea)
    LinearLayout imgArea;

    final int REQ_CODE_SELECT_IMAGE=100;
    String[] imgURL = new String[6];
    int imgCount = 0;


    private DialogRegister dialog_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_step_four);
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
        View mCustomView = mInflater.inflate(R.layout.actionbar_back_layout, null);

        TextView actionbarTitle = (TextView)mCustomView.findViewById(R.id.mytext);
        actionbarTitle.setText("마켓 제보");
        ImageView backBtn = (ImageView) mCustomView.findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);


    }

    @OnClick(R.id.registerServer)
    public void registerServer(){
        WindowManager.LayoutParams registerParams;
        dialog_Register = new DialogRegister(ReportStepFourActivity.this, registerEvent,registerCancelEvent);

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
            Toast.makeText(getApplicationContext(),"제보 완료!",Toast.LENGTH_SHORT).show();
            /**
             * 성공시 메인페이지로 이동한다.
             */
            Intent intent = new Intent(ReportStepFourActivity.this, MainTabActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }

    };

    private View.OnClickListener registerCancelEvent = new View.OnClickListener() {
        public void onClick(View v) {
            dialog_Register.dismiss();
        }

    };

    @OnClick(R.id.imgArea)
    public void getImg(){
        // 사진 갤러리 호출
        if(imgCount < 6){

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
        }
        else{
            Toast.makeText(getApplicationContext(),"최대 이미지 6장입니다.",Toast.LENGTH_SHORT).show();
        }
    }

    // 선택된 이미지 가져오기
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    String name_Str = getImageNameToUri(data.getData());

                    Log.i("myTag",name_Str);

                    imgURL[imgCount] = name_Str;
                    //이미지 데이터를 비트맵으로 받아온다.
//                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    AssetFileDescriptor afd = getContentResolver().openAssetFileDescriptor(data.getData(), "r");
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inSampleSize = 4;
                    Bitmap image_bitmap = BitmapFactory.decodeFileDescriptor(afd.getFileDescriptor(), null, opt);


                    //배치해놓은 ImageView에 set
                    if(imgCount == 0)
                        itemImg1.setImageBitmap(image_bitmap);
                    else if(imgCount == 1)
                        itemImg2.setImageBitmap(image_bitmap);
                    else if(imgCount == 2)
                        itemImg3.setImageBitmap(image_bitmap);
                    else if(imgCount == 3)
                        itemImg4.setImageBitmap(image_bitmap);
                    else if(imgCount == 4)
                        itemImg5.setImageBitmap(image_bitmap);
                    else if(imgCount == 5)
                        itemImg6.setImageBitmap(image_bitmap);

                    imgCount++;

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
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

}
