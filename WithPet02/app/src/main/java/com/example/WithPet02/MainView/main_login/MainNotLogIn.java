package com.example.WithPet02.MainView.main_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.WithPet02.MainActivity;
import com.example.WithPet02.R;
import com.example.WithPet02.view.login.LoginActivity;

public class MainNotLogIn extends Fragment {

    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main_not_log_in, container, false);

        activity = (MainActivity) getActivity();

        LinearLayout notlogin = rootView.findViewById(R.id.notlogin);
        notlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });

        return rootView;
    }
}
