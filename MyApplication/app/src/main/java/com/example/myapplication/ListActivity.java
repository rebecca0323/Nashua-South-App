//Made by Rebecca Zhu
//purpose is to display the activites in the Firebase Database

package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//Widgets from Android studio
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

//imports from Google's firebase
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    //method that creates the menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); //used to make menu
        inflater.inflate(R.menu.cal_menu, menu); //using XML resource document to create/display menu
        return true;
    }

    //method that allows menu options to be clicked and do something
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.calinsert){ //if user chooses to insert an activity
            Intent intent = new Intent(this, Calendar.class); //leads to calendar page
            startActivity(intent);
            return true;
        }
        else if(item.getItemId() == R.id.logout_menu){ //user chooses to log out
            AuthUI.getInstance()
                    .signOut(this) //sign out
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(getBaseContext(), HomePage.class); //goes back to the home page
                            startActivity(intent);
                        }
                    });
            FirebaseUtil.detachListener(); //detaches the listener so that it doesn't have to keep checking for updates from database
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //when user doesn't sign out but leaves this screen
    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener(); //detaches the listener so that it doesn't have to keep checking for updates from database
    }

    //when user returns to this screen
    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.openFbReference("Eblock", this); //opens firebase reference from FirebaseUtil class
        RecyclerView rvActivities = findViewById(R.id.theActivities);
        final EblockAdapter adapter = new EblockAdapter(); //instantiates a new Adapter to update the List of activities
        rvActivities.setAdapter(adapter); //displaying the activities
        LinearLayoutManager actLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvActivities.setLayoutManager(actLayoutManager); //displaying activities
        FirebaseUtil.attachListener(); //attaches listener so that it checks to see if database was updated
    }
}
