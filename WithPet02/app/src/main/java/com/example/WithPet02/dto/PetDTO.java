package com.example.WithPet02.dto;

import java.io.Serializable;

public class PetDTO implements Serializable {
    //멤버변수
    private int p_num;
    private String p_name;
    private String p_tel;
    private String p_animal;
    private String p_a_animal;
    private String p_birth;
    private String p_pic;
    private String p_gender;

    public PetDTO(int p_num, String p_name, String p_tel, String p_animal, String p_a_animal, String p_birth, String p_pic, String p_gender) {
        this.p_num = p_num;
        this.p_name = p_name;
        this.p_tel = p_tel;
        this.p_animal = p_animal;
        this.p_a_animal = p_a_animal;
        this.p_birth = p_birth;
        this.p_pic = p_pic;
        this.p_gender = p_gender;
    }

    public int getP_num() {
        return p_num;
    }

    public void setP_num(int p_num) {
        this.p_num = p_num;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_tel() {
        return p_tel;
    }

    public void setP_tel(String p_tel) {
        this.p_tel = p_tel;
    }

    public String getP_animal() {
        return p_animal;
    }

    public void setP_animal(String p_animal) {
        this.p_animal = p_animal;
    }

    public String getP_a_animal() {
        return p_a_animal;
    }

    public void setP_a_animal(String p_a_animal) {
        this.p_a_animal = p_a_animal;
    }

    public String getP_birth() {
        return p_birth;
    }

    public void setP_birth(String p_birth) {
        this.p_birth = p_birth;
    }

    public String getP_pic() {
        return p_pic;
    }

    public void setP_pic(String p_pic) {
        this.p_pic = p_pic;
    }

    public String getP_gender() {
        return p_gender;
    }

    public void setP_gender(String p_gender) {
        this.p_gender = p_gender;
    }
}
