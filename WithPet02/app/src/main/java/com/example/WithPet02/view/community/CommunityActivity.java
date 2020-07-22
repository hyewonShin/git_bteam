package com.example.WithPet02.view.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WithPet02.Adapter.LinearRecyclerViewAdapter;
import com.example.WithPet02.R;
import com.example.WithPet02.dto.FreeBoardDTO;
import com.example.WithPet02.view.community.atask.FreeBoardList;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CommunityActivity extends AppCompatActivity implements LinearRecyclerViewAdapter.OnListItemSelectedInterface{

    ArrayList<FreeBoardDTO> list;

    LinearLayout fillList;
    TextView emptyList;

    RecyclerView recyclerView;
    LinearRecyclerViewAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    Context context;
    Button btnWrite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        context = this;

        //툴바
        Toolbar c_toolbar = (Toolbar) findViewById(R.id.c_toolbar);
        setSupportActionBar(c_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setDisplayShowTitleEnabled(false);  // 타이틀 안보이게 하기

        //뷰에서 가져오기
        fillList = findViewById(R.id.fillList);
        emptyList = findViewById(R.id.emptyList);
        recyclerView = findViewById(R.id.recyclerview);
        btnWrite = findViewById(R.id.btnWrite);

        //게시판 목록 가져오기
        FreeBoardList freeBoardList = new FreeBoardList();
        try {
            list = freeBoardList.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(list == null){
            fillList.setVisibility(View.GONE);
            emptyList.setVisibility(View.VISIBLE);
        } else {
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new LinearRecyclerViewAdapter(this, list, this);
            recyclerView.setAdapter(adapter);

            btnWrite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //새글쓰기 액티비티로
                    Intent intent = new Intent(context, CommunityBoardWrite.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //리사이클러 뷰 아이템 선택
    @Override
    public void onItemSelected(View v, int position) {
        //게시글 상세 화면으로
        LinearRecyclerViewAdapter.MyViewHolder viewHolder = (LinearRecyclerViewAdapter.MyViewHolder)recyclerView.findViewHolderForAdapterPosition(position);
        Intent intent = new Intent(context, CommunityBoardDetail.class);
        FreeBoardDTO dto = list.get(position);
        intent.putExtra("dto", dto);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //게시판 목록 다시 가져오기
        FreeBoardList freeBoardList = new FreeBoardList();
        try {
            list = freeBoardList.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(list == null){
            fillList.setVisibility(View.GONE);
            emptyList.setVisibility(View.VISIBLE);
        } else {
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new LinearRecyclerViewAdapter(this, list, this);
            recyclerView.setAdapter(adapter);
        }
    }
}
