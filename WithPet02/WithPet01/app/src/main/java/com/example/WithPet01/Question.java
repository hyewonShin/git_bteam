package com.example.WithPet02;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Question extends Fragment {
    //메인에 속한것을 알려주기 위한 변수 설정
    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_question, container, false);
        //MainActivity에 속했음을 알려준다.
        activity = (MainActivity) getActivity();

        //free버튼을 누를 수 있게 위치를 찾아옴
        Button free = rootView.findViewById(R.id.free);
        free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity.onFragmentChange(1);
            }//onClick()
        });//setOnClickListener()

        //글쓰기 버튼 찾고 눌렀을 때 화면 전환
        Button writebtn = rootView.findViewById(R.id.writeBtn);
        writebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), Board_List1.class);
                startActivity(intent);
            }//onClick()
        });//setOnClickListener()


        return rootView;
    }//onCreateView
}//class
