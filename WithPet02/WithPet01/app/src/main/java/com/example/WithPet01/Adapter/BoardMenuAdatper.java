package com.example.WithPet02.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WithPet02.R;
import com.example.WithPet02.dto.BoardMenuDTO;

import java.util.ArrayList;

public class BoardMenuAdatper extends RecyclerView.Adapter<BoardMenuAdatper.ViewHolder> {

    ArrayList<BoardMenuDTO> list = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.boardmenulist, parent, false);

        return new ViewHolder(itemView);
    }//onCreateViewHolder()

    ////ViewHolder에대해 position마다 item을 붙여준다.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BoardMenuDTO item = list.get(position);
        holder.setItem(item);

        //boardIcon을 클릭했을 때 이벤트 설정
        holder.boardIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }//onClick()
        });//setOnClickListener()

        //boardName을 클릭했을 때 이벤트 설정
        holder.boardName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }//onClick()
        });//setOnClickListener()

    }//onBindViewHolder()

    //item(ArrayList)안에 얼마큼 들어가 있는지 알려주는 것
    @Override
    public int getItemCount() {
        return list.size();
    }//getItemCount()

    //가지고온 DTO를 ArrayList에 넣어준다
    public void addItem(BoardMenuDTO item) {
        list.add(item);
    }//addItem()

    //ViewHolder에 xml의 것들 가져와주기
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView boardIcon;
        TextView boardName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //xml의 view를 가지고 온다.
            boardIcon = itemView.findViewById(R.id.boardIcon);
            boardName = itemView.findViewById(R.id.boardName);
        }//ViewHolder()

        //DTO에 아이콘과 이름 넣기
        public void setItem(BoardMenuDTO item) {
            boardIcon.setImageResource(item.getResid());
            boardName.setText(item.getMenu_name());
        }//setItem()
    }//ViewHolder()

}//class
