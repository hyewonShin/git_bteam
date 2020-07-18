package com.example.WithPet02.dto;

public class LocationDTO {
    String name;
    double longtitude,latitude; //경도위도
    String num;
    String addr;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public LocationDTO(String name, String num, String addr){
        this.name = name;
        this.num = num;
        this.addr = addr;
    }
    public LocationDTO(String name, double longtitude, double latitude, String num, String addr) {
        this.name = name;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.num = num;
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
