package com.example.WithPet02.view.customerc_service;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WithPet02.R;
import com.example.WithPet02.dto.BoardDTO;
import com.example.WithPet02.view.customerc_service.atask.BoardGet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.view.customerc_service.SiteCsActivity.CsContext;
import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

public class SiteCsFragment2 extends Fragment {

    Context context;

    RecyclerView recyclerView;
    ArrayList<BoardDTO> list = new ArrayList<>();
    ArrayList<BoardDTO> r_list = new ArrayList<>();

    Button QnAbutton;

    LinearLayoutManager layoutManager;
    SiteCsFragment2Adapter adapter;

    public SiteCsFragment2 () {

    }

    public SiteCsFragment2(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_site_cs2,
                container, false);

        //recyclerView찾기
        recyclerView = rootView.findViewById(R.id.cs2_recyclerview);

        setRecyclerView();

        /*//recyclerview에 Recyclerview 형태 설정하고 사용하겠다고 넣어줌
        layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SiteCsFragment2Adapter(context, list);
        recyclerView.setAdapter(adapter);*/

        QnAbutton = rootView.findViewById(R.id.QnAbutton);

        QnAbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SiteCsWirte.class);
                startActivity(intent);
            }
        });

        return rootView;
    }//onCreateView()

    public void setRecyclerView() {

        //DB와 통신해서 list의 값을 가지고 와줌
        BoardGet boardGet = new BoardGet(loginDTO.getM_tel());
        try {
            list =  boardGet.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //recyclerview에 Recyclerview 형태 설정하고 사용하겠다고 넣어줌
        layoutManager = new LinearLayoutManager(CsContext, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SiteCsFragment2Adapter(CsContext, list);
        recyclerView.setAdapter(adapter);

    }//setRecyclerView()

}//class
