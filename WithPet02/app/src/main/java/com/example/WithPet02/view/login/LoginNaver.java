package com.example.WithPet02.view.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.WithPet02.view.login.atask.LoginSnsSelect;
import com.example.WithPet02.view.mypage.MyPageInfoActivity;
import com.example.WithPet02.view.mypage.atask.MySnsUpdate;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.data.OAuthLoginState;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

public class LoginNaver {
    private static final String TAG = "LoginNaver";

    private static String OAUTH_CLIENT_ID = "wLPXmcHr2MNrzvYO0Mit";
    private static String OAUTH_CLIENT_SECRET = "L0j34uBhe_";
    private static String OAUTH_CLIENT_NAME = "WithPet";

    private static OAuthLogin mOAuthLoginInstance;
    private static Context mContext;

    private void initData() {
        mOAuthLoginInstance = OAuthLogin.getInstance();

        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
    }

    public void Login(Context context) {
        Log.d(TAG, "Login: " + "네이버 로그인 실행");
        mContext = context;
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
        mOAuthLoginInstance.startOauthLoginActivity((Activity) mContext, mOAuthLoginHandler);
    }

    @SuppressLint("HandlerLeak")
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                Log.d(TAG, "run: " + "네이버 로그인 성공");
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                Log.d(TAG, "액세스 토큰: " + accessToken);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                Log.d(TAG, "리프레쉬 토큰: " + refreshToken);
                Log.d(TAG, "getState 상태: " + mOAuthLoginInstance.getState(mContext).toString());
                new RequestApiTask(mContext).execute();
            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Log.d(TAG, "네이버 인증 에러 : errorCode: " + errorCode + ", errorDesc:" + errorDesc);
            }
        }

    };

    //로그아웃
    public static void signOut(Context context) {
        mOAuthLoginInstance = OAuthLogin.getInstance();
        String loginState = mOAuthLoginInstance.getState(context).toString();
        if (!loginState.equals("NEED_LOGIN")) {
            Log.d(TAG, "signOut: " + "네이버로그아웃");
            mOAuthLoginInstance.logout(context);
            //RedirectActivity.getInstance().signOutAfterLoginActivity(context);
        } else {
            Log.d(TAG, "signOut: " + "네이버 세션 없음");
        }
    }

    private static class RequestApiTask extends AsyncTask<Void, Void, StringBuffer> {
        private String token;

        RequestApiTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected StringBuffer doInBackground(Void... params) {
            OAuthLogin mOAuthLoginInstance = OAuthLogin.getInstance();
            if (OAuthLoginState.NEED_REFRESH_TOKEN.equals(mOAuthLoginInstance.getState(mContext))) {  // 네이버
                Log.d(TAG, "doInBackground: " + "네이버 로그인 확인" + mOAuthLoginInstance.getState(mContext));
                mOAuthLoginInstance.refreshAccessToken(mContext);
            }
            this.token = mOAuthLoginInstance.getAccessToken(mContext);
            String header = "Bearer " + this.token;
            try {
                final String apiURL = "https://openapi.naver.com/v1/nid/me";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", header);
                int responseCode = con.getResponseCode();

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        responseCode == 200 ? con.getInputStream() : con.getErrorStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }

                br.close();
                return response;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(StringBuffer content) {
            super.onPostExecute(content);

            try {
                JSONObject jsonObject = new JSONObject(String.valueOf(content));
                JSONObject response = jsonObject.getJSONObject("response");
                String id = response.getString("id");
                String email;
                try {
                    email = response.getString("email");
                } catch (Exception e) {
                    email = null;
                }

                String nickname;
                try {
                    nickname = response.getString("nickname");
                } catch (Exception e) {
                    nickname = null;
                }

                //리퀘스트 변경
                //Request(mContext, this.token, id, email);
                Log.d(TAG, "네이버 계정 id: " + id);
                Log.d(TAG, "네이버 계정 email: " + email);
                Log.d(TAG, "네이버 계정 nickname: " + nickname);


                if(loginDTO == null) {
                    //회원가입/로그인창에서 호출
                    LoginSnsSelect loginSnsSelect = new LoginSnsSelect(id);
                    try {
                        loginSnsSelect.execute().get();
                        LoginActivity loginActivity = new LoginActivity();
                        loginActivity.snsLogin(id, email, nickname);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    //마이페이지에서 네이버 계정 연동
                    MySnsUpdate mySnsUpdate = new MySnsUpdate(loginDTO.getM_tel(), loginDTO.getM_kakao(), id);
                    try {
                        String result = mySnsUpdate.execute().get().trim();
                        MyPageInfoActivity myPageInfoActivity = new MyPageInfoActivity();
                        if(result.equals("1")){
                            loginDTO.setM_naver(id);
                            myPageInfoActivity.snsUpdate(1);
                        } else {
                            myPageInfoActivity.snsUpdate(-1);
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
