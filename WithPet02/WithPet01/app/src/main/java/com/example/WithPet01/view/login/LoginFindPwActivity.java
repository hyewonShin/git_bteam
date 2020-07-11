package com.example.WithPet02.view.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.WithPet02.R;

// 비밀번호 찾기 액티비티
public class LoginFindPwActivity extends AppCompatActivity {

    Toolbar toolbar;
    Fragment loginChk1Fragment, loginChk2Fragment, loginChk3Fragment, loginChk4Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_find_pw);

        //툴바를 액션바 대신 사용
        toolbar = findViewById(R.id.loginFindPwToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginChk1Fragment = getSupportFragmentManager().findFragmentById(R.id.loginChkFragment1);
        loginChk2Fragment = new LoginChk2Fragment();
        loginChk3Fragment = new LoginChk3Fragment();
        loginChk4Fragment = new LoginChk4Fragment();

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

    // 프래그먼트 전환
    public void onFragmentChange(int fragmentNum){
        Log.d("로그인", "onFragmentChange: " + fragmentNum);
        if(fragmentNum == 1) {  // 핸드폰으로 찾기
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.loginChkContainer, loginChk2Fragment).commit();
        } else if(fragmentNum == 2) {   // 이메일로 찾기
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.loginChkContainer, loginChk3Fragment).commit();
        } else if(fragmentNum == 4) {   // 임시 비밀번호 발급 화면
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.loginChkContainer, loginChk4Fragment).commit();
        }
    }

}
