package com.app.emailapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText addressInput;
    private EditText passwordInput;
    private TextView addressError;
    private TextView passwordError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addressInput = findViewById(R.id.address_input);
        passwordInput = findViewById(R.id.password_input);
        addressError = findViewById(R.id.address_empty);
        passwordError = findViewById(R.id.password_empty);


        //turn to the email main menu
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if the input is empty, or give a notice
                String address = addressInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                addressError.setVisibility(View.INVISIBLE);
                passwordError.setVisibility(View.INVISIBLE);

                boolean isValid = true;
                if (address.isEmpty()){
                    addressError.setVisibility(View.VISIBLE);
                    isValid=false;
                }
                if (password.isEmpty()){
                    passwordError.setVisibility(View.VISIBLE);
                    isValid=false;
                }
                if(isValid) {
                    //login successfully
                    //store the user's email address
                    SharedPreferences sp =getSharedPreferences("user_prefs",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("USER_EMAIL",address);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("USER_EMAIL",address);
                    startActivity(intent);
                }
            }
        });
    }
}