package com.example.designer2.communityblog;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterFragment extends Fragment   {
    public static final String BASE_URL ="http://mufassirislam.com/apps/blog/api/user.php?action=createUserList";
    ProgressDialog progressDialog;
    Boolean Val = true;
    private EditText firstname,lastname,email,password;
    private Button buttonsigin;



    public RegisterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        ProgressDialog progressDialog;

        buttonsigin = view.findViewById(R.id.go_signin);
        firstname = view.findViewById(R.id.firstName);
        lastname = view.findViewById(R.id.lastname);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);

        // phone = view.findViewById(R.id.phone);
       // ImageButton register = (ImageButton)view.findViewById(R.id.register_submit);

//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShowsignInFragment();
//            }
//        });

        buttonsigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        return view;
    }
//    private void ShowsignInFragment(){
//        Fragment signinfragment = new LogInFragment();
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.addToBackStack(null);
//        transaction.setCustomAnimations(android.R.anim.slide_in_left,
//                android.R.anim.slide_out_right);
//        transaction.replace(R.id.account, signinfragment);
//        transaction.commit();
//
//    }
    public void registerUser(){
        final String Firtstname = firstname.getText().toString();
        final String Lastname = lastname.getText().toString();
        final String Email = email.getText().toString();
        final String Password  = password.getText().toString();

            progressDialog.setMessage("Posting Please Wait....");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error is :=-", "volly error" + error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("first_name", Firtstname);
                    //params.put("last_name", Lastname);
                    params.put("email", Email);
                    params.put("username", Lastname);
                    params.put("password", Password);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            requestQueue.add(stringRequest);
        }




}
