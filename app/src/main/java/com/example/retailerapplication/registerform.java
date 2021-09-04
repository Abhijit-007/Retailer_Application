package com.example.retailerapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class registerform extends AppCompatActivity {

    EditText firstName, lastName, email, addressLine1, addressLine2,
            pincode, storeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerform);

        final Button registerForm = findViewById(R.id.registerform);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.edit_email);
        addressLine1 = findViewById(R.id.edit_addressline1);
        addressLine2 = findViewById(R.id.edit_addressline2);
        pincode = findViewById(R.id.edit_pincode);
        storeName = findViewById(R.id.store_Name);

        final ProgressBar progressBarRegisterForm = findViewById(R.id.progressbar_registerform);

        registerForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!firstName.getText().toString().trim().isEmpty() &&
                        !lastName.getText().toString().trim().isEmpty() &&
                        !addressLine1.getText().toString().trim().isEmpty() &&
                        !pincode.getText().toString().trim().isEmpty()) {
                    progressBarRegisterForm.setVisibility(View.VISIBLE);
                    registerForm.setVisibility(View.INVISIBLE);

                    // TODO: save register form info in DB.

                    progressBarRegisterForm.setVisibility(View.GONE);
                    registerForm.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(getApplicationContext(), dashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all mandatory fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}