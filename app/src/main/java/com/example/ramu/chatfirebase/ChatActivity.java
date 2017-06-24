package com.example.ramu.chatfirebase;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.view.View.VISIBLE;


public class ChatActivity extends AppCompatActivity implements ChatAdapter.ChatListAdapterListener {

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
    ImageView attachIcon;

    private boolean check = false;

    LinearLayout bottomParentView,bottomParentViewTop;
    RelativeLayout bottomMainLayout;

    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    JSONObject paretnJsonObject;

    ClipData myClip;
    public ClipboardManager myClipboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatMessageList = new ArrayList<ChatMessage>();
        actionModeCallback = new ActionModeCallback();

        myClipboard = (ClipboardManager) this.getSystemService(CLIPBOARD_SERVICE);

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


        bottomParentView    =   (LinearLayout)findViewById(R.id.bottom_parent_view);
        bottomParentViewTop =   (LinearLayout)findViewById(R.id.bottom_parent_view_top);
        bottomMainLayout    =   (RelativeLayout)findViewById(R.id.bottom_main_layout);

        attachIcon      =   (ImageView)findViewById(R.id.attach_icon);
        attachIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                bottomMainLayout.startAnimation(slideUp);
                bottomMainLayout.setVisibility(VISIBLE);
            }
        });


        bottomParentViewTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomMainLayout.getVisibility() == VISIBLE) {
                    Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                    bottomMainLayout.startAnimation(slideDown);
                    bottomMainLayout.setVisibility(View.GONE);
                }
            }
        });
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

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setSenderId(senderId);
                chatMessage.setReceiverId(receiverId);
                chatMessage.setMessage(message.getText().toString());
                chatMessage.setSent_time(getCurrentDateAndTime());
                chatMessage.setSenderId_receiverId(senderId+"_"+receiverId);
/*
                HashMap hashMap = new HashMap();
                hashMap.put("senderId", senderId);
                hashMap.put("receiverId", receiverId);
                hashMap.put("message", message.getText().toString());
                hashMap.put("sent_time", getCurrentDateAndTime());
                hashMap.put("senderId_receiverId", senderId + "_" + receiverId);
                databaseReference.push().setValue(hashMap);
*/
                databaseReference.push().setValue(chatMessage);
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

        chatAdapter = new ChatAdapter(chatMessageList, senderId,this);
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


    @Override
    public void onIconClicked(int position) {

    }

    @Override
    public void onIconImportantClicked(int position) {

    }

    @Override
    public void onMessageRowClicked(int position) {
        // verify whether action mode is enabled or not
        // if enabled, change the row state to activated
        if (chatAdapter.getSelectedItemCount() > 0) {
            actionMode.getMenu().getItem(0).setVisible(false);
        } else {
            if (actionMode != null)
                actionMode.getMenu().getItem(0).setVisible(true);
        }

        if (actionMode != null) {
            if (chatAdapter.getSelectedItemCount() == 0)
                actionMode.getMenu().getItem(0).setVisible(true);
        }


        if (chatAdapter.getSelectedItemCount() > 0) {
            enableActionMode(position);
        } else {
            // read the message which removes bold from the row
            //  Message message = messages.get(position);
            //  message.setRead(true);
            //  messages.set(position, message);
            chatAdapter.notifyDataSetChanged();


            //Toast.makeText(getApplicationContext(), "Read: " + message.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //   getSupportActionBar().hide();
        //   replyToolbar.setVisibility(View.VISIBLE);

    }


    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }


    private void toggleSelection(int position) {


        chatAdapter.toggleSelection(position);
        int count = chatAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();

        }
    }

    @Override
    public void onRowLongClicked(int position) {
        enableActionMode(position);
    }



    public class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {


            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {


                case R.id.action_copy:
                    Log.i("Data", "values are" + chatAdapter.getSelectedItemsListData());
                    paretnJsonObject = new JSONObject();

                    SparseBooleanArray sparseBooleanArray = chatAdapter.getSelectedItemsListData();
                    try {
                        String resultData = "";
                        if (sparseBooleanArray != null) {

                            for (int m = 0; m < sparseBooleanArray.size(); m++) {
                                int position = sparseBooleanArray.keyAt(m);
                                for (int i = 0; i < chatMessageList.size(); i++) {
                                    if (position == i) {
                                        ChatMessage chatMessageEntity = chatMessageList.get(i);
                                            resultData += chatMessageEntity.getMessage() + "\n";

                                        break;
                                    }
                                }
                            }
                        }

                        try {
                            Log.i("tag","result copy data after"+resultData);
                            String finaldata = resultData;
                            myClip = ClipData.newPlainText("text", finaldata.trim());
                            myClipboard.setPrimaryClip(myClip);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mode.finish();

                    return true;
                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            chatAdapter.clearSelections();
            actionMode = null;
        }

    }




}




