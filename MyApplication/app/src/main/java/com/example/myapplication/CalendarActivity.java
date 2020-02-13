//Made by Rebecca Zhu
//purpose is to make the screen to insert an activity

package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

//imports for Android Studio Widgets and notifications
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.example.myapplication.Eblock;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//class that manages the calendar for South's activities
public class CalendarActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebase; //for the Firebase
    private DatabaseReference mDataRef; //data reference in order to receive information from the database
    private EditText eDate; //the widgets on the application page
    private FloatingActionButton notification;
    private EditText eTeacher;
    private EditText eDescription;
    private Eblock eb; //eblock object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        //opens a firebase reference to store the activities
        FirebaseUtil.openFbReference("Eblock", this);
        mFirebase = FirebaseUtil.getmFirebase();
        mDataRef = FirebaseUtil.getmDataRef();

        //finds the widgets and sets them to the variables so that they can be manipulated
        eDate = findViewById(R.id.txtDate);
        eTeacher = findViewById(R.id.txtTeacher);
        eDescription = findViewById(R.id.txtDescript);
        Intent intent = getIntent();
        Eblock eb = (Eblock) intent.getSerializableExtra("Eblock"); //allows the Firebase to store the Eblock objects
        if(eb == null){
            eb = new Eblock(); //creates a new one if it's not null, this means the user didn't clicked on it from the ListActivity screen
        }
        this.eb = eb;
        eDate.setText(eb.getDate()); //sets the text with what the user clicked on from the List Activity or blank if its new
        eTeacher.setText(eb.getTeacher());
        eDescription.setText(eb.getDescription());

        notification = findViewById(R.id.floatingActionButton); //button to add the notification

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //looks to see if it's been pressed
                String message = eDescription.getText().toString();
                String teacher = eTeacher.getText().toString();
                //making the notification pop up on the emulator
                NotificationCompat.Builder builder = new NotificationCompat.Builder(CalendarActivity.this)
                        .setSmallIcon(R.drawable.ic_android_black_24dp)
                        .setContentTitle(message) //sets the actual info of the notification
                        .setContentText(teacher)
                        .setAutoCancel(true);

                //makes an intent that allows notification to go through, given it has permission
                Intent intent = new Intent(CalendarActivity.this, NotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Title", message);
                intent.putExtra("Teacher", teacher);

                //needed to have notification to display on emulator
                PendingIntent pendingIntent = PendingIntent.getActivity(CalendarActivity.this,
                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                //builds notification
                builder.setContentIntent(pendingIntent);
                //makes a list of notifications if there are multiple
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                //makes sure notifications are compatible, since this is a relatvely new emulator, needs a notification channel
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    String channelId = "Your_channel_id";
                    NotificationChannel channel = new NotificationChannel( //builds a notification channel to view the notifications
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(channel);
                    builder.setChannelId(channelId);
                }
                //allows user to see notification
                notificationManager.notify(0, builder.build());
            }
        });
    }

    //deals with what happens when a user selects an item from the menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.save_menu) { //user chooses to save the activity
            saveEblock();
            Toast.makeText(this, "Eblock Added!", Toast.LENGTH_LONG).show(); //shows up on the screen to let the user know it's successful
            clean(); //resets the text boxes
            backToList(); //goes back to the list of activities to see that the activity has been added
            return true;
        }
        else if(item.getItemId() == R.id.delete_menu) { //user wants to delete
            deleteEblock(); //calls the delete eblock method
            Toast.makeText(this, "Deal Deleted", Toast.LENGTH_LONG).show(); //toasts to let user know it worked
            backToList(); //goes back to the list of activities to see that it actually isn't there
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

    //creates the menus for the users
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); //menu inflator is used in android studio to manage the menu in the top right corner of the emulator
        inflater.inflate(R.menu.save_menu, menu); //using the XML resource document to make the menu
        return true;
    }

    //saves the Eblock to the Firebase database
    private void saveEblock(){
        eb.setDate(eDate.getText().toString()); //sets the instance variables of the eblock
        eb.setTeacher(eTeacher.getText().toString());
        eb.setDescription(eDescription.getText().toString());
        if(eb.getId() == null){
            mDataRef.push().setValue(eb); //push method makes a new object in the firebase database
        }
        else{
            mDataRef.child(eb.getId()).setValue(eb); //this changes an existing activity, so it must find its unique id first
        }
    }

    //deletes the eblock from the firebase
    private void deleteEblock(){
        if(eb == null){ //user is trying to delete an empty activity
            Toast.makeText(this, "Please save before deleting!", Toast.LENGTH_LONG).show();
            return;
        }
        else{
            mDataRef.child(eb.getId()).removeValue(); //removes it from the firebase database
        }
    }

    //brings user back to list activity
    private void backToList(){
        Intent intent = new Intent(this, ListActivity.class); //intent allows going from screen to screen
        startActivity(intent);
    }

    //resets all the text boxes so that user can add a new one when clicked again
    private void clean(){
        eDate.setText("");
        eTeacher.setText("");
        eDescription.setText("");
        eDate.requestFocus(); //makes cursor go to the first editText
    }
}
