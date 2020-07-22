package com.example.WithPet02.view.MyPet.model;

import com.example.WithPet02.view.MyPet.Util.DateUtil;

public class CalendarHeader extends ViewModel {

    String header;
    long mCurrentTime;

    public CalendarHeader() {
    }//CalendarHeader()

    public String getHeader() {
        return header;
    }//getHeader()

    public void setHeader(long time) {

        String value = DateUtil.getDate(time, DateUtil.CALENDAR_HEADER_FORMAT);
        this.header = value;

    }//setHeader()

    public String setHeader2(long time) {

        String value = DateUtil.getDate(time, DateUtil.CALENDAR_HEADER_FORMAT);
        return value;
    }//setHeader()

    public void setHeader(String header) {

        this.header = header;

    }//setHeader()

}//class
