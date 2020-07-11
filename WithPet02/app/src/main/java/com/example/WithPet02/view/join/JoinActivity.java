package com.example.WithPet02.view.join;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.WithPet02.R;
import com.example.WithPet02.view.join.atask.JoinSelect;
import com.example.WithPet02.view.join.atask.JoinInsert;
import com.example.WithPet02.view.login.LoginActivity;
import com.example.WithPet02.view.join.JoinCheck;

import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.common.CommonMethod.isNetworkConnected;

// 가입 정보 입력 액티비티
public class JoinActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnJoin, btnCancel;
    String email = "", name = "", pw, pwChk, tel = "";
    String naver, kakao;
    String colorRed, colorGreen;

    EditText etEmail, etName, etPw, etPwChk;
    TextView joinTvPwChk;

    String state;

    JoinCheck joinCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        joinCheck = new JoinCheck(JoinActivity.this);

        kakao = "";
        naver = "";
        tel = getIntent().getStringExtra("m_tel");
        Log.d("main:Join", "tel: " + tel);

        if(getIntent().getStringExtra("m_kakao") != null){
            kakao = getIntent().getStringExtra("m_kakao");
        }

        if(getIntent().getStringExtra("m_naver") != null){
            naver = getIntent().getStringExtra("m_naver");
        }

        if(getIntent().getStringExtra("m_email") != null){
            email = getIntent().getStringExtra("m_email");
        }

        if(getIntent().getStringExtra("m_name") != null){
            name = getIntent().getStringExtra("m_name");
        }

        //툴바를 액션바 대신 사용
        toolbar = findViewById(R.id.joinToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etPw = findViewById(R.id.etPw);
        etPwChk = findViewById(R.id.etPwChk);
        joinTvPwChk = findViewById(R.id.joinTvPwChk);
        // 비밀번호 체크 텍스트뷰 클릭시 비밀번호 조건 알림
        joinTvPwChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JoinActivity.this, "영문 대소문자, 숫자, 일부 특수문자"
                        + "\n(~`!@#$%\\^&*()-)를 포함한 8-20자리 비밀번호만 사용가능합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        colorRed = "#FF0000";
        colorGreen = "#00AA00";

        //카카오로 회원가입시 정보 자동입력
        if(kakao != null){
            etEmail.setText(email);
            etName.setText(name);
        }

        // 이메일 TextView에 입력시 유효성검사
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                email = etEmail.getText().toString().trim();
                joinCheck.chkEmail(email);
            }
        });

        // 닉네임 TextView에 입력시 유효성검사
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                name = etName.getText().toString().trim();
                joinCheck.chkName(name);
            }
        });

        // 비밀번호 TextView에 입력시 유효성검사
        etPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                pw = etPw.getText().toString().trim();
                joinCheck.chkPw(pw);
            }
        });

        // 비밀번호확인 TextView에 입력시 유효성검사
        etPwChk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                pw = etPw.getText().toString().trim();
                pwChk = etPwChk.getText().toString().trim();
                joinCheck.chkPwChk(pw, pwChk);
            }
        });

        // 회원가입 버튼 클릭
        btnJoin = findViewById(R.id.btnJoin);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString().trim();
                name = etName.getText().toString().trim();
                pw = etPw.getText().toString().trim();
                pwChk = etPwChk.getText().toString().trim();

                if (joinCheck.chkEmail(email) && joinCheck.chkName(name) && joinCheck.chkPw(pw) && joinCheck.chkPwChk(pw, pwChk)){
                    state = "-100";

                    JoinInsert joinInsert = new JoinInsert(tel, email, name, pw, kakao, naver);
                    try {
                        Log.d("main:Join", "state: " + state);
                        state = joinInsert.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(state.equals("1")){
                        Toast.makeText(JoinActivity.this, "회원가입 되었습니다. 가입하신 방식으로 로그인해주세요.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(JoinActivity.this, "회원가입실패.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JoinActivity.this, "입력란을 다시 작성하여주십시오.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // 취소 버튼 클릭
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }








    // 툴바 뒤로가기 클릭
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home ){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
