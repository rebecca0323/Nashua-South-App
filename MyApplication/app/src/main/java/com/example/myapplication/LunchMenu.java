//Made by Rebecca Zhu
//Purpose is to make the screen for the lunch menu page

package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

public class LunchMenu extends AppCompatActivity {
    //widgets on the page
    private CalendarView lunchCalendar;
    private TextView lunch;
    private String[] lunchOptions; //array with options for lunch

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_menu);

        //finds the widgets from the layout file
        lunchCalendar = findViewById(R.id.lunchcal);
        lunch = findViewById(R.id.textlunch);
        //makes the lunch options an array of strings from South's lunch menu
        lunchOptions = new String[] {"Pasta w/Sauce, Meatballs", "Pulled Pork Nachos W/Toppings", "Chicken Teriyaki Sub, Baked Oven Fries",
                "Chicken Parm W/ Pasta, Dinner Roll", "Cheeseburger on w/g Bun", "Korean BBQ Chicken Drumsticks",
                "Panther Bowls (Popcorn Chix, Mashed Potato, Corn, Gravy)",
                "Baked Chicken Drumsticks", "Chicken Nachos or Soft Shell Tacos", "Homemade Soup Du Jour"};

        //checks if date was changed, which means it has to update the text with the lunch
        lunchCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) { //method from calendarView in Android Studio
                int index = (int) (Math.random()*(lunchOptions.length)); //choose random item
                //text to be displayed on the screen
                String mealOnDate = (month + 1) + "/" + dayOfMonth + "/" + year + ": " + lunchOptions[index];
                lunch.setText(mealOnDate); //sets the text to the textView
            }
        });

    }
}
