//Made by Rebecca Zhu
//purpose is to display the bell schedule

package com.example.myapplication;

//import Android Studio Activity
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Schedule extends AppCompatActivity {

    //to show the screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule); //connects to the XML layout for the bell schedule
    }
}
