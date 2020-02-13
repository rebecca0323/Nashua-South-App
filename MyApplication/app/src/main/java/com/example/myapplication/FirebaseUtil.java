//Made by Rebecca Zhu
//allows all the Firebase references to be stored in this one class for code reuse instead of repeating same code in multiple classes
//mainly for organizational purposes

package com.example.myapplication;

//imports for Android Studio's widgets
import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

//imports for Google's Firebase
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//imports for java objects
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {
    //all instance variables are static so that they can be accessed through the class name and since there is only one object that is instantiated
    private static FirebaseDatabase mFirebase; //firebase database
    private static DatabaseReference mDataRef; //data reference from Friebase

    private static FirebaseUtil firebaseUtil; //itself

    private static ArrayList<Eblock> eblockactivities; //array list of the Eblock/activities

    private static FirebaseAuth mFAuth; //for authentification
    private static FirebaseAuth.AuthStateListener mAuthList; //for authentification
    private static final int RC_SIGN_IN = 123; //for authentification, hardcoded permission

    private static Activity caller;

    //empty and private so that an object can't be instantiated outside of this class
    private FirebaseUtil(){

    }

    //makes the Reference to Firebase for the database
    public static void openFbReference(String ref, final Activity callerActivity){
        if(firebaseUtil == null){
            firebaseUtil = new FirebaseUtil(); //only time the object is created
            setmFirebase(FirebaseDatabase.getInstance()); //sets firebase
            setmFAuth(FirebaseAuth.getInstance()); //sets the authenfication
            caller = callerActivity;
            mAuthList = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if(firebaseAuth.getCurrentUser() == null){ //check if user is signed in
                        FirebaseUtil.signIn();
                    }
                    else{
                        Toast.makeText(callerActivity.getBaseContext(), "Welcome back!", Toast.LENGTH_LONG).show();//when user is logged in
                    }
                }
            };
        }
        setmEblocks(new ArrayList<Eblock>()); //creates an empty array list
        setmDataRef(getmFirebase().getReference().child(ref)); //gets the data reference from the firebase database
    }

    //getter for the firebase
    public static FirebaseDatabase getmFirebase() {
        return mFirebase;
    }

    //setter for the firebase
    public static void setmFirebase(FirebaseDatabase mFirebase) {
        FirebaseUtil.mFirebase = mFirebase;
    }

    //getter for the database reference
    public static DatabaseReference getmDataRef() {
        return mDataRef;
    }

    //setter for the database reference
    public static void setmDataRef(DatabaseReference mDataRef) {
        FirebaseUtil.mDataRef = mDataRef;
    }

    //getter for the eblockactivities
    public static ArrayList<Eblock> getmEblocks() {
        return eblockactivities;
    }

    //setter for the eblock activities
    public static void setmEblocks(ArrayList<Eblock> mEblocks) {
        FirebaseUtil.eblockactivities = mEblocks;
    }

    //getter for the authenficiation
    public static void setmFAuth(FirebaseAuth mFAuth) {
        FirebaseUtil.mFAuth = mFAuth;
    }

    //for authentification
    public static void attachListener(){
        mFAuth.addAuthStateListener(mAuthList);
    }

    //for authentification
    public static void detachListener(){
        mFAuth.removeAuthStateListener(mAuthList);
    }

    //allows users to sign in, method provided by Firebase
    private static void signIn(){
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());
        // Create and launch sign-in intent
        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(), RC_SIGN_IN);
    }
}
