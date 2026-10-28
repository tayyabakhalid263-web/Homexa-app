package com.homexa.app.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.homexa.app.R;

/**
 * Sign up screen with basic validation only (no server / database).
 */
public class SignupActivity extends AppCompatActivity {

    private EditText editSignupName;
    private EditText editSignupEmail;
    private EditText editSignupPhone;
    private EditText editSignupPassword;
    private EditText editSignupConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editSignupName = findViewById(R.id.editSignupName);
        editSignupEmail = findViewById(R.id.editSignupEmail);
        editSignupPhone = findViewById(R.id.editSignupPhone);
        editSignupPassword = findViewById(R.id.editSignupPassword);
        editSignupConfirmPassword = findViewById(R.id.editSignupConfirmPassword);
        MaterialButton buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        TextView textSignInLink = findViewById(R.id.textSignInLink);

        buttonCreateAccount.setOnClickListener(v -> attemptSignup());
        textSignInLink.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void attemptSignup() {
        String name = editSignupName.getText().toString().trim();
        String email = editSignupEmail.getText().toString().trim();
        String phone = editSignupPhone.getText().toString().trim();
        String password = editSignupPassword.getText().toString().trim();
        String confirm = editSignupConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            editSignupName.setError("Full name is required");
            editSignupName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editSignupEmail.setError("Enter a valid email");
            editSignupEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone) || phone.length() < 10) {
            editSignupPhone.setError("Enter a valid phone number");
            editSignupPhone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 4) {
            editSignupPassword.setError("Password must be at least 4 characters");
            editSignupPassword.requestFocus();
            return;
        }
        if (!password.equals(confirm)) {
            editSignupConfirmPassword.setError("Passwords do not match");
            editSignupConfirmPassword.requestFocus();
            return;
        }

        Toast.makeText(this, "Account created (demo). Please sign in.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
