package com.example.imransk.loginwithretrofit.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imransk.loginwithretrofit.ModelClass.User;
import com.example.imransk.loginwithretrofit.R;
import com.example.imransk.loginwithretrofit.Storage.SharedPrefManager;


public class HomeFragment extends Fragment {

    private TextView textVIewEmail;
    private TextView textVIewName;
    private TextView textVIewSchool;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textVIewEmail = view.findViewById(R.id.textviewEmail);
        textVIewName = view.findViewById(R.id.textviewname);
        textVIewSchool = view.findViewById(R.id.textviewschool);

        User user= SharedPrefManager.getInstance(view.getContext()).getUser();
        textVIewEmail.setText(user.getEmail());
        textVIewName.setText(user.getName());
        textVIewSchool.setText(user.getSchool());


    }
}
