//Made by Rebecca Zhu
//Allows the eblock and afterschool activities to be viewed in a recycle view from the Firebase database

package com.example.myapplication;

//imports for the Android widgets
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

//imports for firebase database
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EblockAdapter extends RecyclerView.Adapter<EblockAdapter.EblockViewHolder> {
    //stores the eblocks and activities
    private ArrayList<Eblock> eblocks;
    private FirebaseDatabase mFirebase; //firebase
    private DatabaseReference mDataRef; //data from firebase
    private ChildEventListener mChildList; //child = items stored in the firebase

    public EblockAdapter(){
        mFirebase = FirebaseUtil.getmFirebase(); //uses the FirebaseUtil class's firebase
        mDataRef = FirebaseUtil.getmDataRef(); //from FirebaseUtil class
        eblocks = FirebaseUtil.getmEblocks(); //from FireBase Util class
        mChildList = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Eblock eb = dataSnapshot.getValue(Eblock.class);
                eb.setId(dataSnapshot.getKey()); //unique from Firebase
                eblocks.add(eb); //gets the Eblock from the firebase database and adds it into the array list so it can be loaded into recycle view
                notifyItemInserted(eblocks.size()-1); //so that they're loaded in order
            }

            @Override //needed for ChildEventListener object
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override //needed for ChildEventListener object
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override //needed for ChildEventListener object
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override //needed for ChildEventListener object
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDataRef.addChildEventListener(mChildList); //sets the ChildEventListener so that the recycle view can be updated
    }

    //returns an EblockViewHolder that is the recycle view
    @NonNull
    @Override
    public EblockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        //returns an Eblock from the Eblock View Holder
        View itemView = LayoutInflater.from(context).inflate(R.layout.south_row, parent, false);
        return new EblockViewHolder(itemView);
    }

    //binds Eblock into viewHolder so that it is displayed to the user
    @Override
    public void onBindViewHolder(@NonNull EblockViewHolder holder, int position) {
        Eblock eblock = eblocks.get(position); //position in the array list
        holder.bind(eblock); //sets it into the holder for user to see
    }

    //returns size of the eblock array list
    @Override
    public int getItemCount() {
        return eblocks.size();
    }

    //Made by Rebecca Zhu
    //purpose is to manage the Recycle View of Eblock objects
    public class EblockViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //parts of each row in the recycle view
        TextView ebDate;
        TextView ebDescript;
        TextView ebTeacher;
        public EblockViewHolder(@NonNull View itemView) {
            super(itemView);
            //sets variabels to widgets
            ebDate = itemView.findViewById(R.id.ebDate);
            ebDescript = itemView.findViewById(R.id.ebDescript);
            ebTeacher = itemView.findViewById(R.id.ebTeacher);
            itemView.setOnClickListener(this);
        }

        //binds an eblock to the row
        public void bind(Eblock eblock){
            ebDate.setText(eblock.getDate());
            ebDescript.setText(eblock.getDescription());
            ebTeacher.setText(eblock.getTeacher());
        }

        //when an activity is clicked, then go to the editing screen
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); //finds which eblock/actiity was clicked to track
            Eblock selected = eblocks.get(position); //finds the eblock that is clicked
            Intent intent = new Intent(v.getContext(), CalendarActivity.class); //creates new intent to go to editing screen
            intent.putExtra("Eblock", selected); //has to push the eblock info so that it can be loaded into the edit texts
            v.getContext().startActivity(intent); //starts intent = go to new screen
        }
    }
}
