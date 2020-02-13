//Made by Rebecca Zhu
//purpose is to make the Contage Page Screen

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

//imports for Android Studio Widgets
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.*;

import android.os.Bundle;

public class ContactPage extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1; //hardcoded permission to call using the emulator
    private Button call; //widget from the XML layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);

        call = findViewById(R.id.callSchool);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //when user clicks
                Intent intent = new Intent(Intent.ACTION_CALL); //intent to call
                intent.setData(Uri.parse("tel:6039661100")); //south's #
                //has to see if it has permission
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    requestForCallPermission(); //calls for permission
                    return;
                }
                else{ //has permission and can call, so make call
                    startActivity(intent);
                }
            }
        });
    }

    //goes to the twitter page
    public void goTwitter(View view){
        //url in the webview is the twitter url
        Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/nashuaschools"));
        startActivity(browseIntent);
    }

    //gets permission to call from emulator
    public void requestForCallPermission() {
        //has permission
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)) {
        }
        else { //ask for permission
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_REQUEST_CODE);
        }
    }

    //goes to the Bell Schedule screen
    public void Bell(View view){
        Intent intent = new Intent(getBaseContext(), Schedule.class); //intent to go to the Schedule.class screen which displays the schedules
        startActivity(intent);
    }
}