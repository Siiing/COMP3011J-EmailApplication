package com.app.emailapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DraftActivity extends AppCompatActivity {
    private EditText senderEditText, receiverEditText, ccEditText, bccEditText, subjectEditText, contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);

        senderEditText = findViewById(R.id.sender);
        receiverEditText = findViewById(R.id.receiver);
        ccEditText = findViewById(R.id.cc);
        bccEditText = findViewById(R.id.bcc);
        subjectEditText = findViewById(R.id.subject);
        contentEditText = findViewById(R.id.content);

        loadDraft();

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //turn to main menu & save current draft
                finish();
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

        findViewById(R.id.preview_button).setOnClickListener(view -> {
            Intent intent = new Intent(DraftActivity.this, PreviewActivity.class);
            intent.putExtra("SENDER", senderEditText.getText().toString());
            intent.putExtra("RECEIVER", receiverEditText.getText().toString());
            intent.putExtra("CC", ccEditText.getText().toString());
            intent.putExtra("BCC", bccEditText.getText().toString());
            intent.putExtra("SUBJECT", subjectEditText.getText().toString());
            intent.putExtra("CONTENT", contentEditText.getText().toString());
            startActivity(intent); // 跳转到预览界面
        });

    }

    private void loadDraft(){
        SharedPreferences sharedPreferences = getSharedPreferences("EmailDraft", MODE_PRIVATE);
        senderEditText.setText(sharedPreferences.getString("SENDER", ""));
        receiverEditText.setText(sharedPreferences.getString("RECEIVER", ""));
        ccEditText.setText(sharedPreferences.getString("CC", ""));
        bccEditText.setText(sharedPreferences.getString("BCC", ""));
        subjectEditText.setText(sharedPreferences.getString("SUBJECT", ""));
        contentEditText.setText(sharedPreferences.getString("CONTENT", ""));
    }
}