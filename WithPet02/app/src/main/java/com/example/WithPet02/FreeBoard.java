package com.example.WithPet02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FreeBoard extends Fragment {
    //메인에 속한것을 설정해주기 위한 변수 설정
    MainActivity activity;

    //MainActivity에 속했다는 것을 설정
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }//onAttach()

    //위에 설정한 activity로 화면을 꺼주게 해주기
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }//onDetach()



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //activity_freeboard.xml과 연결시켜주기
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_freeboard, container, false);

        //question을 가져와준다.
        //question를 누르면 물어보세요 게시판으로 이동
        Button question = rootView.findViewById(R.id.question);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //question버튼을 눌렀을 때 2
                //activity.onFragmentChange(2);
            }//onClick()
        });//setOnClickListener()

        //글쓰기 버튼 찾아와 준다.
        Button writeBtn = rootView.findViewById(R.id.writeBtn);

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), Board.class);
                startActivity(intent);
            }//onClick()
        });//setOnClickListener()


        return rootView;
    }//onCreateView()

}//class
