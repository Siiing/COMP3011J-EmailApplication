package com.app.emailapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PreviewActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        TextView senderTextView;
        TextView receiverTextView;
        TextView ccTextView;
        TextView bccTextView;
        TextView subjectTextView;
        TextView contentTextView;


        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //turn to main menu
                finish();
            }
        });

        findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //turn to sent interface
                Intent intent = new Intent(PreviewActivity.this,SentActivity.class);
                startActivity(intent);
                sendEmail();
            }
        });

        //send the data from composition to preview
        senderTextView = findViewById(R.id.sender);
        receiverTextView = findViewById(R.id.receiver);
        ccTextView = findViewById(R.id.cc);
        bccTextView = findViewById(R.id.bcc);
        subjectTextView = findViewById(R.id.subject);
        contentTextView = findViewById(R.id.content);

        Intent intent = getIntent();
        String sender = intent.getStringExtra("SENDER");
        String receiver = intent.getStringExtra("RECEIVER");
        String cc = intent.getStringExtra("CC");
        String bcc = intent.getStringExtra("BCC");
        String subject = intent.getStringExtra("SUBJECT");
        String content = intent.getStringExtra("CONTENT");

        senderTextView.setText(sender);
        receiverTextView.setText(receiver);
        ccTextView.setText(cc);
        bccTextView.setText(bcc);
        subjectTextView.setText(subject);
        contentTextView.setText(content);


        //send data from composition to preview
        findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreviewActivity.this,CompositionActivity.class);
                intent.putExtra("SENDER",senderTextView.getText().toString());
                intent.putExtra("RECEIVER",receiverTextView.getText().toString());
                intent.putExtra("CC",ccTextView.getText().toString());
                intent.putExtra("BCC",bccTextView.getText().toString());
                intent.putExtra("SUBJECT",subjectTextView.getText().toString());
                intent.putExtra("CONTENT",contentTextView.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void sendEmail() {
        String sender = ((TextView) findViewById(R.id.sender)).getText().toString();
        String receiver = ((TextView) findViewById(R.id.receiver)).getText().toString();
        String cc = ((TextView) findViewById(R.id.cc)).getText().toString();
        String bcc = ((TextView) findViewById(R.id.bcc)).getText().toString();
        String subject = ((TextView) findViewById(R.id.subject)).getText().toString();
        String content = ((TextView) findViewById(R.id.content)).getText().toString();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{receiver}); // Receiver
        emailIntent.putExtra(Intent.EXTRA_CC, cc.split(",")); // CC
        emailIntent.putExtra(Intent.EXTRA_BCC, bcc.split(",")); // BCC
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject); // Subject
        emailIntent.putExtra(Intent.EXTRA_TEXT, content); // Content

        // Add CC and BCC if they are not empty
        if (!cc.isEmpty()) {
            emailIntent.putExtra(Intent.EXTRA_CC, new String[]{cc});
        }
        if (!bcc.isEmpty()) {
            emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{bcc});
        }

        // Check if there is an email app that can handle the intent
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent); // Start the email client
        } else {
            // Optional: Notify user that no email clients are available
            Toast.makeText(this, "No email client found.", Toast.LENGTH_SHORT).show();
        }
        saveEmail();
    }

    //save email to 'sent' after clicking 'send' button
    private void saveEmail(){
        String sender = ((TextView) findViewById(R.id.sender)).getText().toString();
        String receiver = ((TextView) findViewById(R.id.receiver)).getText().toString();
        String cc = ((TextView) findViewById(R.id.cc)).getText().toString();
        String bcc = ((TextView) findViewById(R.id.bcc)).getText().toString();
        String subject = ((TextView) findViewById(R.id.subject)).getText().toString();
        String content = ((TextView) findViewById(R.id.content)).getText().toString();

        String sentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


        //set the style of sent emails
        String emailContent = "Sender: " + sender + "\n" +
                "Receiver: " + receiver + "\n" +
                "CC: " + cc + "\n" +
                "BCC: " + bcc + "\n" +
                "Subject: " + subject + "\n" +
                "Content: " + content + "\n" +
                "Sent_Time: " + sentTime + "\n";

        File directory = new File(getExternalFilesDir(null), "SentEmails");
        if (!directory.exists()) {
            directory.mkdirs(); // Create directory if it does not exist
        }
        File file = new File(directory, "email_" + System.currentTimeMillis() + ".txt");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(emailContent.getBytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}