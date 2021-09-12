package com.example.retailerapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class registerform extends AppCompatActivity {

    EditText firstName, lastName, email, addressLine1, addressLine2,
            pincode, storeName;
    String firstNameValue="",lastNameValue="",emailValue="",addressValue1="",addressValue2="",pincodeValue="",storeNameValue="";
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


                    firstNameValue=firstName.getText().toString();
                    lastNameValue=lastName.getText().toString();
                    emailValue=email.getText().toString();
                    addressValue1=addressLine1.getText().toString();
                    addressValue2=addressLine2.getText().toString();
                    pincodeValue=pincode.getText().toString();
                    storeNameValue=storeName.getText().toString();

                    progressBarRegisterForm.setVisibility(View.GONE);
                    registerForm.setVisibility(View.VISIBLE);

                    //RegisterData registerData=new RegisterData();
                    //registerData.execute("");
                    processData("+91737838338","yes");
                    Intent intent = new Intent(getApplicationContext(), dashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Succesfully registered",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all mandatory fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void processData(String s, String eieiei) {
        /*StringRequest request=new StringRequest(Request.Method.POST, "https://jgmsfyyvk4.execute-api.us-east-2.amazonaws.com/default/RetailerFunction?mobileNo=9773737228", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Rest API Success.."+response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Rest API Failed.."+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<String,String>();
               // map.put("mobileNo",s);
                //map.put("isAvailable",eieiei);
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);*/
        String URL="https://1o2016dkq7.execute-api.us-east-2.amazonaws.com/Dev/retailer";
        Map<String, String> params = new HashMap();

        params.put("mobileNo", getIntent().getStringExtra("mobileNo"));
        params.put("firstName", firstNameValue);
        params.put("lastName", lastNameValue);

        params.put("email", emailValue);
        params.put("address", addressValue1+addressValue2);
        params.put("pincode", pincodeValue);
        params.put("storeName", storeNameValue);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, URL, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(),"Succesfully registered",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Fail to register", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


    }

    private class RegisterData extends AsyncTask<String,String,String>{

        String DB_URL="jdbc:mysql://retailerdb.cwuhlpfvzj88.us-east-2.rds.amazonaws.com:3306";
        String USER="admin";
        String PASS="admin123";
        String msg="";
        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn= DriverManager.getConnection(DB_URL,USER,PASS);
                if(conn==null){
                    msg="connection wring";
                }
                else{
                    String query="INSERT INTO UserData(mobileNo,firstName,lastName,emailid,address,pincode,storeName) Values('+911','first','last','email','add','pin','store')";
                    Statement stmt=conn.createStatement();
                    stmt.executeQuery(query);
                    msg="succeful registration";
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}