package com.example.WithPet02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.WithPet02.dto.MemberDTO;
import com.google.gson.Gson;

import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 자동로그인
        SharedPreferences sf = getSharedPreferences("WithPetM", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sf.getString("loginDTO", "");
        MemberDTO dto = gson.fromJson(json, MemberDTO.class);
        loginDTO = dto;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
