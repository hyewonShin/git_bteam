package com.example.WithPet02.view.MyPet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.WithPet02.R;

public class MyPetCheckList extends AppCompatActivity {
    Fragment tabFragment = new Fragment();

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_check_list);

        //툴바 위치 찾기
        toolbar = findViewById(R.id.dialog_toolbar);
        setSupportActionBar(toolbar);

        //툴바 타이틀 보일지 말지 설정
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //홈버튼 보일지 말지 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //검진기록fragment로 가져오기
        tabFragment = getSupportFragmentManager().findFragmentById(R.id.fragment);

    }//onCreate()

    //OptionMenu선택시 작동되게 하기
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

