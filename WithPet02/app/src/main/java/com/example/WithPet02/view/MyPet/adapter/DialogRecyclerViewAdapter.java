package com.example.WithPet02.view.MyPet.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.WithPet02.R;
import com.example.WithPet02.dto.CalenderDTO;
import com.example.WithPet02.dto.DiagnosisDTO;
import com.example.WithPet02.view.MyPet.asynctask.CalendarDelete;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DialogRecyclerViewAdapter extends RecyclerView.Adapter<DialogRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "DialogRecyclerViewAdapt";

    //ContentListener라는 listener를 사용할 수 있게 변수 선언
    private ContentListener listener;
    private Dialog dlg;
    private ArrayList<DiagnosisDTO> diaList;

    //listener를 가져와서 setContentListener에 넣어줌
    public void setContentListener(ContentListener listener) {
        this.listener = listener;
    }

    //interface선언하여 onContent를 상속할 수 있도록 해줌
    public interface ContentListener{
        void onContent(CalenderDTO calenderDTO);
    }//DateClickListener

    Context context;
    ArrayList<CalenderDTO> list = new ArrayList<>();

    public DialogRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.calender_recyclerview_item, parent, false);

        return new ViewHolder(itemView);
    }//onCreateViewHolder()

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //각 position의 list들은 CalenderDTO
        CalenderDTO item = list.get(position);
        //DTO의 값들을 setItem에 보내준다.
        holder.setItem(item);

        //ViewHolder에 넣어준 recyclerView_text를 눌렀을 때 이벤트
        holder.recyclerView_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onContent(list.get(position));

                //intent띄울 때 Dialog의 환경(contexnt)을 가져와서
                //Intent intent = new Intent(context, CalenderContent.class);

                //intent.putExtra("calenderContent", list.get(position));

                //플래그 지정
                //intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //플래그 추가
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //Dialog의 환경에서 startActivity를 시켜준다.
                //context.startActivity(intent);

                //dlg.dismiss();

                //Log.d(TAG, "holderClick: " + list.get(position).getContent());

            }//onClick()
        });//setOnClickListener()

        //휴지통 모양 눌렀을 때
        holder.recyclerView_garbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "휴지통", Toast.LENGTH_SHORT).show();
                //DB와 연결해서 해당 데이터 없애주기
                CalendarDelete calendarDelete = new CalendarDelete(list.get(position).getNum(),
                        list.get(position).getYear(), list.get(position).getMonth(), list.get(position).getDate());
               try {
                    calendarDelete.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                list.remove(position);
                notifyDataSetChanged();

            }//onClick
        });//setOnClickListener()


    }//onBindViewHolder()


    @Override
    public int getItemCount() {

        return list.size();
    }//getItemCount()


    //calender_recyclerview_item.xml의 것을 가지고 온다.
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView recyclerView_text;
        Button recyclerView_garbage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //calender_recyclerview_item.xml의 view를 가지고 옴
            //RecyclerView에게 이런 View를 쓸 것을 알려줌
            recyclerView_text = itemView.findViewById(R.id.recyclerView_text);
            recyclerView_garbage = itemView.findViewById(R.id.recyclerView_garbage);

        }//ViewHolder()

        public void setItem(CalenderDTO item) {
            recyclerView_text.setText(item.getContent());
            Log.d(TAG, "setItem: " + item.getContent());
        }//setItem()

    }//ViewHolder


    //검진기록 보이는 Holder
    public class DiaHolder extends RecyclerView.ViewHolder {
        ImageView diagnosis_check;

        public DiaHolder(@NonNull View itemView) {
            super(itemView);
            diagnosis_check = itemView.findViewById(R.id.examine);

        }//DiaHolder()

    }//DiaHolder()


    //가져온 list를 ArrayList<CalenderDTO> list에 넣어준다
    public void addItem(CalenderDTO item, Dialog dlg, ArrayList<DiagnosisDTO> diaList) {
        this.dlg = dlg;
        this.diaList = diaList;
        list.add(item);

    }//addItem

    //Data 갱신
    public void DataUpdate () {
        notifyDataSetChanged();
    }
}//class
