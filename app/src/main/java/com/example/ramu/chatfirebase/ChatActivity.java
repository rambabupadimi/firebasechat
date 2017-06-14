package com.example.ramu.chatfirebase;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;

import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiBackspaceClickListener;
import com.vanniktech.emoji.listeners.OnEmojiClickedListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupDismissListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupShownListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardCloseListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardOpenListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;



public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private EmojiEditText message;
    private Button send;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference   databaseReference;

    private String senderId,receiverId;
    private FirebaseAuth mAuth;
    Query databaseReference1,databaseReference2;
    List<ChatMessage> chatMessageList;

    ChatAdapter chatAdapter;
    ImageView emojiButton;
    EmojiPopup emojiPopup;
    ViewGroup rootView;
    private String TAG ="CHatMessage";

    Toolbar chatToolbar;
    TextView toolbarTitle;

    private boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatMessageList = new ArrayList<ChatMessage>();



        chatToolbar     = (Toolbar) findViewById(R.id.chat_toolbar);
        toolbarTitle    =   (TextView) findViewById(R.id.chat_toolbar_textview);
        toolbarTitle.setText(getIntent().getStringExtra("name"));


        chatRecyclerView = (RecyclerView) findViewById(R.id.chat_recylerview);
        message = (EmojiEditText) findViewById(R.id.emojiEditText);
        send = (Button) findViewById(R.id.send);
        mAuth = FirebaseAuth.getInstance();

        emojiButton = (ImageView) findViewById(R.id.main_activity_emoji);
        emojiButton.setColorFilter(ContextCompat.getColor(this, R.color.emoji_icons), PorterDuff.Mode.SRC_IN);


        rootView = (ViewGroup) findViewById(R.id.main_activity_root_view);


        try {
            senderId = mAuth.getCurrentUser().getUid();
            receiverId = getIntent().getStringExtra("userid");

        } catch (Exception e) {
            e.printStackTrace();
        }


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Messages");
        databaseReference.keepSynced(true);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                HashMap hashMap = new HashMap();
                hashMap.put("senderId", senderId);
                hashMap.put("receiverId", receiverId);
                hashMap.put("message", message.getText().toString());
                hashMap.put("sent_time", getCurrentDateAndTime());
                hashMap.put("senderId_receiverId", senderId + "_" + receiverId);

                databaseReference.push().setValue(hashMap);
                databaseReference.keepSynced(true);

                check = true;

                message.setText("");
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("val", "data snapchat" + dataSnapshot.getValue());
                Log.i("val", "children" + dataSnapshot.getChildren());
                Log.i("tag", "listener for single event called");
                //   for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                Log.i("tag", "chatmessage" + chatMessage);
                if (chatMessage.getSenderId_receiverId().toString().equalsIgnoreCase(senderId + "_" + receiverId)
                        || chatMessage.getSenderId_receiverId().toString().equalsIgnoreCase(receiverId + "_" + senderId)) {
                    chatMessageList.add(chatMessage);
                }

                // }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        chatAdapter = new ChatAdapter(chatMessageList, senderId);
        chatRecyclerView = (RecyclerView) findViewById(R.id.chat_recylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
        chatRecyclerView.setAdapter(null);
        chatRecyclerView.setAdapter(chatAdapter);


        if(check==true) {


            if (chatAdapter != null && chatMessageList != null) {
                chatAdapter.notifyItemChanged(chatMessageList.size());
                chatRecyclerView.scrollToPosition(chatMessageList.size()+1);
            }
            check=false;
        }

        Log.i("ref", "database ref" + databaseReference1);
        Log.i("senderid", "senderid" + senderId);
        Log.i("receiverid", "receiverid" + receiverId);

      /*
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
*/

        if (Build.VERSION.SDK_INT >= 11) {
            chatRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v,
                                           int left, int top, int right, int bottom,
                                           int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if (bottom < oldBottom) {
                        chatRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    chatRecyclerView.smoothScrollToPosition(chatMessageList.size() - 1);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }, 100);
                    }
                }
            });

        }

        //Add to Activity
        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");

        emojiButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(final View v) {
                emojiPopup.toggle();
            }
        });


        setUpEmojiPopup();
    }
    public String getCurrentDateAndTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override public void onBackPressed() {
        if (emojiPopup != null && emojiPopup.isShowing()) {
            emojiPopup.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override protected void onStop() {
        if (emojiPopup != null) {
            emojiPopup.dismiss();
        }

        super.onStop();
    }


    private void setUpEmojiPopup() {
        emojiPopup = EmojiPopup.Builder.fromRootView(rootView)
                .setOnEmojiBackspaceClickListener(new OnEmojiBackspaceClickListener() {
                    @Override public void onEmojiBackspaceClicked(final View v) {
                        Log.d(TAG, "Clicked on Backspace");
                    }
                })
                .setOnEmojiClickedListener(new OnEmojiClickedListener() {
                    @Override public void onEmojiClicked(final Emoji emoji) {
                        Log.d(TAG, "Clicked on emoji");
                    }
                })
                .setOnEmojiPopupShownListener(new OnEmojiPopupShownListener() {
                    @Override public void onEmojiPopupShown() {
                        emojiButton.setImageResource(R.drawable.ic_keyboard);
                    }
                })
                .setOnSoftKeyboardOpenListener(new OnSoftKeyboardOpenListener() {
                    @Override public void onKeyboardOpen(final int keyBoardHeight) {
                        Log.d(TAG, "Opened soft keyboard");
                    }
                })
                .setOnEmojiPopupDismissListener(new OnEmojiPopupDismissListener() {
                    @Override public void onEmojiPopupDismiss() {
                        emojiButton.setImageResource(R.drawable.emoji_ios_category_people);
                    }
                })
                .setOnSoftKeyboardCloseListener(new OnSoftKeyboardCloseListener() {
                    @Override public void onKeyboardClose() {
                        Log.d(TAG, "Closed soft keyboard");
                    }
                })
                .build(message);
    }




}
