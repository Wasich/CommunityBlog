package com.example.designer2.communityblog;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LogInFragment extends Fragment {


    public LogInFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_log_in, container, false);
        Button buttonsignup = (Button)view.findViewById(R.id.go_signup);
        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpFragment();
            }
        });
        return view;
    }
    private void showSignUpFragment(){
        Fragment signupfragment = new RegisterFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.setCustomAnimations(android.R.anim.slide_out_right,
                android.R.anim.slide_in_left);
        transaction.replace(R.id.account, signupfragment);
        transaction.commit();
    }

}
