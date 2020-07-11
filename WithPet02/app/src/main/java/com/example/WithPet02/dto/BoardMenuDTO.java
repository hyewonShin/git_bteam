package com.example.WithPet02.dto;

public class BoardMenuDTO {
    String menu_name;
    int resid;

    //생성자 초기화
    public BoardMenuDTO(String menu_name, int resid) {
        this.menu_name = menu_name;
        this.resid = resid;
    }

    //getter & setter

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }
}
