package com.app.emailapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.emailapplication.R;

public class CompositionActivity extends AppCompatActivity {

    private EditText sender;
    //transport the data to draft
    public EditText senderEditText;
    public EditText receiverEditText;
    public EditText ccEditText;
    public EditText bccEditText;
    public EditText subjectEditText;
    public EditText contentEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composition);

        //input the sender's email automatically
        sender = findViewById(R.id.sender);
        String userEmail = getIntent().getStringExtra("USER_EMAIL");
        if (userEmail != null) {
            sender.setText(userEmail);
        }

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //turn to main menu & save current draft
            saveDialog();
            }
        });

        EditText contentEdit = findViewById(R.id.content);
        findViewById(R.id.clear_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear the content
                contentEdit.setText("");
            }
        });

        //get data from composition
        senderEditText = findViewById(R.id.sender);
        receiverEditText = findViewById(R.id.receiver);
        ccEditText = findViewById(R.id.cc);
        bccEditText = findViewById(R.id.bcc);
        subjectEditText = findViewById(R.id.subject);
        contentEditText = findViewById(R.id.content);

        findViewById(R.id.preview_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompositionActivity.this, PreviewActivity.class);
                intent.putExtra("SENDER", senderEditText.getText().toString());
                intent.putExtra("RECEIVER", receiverEditText.getText().toString());
                intent.putExtra("CC", ccEditText.getText().toString());
                intent.putExtra("BCC", bccEditText.getText().toString());
                intent.putExtra("SUBJECT", subjectEditText.getText().toString());
                intent.putExtra("CONTENT", contentEditText.getText().toString());
                startActivity(intent);
            }
        });

        //resend the data from draft to composition
        Intent intent = getIntent();
        String sender = intent.getStringExtra("SENDER");
        String receiver = intent.getStringExtra("RECEIVER");
        String cc = intent.getStringExtra("CC");
        String bcc = intent.getStringExtra("BCC");
        String subject = intent.getStringExtra("SUBJECT");
        String content = intent.getStringExtra("CONTENT");
        if (sender != null) {
            senderEditText.setText(sender);
        }
        if (receiver != null) {
            receiverEditText.setText(receiver.replace(",",","));
        }
        if (cc != null) {
            ccEditText.setText(cc.replace(",",","));
        }
        if (bcc != null) {
            bccEditText.setText(bcc.replace(",",","));
        }
        if (subject != null) {
            subjectEditText.setText(subject);
        }
        if (content != null) {
            contentEditText.setText(content);
        }


    }

    private void saveDraft() {
        SharedPreferences sharedPreferences = getSharedPreferences("EmailDraft", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SENDER", senderEditText.getText().toString());
        editor.putString("RECEIVER", receiverEditText.getText().toString());
        editor.putString("CC", ccEditText.getText().toString());
        editor.putString("BCC", bccEditText.getText().toString());
        editor.putString("SUBJECT", subjectEditText.getText().toString());
        editor.putString("CONTENT", contentEditText.getText().toString());
        editor.apply();
    }

    private void saveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to save current content to your draft?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveDraft();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

}