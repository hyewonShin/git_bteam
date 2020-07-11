package com.example.WithPet02.view.customerc_service;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.WithPet02.R;

public class SiteCsFragment2 extends Fragment {


    TextView textView2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_site_cs2,
                container, false);

        textView2 = rootView.findViewById(R.id.textView2);







        return rootView;
    }
}
