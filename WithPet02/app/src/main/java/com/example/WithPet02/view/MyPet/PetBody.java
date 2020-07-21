package com.example.WithPet02.view.MyPet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.WithPet02.MainView.MainLogIn;
import com.example.WithPet02.R;

public class PetBody extends AppCompatActivity {

    LinearLayout myPetPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_body);

        /*ImageView load = (ImageView)findViewById(R.id.bpm);
        Glide.with(this).load(R.raw.heart).into(load);*/


        //메인화면 내 동물정보
        myPetPager = findViewById(R.id.myPetPager);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_main_login, myPetPager, true);

        MainLogIn mainLogIn = new MainLogIn(this, myPetPager);
        mainLogIn.setMyPetPager();
    }
}
