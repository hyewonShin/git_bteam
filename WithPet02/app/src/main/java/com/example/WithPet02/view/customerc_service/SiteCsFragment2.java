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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WithPet02.Board;
import com.example.WithPet02.R;
import com.example.WithPet02.dto.BoardDTO;
import com.example.WithPet02.view.customerc_service.atask.BoardGet;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SiteCsFragment2 extends Fragment {

    RecyclerView recyclerView;
    ArrayList<BoardDTO> list = new ArrayList<>();

    TextView textView2;
    Button QnAbutton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_site_cs2,
                container, false);

        //recyclerView찾기
        rootView.findViewById(R.id.cs2_recyclerview);

        //recyclerview에 Recyclerview 형태 설정하고 사용하겠다고 넣어줌
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //DB와 통신해서 list의 값을 가지고 와줌(지금은 이름을 따로 가져오지 않고 1로 넣어줌)
        BoardGet boardGet = new BoardGet("1");
        try {
          list =  boardGet.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SiteCsFragment2Adapter adapter = new SiteCsFragment2Adapter();
        adapter.addItem(list);

        recyclerView.setAdapter(adapter);

        //textView2 = rootView.findViewById(R.id.textView2);

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
