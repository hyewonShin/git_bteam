package com.example.WithPet02.view.MyPet.spread;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.WithPet02.R;
import com.example.WithPet02.dto.CalenderDTO;
import com.example.WithPet02.dto.DiagnosisDTO;

import java.util.ArrayList;


public class Spread extends AppCompatActivity {
    private static final String TAG = "Spread";
    //해당 달력의 년 월 일 가져오기
    public int Syear;
    public int Smonth;
    public int Sdate;

    //DB의 년월일 가져오기
    public int Dyear;
    public int Dmonth;
    public int Ddate;

    //생성자
    public Spread() {

    }

    //초기화하며 해당 달력의 년 월 일 가져오기
    public Spread(int year, int month, int date) {
        Syear = year;
        Smonth = month;
        Sdate = date;
    }//Spread

    //DB의 년 월 일 받아오기
    public void spreadInsert(int year, int month, int date) {
        Dyear = year;
        Dmonth = month;
        Ddate = date;
    }//spreadInsert

    //검진기록과 DB일정 뜨게 하는 메소드
    public void medical_examination(LayoutInflater inflater, View view, Context frgContext, ArrayList<DiagnosisDTO> list, ArrayList<CalenderDTO> calList) {
        int dyear = 0;
        int dmonth = 0;
        int ddate= 0;
        Log.d(TAG, "DBSpread: " + list.get(0).getD_date() + list.get(1).getD_date());

        //검진기록
        for (int i = 0; i < list.size(); i++){
            String date = list.get(i).getD_date();
            String[] cutDate = date.split("-");
            String[] cutingDay = cutDate[2].split(" ");

            dyear = Integer.parseInt(cutDate[0]);
            dmonth = Integer.parseInt(cutDate[1]);
            ddate = Integer.parseInt(cutingDay[0]);

            if(Syear == dyear && Smonth == dmonth) {
                if(Sdate == (ddate)) {
                    inflater.inflate(R.layout.item_medical_examine, (ViewGroup) view, true);

                }//if
            }//if
        }//for

        //DB일정
        for (int j=0; j<calList.size(); j++) {
            int calYear = calList.get(j).getYear();
            int calMonth = calList.get(j).getMonth();
            int calDate = calList.get(j).getDate();
            String calContent = calList.get(j).getContent();

            if(Syear == calYear && Smonth == calMonth) {
                if(Sdate == (calDate)) {
                    //동적 뷰생성
                    LinearLayout layout = view.findViewById(R.id.checked); //넣어줄 레이아웃 설정
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,1f);
                    TextView viewText = new TextView(frgContext);
                    Log.d(TAG, "medical_examination: " + calContent);
                    //Content길이에 따라 값 정해주기
                    if( calContent.length() >= 2 ) {
                        viewText.setText("· " + calContent.substring(0,2) + "...");
                    } else if ( calContent.length() < 2 ) {
                        viewText.setText("· " + calContent);
                    } else if( calContent == null) {
                        viewText.setText("· " + "");
                    }//if
                    Log.d(TAG, "calListCheck: " + calList.get(j).getContent());
                    viewText.setLayoutParams(layoutParams);
                    layout.addView(viewText);
                }//if
            }//if
        }//for
    }//medical_examination


    /* 캘린더 테이블 생성 sql
    create table calender (
            c_tel varchar2(20),
    c_num number,
    c_year number,
    c_month number,
    c_date number,
    constraint calender_c_tel_fk foreign key(c_tel) references member (m_tel),
    constraint calender_c_num_pk primary key (c_num)
    );

    commit*/

    /*캘린더 시퀀스
    create sequence c_seq
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue*/

}//class
