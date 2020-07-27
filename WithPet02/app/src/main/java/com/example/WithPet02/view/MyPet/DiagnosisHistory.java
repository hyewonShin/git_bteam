package com.example.WithPet02.view.MyPet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.WithPet02.R;
import com.example.WithPet02.dto.DiagnosisDTO;
import com.example.WithPet02.view.MyPet.asynctask.CalenderGet;
import com.example.WithPet02.view.MyPet.asynctask.DiagnosisGet;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DiagnosisHistory extends AppCompatActivity {
    ArrayList<DiagnosisDTO> list = null;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_history);

        //툴바 위치 찾기
        toolbar = findViewById(R.id.diagnosis_history_toolbar);
        setSupportActionBar(toolbar);

        //툴바 타이틀 보일지 말지 설정
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //홈버튼 보일지 말지 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //해당하는 년월일의 검진기록 넘겨준것 받기
        DiagnosisDTO diagnosisDTO = (DiagnosisDTO) getIntent().getSerializableExtra("diagnosisList");

        //DB에서 날짜 값을 가져와서 검진날 출력 해줄 수 있게 하기
        //DB에서 달력 입력 내용 가져와서 출력 할 수 있게 하기
        //d_pet을 임시로 1로 설정
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

        TextView diagnosis_content = findViewById(R.id.diagnosis_content);
        diagnosis_content.setText(diagnosisDTO.getD_content());
    }//onCreate

    //홈버튼(뒤로기기) 눌리면 꺼지게 하기
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();
        switch (curId){
            case android.R.id.home ://홈 메뉴 눌리면 꺼지도록 설정
                this.finish();
                break;
        }//switch

        return true;
    }//onOptionsItemSelected()

}//class
