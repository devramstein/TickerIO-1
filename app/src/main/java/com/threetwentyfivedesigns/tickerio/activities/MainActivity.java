package com.threetwentyfivedesigns.tickerio.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.threetwentyfivedesigns.tickerio.R;
import com.threetwentyfivedesigns.tickerio.theme.ThemeHelper;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    // Instances of Items
    private Toolbar toolbar;
    private TextView currLoc;
    private TextView currTime;
    private TextView currDate;
    private TextView zuluDate;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        String themePref = sharedPreferences.getString("themePref", ThemeHelper.DEFAULT_MODE);
        ThemeHelper.applyTheme(themePref);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.darkTextColor));
        setSupportActionBar(toolbar);

        // Get Julian Date - Local
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) % 100;
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        currDate = findViewById(R.id.julianDateLocal);
        currDate.setText("Local Date: " + year + dayOfYear);
        message = findViewById(R.id.message);

        //Set greeting
        String greeting = null;
        if(hour>= 12 && hour < 17){
            greeting = "Great Afternoon ";
        } else if(hour >= 17 && hour < 21){
            greeting = "Good Evening ";
        } else if(hour >= 21 && hour < 24){
            greeting = "Good Night ";
        } else {
            greeting = "Great Morning ";
        }
        message.setText("Have a " + greeting);

        // Get the Julian Day for UTC Time
        // More Info: https://earthsky.org/astronomy-essentials/universal-time
        Calendar utcCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        int year2 = utcCal.get(Calendar.YEAR) % 100;
        int dayOfYear2 = utcCal.get(Calendar.DAY_OF_YEAR);
        zuluDate = findViewById(R.id.julianDateZulu);
        zuluDate.setText("Zulu Date: " + year2 + dayOfYear2);

        // Gets User Location
        userLocation();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Gets location of user
    // Displays as String in Footer
    public void userLocation(){
        // Local Time Zone
        currLoc = findViewById(R.id.location);
        currTime = findViewById(R.id.timezone);

        TimeZone tz = TimeZone.getDefault();
        currLoc.setText("Time Zone Location: " + tz.getID());
        currTime.setText("Your Time Zone: " + tz.getDisplayName(false, TimeZone.SHORT) + " (Local)");
    }
} // End of Class
