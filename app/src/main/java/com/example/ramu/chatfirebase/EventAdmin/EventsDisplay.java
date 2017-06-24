package com.example.ramu.chatfirebase.EventAdmin;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.ramu.chatfirebase.EventUser.EventUserDisplay;
import com.example.ramu.chatfirebase.R;
import com.example.ramu.chatfirebase.SignupOrLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventsDisplay extends AppCompatActivity {

    private RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    Query query1;
    String userid;
    ImageView qrcodeIcon;

    List<CreateEvent> createEventList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_display);
        recyclerView = (RecyclerView)findViewById(R.id.show_events_recyclerview);


        qrcodeIcon  = (ImageView) findViewById(R.id.qrcode_icon);
        qrcodeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheetDialogFragment1().show(getSupportFragmentManager(), "Dialog");
            }
        });

        mAuth = FirebaseAuth.getInstance();

      Log.i("tag",",mauth"+mAuth.getCurrentUser());
        createEventList = new ArrayList<>();
        try {

            if(mAuth.getCurrentUser()==null)
            {
                Intent intent = new Intent(this, SignupOrLogin.class);
                startActivity(intent);
            }


            userid = mAuth.getCurrentUser().getUid();


        } catch (Exception e) {
            e.printStackTrace();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Events");
        query1 = databaseReference.orderByChild("eventUserId").equalTo(userid);

        databaseReference.keepSynced(true);


        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    CreateEvent createEvent = child.getValue(CreateEvent.class);
                    createEventList.add(createEvent);
                }

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        EventDisplayAdapter eventDisplayAdapter =new EventDisplayAdapter(this,createEventList);
        recyclerView.setAdapter(eventDisplayAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.create_event);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventsDisplay.this,EventCreationActivity.class);
            //    Intent intent = new Intent(EventsDisplay.this,EventUserDisplay.class);
                startActivity(intent);
            }
        });
    }

}
