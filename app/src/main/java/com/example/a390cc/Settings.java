package com.example.a390cc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

//this activity has the user manual text
public class Settings extends AppCompatActivity {
    TextView settingText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settingText = findViewById(R.id.settingText);
        loadText();
    }
    private void loadText() {
        //TODO:Please fill the sentence in part 3
        String s = "HOW TO USE\n\n"+
                "1. Home Page\nDisplays the temperature and the humidity of the room. Press the button 'Update' to obtain the newest readings.\n\n"+
                "2. Notify Page\nThis page is to set the acceptable range of the temperature and humidity. \nEnter the desire range for the temperature on the first row. i.e. '19' to '26' degree. The unit is in degree Celsius.\n" +
                "Enter the desire range for the humidity on the second row. i.e. '45' to '60' percent. The unit is in percent. \nPress 'Save' to confirm the new range.\n\n" +
                "3. Analytics Page\nThis page displays the plot of temperature on the top and the plot of the humidity on the bottom. User gets to specify a time limit for the data that is being displayed  \n\n" +
                "4. The analytics also contains the suggestions for both home environment, that can be viewed at any time with the suggestions, regarding the HVAC's relative system efficiency and how to maintain ideal temperature and humidity conditions for home environment,  available at all times \n\n"+
                "5. Control Page\nThe control page will be able to perform some commands on the heating system(LED for this project implementation).\n";

        settingText.setMovementMethod(new ScrollingMovementMethod());
        settingText.setText(s);
    }
}