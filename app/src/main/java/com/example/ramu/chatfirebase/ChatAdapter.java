package com.example.ramu.chatfirebase;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 07-05-2017.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<ChatMessage> chatMessageList;
    String senderId;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView messageLeft,messageRight,messageLeftTime,messageRightTime;
        LinearLayout leftLayout,rightLayout;

        public MyViewHolder(View view) {
            super(view);
            leftLayout = (LinearLayout) view.findViewById(R.id.message_layout_left);
            rightLayout = (LinearLayout) view.findViewById(R.id.message_layout_right);
            messageLeft =  (TextView) view.findViewById(R.id.message_left);
            messageLeftTime =  (TextView) view.findViewById(R.id.send_time_left);
            messageRight =  (TextView) view.findViewById(R.id.message_right);
            messageRightTime =  (TextView) view.findViewById(R.id.send_time_right);

        }
    }


    public ChatAdapter(List<ChatMessage> chatMessageList,String senderId) {
        this.chatMessageList = chatMessageList;
        this.senderId = senderId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ChatMessage chatMessage =chatMessageList.get(position);
        if(chatMessage.getSenderId().toString().equalsIgnoreCase(senderId))
        {
            // visible right
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.messageRight.setText(chatMessage.getMessage());
            holder.messageRightTime.setText(chatMessage.getSent_time());
        }
        else
        {
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.messageLeft.setText(chatMessage.getMessage());
            holder.messageLeftTime.setText(chatMessage.getSent_time());
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
