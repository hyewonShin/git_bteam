package com.example.WithPet02.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.WithPet02.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    List<String> data;
    LayoutInflater inflater;

    public SpinnerAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }//SpinnerAdapter()

    @Override
    public int getCount() {
        if(data!=null) return data.size();
        else return 0;
    }//getCount()

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //평소 spinner 화면가져오기
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_spinner1_normal, parent, false);
        }//if
        if(data != null) {
            //데이터 넣기
            String text = data.get(position);
            ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);
        }//if
        return convertView;
    }//getView()

    //spinner 내려간 화면 세팅
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.spinner_spinner1_dropdown, parent, false);
        }//if
        String text = data.get(position);
        ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);
        return convertView;
    }
}//class
