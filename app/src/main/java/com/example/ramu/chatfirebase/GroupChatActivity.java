 package com.example.ramu.chatfirebase;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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


 public class GroupChatActivity extends AppCompatActivity {

     private RecyclerView chatRecyclerView;
     private EmojiEditText message;
     private Button send;

     private FirebaseDatabase firebaseDatabase;
     private DatabaseReference databaseReference;

     private String senderId,receiverId;
     private FirebaseAuth mAuth;
     Query databaseReference1,databaseReference2;
     List<ChatMessage> chatMessageList;

     GroupChatAdapter groupChatAdapter;
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
         setContentView(R.layout.activity_group_chat);
         chatMessageList = new ArrayList<ChatMessage>();



         chatToolbar     = (Toolbar) findViewById(R.id.group_chat_toolbar);
         toolbarTitle    =   (TextView) findViewById(R.id.group_chat_toolbar_textview);
         toolbarTitle.setText("Group");


         chatRecyclerView = (RecyclerView) findViewById(R.id.group_chat_recylerview);
         message = (EmojiEditText) findViewById(R.id.group_chat_emojiEditText);
         send = (Button) findViewById(R.id.group_chat_send);
         mAuth = FirebaseAuth.getInstance();

         emojiButton = (ImageView) findViewById(R.id.group_chat_main_activity_emoji);
         emojiButton.setColorFilter(ContextCompat.getColor(this, R.color.emoji_icons), PorterDuff.Mode.SRC_IN);


         rootView = (ViewGroup) findViewById(R.id.group_chat_main_activity_root_view);





         try {
             senderId = mAuth.getCurrentUser().getUid();
            // receiverId = getIntent().getStringExtra("userid");

         } catch (Exception e) {
             e.printStackTrace();
         }


         databaseReference = FirebaseDatabase.getInstance().getReference().child("GroupMessages");
         databaseReference.keepSynced(true);
         send.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 HashMap hashMap = new HashMap();
                 hashMap.put("senderId", senderId);
              //   hashMap.put("receiverId", receiverId);
                 hashMap.put("message", message.getText().toString());
                 hashMap.put("sent_time", getCurrentDateAndTime());
              //   hashMap.put("senderId_receiverId", senderId + "_" + receiverId);
                 hashMap.put("group_name","testgroup");
                 databaseReference.push().setValue(hashMap);
                 databaseReference.keepSynced(true);

                 check = true;

                 message.setText("");
                 getWindow().setSoftInputMode(
                         WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                 );
             }
         });


/*
         databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {

                 for (DataSnapshot child : dataSnapshot.getChildren()) {
                     final ChatMessage chatMessage1 = child.getValue(ChatMessage.class);
                    Log.i(TAG,"test group chat message"+chatMessage1);
                     String senderId = chatMessage1.getSenderId();
                     Log.i(TAG,"test group chat message sender id"+senderId);

                     FirebaseDatabase.getInstance().getReference("Users")
                             .orderByKey().equalTo(senderId).addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot dataSnapshot) {
                             String mUrl="";
                             for (DataSnapshot child : dataSnapshot.getChildren()) {
                                 Log.i(TAG, "test group datasnapchat" + dataSnapshot);

                                 Users users = child.getValue(Users.class);
                                 Log.i(TAG, "test group datasnapchat users" + users);

                                 Log.i(TAG, "test group datasnapchat name" + users.getName());

                                 mUrl = users.getImgurl();
                                 Log.i(TAG, "test group datasnapchat imageurl" + mUrl);
                                 chatMessage1.setUserThumbnail(mUrl);

                                 chatMessageList.add(chatMessage1);
                             }

                         }

                         @Override
                         public void onCancelled(DatabaseError databaseError) {

                         }
                     });
                     chatMessageList.add(chatMessage1);
                 }

                 Log.i(TAG,"final chat message list "+chatMessageList);
                 groupChatAdapter = new GroupChatAdapter(chatMessageList, senderId,getApplicationContext());
                 chatRecyclerView = (RecyclerView) findViewById(R.id.group_chat_recylerview);
                 LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GroupChatActivity.this);
                 linearLayoutManager.setStackFromEnd(true);
                 chatRecyclerView.setLayoutManager(linearLayoutManager);
                 chatRecyclerView.setAdapter(null);
                 chatRecyclerView.setAdapter(groupChatAdapter);

             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }



         });

*/

         databaseReference.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                 Log.i("val", "data snapchat" + dataSnapshot.getValue());
                 Log.i("val", "children" + dataSnapshot.getChildren());
                 Log.i("tag", "listener for single event called"+dataSnapshot);


                 //   for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                 final ChatMessage chatMessage1 = dataSnapshot.getValue(ChatMessage.class);
                 Log.i("tag", "chatmessage" + chatMessage1);
                 String senderId = chatMessage1.getSenderId();

                 Log.i(TAG,"group chat senderid"+senderId);

                 DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("Users").child(senderId);
                 databaseReference3.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {

                        Users users = dataSnapshot.getValue(Users.class);
                        String mUrl = users.getImgurl();
                        Log.i(TAG,"group chat url"+mUrl);
                        chatMessage1.setUserThumbnail(mUrl);
                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {

                     }
                 });

                     chatMessageList.add(chatMessage1);
                Log.i(TAG,"print message list"+chatMessageList);

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



         groupChatAdapter = new GroupChatAdapter(chatMessageList, senderId,getApplicationContext());
         chatRecyclerView = (RecyclerView) findViewById(R.id.group_chat_recylerview);
         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GroupChatActivity.this);
         linearLayoutManager.setStackFromEnd(true);
         chatRecyclerView.setLayoutManager(linearLayoutManager);
         chatRecyclerView.setAdapter(null);
         chatRecyclerView.setAdapter(groupChatAdapter);


         if(check==true) {


             if (groupChatAdapter != null && chatMessageList != null) {
                 groupChatAdapter.notifyItemChanged(chatMessageList.size());
                 chatRecyclerView.scrollToPosition(chatMessageList.size()+1);
             }
             check=false;
         }

         Log.i("ref", "database ref" + databaseReference1);
         Log.i("senderid", "senderid" + senderId);
         Log.i("receiverid", "receiverid" + receiverId);


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
