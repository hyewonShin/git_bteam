package com.example.WithPet02.view.community;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.WithPet02.R;


public class CommunityFragment2 extends Fragment {
    Button QnAbutton;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_community2,
                container, false);

        QnAbutton = rootView.findViewById(R.id.QnAbutton);

        QnAbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),CommunityBoardWrite.class);
                startActivity(intent);
            }
        });



        return rootView;
    }
}
