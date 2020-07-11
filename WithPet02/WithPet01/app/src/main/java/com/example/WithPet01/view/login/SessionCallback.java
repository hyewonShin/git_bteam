package com.example.WithPet02.view.login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.WithPet02.view.login.atask.LoginSnsSelect;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ApiErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.exception.KakaoException;

import java.util.concurrent.ExecutionException;

// 카카오 세션 콜백
public class SessionCallback implements ISessionCallback {

    private static Context mContext;

    public SessionCallback(Context lContext) {
        this.mContext = lContext;
    }

    @Override
    public void onSessionOpened() {
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                int result = errorResult.getErrorCode();

                if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                    Toast.makeText(mContext, "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                    //finish();
                } else {
                    Toast.makeText(mContext, "로그인 도중 오류가 발생했습니다: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Toast.makeText(mContext, "세션이 닫혔습니다. 다시 시도해 주세요: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(MeV2Response result) {
                String kakaoId = "", kakaoEmail = "", kakaoName = "";
                Log.d("까까오로그인해먹기힘들다", "아이디: " + result.getId());
                if(result.getId() != 0){
                    kakaoId = String.valueOf(result.getId());
                }

                UserAccount kakaoAccount = result.getKakaoAccount();
                Log.d("까까오로그인해먹기힘들다", "이메일: " + kakaoAccount.getEmail());
                if(kakaoAccount.getEmail() != null){
                    kakaoEmail = kakaoAccount.getEmail();
                }

                Log.d("까까오로그인해먹기힘들다", "닉네임: " + kakaoAccount.getProfile().getNickname());
                if(kakaoAccount.getProfile() != null){
                    kakaoName = kakaoAccount.getProfile().getNickname();
                }

                LoginSnsSelect loginSnsSelect = new LoginSnsSelect(kakaoId);

                try {
                    //카카오 계정과 연동된 계정이 있는지 검색
                    loginSnsSelect.execute().get();
                    LoginActivity loginActivity = new LoginActivity();
                    loginActivity.snsLogin(kakaoId, kakaoEmail, kakaoName);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onSessionOpenFailed(KakaoException e) {
        Toast.makeText(mContext, "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: " + e.toString(), Toast.LENGTH_SHORT).show();
    }
}
