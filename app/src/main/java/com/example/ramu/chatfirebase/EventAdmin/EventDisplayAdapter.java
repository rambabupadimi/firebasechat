package com.example.ramu.chatfirebase.EventAdmin;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ramu.chatfirebase.ChatMessage;
import com.example.ramu.chatfirebase.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ramu on 05-06-2017.
 */

public class EventDisplayAdapter extends RecyclerView.Adapter<EventDisplayAdapter.MyViewHolder> {

    private List<ChatMessage> chatMessageList;
    private String TAG ="EventDisplayAdapter";

    private ImageView qrcodeIcon;
Context context;
     public EventDisplayAdapter(Context context)
     {
         this.context = context;
     }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view)
        {
            super(view);
            qrcodeIcon = (ImageView) view.findViewById(R.id.qrcode_icon);
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

        qrcodeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        new BottomSheetDialogFragment1().show(((EventsDisplay)context).getSupportFragmentManager(), "Dialog");

            }
        });

    }

    @Override
    public int getItemCount() {
       // return (chatMessageList!=null)?chatMessageList.size():0;
        return 8;
    }
}