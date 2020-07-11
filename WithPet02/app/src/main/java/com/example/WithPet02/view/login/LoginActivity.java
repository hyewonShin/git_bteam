package com.example.WithPet02.view.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.WithPet02.MainActivity;
import com.example.WithPet02.R;
import com.example.WithPet02.dto.MemberDTO;
import com.example.WithPet02.view.join.JoinTelActivity;
import com.example.WithPet02.view.login.atask.LoginSelect;
import com.example.WithPet02.view.login.LoginNaver;
import com.google.gson.Gson;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;

import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.common.CommonMethod.isNetworkConnected;

public class LoginActivity extends AppCompatActivity {

    // 로그인이 성공하면 static 로그인DTO 변수에 담아서
    // 어느곳에서나 접근할 수 있게 한다
    public static MemberDTO loginDTO = null;

    EditText etID, etPASSWD;
    Button btnLogin;
    TextView loginTvFindEmail, loginTvFindPw;

    ImageView btnJoin, snsKakaoLogin, snsNaverLogin;
    private Button btn_custom_login_out;

    Toolbar toolbar;

    // 카카오 로그인 세션
    private SessionCallback sessionCallback;
    Session session;

    private static Context lContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lContext = this;
        sessionCallback = new SessionCallback(lContext);

        SharedPreferences sf = getSharedPreferences("WithPetM",MODE_PRIVATE);
        String m_tel = sf.getString("m_tel", "");

        //툴바를 액션바 대신 사용
        toolbar = findViewById(R.id.loginToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etID = findViewById(R.id.etId);
        etPASSWD = findViewById(R.id.etPASSWD);

        btnLogin = findViewById(R.id.btnLogin);
        btnJoin = findViewById(R.id.btnJoin);

        loginTvFindEmail = findViewById(R.id.loginTvFindEmail);
        loginTvFindPw = findViewById(R.id.loginTvFindPw);

        snsKakaoLogin = findViewById(R.id.snsKakaoLogin);
        snsNaverLogin = findViewById(R.id.snsNaverLogin);

        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("로그인 처리 중 입니다...");
                progressDialog.show();

                if(isNetworkConnected(LoginActivity.this) == false){
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "인터넷 연결 확인 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etID.getText().toString().length() != 0
                        && etPASSWD.getText().toString().length() != 0){
                    String m_tel = etID.getText().toString();
                    String m_pw = etPASSWD.getText().toString();

                    LoginSelect loginSelect = new LoginSelect(m_tel, m_pw);
                    try {
                        loginSelect.execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "이메일과 암호를 모두 입력하세요", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", "이메일과 암호를 모두 입력하세요");
                    return;
                }

                if(loginDTO != null){
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "로그인 되었습니다!!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", loginDTO.getM_name() + "님 로그인 되었습니다 !!!");
                    autoLogin();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "이메일이나 비밀번호가 일치안함 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", "이메일이나 비밀번호가 일치하지 않습니다 !!!");
                    etID.setText("");
                    etPASSWD.setText("");
                    etID.requestFocus();
                }
            }
        });

        // 회원 가입 버튼
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 핸드폰 번호 인증화면
                Intent intent = new Intent(getApplicationContext(), JoinTelActivity.class);
                startActivity(intent);
            }
        });

        // 이메일 찾기
        loginTvFindEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinTelActivity.class);
                intent.putExtra("type", "이메일 찾기");
                startActivity(intent);
            }
        });

        // 비밀번호 찾기
        loginTvFindPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinTelActivity.class);
                intent.putExtra("type", "비밀번호 찾기");
                startActivity(intent);
            }
        });

        // 카카오 로그인
        snsKakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected(LoginActivity.this) == false){
                    Toast.makeText(LoginActivity.this, "인터넷 연결 확인 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                session = Session.getCurrentSession();
                session.addCallback(sessionCallback);
                session.open(AuthType.KAKAO_LOGIN_ALL, LoginActivity.this);
            }
        });

        // 네이버 로그인
        snsNaverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkConnected(LoginActivity.this) == false){
                    Toast.makeText(LoginActivity.this, "인터넷 연결 확인 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                LoginNaver loginNaver = new LoginNaver();
                loginNaver.Login(LoginActivity.this);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    public void snsLogin(String snsId, String snsEmail, String snsName){
        if(loginDTO != null){
            Toast.makeText(lContext, loginDTO.getM_name() +"님 로그인 되었습니다", Toast.LENGTH_SHORT).show();
            Log.d("main:login", loginDTO.getM_name() + "님 로그인 되었습니다");
            autoLogin();
            Intent intent = new Intent(lContext, MainActivity.class);
            lContext.startActivity(intent);
            finish();
        } else {
            Toast.makeText(lContext, "소셜 계정으로 등록된 회원 정보가 존재하지 않습니다.\n가입화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
            Log.d("main:login", "회원 정보가 존재하지 않습니다.\n가입화면으로 이동합니다.");


            Intent intent = new Intent(lContext, JoinTelActivity.class);
            if(snsId.length() == 10){
                intent.putExtra("m_kakao", snsId);
            } else if (snsId.length() == 8){
                intent.putExtra("m_naver", snsId);
            }
            intent.putExtra("m_email", snsEmail);
            intent.putExtra("m_name", snsName);
            lContext.startActivity(intent);
            finish();
        }
    }

    // 자동로그인을 위해 SharedPreferences로 파일생성
    public void autoLogin(){
        SharedPreferences sf = getSharedPreferences("WithPetM", MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        Gson gson = new Gson();
        String json = gson.toJson(loginDTO);
        editor.putString("loginDTO", json);
        editor.commit();
    }

    //뒤로가기
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    // 툴바 뒤로가기 클릭시 메인화면으로
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home ){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}