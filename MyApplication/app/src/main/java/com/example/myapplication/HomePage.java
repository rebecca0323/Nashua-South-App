//Made by Rebecca Zhu
//purpose is to design the home page for the app

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

//widgets from Android Studio
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

    }

    //methods below use intents to navigate to different screens in app, more efficient than menu
    //goes to contact page
    public void contactPage(View view){
        Intent intent = new Intent(getBaseContext(), ContactPage.class);
        startActivity(intent);
    }

    //goes to calendar page
    public void calendarPage(View view){
        Intent intent = new Intent(getBaseContext(), ListActivity.class);
        startActivity(intent);
    }

    //goes to sports page
    public void sportsPage(View view){
        Intent intent = new Intent(getBaseContext(), SportPage.class);
        startActivity(intent);
    }

    //goes to x2 login page
    public void x2Page(View view){
        Intent intent = new Intent(getBaseContext(), X2Login.class);
        startActivity(intent);
    }

    //goes to reporting suspicious activity page
    public void reportPage(View view){
        Intent intent = new Intent(getBaseContext(), ReportActivity.class);
        startActivity(intent);
    }

    //goes to lunch page
    public void lunchPage(View view){
        Intent intent = new Intent(getBaseContext(), LunchMenu.class);
        startActivity(intent);
    }
}
