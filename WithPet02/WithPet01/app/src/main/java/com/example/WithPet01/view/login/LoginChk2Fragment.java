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

// 핸드폰인증으로 비밀번호 찾기 Fragment
public class LoginChk2Fragment extends Fragment {

    LoginFindPwActivity loginFindPwActivity;
    Button loginFindPwBtn2, loginFindPwBtn3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login_chk2, container, false);
        loginFindPwActivity = (LoginFindPwActivity) getActivity();
        loginFindPwBtn3 = rootView.findViewById(R.id.loginFindPwBtn3);

        loginFindPwBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 핸드폰 인증 완료시 성공 화면으로
                loginFindPwActivity.onFragmentChange(4);
            }
        });
        return rootView;
    }
}
