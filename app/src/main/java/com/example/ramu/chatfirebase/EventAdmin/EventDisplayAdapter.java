package com.example.ramu.chatfirebase.EventAdmin;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ramu.chatfirebase.ChatMessage;
import com.example.ramu.chatfirebase.MainActivity;
import com.example.ramu.chatfirebase.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ramu on 05-06-2017.
 */

public class EventDisplayAdapter extends RecyclerView.Adapter<EventDisplayAdapter.MyViewHolder> {

    private List<CreateEvent> createEventList;
    private String TAG ="EventDisplayAdapter";

    private ImageView qrcodeIcon;

    Context context;
     public EventDisplayAdapter(Context context,List<CreateEvent> createEventList)
     {
         this.createEventList = createEventList;
         this.context = context;
     }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView eventAdapterImageView;
        TextView eventAdapterTitle,eventAdapterDescription,eventAdapterOrganizers,eventAdapterStartDate;
        Button scannedCount;
        public MyViewHolder(View view)
        {

            super(view);
         //   qrcodeIcon          = (ImageView) view.findViewById(R.id.qrcode_icon);
            eventAdapterTitle   =   (TextView) view.findViewById(R.id.event_adapter_title);
            eventAdapterDescription =   (TextView) view.findViewById(R.id.event_adapter_description);
            eventAdapterOrganizers  =   (TextView) view.findViewById(R.id.event_adapter_organizers);
            eventAdapterStartDate   =   (TextView) view.findViewById(R.id.event_adapter_start_date);
            eventAdapterImageView   =   (ImageView) view.findViewById(R.id.event_adapter_poster);
            scannedCount            =   (Button) view.findViewById(R.id.scanned_users_count);
        }

    }



    @Override
    public EventDisplayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_display_adapter, parent, false);

        return new EventDisplayAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EventDisplayAdapter.MyViewHolder holder, int position) {


         final CreateEvent  createEvent = createEventList.get(position);
         holder.eventAdapterTitle.setText(createEvent.getEventTitle());
        holder.eventAdapterDescription.setText(createEvent.getEventDescription());
        holder.eventAdapterOrganizers.setText(createEvent.getEventOrganizers());
        holder.eventAdapterStartDate.setText(createEvent.getEventStartDate());

        holder.eventAdapterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EventDisplayDetailsActivity.class);
                intent.putExtra("data", (Serializable) createEvent);
                context.startActivity(intent);
            }
        });
        Picasso.with(context).load(createEvent.getEventPosterURL()).networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.eventAdapterImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(context).load(createEvent.getEventPosterURL()).into(holder.eventAdapterImageView);
                    }
                });

        holder.scannedCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
            }
        });

/*
        qrcodeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        new BottomSheetDialogFragment1().show(((EventsDisplay)context).getSupportFragmentManager(), "Dialog");

            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return (createEventList!=null)?createEventList.size():0;
    }
}