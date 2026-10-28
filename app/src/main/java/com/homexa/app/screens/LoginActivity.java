package com.homexa.app.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.homexa.app.R;

/**
 * Login screen with basic validation only (no server / database).
 */
public class LoginActivity extends AppCompatActivity {

    private EditText editLoginEmail;
    private EditText editLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editLoginEmail = findViewById(R.id.editLoginEmail);
        editLoginPassword = findViewById(R.id.editLoginPassword);
        CheckBox checkboxRememberMe = findViewById(R.id.checkboxRememberMe);
        MaterialButton buttonSignIn = findViewById(R.id.buttonSignIn);
        TextView textCreateAccount = findViewById(R.id.textCreateAccount);
        TextView textForgotPassword = findViewById(R.id.textForgotPassword);

        buttonSignIn.setOnClickListener(v -> attemptLogin());
        textCreateAccount.setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class)));
        textForgotPassword.setOnClickListener(v ->
                Toast.makeText(this, "Reset link will be added later", Toast.LENGTH_SHORT).show());
        // Remember-me is UI only for now.
        checkboxRememberMe.setChecked(false);
    }

    private void attemptLogin() {
        String email = editLoginEmail.getText().toString().trim();
        String password = editLoginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            editLoginEmail.setError("Email is required");
            editLoginEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editLoginEmail.setError("Enter a valid email");
            editLoginEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 4) {
            editLoginPassword.setError("Password must be at least 4 characters");
            editLoginPassword.requestFocus();
            return;
        }

        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
