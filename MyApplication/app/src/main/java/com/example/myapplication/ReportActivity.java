//Made by Rebecca Zhu
//Purpose is to write the suspicious activity into a file

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

//android studio widgets
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

//import java writing to file
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReportActivity extends AppCompatActivity {
    private static final String FILE_NAME = "suspiciousActivity.txt"; //name of the file where suspicious activity is reported
    private Button submit; //widget
    private EditText date, location, description; //widget for user inputs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report); //connects to the XML layout file

        //finds the widgets' ids from teh XML files
        submit = findViewById(R.id.submit);
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
    }

    //when the submit button is clicked
    public void button(View view){
        //turns the user's input into a string
        String text = date.getText().toString() + " - " + location.getText().toString() + " - " + description.getText().toString();
        FileOutputStream fos = null; //similar to buffered writer for Android Studio

        try{
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE); //makes the file
            fos.write(text.getBytes()); //adds the string into the file

            date.getText().clear(); //clears the edit texts so that the user can submit something else
            location.getText().clear();
            description.getText().clear();
            Toast.makeText(this, "Saved!", Toast.LENGTH_LONG).show(); //gives user toast so they know it worked
            System.out.println(getFilesDir() + "/" + FILE_NAME); //checking where the file is
        } catch(FileNotFoundException e){ //exception if the file isn't found
            e.printStackTrace();
        } catch(IOException e){ //other exceptions
            e.printStackTrace();
        } finally{
            if (fos != null){ //if it is instantiated then close it
                try{
                    fos.close();
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        }
    }
}
