package com.example.WithPet02.view.MyPet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.WithPet02.R;

public class PetBody extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_body);

        ImageView load = (ImageView)findViewById(R.id.bpm);
        Glide.with(this).load(R.raw.heart).into(load);
    }
}
