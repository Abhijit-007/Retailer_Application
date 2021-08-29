package com.example.retailerapplication;

import static com.google.firebase.auth.PhoneAuthProvider.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.PrimitiveIterator;
import java.util.concurrent.TimeUnit;

public class LoginScreen extends AppCompatActivity {
    private static final String TAG = "PhoneAuthActivity";
    EditText enterNumber;
    Button getOtpButton;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private String phoneNo;
    private ForceResendingToken mResendToken;
    private OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);

         enterNumber=findViewById(R.id.input_mobile_no);
         getOtpButton=findViewById(R.id.buttongetotp);
         ProgressBar progressbar=findViewById(R.id.progressbar_sending_otp);

        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.

                progressbar.setVisibility(View.GONE);
                getOtpButton.setVisibility(View.VISIBLE);
                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);


                progressbar.setVisibility(View.GONE);
                getOtpButton.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(),"Please Check Internet Connection Failed",Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                progressbar.setVisibility(View.GONE);
                getOtpButton.setVisibility(View.VISIBLE);
                Intent intent=new Intent(getApplicationContext(),VerifyOtp.class);
                intent.putExtra("mobile",enterNumber.getText().toString());
                intent.putExtra("verificationId",verificationId);

                startActivity(intent);
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }


        };


         getOtpButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(!enterNumber.getText().toString().trim().isEmpty()){
                    if(enterNumber.getText().toString().length()==10){
                        phoneNo="+91"+enterNumber.getText().toString();
                        progressbar.setVisibility(View.VISIBLE);
                        getOtpButton.setVisibility(View.INVISIBLE);

                        startPhoneNumberVerification(phoneNo);
                        /*Intent intent=new Intent(getApplicationContext(),VerifyOtp.class);
                        intent.putExtra("mobile",enterNumber.getText().toString());
                        startActivity(intent);*/
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Please Enter Correct NUmber",Toast.LENGTH_SHORT).show();
                    }
                 }
                 else{

                     Toast.makeText(getApplicationContext(),"Enter Mobile Number",Toast.LENGTH_SHORT).show();
                 }
             }
         });
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(20L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }


}