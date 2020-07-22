package com.example.WithPet02.view.MyPet.model;

import com.example.WithPet02.view.MyPet.Util.DateUtil;

import java.util.Calendar;

public class Day extends ViewModel {

    String day;

    public Day() {
    }//Day()

    public String getDay() {
        return day;
    }//getDay()

    public void setDay(String day) {
        this.day = day;
    }//setDay()

    // TODO : day에 달력일값넣기
    public void setCalendar(Calendar calendar){

        day = DateUtil.getDate(calendar.getTimeInMillis(), DateUtil.DAY_FORMAT);

    }//setCalendar
}//class
