package com.example.WithPet02.view.community;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.WithPet02.Board;
import com.example.WithPet02.Community;
import com.example.WithPet02.R;

import java.util.ArrayList;


public class CommunityFragment1 extends Fragment {
    ListView clistView;
    Community community;
    ArrayList<String> clist ;
    Button boardbutton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_community1,
                container, false);

        clist = new ArrayList<>();

        community = (Community) getActivity();

        clistView = rootView.findViewById(R.id.listView);
        ArrayList<String> list = new ArrayList<>();


        boardbutton = rootView.findViewById(R.id.boardbutton);
        boardbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Board.class);
                startActivity(intent);
            }
        });

        /*// 게시판글쓰기 버튼 눌렀을 때 board_write2창으로 넘어감
        boardbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),CommunityBoardButton.class);
                startActivity(intent);
            }
        });*/



        /*//listview클릭 시 activity_test로 연결 !
        clistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), CommunityBoardTest.class);
                startActivity(intent);
            }
        });*/



        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, list);
        clistView.setAdapter(adapter);


        list.add("강아지 여름철 간식레시피");
        list.add("우리 강아지가 최고에요!");
        list.add("님들 고양이도 이런가요?ㅠ");
        list.add("털갈이 힘들어요 하..ㅋ");
        list.add("유기견 입양 어떤가요?");
        list.add("털갈이 힘들어요 하..ㅋ");
        list.add("말티즈 옷 팔아요~");
        list.add("산책시 신발 신기는거");
        list.add("토끼 통 모양 정상인가요?");
        list.add("우리집 강아지 보고가세요~");
        list.add("강아지 여름철 간식레시피");
        list.add("님들 고양이도 이런가요?ㅠ");
        list.add("털갈이 힘들어요 하..ㅋ");
        list.add("산책시 신발 신기는거");

        return rootView;
    }
}
