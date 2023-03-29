package com.example.a390cc;

//class for sensor value database
public class table {

private int id;
private String temp;
private String hum;

    public table(int id, String temp, String hum) {
        this.id = id;
        this.temp = temp;
        this.hum = hum;
    }
    public table(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    @Override
    public String toString() {
        return "table{" +
                "id='" + id + '\'' +
                ", temp='" + temp + '\'' +
                ", hum='" + hum + '\'' +
                '}';
    }
}
