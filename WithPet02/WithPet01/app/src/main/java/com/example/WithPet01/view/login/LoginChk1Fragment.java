package com.example.WithPet02.view.login;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.WithPet02.R;

// 비밀번호 찾기 → 방법 선택 프래그먼트
public class LoginChk1Fragment extends Fragment {

    LoginFindPwActivity loginFindPwActivity;
    ImageView loginChkTelBtn, loginChkEmailBtn;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        loginFindPwActivity = (LoginFindPwActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loginFindPwActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login_chk1, container, false);

        // 핸드폰 번호로 비밀번호찾기
        loginChkTelBtn = rootView.findViewById(R.id.loginChkTelBtn);
        loginChkTelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFindPwActivity.onFragmentChange(1);
            }
        });

        // 이메일로 비밀번호
        loginChkEmailBtn = rootView.findViewById(R.id.loginChkEmailBtn);
        loginChkEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFindPwActivity.onFragmentChange(2);
            }
        });

        return rootView;
    }
}
