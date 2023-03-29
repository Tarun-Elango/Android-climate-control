package com.example.a390cc;
//class for the testing database
public class tabletwo {
    private int id;
    private String temp;
    private String hum;

    public tabletwo(int id, String temp, String hum) {
        this.id = id;
        this.temp = temp;
        this.hum = hum;
    }

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
        return "tabletwo{" +
                "id=" + id +
                ", temp='" + temp + '\'' +
                ", hum='" + hum + '\'' +
                '}';
    }
}
