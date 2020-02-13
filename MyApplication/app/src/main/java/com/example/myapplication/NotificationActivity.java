//Made by Rebecca Zhu
//purpose is to manage the Notifications made in this application

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

//imports for the Android Studio widgets
import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {
    //each notification has a txt, title, teacher once clicked
    private TextView txt;
    private String title;
    private String teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification); //find the XML layout file with the screen

        //finds the textView from the layout file
        txt = findViewById(R.id.notification);
        title = getIntent().getStringExtra("Title"); //adds the title of the notfication
        teacher = getIntent().getStringExtra("Teacher"); //adds the teacher to the notification

        txt.setText(title + "\n\n" + teacher); //sets the text to the title and teacher

    }
}
