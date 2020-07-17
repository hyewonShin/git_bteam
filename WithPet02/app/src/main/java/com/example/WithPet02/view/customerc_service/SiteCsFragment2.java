package com.example.WithPet02.view.customerc_service;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.WithPet02.Board;
import com.example.WithPet02.R;

public class SiteCsFragment2 extends Fragment {


    TextView textView2;
    Button QnAbutton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_site_cs2,
                container, false);

        textView2 = rootView.findViewById(R.id.textView2);

        QnAbutton = rootView.findViewById(R.id.QnAbutton);

        QnAbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SiteCsWirte.class);
                startActivity(intent);
            }
        });





        return rootView;
    }
}
