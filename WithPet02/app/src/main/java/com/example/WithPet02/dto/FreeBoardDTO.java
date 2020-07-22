package com.example.WithPet02.dto;

import java.io.Serializable;
import java.util.Date;

public class FreeBoardDTO implements Serializable {
    private int f_num;
    private String f_tel, f_title, f_content, f_file;
    private Date f_date;

    public FreeBoardDTO() {}

    public FreeBoardDTO(int f_num, String f_tel, String f_title, String f_content, String f_file, Date f_date) {
        super();
        this.f_num = f_num;
        this.f_tel = f_tel;
        this.f_title = f_title;
        this.f_content = f_content;
        this.f_file = f_file;
        this.f_date = f_date;
    }

    public int getF_num() {
        return f_num;
    }

    public void setF_num(int f_num) {
        this.f_num = f_num;
    }

    public String getF_tel() {
        return f_tel;
    }

    public void setF_tel(String f_tel) {
        this.f_tel = f_tel;
    }

    public String getF_title() {
        return f_title;
    }

    public void setF_title(String f_title) {
        this.f_title = f_title;
    }

    public String getF_content() {
        return f_content;
    }

    public void setF_content(String f_content) {
        this.f_content = f_content;
    }

    public String getF_file() {
        return f_file;
    }

    public void setF_file(String f_file) {
        this.f_file = f_file;
    }

    public Date getF_date() {
        return f_date;
    }

    public void setF_date(Date f_date) {
        this.f_date = f_date;
    }

}
