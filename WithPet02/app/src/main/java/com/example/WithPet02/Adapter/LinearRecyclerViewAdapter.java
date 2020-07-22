package com.example.WithPet02.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WithPet02.R;
import com.example.WithPet02.dto.FreeBoardDTO;

import java.util.ArrayList;

public class LinearRecyclerViewAdapter extends RecyclerView.Adapter<LinearRecyclerViewAdapter.MyViewHolder> {

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface mListener;

    Context context;
    ArrayList<FreeBoardDTO> list;

    public LinearRecyclerViewAdapter(Context context, ArrayList<FreeBoardDTO> list, OnListItemSelectedInterface mListener) {
        this.context = context;
        this.list = list;
        this.mListener = mListener;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FreeBoardDTO item = list.get(position);
        holder.tvTitle.setText(String.valueOf(item.getF_title()));
        holder.tvName.setText(item.getF_tel());

        //holder.setItem(item);
    }//onBindViewHolder()

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_free_board_recyclerview, parent, false);

        return new MyViewHolder(itemView);
    }//onCreateViewHolder()


    @Override
    public int getItemCount() {
        return list.size();
    }//getItemCount()

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvName;
        LinearLayout listItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            listItem = itemView.findViewById(R.id.listItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvName = itemView.findViewById(R.id.tvName);

            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(view, getAdapterPosition());
                }
            });

        }//ViewHolder()

    }//ViewHolder()

}//class
