package com.app.emailapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SentActivity extends AppCompatActivity {

    private TextView sentTextView;
    private File emailDirectory;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);

        //back button
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //turn to main menu
                Intent intent = new Intent(SentActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //clear button
        sentTextView = findViewById(R.id.sent_text);
        emailDirectory = new File(getExternalFilesDir(null), "SentEmails");

        //delete all the sent email
        findViewById(R.id.clear_sent_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sentTextView.setText("");
                deleteAllSentEmails();
            }
        });

        loadSentEmails();
    }

    //show all the sent emails
    private void loadSentEmails(){
        StringBuilder allEmails = new StringBuilder();
        if (emailDirectory.exists()&& emailDirectory.isDirectory()){
            File[] files = emailDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    allEmails.append(readFileContent(file)).append("\n\n");
                }
            }
        }

        if  (allEmails.length() >0){
            sentTextView.setText(allEmails.toString());
            sentTextView.setTextSize(20);
        }else{
            sentTextView.setText("  No sent emails.");
            sentTextView.setTextSize(20);
        }
    }

    //read each sent emails
    private String readFileContent(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    //delete all the sent emails
    private void deleteAllSentEmails(){
        if (emailDirectory.exists() && emailDirectory.isDirectory()){
            File[] files = emailDirectory.listFiles();
            if (files != null){
                for (File file:files){
                    file.delete();
                }
            }
        }
    }

}
