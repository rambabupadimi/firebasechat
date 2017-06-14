package com.example.ramu.chatfirebase.EventAdmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramu.chatfirebase.R;

import java.util.HashMap;

public class EventCreationActivity extends AppCompatActivity {

    EditText eventTitle,eventDescription,eventStartDate,eventEndDate,
        eventLocation,eventOrganizers,eventGuests;

    ImageView eventLocationIcon;

    TextView createEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        eventTitle              = (EditText) findViewById(R.id.event_title);
        eventDescription        = (EditText) findViewById(R.id.event_description);
        eventStartDate          =   (EditText) findViewById(R.id.event_start_time);
        eventEndDate            =   (EditText)findViewById(R.id.event_end_time);
        eventGuests             =   (EditText)findViewById(R.id.event_guests);
        eventLocationIcon       =   (ImageView) findViewById(R.id.event_location_icon);
        eventLocation           =   (EditText)findViewById(R.id.event_address);
        eventOrganizers         =   (EditText)findViewById(R.id.event_organizers);

        createEvent             =   (TextView) findViewById(R.id.event_save);

        HashMap hashMap = new HashMap();

        final CreateEvent createEventObject = new CreateEvent();
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventTitle!=null && eventTitle.toString().length()>0)
                {
                    createEventObject.setEventTitle(eventTitle.getText().toString());
                }
                else
                {
                    Toast.makeText(EventCreationActivity.this,"Please enter title",Toast.LENGTH_LONG).show();
                    return;
                }

                if(eventDescription!=null && eventDescription.getText().toString().length()>0)
                {
                    createEventObject.setEventDescription(eventDescription.getText().toString());
                }
                else
                {
                    Toast.makeText(EventCreationActivity.this,"Please enter description",Toast.LENGTH_LONG).show();
                    return;
                }
                if(eventStartDate!=null && eventStartDate.getText().toString().length()>0)
                {
                    createEventObject.setEventStartDate(eventStartDate.getText().toString());
                }
                else
                {
                    Toast.makeText(EventCreationActivity.this,"Please enter event start data",Toast.LENGTH_LONG).show();
                    return;
                }

                if(eventOrganizers!=null && eventOrganizers.getText().toString().length()>0)
                {
                    createEventObject.setEventOrganizers(eventOrganizers.getText().toString());
                }
                else
                {
                    Toast.makeText(EventCreationActivity.this,"Please enter organizers",Toast.LENGTH_LONG).show();
                    return;
                }

                if(eventEndDate!=null && eventEndDate.getText().toString().length()>0)
                {
                    createEventObject.setEventEndDate(eventEndDate.getText().toString());
                }

                if(eventGuests!=null && eventGuests.getText().toString().length()>0)
                {
                    createEventObject.setEventGuests(eventGuests.getText().toString());
                }

                if(eventOrganizers!=null && eventOrganizers.getText().toString().length()>0)
                {
                    createEventObject.setEventOrganizers(eventOrganizers.getText().toString());
                }

            }
        });
    }
}
