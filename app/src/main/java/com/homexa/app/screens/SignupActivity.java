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
import com.homexa.app.data.AuthHelper;

/**
 * Sign up screen — saves new user into SQLite users table.
 */
public class SignupActivity extends AppCompatActivity {

    private EditText editSignupName;
    private EditText editSignupEmail;
    private EditText editSignupPhone;
    private EditText editSignupPassword;
    private EditText editSignupConfirmPassword;
    private MaterialButton buttonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editSignupName = findViewById(R.id.editSignupName);
        editSignupEmail = findViewById(R.id.editSignupEmail);
        editSignupPhone = findViewById(R.id.editSignupPhone);
        editSignupPassword = findViewById(R.id.editSignupPassword);
        editSignupConfirmPassword = findViewById(R.id.editSignupConfirmPassword);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
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
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            editSignupPassword.setError("Password must be at least 6 characters");
            editSignupPassword.requestFocus();
            return;
        }
        if (!password.equals(confirm)) {
            editSignupConfirmPassword.setError("Passwords do not match");
            editSignupConfirmPassword.requestFocus();
            return;
        }

        buttonCreateAccount.setEnabled(false);

        AuthHelper.signUp(this, name, email, phone, password, new AuthHelper.AuthCallback() {
            @Override
            public void onSuccess(long userId) {
                buttonCreateAccount.setEnabled(true);
                Toast.makeText(SignupActivity.this,
                        "Account created! You are signed in.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String message) {
                buttonCreateAccount.setEnabled(true);
                Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
