package com.example.designer2.communityblog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.session.PlaybackState;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.designer2.communityblog.activites.MainActivity;
import com.example.designer2.communityblog.activites.PostLists;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class SignIn extends AppCompatActivity {
    public static final String BASE_URL = "http://mufassirislam.com/apps/blog/api/user.php?action=getUserList";
    private EditText email, password;
    private Button signup;
    ImageButton login;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        login = findViewById(R.id.signin_btn);
        signup = findViewById(R.id.go_signup_btn);
        progressDialog = new ProgressDialog(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin();

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, SignUp.class));
                CustomIntent.customType(SignIn.this,"fadein-to-fadeout");




            }
        });

    }

    private void userlogin() {
        final String Email = email.getText().toString();
        final String Password = password.getText().toString();
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        if (TextUtils.isEmpty(Email)) {
            email.setError("Please Enter your Email");
            email.requestFocus();
            progressDialog.dismiss();
            return;

        }
        if (TextUtils.isEmpty(Password)) {
            password.setError("Please Enter your Password");
            password.requestFocus();
            progressDialog.dismiss();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();


                    if (response.contains("truenull")) {
                        startActivity(new Intent(SignIn.this, MainActivity.class));
                        CustomIntent.customType(SignIn.this,"fadein-to-fadeout");
                        Toast.makeText(getApplicationContext(),"Log in Successfully",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"wrong username or password",Toast.LENGTH_SHORT).show();



                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error is :=-", "volly error" + error);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",email.getText().toString());
                params.put("password",password.getText().toString() );
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}