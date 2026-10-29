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
import com.homexa.app.data.AuthHelper;

/**
 * Login screen — checks email/password in SQLite.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText editLoginEmail;
    private EditText editLoginPassword;
    private MaterialButton buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // If user already logged in, go straight to Home
        if (AuthHelper.isLoggedIn(this)) {
            openHome();
            return;
        }

        editLoginEmail = findViewById(R.id.editLoginEmail);
        editLoginPassword = findViewById(R.id.editLoginPassword);
        CheckBox checkboxRememberMe = findViewById(R.id.checkboxRememberMe);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        TextView textCreateAccount = findViewById(R.id.textCreateAccount);
        TextView textForgotPassword = findViewById(R.id.textForgotPassword);

        buttonSignIn.setOnClickListener(v -> attemptLogin());
        textCreateAccount.setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class)));
        textForgotPassword.setOnClickListener(v ->
                Toast.makeText(this, "Use Sign Up if you are new", Toast.LENGTH_SHORT).show());
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
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            editLoginPassword.setError("Password must be at least 6 characters");
            editLoginPassword.requestFocus();
            return;
        }

        buttonSignIn.setEnabled(false);

        AuthHelper.signIn(this, email, password, new AuthHelper.AuthCallback() {
            @Override
            public void onSuccess(long userId) {
                buttonSignIn.setEnabled(true);
                Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                openHome();
            }

            @Override
            public void onError(String message) {
                buttonSignIn.setEnabled(true);
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
