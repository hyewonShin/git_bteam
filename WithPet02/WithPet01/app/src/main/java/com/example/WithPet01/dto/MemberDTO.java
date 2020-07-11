package com.example.WithPet02.dto;

import java.io.Serializable;

//회원 정보 DTO
public class MemberDTO implements Serializable {
    String m_tel;
    String m_pw;
    String m_name;
    String m_email;
    String m_kakao;
    String m_naver;
    String m_pic;
    String m_grade;

    public MemberDTO() {}

    // 암호 없이 멤버정보를 가져올때
    public MemberDTO(String m_tel, String m_name, String m_email, String m_kakao, String m_naver, String m_pic, String m_grade) {
        this.m_tel = m_tel;
        this.m_name = m_name;
        this.m_email = m_email;
        this.m_kakao = m_kakao;
        this.m_naver = m_naver;
        this.m_pic = m_pic;
        this.m_grade = m_grade;
    }

    // 이메일로 회원가입시 데이터베이스에 멤버정보를 추가 할 때
    public MemberDTO(String m_tel, String m_pw, String m_name, String m_email, String m_kakao, String m_naver, String m_pic, String m_grade) {
        this.m_tel = m_tel;
        this.m_pw = m_pw;
        this.m_name = m_name;
        this.m_email = m_email;
        this.m_kakao = m_kakao;
        this.m_naver = m_naver;
        this.m_pic = m_pic;
        this.m_grade = m_grade;
    }

    public String getM_tel() {
        return m_tel;
    }

    public void setM_tel(String m_tel) {
        this.m_tel = m_tel;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_email() {
        return m_email;
    }

    public void setM_email(String m_email) {
        this.m_email = m_email;
    }

    public String getM_grade() {
        return m_grade;
    }

    public void setM_grade(String m_grade) {
        this.m_grade = m_grade;
    }

    public String getM_pw() {
        return m_pw;
    }

    public void setM_pw(String m_pw) {
        this.m_pw = m_pw;
    }

    public String getM_pic() {
        return m_pic;
    }

    public void setM_pic(String m_pic) {
        this.m_pic = m_pic;
    }

    public String getM_kakao() {
        return m_kakao;
    }

    public void setM_kakao(String m_kakao) {
        this.m_kakao = m_kakao;
    }

    public String getM_naver() {
        return m_naver;
    }

    public void setM_naver(String m_naver) {
        this.m_naver = m_naver;
    }
}
