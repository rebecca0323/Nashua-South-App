//Made by Rebecca Zhu
//purpose is to make the Calendar Screen for inserting activities

package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//imports for all the widgets used in the XML
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

public class Calendar extends AppCompatActivity {
    //widgets from layout
    private TextView thedate;
    private CalendarView actcal;

    //method that runs when this activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar); //links the XML sheet

        //finds the widgets
        thedate = findViewById(R.id.goinsertact);
        actcal = findViewById(R.id.ActivityCal);
        //when a new date is pressed, update the textbook with the date
        actcal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                thedate.setText(date);
            }
        });
    }

    //for the menu bar in the upper right hand corner
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); //allows to manage the menu bar options
        inflater.inflate(R.menu.insert_activity_menu, menu); //uses the layout I made in the XML resource documents
        return true;
    }

    //for the menu bar in the upper right hand corner
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.insert_menu){ //if the person wants to insert an activity on a particular date
            Intent intent = new Intent(this, CalendarActivity.class); //allows to bring user to a new activity page
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
