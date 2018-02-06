package com.example.android.cuacaapi.entity;

/**
 * Created by ASUS on 2/4/2018.
 */

public class Cuaca {

    //TODO: 5
    private String date;
    private String temp;
    private String weather;

    // TODO: 6
    public Cuaca(String date, String temp, String weather) {
        this.date = date;
        this.temp = temp;
        this.weather = weather;
    }

    //TODO: 7
    public String getDate() {
        return date;
    }

    public String getTemp() {
        return temp;
    }

    public String getWeather() {
        return weather;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
