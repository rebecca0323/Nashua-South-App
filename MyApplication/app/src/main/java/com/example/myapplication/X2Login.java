//Made by Rebecca Zhu
//purpose is to make the x2 login page to view South's grades portal

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

//Android Studio Widget imports
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//Jsoup import statements for Java HTML parsing
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class X2Login extends AppCompatActivity {
    //Widgets on the screen
    private TextView c1, c2, c3, c4, g1, g2, g3, g4, eblocks;
    private WebView mWebView;
    private Button eblockShow, show1, show2, show3, show4;
    private FloatingActionButton back;
    //will parse and get 9 grades (4 classes and 5 eblocks)
    private Grade[] gradeBook = new Grade[9];

    //to view the screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //connects to the XML layout page

        //finds the widgets based on their ids
        eblockShow = findViewById(R.id.showE);
        eblockShow.setVisibility(View.INVISIBLE); //makes buttons invisible until user logins in
        show1 = findViewById(R.id.show1); //finds the widgets based on their ids
        show2 = findViewById(R.id.show2);
        show3 = findViewById(R.id.show3);
        show4 = findViewById(R.id.show4);
        back = findViewById(R.id.back);

        //makes buttons invisible until user logins in
        show1.setVisibility(View.INVISIBLE);
        show2.setVisibility(View.INVISIBLE);
        show3.setVisibility(View.INVISIBLE);
        show4.setVisibility(View.INVISIBLE);

        //finds WebView to load x2
        mWebView = findViewById(R.id.x2webview);
        mWebView.getSettings().setLoadWithOverviewMode(true); //webview settings
        mWebView.getSettings().setUseWideViewPort(true);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); //makes it so that it can receive the javascript being dynamically embedded into webpage
        final MyJavaScriptInterface jInterface = new MyJavaScriptInterface(getApplicationContext()); //parsing HTML with Javascript
        mWebView.addJavascriptInterface(jInterface, "HtmlViewer"); //receiving the HTML
        mWebView.loadUrl("https://x2.nashua.edu/aspen/logon.do"); // x2 website to load

        mWebView.setWebViewClient(new WebViewClient() { //allows for the HTML to be downloaded

            //once page is loaded which means all the JavaScript is loaded in the HTML, then parse
            @Override
            public void onPageFinished(WebView view, String url) {
                //gets the HTML
                String jsurl = "javascript:window.HtmlViewer.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');";
                //Load HTML
                mWebView.loadUrl(jsurl);
                String html1234 = jInterface.getHtml();
                //unique HTML that is on the Aspen page with the grades, tries to find it in the HTML
                if(html1234 != null && html1234.contains("javascript:doParamSubmit(2100, document.forms['classListForm']")){
                    //sets all the buttons to be visible
                    eblockShow.setVisibility(View.VISIBLE);
                    show1.setVisibility(View.VISIBLE);
                    show2.setVisibility(View.VISIBLE);
                    show3.setVisibility(View.VISIBLE);
                    show4.setVisibility(View.VISIBLE);
                    back.show();
                    mWebView.setVisibility(View.GONE); //closes the webview to view buttons beneath it
                    setTheText(html1234); //will parse the html string to set the TextView to student's grades
                }
            }

        });
    }

    //method that uses Jsoup to parse HTML
    public void setTheText(String html){
        //Document = Jsoup Object
        Document doc = Jsoup.parse(html); //parses based on HTML tags

        //days for the Eblock loads
        String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        int counter = 0; //counter for which eblock day it is for the for loop

        //table id in aspen is called dataGrid, so it gets the HTML inside this table
        Element table = doc.getElementById("dataGrid");
        //gets the rows in the table
        Elements rows = table.select("tr");
        Elements tds = rows.select("td"); //gets all the cells in the rows
        String name = ""; //place holder for the teacher's name
        String grade = ""; //place holder for the student's grade
        int index = 0; // index for the Grade's array

        //for loop that makes the Grade objects and puts them into the array
        for(int i = 0; i < tds.size() - 1; i++){ //loops through all the table cells in the rows
            //all the Teacher names have commas in them, no other elements have commas, so can look for commas instead of an id
            if(tds.get(i).text().contains(",")){
                name = tds.get(i).text(); //name of the teacher is the table cell
                if((i+2) <= tds.size()){ //checks if there is a value at i+2 table cell
                    //Aspen's layout makes it so that grade and teacher are always 2 table cells apart
                    if(tds.get(i+2).text().equals("")){ //eblock, not a class
                        grade = days[counter]; //gets the day of the week
                        counter++;
                    }
                    else {
                        grade = tds.get(i+2).text(); //grade in the class
                    }
                }
                gradeBook[index] = new Grade(name, grade); //instantiates Grade object and places it in array
                index++;
            }
        }
    }

    //method for the button for first block
    public void show1(View view){
        //finds the widgets
        c1 = findViewById(R.id.class1);
        g1 = findViewById(R.id.g1);
        if(c1.getText().equals("")){ //info is hidden, so display info
            c1.setText(gradeBook[0].getTeacher()); //first grade object in the grade array is first block
            g1.setText(gradeBook[0].getGrade());
        }
        else{ //info is available, so make it hidden
            c1.setText("");
            g1.setText("");
        }
    }

    //method for the button for second block
    public void show2(View view){
        //identical logic as show 1

        //finds the widgets
        c2 = findViewById(R.id.class2);
        g2 = findViewById(R.id.g2);
        if(c2.getText().equals("")){ //info is hidden, so display info
            c2.setText(gradeBook[1].getTeacher()); //second grade object in the grade array is second block
            g2.setText(gradeBook[1].getGrade());
        }
        else{ //info is visible, make hidden
            c2.setText("");
            g2.setText("");
        }
    }

    //method for the button for third block
    public void show3(View view){
        c3 = findViewById(R.id.class3); //finds the widgets
        g3 = findViewById(R.id.g3);
        if(c3.getText().equals("")){ //info is hidden, so display it
            c3.setText(gradeBook[2].getTeacher()); //third grade object in the grade array is third block
            g3.setText(gradeBook[2].getGrade());
        }
        else{ //info is visible, which means user wants to close it, so make it hidden
            c3.setText("");
            g3.setText("");
        }
    }

    //method for the button for fourth block
    public void show4(View view){
        c4 = findViewById(R.id.class4); //finds the widgets
        g4 = findViewById(R.id.g4);
        if(c4.getText().equals("")){ //info is hidden, so display it
            c4.setText(gradeBook[8].getTeacher()); //last grade object in the grade array is fourth block
            g4.setText(gradeBook[8].getGrade());
        }
        else{ //info is visible and user wants to make it hidden, so clear the text
            c4.setText("");
            g4.setText("");
        }
    }

    //method for the button for the eblocks
    public void goEbs(View view){
        eblocks = findViewById(R.id.EblockList); //find the widget
        if(eblocks.getText().equals("")){ //if hidden, then user clicked to display it, so make info visible
            String names = ""; //string that will be the text in the widget
            for(int i = 3; i < 8; i++){ //loops through grades 4-7 inclusive in the grade array
                //concatenates the string with each eblock
                names += gradeBook[i].getTeacher() + " - " + gradeBook[i].getGrade() + "\n";
            }
            eblocks.setText(names); //sets the text
            eblockShow.setText("Hide Eblocks"); //changes the button's text
        }
        else{ //info is visible and user wants to hide it, so hide eblocks
            eblocks.setText("");  //clears the text
            eblockShow.setText("Show Eblocks"); //changes the button's text
        }

    }

    //method for going back in the WebView
    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) { //goes back if allowed
            mWebView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }

    //Made by Rebecca Zhu
    //Purpose is to be able to view dynamically loaded code in the HTML
    public class MyJavaScriptInterface {
        //context for the Javascript page
        private Context ctx;
        private String html; //string trying to be parsed

        //constructor for the MyJavaScriptInterface object
        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        //getter for the html
        public String getHtml(){
            return html;
        }

        //setter for the html
        @JavascriptInterface
        public void showHTML(String _html) {
            html = _html;
        }
    }

    //method for button to go back to the home page
    public void backHome(View view){
        Intent intent = new Intent(getBaseContext(), HomePage.class); //uses intents to navigate between screens
        startActivity(intent);
    }
}