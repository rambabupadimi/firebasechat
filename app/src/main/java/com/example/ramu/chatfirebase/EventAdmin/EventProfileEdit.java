package com.example.ramu.chatfirebase.EventAdmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ramu.chatfirebase.R;

public class EventProfileEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_profile_edit);

        CreateEvent createEvent = (CreateEvent) getIntent().getSerializableExtra("data");
        Log.i("tag","title"+createEvent.getEventTitle());

    }
}
