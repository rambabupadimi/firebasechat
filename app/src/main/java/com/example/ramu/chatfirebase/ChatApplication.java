package com.example.ramu.chatfirebase;

import android.app.Application;


import com.google.firebase.database.FirebaseDatabase;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

/**
 * Created by Ramu on 28-05-2017.
 */

public class ChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);


        EmojiManager.install(new IosEmojiProvider());
    }
}
