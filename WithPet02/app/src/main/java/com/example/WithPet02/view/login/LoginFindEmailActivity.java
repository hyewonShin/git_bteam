package com.example.WithPet02.view.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.WithPet02.R;
import com.example.WithPet02.view.join.JoinTelActivity;
import com.example.WithPet02.view.login.atask.LoginFindEmail;
import com.example.WithPet02.view.login.LoginActivity;

import java.util.concurrent.ExecutionException;

// 핸드폰인증으로 가입한 이메일 찾기 Fragment
public class LoginFindEmailActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button loginFindEmailBtn1, loginFindEmailBtn2;
    String m_tel, m_email;
    TextView loginFindEmailTv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_find_email);

        //툴바를 액션바 대신 사용
        toolbar = findViewById(R.id.loginFindEmailToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // AsyncTask(LoginFindEmail) 이용하여 m_tel 로 m_email 검색한 후
        // sql = "select m_email from member where m_tel = '" + m_tel + "' ";

        m_tel = getIntent().getStringExtra("m_tel");
        LoginFindEmail loginFindEmail = new LoginFindEmail(m_tel);
        try {
            m_email = loginFindEmail.execute().get().trim();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // TextView 에 m_email 출력
        loginFindEmailTv2 = findViewById(R.id.loginFindEmailTv2);
        String msg = "회원님의 이메일은" + m_email + "입니다.";
        loginFindEmailTv2.setText(msg);

        // 로그인 화면으로
        loginFindEmailBtn1 = findViewById(R.id.loginFindEmailBtn1);
        loginFindEmailBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFindEmailActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        // 비밀번호 찾기 화면으로
        loginFindEmailBtn2 = findViewById(R.id.loginFindEmailBtn2);
        loginFindEmailBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinTelActivity.class);
                intent.putExtra("type", "비밀번호 찾기");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home ){
            Intent intent = new Intent(LoginFindEmailActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
