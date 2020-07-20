package com.example.WithPet02.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.WithPet02.R;
import com.example.WithPet02.dto.AlbumDTO;

import java.util.ArrayList;

import static com.example.WithPet02.common.CommonMethod.ipConfig;

public class GridRecyclerViewAdapter extends RecyclerView.Adapter<GridRecyclerViewAdapter.MyViewHolder> {

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface mListener;

    Context context;
    ArrayList<AlbumDTO> list;
    String filePath = ipConfig + "/app/resources/upload/album/";;

    public GridRecyclerViewAdapter(Context context, ArrayList<AlbumDTO> list
            , OnListItemSelectedInterface listener) {
        super();
        this.context = context;
        this.list = list;
        this.mListener = listener;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(filePath + list.get(position).getA_pet() + "/" + list.get(position).getA_file())
                .signature(new ObjectKey(System.currentTimeMillis()))
                .into(holder.imageView);
        //holder.imageView.setImageResource(list.get(position).getA_file());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_my_pet_album, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gridImage);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    mListener.onItemSelected(view, getAdapterPosition());
                }
            });
        }
    }



}
