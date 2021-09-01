package com.example.retailerapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyOtp extends AppCompatActivity {

    EditText inputNo1, inputNo2, inputNo3, inputNo4, inputNo5, inputNo6;
    String getOtpBackend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        final Button submitotp = findViewById(R.id.submitotp);

        inputNo1 = findViewById(R.id.inputotp1);
        inputNo2 = findViewById(R.id.inputotp2);
        inputNo3 = findViewById(R.id.inputotp3);
        inputNo4 = findViewById(R.id.inputotp4);
        inputNo5 = findViewById(R.id.inputotp5);
        inputNo6 = findViewById(R.id.inputotp6);

        getOtpBackend = getIntent().getStringExtra("verificationId");

        final ProgressBar preogressBarVerifyOtp = findViewById(R.id.progressbar_verify_otp);

        TextView textView = findViewById(R.id.loginsendotpdescr);
        textView.setText(String.format("+91-%s", getIntent().getStringExtra("mobile")));

        submitotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inputNo1.getText().toString().trim().isEmpty() && !inputNo2.getText().toString().trim().isEmpty()
                        && !inputNo3.getText().toString().trim().isEmpty() && !inputNo4.getText().toString().trim().isEmpty()
                        && !inputNo5.getText().toString().trim().isEmpty() && !inputNo6.getText().toString().trim().isEmpty()) {

                    String userEnterOtp = inputNo1.getText().toString().trim() +
                            inputNo2.getText().toString().trim() +
                            inputNo3.getText().toString().trim() +
                            inputNo4.getText().toString().trim() +
                            inputNo5.getText().toString().trim() +
                            inputNo6.getText().toString().trim();

                    if (getOtpBackend != null) {
                        preogressBarVerifyOtp.setVisibility(View.VISIBLE);
                        submitotp.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(getOtpBackend, userEnterOtp);

                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                preogressBarVerifyOtp.setVisibility(View.GONE);
                                submitotp.setVisibility(View.VISIBLE);

                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(getApplicationContext(), registerform.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please Enter Correct Otp", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Check Internet Connection yyyy", Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(getApplicationContext(), "otp verify", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter all number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        numbertomove();
        findViewById(R.id.tesxtresendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void numbertomove() {
        inputNo1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputNo2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        inputNo2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputNo3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        inputNo3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputNo4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        inputNo4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputNo5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        inputNo5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputNo6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}