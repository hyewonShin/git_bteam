package com.example.WithPet02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.WithPet02.dto.PetDTO;

import java.util.ArrayList;

public class DbResult extends AppCompatActivity {

    private static final String TAG = "DBResult";

    //DBResultArray dbResultArray = null;
    ArrayList<PetDTO> list = null;
    int i = 0;
    int fragmentNum = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_result);
    }
}
