package com.example.WithPet02.view.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.WithPet02.R;
import com.example.WithPet02.view.login.atask.LoginFindPw;
import com.example.WithPet02.view.login.LoginActivity;

import java.util.concurrent.ExecutionException;

// 비밀번호 찾기 액티비티
public class LoginFindPwActivity extends AppCompatActivity {

    Toolbar toolbar;
    String m_tel, m_pw;
    EditText loginFindPwEt;
    Button loginFindPwBtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_find_pw);

        //툴바를 액션바 대신 사용
        toolbar = findViewById(R.id.loginFindPwToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        m_tel = getIntent().getStringExtra("m_tel");
        // AsyncTask 이용하여 m_tel 로 m_pw 업데이트한 후 EditText 에 출력
        // sql = "update member set m_pw = (랜덤알파벳6자리 만들어서 지정) where m_tel = '" + m_tel + "' ";
        LoginFindPw loginFindPw = new LoginFindPw(m_tel);
        try {
            m_pw = loginFindPw.execute().get().trim();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loginFindPwEt = findViewById(R.id.loginFindPwEt);
        loginFindPwEt.setText(m_pw);

        loginFindPwBtn1 = findViewById(R.id.loginFindPwBtn1);
        loginFindPwBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginFindPwActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    // 툴바 뒤로가기 클릭시 액티비티 finish
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home ){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
