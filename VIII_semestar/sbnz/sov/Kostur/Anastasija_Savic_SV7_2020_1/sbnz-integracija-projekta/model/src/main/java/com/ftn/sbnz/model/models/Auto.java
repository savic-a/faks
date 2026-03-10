package com.ftn.sbnz.model.models;

public class Auto {
    private int id;
    private int temp;
    private int pritisak;
    private int ulje;

    public Auto(int id, int temp, int pritisak, int ulje) {
        this.id = id;
        this.temp = temp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public void setPritisak(int pritisak) {
        this.pritisak = pritisak;
    }

    public void setUlje(int ulje) {
        this.ulje = ulje;
    }

    public int getId() {
        return id;
    }

    public int getTemp() {
        return temp;
    }

    public int getPritisak() {
        return pritisak;
    }

    public int getUlje() {
        return ulje;
    }
}
