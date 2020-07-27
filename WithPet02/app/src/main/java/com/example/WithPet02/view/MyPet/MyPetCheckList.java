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

        //검진기록fragment로 가져오기
        tabFragment = getSupportFragmentManager().findFragmentById(R.id.fragment);

    }//onCreate()

}//class

