package com.example.a390cc;

//class for led buttons, used in control class, needed for firebase connection
public class LEDButtons {

    protected String ledColor;

    public LEDButtons() {
    }

    public String getLed() {
        return ledColor;
    }

    public void setLed(String led) {
        this.ledColor = led;
    }
}
