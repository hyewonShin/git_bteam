package com.example.WithPet02.view.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.WithPet02.R;

// 이메일로 비밀번호 찾기 Fragment
public class LoginChk3Fragment extends Fragment {

    LoginFindPwActivity loginFindPwActivity;
    Button loginFindPwEmailBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login_chk3, container, false);
        loginFindPwActivity = (LoginFindPwActivity) getActivity();
        loginFindPwEmailBtn = rootView.findViewById(R.id.loginFindPwEmailBtn);

        // 이메일 인증 될 시 결과 화면으로
        loginFindPwEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFindPwActivity.onFragmentChange(4);
            }
        });
        return rootView;
    }
}