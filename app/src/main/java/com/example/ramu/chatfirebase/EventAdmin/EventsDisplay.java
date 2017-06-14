package com.example.ramu.chatfirebase.EventAdmin;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ramu.chatfirebase.R;

public class EventsDisplay extends AppCompatActivity {

    private RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_display);
        recyclerView = (RecyclerView)findViewById(R.id.show_events_recyclerview);
        EventDisplayAdapter eventDisplayAdapter =new EventDisplayAdapter(this);
        recyclerView.setAdapter(eventDisplayAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.create_event);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventsDisplay.this,EventCreationActivity.class);
                startActivity(intent);
            }
        });
    }

}
