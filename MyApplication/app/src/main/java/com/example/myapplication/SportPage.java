//Made by Rebecca Zhu
//purpose is for South's Sports Page

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

//Android Studio widgets imports
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class SportPage extends AppCompatActivity {
    //widget to display the website
    private WebView southSports;

    //to view the screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_page); //connects to the XML layout page

        //finds the widget based on its id in the XML file
        southSports = findViewById(R.id.sports);

        //settings for the WebView
        southSports.getSettings().setLoadWithOverviewMode(true);
        southSports.getSettings().setUseWideViewPort(true);
        WebSettings webSettings = southSports.getSettings();
        webSettings.setJavaScriptEnabled(true); //enables dynamic rendering of the website
        southSports.loadUrl("https://www.nashuasouthathletics.com/"); //link to Nashua South's sports page
    }
}
