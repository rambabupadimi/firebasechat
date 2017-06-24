package com.example.ramu.chatfirebase.EventAdmin;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ramu.chatfirebase.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class EventDisplayDetailsActivity extends AppCompatActivity {


    ImageView eventPoster;
    TextView eventTitle,eventDescription,eventStartDate,eventEndDate,eventAddress;

    TextView eventOrganizers1,eventOrganizers2,eventOrganizers3,eventOrganizers4,eventOrganizers5;
    TextView eventGuests1,eventGuests2,eventGuests3,eventGuests4,eventGuests5;

    FloatingActionButton editIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_display_details);



        eventPoster =   (ImageView) findViewById(R.id.event_detail_imageview);
        eventTitle  =   (TextView) findViewById(R.id.event_detail_title);
        eventDescription    =   (TextView) findViewById(R.id.event_detail_description);
        eventStartDate      =   (TextView) findViewById(R.id.event_start_date_heading_value);
        eventEndDate        =   (TextView) findViewById(R.id.event_end_date_heading_value);
        eventAddress        =   (TextView) findViewById(R.id.event_details_address);

        eventOrganizers1     =   (TextView) findViewById(R.id.event_detail_organizers_value1);
        eventOrganizers2     =   (TextView) findViewById(R.id.event_detail_organizers_value2);
        eventOrganizers3     =   (TextView) findViewById(R.id.event_detail_organizers_value3);
        eventOrganizers4     =   (TextView) findViewById(R.id.event_detail_organizers_value4);
        eventOrganizers5     =   (TextView) findViewById(R.id.event_detail_organizers_value5);


        eventGuests1         =   (TextView) findViewById(R.id.event_detail_guests_value1);
        eventGuests2         =   (TextView) findViewById(R.id.event_detail_guests_value2);
        eventGuests3         =   (TextView) findViewById(R.id.event_detail_guests_value3);
        eventGuests4         =   (TextView) findViewById(R.id.event_detail_guests_value4);
        eventGuests5         =   (TextView) findViewById(R.id.event_detail_guests_value5);


        editIcon            =   (FloatingActionButton) findViewById(R.id.edit_icon);

        final CreateEvent createEvent = (CreateEvent) getIntent().getSerializableExtra("data");

        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDisplayDetailsActivity.this,EventProfileEdit.class);
                intent.putExtra("data",createEvent);
                startActivity(intent);
            }
        });



        eventTitle.setText(createEvent.getEventTitle());
        eventDescription.setText(createEvent.getEventDescription());
        eventStartDate.setText(createEvent.getEventStartDate().split("/")[0].toString()+"\n"+createEvent.getEventStartDate().split("/")[1].toString());
        eventEndDate.setText(createEvent.getEventEndDate().split("/")[0].toString()+"\n"+createEvent.getEventEndDate().split("/")[1].toString());

        eventAddress.setText(createEvent.getEventLocation());

        Picasso.with(this).load(createEvent.getEventPosterURL()).networkPolicy(NetworkPolicy.OFFLINE)
                .into(eventPoster, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getApplicationContext()).load(createEvent.getEventPosterURL()).into(eventPoster);
                    }
                });


        eventOrganizers1.setVisibility(View.GONE);
        eventOrganizers2.setVisibility(View.GONE);
        eventOrganizers3.setVisibility(View.GONE);
        eventOrganizers4.setVisibility(View.GONE);
        eventOrganizers5.setVisibility(View.GONE);

        eventGuests1.setVisibility(View.GONE);
        eventGuests2.setVisibility(View.GONE);
        eventGuests3.setVisibility(View.GONE);
        eventGuests4.setVisibility(View.GONE);
        eventGuests5.setVisibility(View.GONE);


        if(createEvent.getEventOrganizers().toString().contains(","))
        {
            String data =createEvent.getEventOrganizers().toString();
            int length = data.split(",").length;
            for(int i=0;i<length;i++)
            {
                if(i==0)
                {
                    eventOrganizers1.setText(data.split(",")[i].toString());
                    eventOrganizers1.setVisibility(View.VISIBLE);
                }
                else if(i==1)
                {
                    eventOrganizers2.setText(data.split(",")[i].toString());
                    eventOrganizers2.setVisibility(View.VISIBLE);
                }
                else if(i==2)
                {
                    eventOrganizers3.setText(data.split(",")[i].toString());
                    eventOrganizers3.setVisibility(View.VISIBLE);
                }
                else if(i==3)
                {
                    eventOrganizers4.setText(data.split(",")[i].toString());
                    eventOrganizers4.setVisibility(View.VISIBLE);
                }
                else if(i==4)
                {
                    eventOrganizers5.setText(data.split(",")[i].toString());
                    eventOrganizers5.setVisibility(View.VISIBLE);
                }
            }

        }
        else {
            eventOrganizers1.setVisibility(View.VISIBLE);
            eventOrganizers1.setText(createEvent.getEventOrganizers());
        }



        if(createEvent.getEventGuests().toString().contains(","))
        {
            String data =createEvent.getEventGuests().toString();
            int length = data.split(",").length;
            for(int i=0;i<length;i++)
            {
                if(i==0)
                {
                    eventGuests1.setText(data.split(",")[i].toString());
                    eventGuests1.setVisibility(View.VISIBLE);
                }
                else if(i==1)
                {
                    eventGuests2.setText(data.split(",")[i].toString());
                    eventGuests2.setVisibility(View.VISIBLE);
                }
                else if(i==2)
                {
                    eventGuests3.setText(data.split(",")[i].toString());
                    eventGuests3.setVisibility(View.VISIBLE);
                }
                else if(i==3)
                {
                    eventGuests4.setText(data.split(",")[i].toString());
                    eventGuests4.setVisibility(View.VISIBLE);
                }
                else if(i==4)
                {
                    eventGuests5.setText(data.split(",")[i].toString());
                    eventGuests5.setVisibility(View.VISIBLE);
                }
            }

        }
        else {
            eventGuests1.setVisibility(View.VISIBLE);
            eventGuests1.setText(createEvent.getEventGuests());
        }

    }


}
