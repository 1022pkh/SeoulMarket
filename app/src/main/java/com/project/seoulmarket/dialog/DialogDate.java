package com.project.seoulmarket.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.seoulmarket.R;
import com.project.seoulmarket.main.view.MainView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.nlmartian.silkcal.DatePickerController;
import me.nlmartian.silkcal.DayPickerView;
import me.nlmartian.silkcal.SimpleMonthAdapter;


/**
 * Created by KyoungHyun on 16. 5. 1..
 */
public class DialogDate extends Dialog implements DatePickerController {

    @BindView(R.id.sendPerson)
    Button sendBtn;
    @BindView(R.id.calendar_view)
    DayPickerView calendarView;
    @BindView(R.id.startDate)
    TextView startDate;
    @BindView(R.id.endDate)
    TextView endDate;

    int clickCheck=0;

    SimpleDateFormat dateFormat;
    String nowDateValue;
    String startDateValue;
    String endDateValue;


    MainView mainView;

    private View.OnClickListener sendPersonEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_date);

        ButterKnife.bind(this);

        calendarView.setController(this);
        sendBtn.setOnClickListener(sendPersonEvent);


        // 시스템으로부터 현재시간(ms) 가져오기
        long now = System.currentTimeMillis();
        // Data 객체에 시간을 저장한다.
        Date date = new Date(now);
        // 각자 사용할 포맷을 정하고 문자열로 만든다.
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strNow = dateFormat.format(date);

        nowDateValue = strNow;
        startDateValue = strNow;

        startDate.setText(nowDateValue);
        startDate.setTextColor(Color.rgb(200, 66, 60));

        date.setDate(date.getDate()+31);
        String strNextweek = dateFormat.format(date);

        endDateValue = strNextweek;
        endDate.setTextColor(Color.rgb(64, 84, 178));

        endDate.setText(endDateValue);


    }

    public DialogDate(Context context, MainView view, View.OnClickListener BtnEvent) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.mainView = view;
        this.sendPersonEvent = BtnEvent;
    }

    @Override
    public int getMaxYear() {
        return 0;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {

        String temp = year+"-"+(month+1)+"-"+day;

        if(clickCheck %2 == 0) {
//            Log.i("myTag",year+"-"+month+"-"+day);

//            SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());

            try {
                Date clickDateft = dateFormat.parse(temp);
                Date nowDateft = dateFormat.parse(nowDateValue);

                // 선택 날짜가 현재날짜보다 과거이다
                if(clickDateft.after(nowDateft) == false)
                    mainView.prevousStart();
                else{

                    startDateValue = temp;
                    startDate.setText(temp);
                    startDate.setTextColor(Color.rgb(64, 84, 178));
                    endDate.setTextColor(Color.rgb(200, 66, 60));
                    clickCheck++;

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        else{

            try {
                Date clickDateft = dateFormat.parse(temp);
                Date nowDateft = dateFormat.parse(nowDateValue);

                // 선택 날짜가 현재날짜보다 과거이다
                if(clickDateft.after(nowDateft) == false)
                    mainView.prevousStart();
                else{
                    endDateValue = temp;
                    endDate.setText(temp);
                    startDate.setTextColor(Color.rgb(200, 66, 60));
                    endDate.setTextColor(Color.rgb(64, 84, 178));
                    clickCheck++;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

        compareDate();
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

    }

    public void compareDate() {

        Log.i("myTag","in");

        try {
            Date start = dateFormat.parse(startDateValue);
            Date end = dateFormat.parse(endDateValue);

            if(start.after(end) == true)
            {
                String temp = startDateValue;
                startDateValue = endDateValue;
                endDateValue = temp;

                startDate.setText(startDateValue);
                endDate.setText(endDateValue);


                Log.i("myTag","2131");

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public String getStartDate(){
        return startDateValue;
    }
    public String getEndDate(){
        return endDateValue;
    }
}
