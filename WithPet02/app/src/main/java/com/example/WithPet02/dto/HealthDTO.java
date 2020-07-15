package com.example.WithPet02.dto;

public class HealthDTO {
    private int h_num;
    private int h_pet;
    private String h_location;
    private String h_date;

    public HealthDTO(int h_num, int h_pet, String h_location, String h_date) {
        this.h_num = h_num;
        this.h_pet = h_pet;
        this.h_location = h_location;
        this.h_date = h_date;
    }

    public int getH_num() {
        return h_num;
    }

    public void setH_num(int h_num) {
        this.h_num = h_num;
    }

    public int getH_pet() {
        return h_pet;
    }

    public void setH_pet(int h_pet) {
        this.h_pet = h_pet;
    }

    public String getH_location() {
        return h_location;
    }

    public void setH_location(String h_location) {
        this.h_location = h_location;
    }

    public String getH_date() {
        return h_date;
    }

    public void setH_date(String h_date) {
        this.h_date = h_date;
    }
}
