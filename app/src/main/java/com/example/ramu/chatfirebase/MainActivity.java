package com.example.ramu.chatfirebase;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ramu.chatfirebase.EventAdmin.EventsDisplay;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    List userList;
    FirebaseRecyclerAdapter<Users,ExpensesViewHolder> firebaseRecyclerAdapter;

    TextView name,logout;
    Toolbar toolbar;

    TextView showEvent;

    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userList    =   new ArrayList();
        mAuth   =   FirebaseAuth.getInstance();
                mAuthListener   =   new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() ==null)
                {
                    Intent intent = new Intent(MainActivity.this,SignupOrLogin.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        };


        showEvent = (TextView)findViewById(R.id.show_event);
        showEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventsDisplay.class);
                startActivity(intent);
            }
        });

        button = (FloatingActionButton) findViewById(R.id.float_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GroupChatActivity.class);
                startActivity(intent);
            }
        });

        firebaseDatabase   =    FirebaseDatabase.getInstance();
        if(firebaseDatabase==null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }
        databaseReference   = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference.keepSynced(true);
        recyclerView        =   (RecyclerView) findViewById(R.id.recylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        name        =   (TextView) findViewById(R.id.homepage_name);
        if(mAuth!=null && mAuth.getCurrentUser()!=null) {
            name.setText(mAuth.getCurrentUser().getEmail());
            logout = (TextView) findViewById(R.id.logout);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signOut();
                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, ExpensesViewHolder>(
                Users.class,
                R.layout.chat_user_list_layout,
                ExpensesViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(final ExpensesViewHolder viewHolder, final Users model, final int position) {

                viewHolder.setName(model.getName());
                viewHolder.setPhone(model.getPhone());

                Log.i("tag","name"+model.getName());
                Log.i("tag","phone"+model.getPhone());
                Log.i("tag","userid"+model.getUserid());
                Log.i("tag","url"+model.getImgurl());

                if(model.getImgurl()!=null)
                {
                    // viewHolder.setImage(model.getImgurl());
            /*
                    StorageReference storageReference = FirebaseStorage.getInstance()
                            .getReference(model.getImgurl());
            */

                 if(model.getImgurl()!=null) {

         /*
                     Glide.with(MainActivity.this)
                             .load(model.getImgurl())
                             .into(viewHolder.imageView);
         */
                     Picasso.with(MainActivity.this).load(model.getImgurl()).networkPolicy(NetworkPolicy.OFFLINE)
                             .into(viewHolder.imageView, new Callback() {
                                 @Override
                                 public void onSuccess() {

                                 }

                                 @Override
                                 public void onError() {
                                    Picasso.with(MainActivity.this).load(model.getImgurl()).into(viewHolder.imageView);
                                 }
                             });
                 }

                }
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,ChatActivity.class);
                        intent.putExtra("name",model.getName());
                        intent.putExtra("phone",model.getPhone());
                        intent.putExtra("userid",model.getUserid());
                        startActivity(intent);

                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    public static class ExpensesViewHolder extends RecyclerView.ViewHolder
    {

        View mview;
        ImageView imageView;
        public ExpensesViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            imageView = (ImageView) mview.findViewById(R.id.pic);

        }

        public void setName(String name)
        {
            TextView amountTextViw =  (TextView) mview.findViewById(R.id.user_list_name);
            amountTextViw.setText(""+name);
        }

        public void setPhone(String phone)
        {
            TextView amountTextViw =  (TextView) mview.findViewById(R.id.user_list_email);
            amountTextViw.setText(""+phone);
        }

        public void setImage(String image)
        {


        }

    }
}
