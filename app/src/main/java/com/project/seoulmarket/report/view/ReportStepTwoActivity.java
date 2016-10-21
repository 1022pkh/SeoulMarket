package com.project.seoulmarket.report.view;

import android.app.TimePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.project.seoulmarket.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportStepTwoActivity extends AppCompatActivity implements com.andexert.calendarlistview.library.DatePickerController {

    @BindView(R.id.startTimeArea)
    LinearLayout startTimeArea;
    @BindView(R.id.endTimeArea)
    LinearLayout endTimeArea;
    @BindView(R.id.startHour)
    TextView startHour;
    @BindView(R.id.startMinute)
    TextView startMinute;
    @BindView(R.id.endHour)
    TextView endHour;
    @BindView(R.id.endMinute)
    TextView endMinute;
    @BindView(R.id.pickerView)
    com.andexert.calendarlistview.library.DayPickerView pickerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_step_two);

        ButterKnife.bind(this);
        pickerView.setController(this);

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

        /**
         *
         */

    }


    @OnClick(R.id.startTimeArea)
    public void setStartTime(){
        TimePickerDialog dialog = new TimePickerDialog(this, startListener, 00, 00, false);
        dialog.show();
    }

    @OnClick(R.id.endTimeArea)
    public void setEndTime(){
        TimePickerDialog dialog = new TimePickerDialog(this, endListener, 00, 00, false);
        dialog.show();

    }

    private TimePickerDialog.OnTimeSetListener startListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // 설정버튼 눌렀을 때
            if(hourOfDay < 10)
                startHour.setText("0"+String.valueOf(hourOfDay));
            else
                startHour.setText(String.valueOf(hourOfDay));

            if(minute < 10)
                startMinute.setText("00");
            else
                startMinute.setText(String.valueOf(Math.round(minute*0.1)*10));
        }
    };


    private TimePickerDialog.OnTimeSetListener endListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // 설정버튼 눌렀을 때
            if(hourOfDay < 10)
                endHour.setText("0"+String.valueOf(hourOfDay));
            else
                endHour.setText(String.valueOf(hourOfDay));

            if(minute < 10)
                endMinute.setText("00");
            else
                endMinute.setText(String.valueOf(Math.round(minute*0.1)*10));
        }
    };

    @Override
    public int getMaxYear()
    {
        return 2017;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day)
    {
        Log.i("myTag","Day Selected "+day + " / " + month + " / " + year);
    }

    @Override
    public void onDateRangeSelected(com.andexert.calendarlistview.library.SimpleMonthAdapter.SelectedDays<com.andexert.calendarlistview.library.SimpleMonthAdapter.CalendarDay> selectedDays) {
        Log.i("myTag","Date range selected "+ selectedDays.getFirst().toString() + " --> " + selectedDays.getLast().toString());
    }

}
