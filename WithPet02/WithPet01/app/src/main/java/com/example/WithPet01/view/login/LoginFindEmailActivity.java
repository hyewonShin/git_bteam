package com.example.WithPet02.view.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.WithPet02.R;

// 핸드폰인증으로 가입한 이메일 찾기 Fragment
public class LoginFindEmailActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button loginFindEmailBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_find_email);

        //툴바를 액션바 대신 사용
        toolbar = findViewById(R.id.loginFindEmailToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 인증 버튼 누르면 이메일 발송 확인 화면으로
        loginFindEmailBtn2 = findViewById(R.id.loginFindEmailBtn2);
        loginFindEmailBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFindEmailActivity.this, LoginFindResultActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home ){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
