package com.example.WithPet02.dto;

import java.util.Date;

public class AlbumDTO {
    private int a_num, a_pet;
    private String a_title, a_content, a_file;
    private Date a_date;

    public AlbumDTO() {}

    public AlbumDTO(int a_num, int a_pet, String a_title, String a_content, String a_file, Date a_date) {
        super();
        this.a_num = a_num;
        this.a_pet = a_pet;
        this.a_title = a_title;
        this.a_content = a_content;
        this.a_file = a_file;
        this.a_date = a_date;
    }

    public int getA_num() {
        return a_num;
    }

    public void setA_num(int a_num) {
        this.a_num = a_num;
    }

    public int getA_pet() {
        return a_pet;
    }

    public void setA_pet(int a_pet) {
        this.a_pet = a_pet;
    }

    public String getA_title() {
        return a_title;
    }

    public void setA_title(String a_title) {
        this.a_title = a_title;
    }

    public String getA_content() {
        return a_content;
    }

    public void setA_content(String a_content) {
        this.a_content = a_content;
    }

    public String getA_file() {
        return a_file;
    }

    public void setA_file(String a_file) {
        this.a_file = a_file;
    }

    public Date getA_date() {
        return a_date;
    }

    public void setA_date(Date a_date) {
        this.a_date = a_date;
    }
}
