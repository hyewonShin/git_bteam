package com.example.WithPet02.view.MyPet;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.WithPet02.R;
import com.example.WithPet02.dto.DiagnosisDTO;
import com.example.WithPet02.view.MyPet.asynctask.CalenderGet;
import com.example.WithPet02.view.MyPet.asynctask.DiagnosisGet;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DiagnosisHistory extends AppCompatActivity {
    ArrayList<DiagnosisDTO> list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_history);

        //DB에서 날짜 값을 가져와서 검진날 출력 해줄 수 있게 하기
        //DB에서 달력 입력 내용 가져와서 출력 할 수 있게 하기
        //d_pet을 임시로 1로 설정(수정필요)
        list = new ArrayList<>();

        int d_pet = 1;
        DiagnosisGet diagnosisGet = new DiagnosisGet(d_pet);
        CalenderGet calenderGet = new CalenderGet("1");

        try {
            list = diagnosisGet.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }//try

    }//onCreate

}//class
