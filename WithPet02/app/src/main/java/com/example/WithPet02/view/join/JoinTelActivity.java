package com.example.WithPet02.view.join;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.WithPet02.R;
import com.example.WithPet02.view.join.atask.JoinSelect;
import com.example.WithPet02.view.join.atask.SmsSend;
import com.example.WithPet02.view.login.LoginFindEmailActivity;
import com.example.WithPet02.view.login.LoginFindPwActivity;
import com.example.WithPet02.view.join.JoinActivity;

import java.util.Random;
import java.util.concurrent.ExecutionException;

// 전화번호 인증 액티비티
public class JoinTelActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnChkTel, joinBtnChkCode;
    EditText joinInputTel, joinTelChkCode;
    String randomNum;
    int sendMsgChk = 0;
    String state;
    String m_tel, m_email, m_name, m_kakao, m_naver;
    String resultTel;
    String colorRed, colorGreen;
    String type;
    TextView joinTelTv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_tel);

        //툴바를 액션바 대신 사용
        toolbar = findViewById(R.id.joinTelToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        joinInputTel = findViewById(R.id.joinInputTel);
        joinTelChkCode = findViewById(R.id.joinTelChkCode);

        btnChkTel = findViewById(R.id.btnChkTel);
        joinBtnChkCode = findViewById(R.id.joinBtnChkCode);

        // 초기화
        m_kakao = "";
        m_naver="";
        m_email = "";
        m_name = "";
        type = "회원가입";

        joinTelTv1 = findViewById(R.id.joinTelTv1);

        // 화면 호출 타입 확인
        if(getIntent().getStringExtra("type") != null){
            type = getIntent().getStringExtra("type");
            joinTelTv1.setText(type);
        }

        // 소셜계정으로 회원가입할경우 정보 가져오기
        if(getIntent().getStringExtra("m_kakao") != null){
            m_kakao = getIntent().getStringExtra("m_kakao");
        }
        if(getIntent().getStringExtra("m_naver") != null){
            m_naver = getIntent().getStringExtra("m_naver");
        }
        if(getIntent().getStringExtra("m_email") != null){
            m_email = getIntent().getStringExtra("m_email");
        }
        if(getIntent().getStringExtra("m_name") != null){
            m_name = getIntent().getStringExtra("m_name");
        }

        colorRed = "#FF0000";
        colorGreen = "#00AA00";

        if(type.equals("이메일 찾기")){

        }
        if(type.equals("비밀번호 찾기")){

        }

        // 전화번호로 인증번호 전송
        btnChkTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsgChk = 0;
                if(joinInputTel.getText().toString().length() == 0){
                    Toast.makeText(JoinTelActivity.this, "전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    Log.d("main:joinTel", "전화번호를 입력하세요");
                    return;
                } else {
                    m_tel = joinInputTel.getText().toString();

                    // 가입된 전화번호인지 확인
                    JoinSelect joinSelect = new JoinSelect("m_tel", m_tel);
                    try {
                        resultTel = joinSelect.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 화면 구분
                    if(type.equals("이메일 찾기") || type.equals("비밀번호 찾기")){
                        // 회원정보 찾기 화면일경우
                        if(!resultTel.equals("1")){
                            Toast.makeText(JoinTelActivity.this, "가입된 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        // 신규 가입 화면일경우
                        if(resultTel.equals("1")){
                            // 이미 가입 됨
                            Toast.makeText(JoinTelActivity.this, "이미 가입된 전화번호입니다.", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                    // 인증 진행 가능 할 경우 인증번호생성
                    Random random = new Random();
                    randomNum = "";
                    for(int i = 0; i < 6; i++){
                        randomNum += Integer.toString(random.nextInt(10));
                    }

                    SmsSend smsSend = new SmsSend(m_tel, randomNum);
                    try {
                        state = smsSend.execute().get().trim();
                        Log.d("main:joinTel", "state:" + state);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(state.equals("1")){
                        Toast.makeText(JoinTelActivity.this, "인증번호가 전송되었습니다", Toast.LENGTH_SHORT).show();
                        Log.d("main:joinTel", "인증번호 전송 성공!");
                        sendMsgChk = 1;
                    }else{
                        Toast.makeText(JoinTelActivity.this, "인증번호 전송에 실패하였습니다. \n 전화번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        Log.d("main:joinTel", "인증번호 전송 실패 !!!");
                    }

                }
            }
        });

        // 인증 버튼 클릭
        joinBtnChkCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chkCode = joinTelChkCode.getText().toString();

                // 인증번호 전송 여부 확인
                if (sendMsgChk <= 0){
                    Toast.makeText(JoinTelActivity.this, "인증번호를 발신해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 인증 번호 입력 여부 확인
                if(chkCode.length() == 0) {
                    Toast.makeText(JoinTelActivity.this, "인증번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 입력한 인증번호 일치 여부 확인
                if(!randomNum.equals(chkCode)){
                    Toast.makeText(JoinTelActivity.this, "인증번호가 잘못 입력되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(JoinTelActivity.this, "전화번호가 인증되었습니다.", Toast.LENGTH_SHORT).show();

                    if(type.equals("이메일 찾기")){
                        // 이메일 안내 화면으로
                        Intent intent = new Intent(JoinTelActivity.this, LoginFindEmailActivity.class);
                        intent.putExtra("m_tel", m_tel);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    } else if(type.equals("비밀번호 찾기")) {
                        // 비밀번호 초기화 안내 화면으로
                        Intent intent = new Intent(JoinTelActivity.this, LoginFindPwActivity.class);
                        intent.putExtra("m_tel", m_tel);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    } else {
                        // 회원가입 정보 입력 화면으로
                        Intent intent = new Intent(JoinTelActivity.this, JoinActivity.class);
                        intent.putExtra("m_tel", m_tel);
                        intent.putExtra("m_kakao", m_kakao);
                        intent.putExtra("m_naver", m_naver);
                        intent.putExtra("m_email", m_email);
                        intent.putExtra("m_name", m_name);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    // 툴바 뒤로가기 클릭 ▶ 액티비티 finish
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home ){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
