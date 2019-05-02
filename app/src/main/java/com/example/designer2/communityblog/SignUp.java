package com.example.designer2.communityblog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.designer2.communityblog.activites.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class SignUp extends AppCompatActivity {
    public static final String BASE_URL ="http://mufassirislam.com/apps/blog/api/user.php?action=createUserList";
    ProgressDialog progressDialog;
    Boolean Val = true;
    private EditText firstname,lastname,email,password,phone;
    ImageButton imageButton;

    private Button buttonsigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        buttonsigin =  findViewById(R.id.go_signin);
        firstname = findViewById(R.id.firstName);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        imageButton = findViewById(R.id.register_submit);
        progressDialog = new ProgressDialog(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        buttonsigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, SignIn.class));
                CustomIntent.customType(SignUp.this,"fadein-to-fadeout");
            }
        });

    }
    public void registerUser() {
        final String Firtstname = firstname.getText().toString();
        final String Lastname = lastname.getText().toString();
        final String Email = email.getText().toString();
        final String Password = password.getText().toString();
        final String Phone = phone.getText().toString();

        progressDialog.setMessage("Posting Please Wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.contains("true")) {
                    startActivity(new Intent(SignUp.this, MainActivity.class));

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                else {
                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error is :=-", "volly error" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("first_name", Firtstname);
                params.put("last_name", Lastname);
                params.put("email", Email);
                params.put("mobile", Phone);
                params.put("password", Password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
