package com.example.ramu.chatfirebase;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignupOrLogin extends AppCompatActivity {

    Button signup,siginin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_or_login);
        signup  = (Button)findViewById(R.id.signup);
        siginin =   (Button)findViewById(R.id.signin);

        TextView middle = (TextView)findViewById(R.id.main_note);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupOrLogin.this,Register.class);
                startActivity(intent);

            }
        });
        siginin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupOrLogin.this,LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}
