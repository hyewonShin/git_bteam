package com.example.WithPet02.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.example.WithPet02.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePickerActivity extends AppCompatActivity {

    private int mYear =0, mMonth=0, mDay=0;
    private String birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_date_picker);

        Calendar calendar = new GregorianCalendar();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        Log.d("데이트픽커1", "년: " + mYear);
        Log.d("데이트픽커1", "월: " + mMonth);
        Log.d("데이트픽커1", "일: " + mDay);

        if(getIntent().getStringExtra("birth") != null){
            birth = getIntent().getStringExtra("birth");
            mYear = Integer.parseInt(birth.substring(0, 4));
            mMonth = Integer.parseInt(birth.substring(5, 7)) - 1;
            mDay = Integer.parseInt(birth.substring(8));

            Log.d("데이트픽커2", "년: " + mYear);
            Log.d("데이트픽커2", "월: " + mMonth);
            Log.d("데이트픽커2", "일: " + mDay);
        }

        DatePicker datePicker = findViewById(R.id.vDatePicker);

        datePicker.init(mYear, mMonth, mDay,mOnDateChangedListener);

    }

    public void mOnClick(View v){
        Intent intent = new Intent();
        intent.putExtra("mYear",mYear);
        intent.putExtra("mMonth", mMonth);
        intent.putExtra("mDay", mDay);
        setResult(RESULT_OK, intent);
        finish();
    }

    DatePicker.OnDateChangedListener mOnDateChangedListener = new DatePicker.OnDateChangedListener(){
        @Override
        public void onDateChanged(DatePicker datePicker, int yy, int mm, int dd) {
            mYear = yy;
            mMonth = mm;
            mDay = dd;
        }

    };

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("mYear",mYear);
        intent.putExtra("mMonth", mMonth);
        intent.putExtra("mDay", mDay);
        setResult(RESULT_OK, intent);
        finish();
    }
}