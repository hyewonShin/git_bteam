package com.example.WithPet02.view.customerc_service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WithPet02.R;
import com.example.WithPet02.dto.BoardDTO;

import java.util.ArrayList;

public class SiteCsFragment2Adapter extends RecyclerView.Adapter<SiteCsFragment2Adapter.ViewHolder> {

    Context context;
    ArrayList<BoardDTO> list;

    public SiteCsFragment2Adapter(Context context, ArrayList<BoardDTO> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //각 포지션의 item들은 BoardDTO, BoardDTO가 여러개인것이 ArrayList<BoardDTO> list
        BoardDTO item = list.get(position);
        holder.num.setText(String.valueOf(item.getB_num()));
        holder.title.setText(item.getB_title());
        String date = item.getB_date().substring(0, 10);
        holder.date.setText(date);

        //holder.setItem(item);
    }//onBindViewHolder()

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_site_cs_recyclerview, parent, false);

        return new ViewHolder(itemView);
    }//onCreateViewHolder()


    @Override
    public int getItemCount() {

        return list.size();
    }//getItemCount()

    //RecyclerView로 만들 xml의 것을 가져오는 ViewHolder를 생성
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView num, title, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //.xml의 view를 가지고 옴
            num = itemView.findViewById(R.id.cs_num);
            title = itemView.findViewById(R.id.cs_title);
            date = itemView.findViewById(R.id.cs_date);
        }//ViewHolder()

        /*public void setItem(BoardDTO item) {
            num.setText(String.valueOf(item.getB_num()));
            title.setText(item.getB_title());
            date.setText(item.getB_date());
        }//setItem()*/

    }//ViewHolder()


    //가지고 온 DTO를 ArrayList넣어주는 메소드
    /*public void addItem (ArrayList<BoardDTO> item) {
        list = item;
    }//addItem*/

}//class
