package com.app.emailapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String userEmail = getIntent().getStringExtra("USER_EMAIL");

        //get the user's email address
        TextView welcome = findViewById(R.id.welcome_text);
        if (userEmail == null){
            SharedPreferences sp = getSharedPreferences("user_prefs",MODE_PRIVATE);
            userEmail=sp.getString("USER_EMAIL",null);
        }
        if (userEmail != null) {
            welcome.setText("Welcome Back!" + "\n" + userEmail);
        } else{
            welcome.setText("Welcome Back!");
        }

        //transport this user email to composition sender
        String finalUserEmail = userEmail;
        findViewById(R.id.composition_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //turn to composition
                Intent intent = new Intent(MainActivity.this,CompositionActivity.class);
                intent.putExtra("USER_EMAIL", finalUserEmail);
                startActivity(intent);
            }
        });

        findViewById(R.id.draft_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //turn to sent
                Intent intent = new Intent(MainActivity.this,DraftActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.sent_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //turn to sent
                Intent intent = new Intent(MainActivity.this,SentActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.logout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //turn to login
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        //help button for introduction
        ImageView help_button = findViewById(R.id.help_button);
        help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Introduction & Help")
                        .setMessage("Here is the introduction & help for you. \n" + "\n"+
                                "The first button 'COMPOSITION' is where you can edit your email, similar as other email applications"+"\n"+"\n"+
                                "The second button 'DRAFT' is where you can see the recentest email you stored in the draft before your leave."+"\n"+"\n"+
                                "The third button 'SENT' is where you can just read what you have sent but cannot rewrite."+"\n"+"\n"+
                                "The forth button 'LOGOUT' is clear :) if you want to logout this email, just click it!"+"\n"+"\n"+
                                "Tips: If you want to send to multiple people in cc&bcc, just use ',' to split different email address!"
                        )
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();  //show the help information
            }
        });
    }
}