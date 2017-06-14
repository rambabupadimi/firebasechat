package com.example.ramu.chatfirebase;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ramu on 03-06-2017.
 */



public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter.MyViewHolder> {

    private List<ChatMessage> chatMessageList;
    String senderId;
    Context context;
    private String TAG ="GroupChatAdapter";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView messageLeft,messageRight,messageLeftTime,messageRightTime;
        // LinearLayout leftLayout,rightLayout;

        ImageView leftThumbnail,rightThumbnail;
        CardView leftLayout,rightLayout;
        public MyViewHolder(View view) {
            super(view);
            leftLayout = (CardView) view.findViewById(R.id.group_chat_message_layout_left);
            rightLayout = (CardView) view.findViewById(R.id.group_chat_message_layout_right);
            messageLeft =  (TextView) view.findViewById(R.id.group_chat_message_left);
            messageLeftTime =  (TextView) view.findViewById(R.id.group_chat_send_time_left);
            messageRight =  (TextView) view.findViewById(R.id.group_chat_message_right);
            messageRightTime =  (TextView) view.findViewById(R.id.group_chat_send_time_right);


            leftThumbnail   =   (ImageView)view.findViewById(R.id.group_chat_left_image);
            rightThumbnail  =   (ImageView)view.findViewById(R.id.group_chat_right_image);
        }
    }


    public GroupChatAdapter(List<ChatMessage> chatMessageList,String senderId,Context context) {
        this.chatMessageList = chatMessageList;
        this.senderId = senderId;
        this.context = context;
        Log.i(TAG,"group chat adatper chat messagelist senderid"+senderId);
        Log.i(TAG,"group chat adatper chat messagelist"+chatMessageList);
        Log.i(TAG,"group chat adatper chat messagelist size"+chatMessageList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_chat_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final ChatMessage chatMessage =chatMessageList.get(position);
        Log.i(TAG,"group chat adatper message url"+chatMessage);

        Log.i(TAG,"group chat adatper url"+chatMessage.getUserThumbnail());

        holder.leftThumbnail.setVisibility(View.GONE);
        holder.rightThumbnail.setVisibility(View.GONE);
        if(chatMessage.getSenderId().toString().equalsIgnoreCase(senderId))
        {
            // visible right
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);

            holder.messageRight.setText(chatMessage.getMessage());
            holder.messageRightTime.setText(chatMessage.getSent_time());


            if(chatMessage.getUserThumbnail()!=null) {
                holder.rightThumbnail.setVisibility(View.VISIBLE);

                Picasso.with(context).load(chatMessage.getUserThumbnail()).networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.rightThumbnail, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                Picasso.with(context).load(chatMessage.getUserThumbnail()).into(holder.rightThumbnail);
                            }
                        });

            }
        }
        else
        {
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.messageLeft.setText(chatMessage.getMessage());
            holder.messageLeftTime.setText(chatMessage.getSent_time());


            if(chatMessage.getUserThumbnail()!=null) {

             holder.leftThumbnail.setVisibility(View.VISIBLE);
                Picasso.with(context).load(chatMessage.getUserThumbnail()).networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.leftThumbnail, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                Picasso.with(context).load(chatMessage.getUserThumbnail()).into(holder.leftThumbnail);
                            }
                        });
            }
        }


    }
    public void reload(ChatMessage chatMessage)
    {
        chatMessageList.add(chatMessage);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (chatMessageList!=null)?chatMessageList.size():0;
    }
}