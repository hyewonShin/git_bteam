package com.example.WithPet02.view.mypage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.WithPet02.MainActivity;
import com.example.WithPet02.R;
import com.example.WithPet02.view.login.LoginNaver;
import com.example.WithPet02.view.login.SessionCallback;
import com.example.WithPet02.view.mypage.atask.MySnsUpdate;
import com.example.WithPet02.view.mypage.MyDeleteActivity;
import com.example.WithPet02.view.mypage.MyUpdateActivity;
import com.google.gson.Gson;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.common.CommonMethod.ipConfig;
import static com.example.WithPet02.common.CommonMethod.isNetworkConnected;
import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

public class MyPageInfoActivity extends AppCompatActivity {

    TextView nickname, myHomeEmailTv, myKakaoTv, myNaverTv;
    LinearLayout myLogout, myDelete, myKakao, myNaver;
    ImageView myPic;
    String filePath = ipConfig + "/app/resources/member/";

    // 카카오 로그인 세션
    private SessionCallback sessionCallback;
    Session session;

    // 네이버
    LoginNaver loginNaver;

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_info);

        if(loginDTO == null){
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        }


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setDisplayShowTitleEnabled(false);  // 타이틀 안보이게 하기

        if(isNetworkConnected(this) == false){
            Toast.makeText(this, "인터넷 연결 확인 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }

        mContext = this;    //다른 클래스에서 Context에 접근하기 위함
        sessionCallback = new SessionCallback(mContext);

        //회원정보 출력
        nickname = findViewById(R.id.nickname);
        nickname.setText(loginDTO.getM_name());
        myHomeEmailTv = findViewById(R.id.myHomeEmailTv);
        myHomeEmailTv.setText(loginDTO.getM_email());

        //이미지 서버에서 가져오기
        myPic = findViewById(R.id.myPic);
        myPic.setImageResource(0);
        Log.d("MyHome", "m_pic: " + loginDTO.getM_pic());
        if(loginDTO.getM_pic() == null){    //사용자 프로필사진 등록 여부 확인
            //프로필 사진없음
            Glide.with(this).load(filePath + "defalt.jpg").into(myPic);
        } else {
            //프로필 사진 있음
            Glide.with(this).load(filePath + loginDTO.getM_pic()).signature(new ObjectKey(System.currentTimeMillis())).into(myPic);
        }

        //소셜로그인
        myKakao = findViewById(R.id.myKakao);
        myNaver = findViewById(R.id.myNaver);
        myKakaoTv = findViewById(R.id.myKakaoTv);
        myNaverTv = findViewById(R.id.myNaverTv);

        if(loginDTO.getM_kakao() != null) {
            myKakaoTv.setText("카카오 계정 연동 해제");
        } else {
            myKakaoTv.setText("카카오 계정 연동");
        }

        if(loginDTO.getM_naver() != null) {
            myNaverTv.setText("네이버 계정 연동 해제");
        } else {
            myNaverTv.setText("네이버 계정 연동");
        }

        //카카오 연동
        myKakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyPageInfoActivity.this, "카카오 연동버튼 클릭 됨", Toast.LENGTH_SHORT).show();
                if(loginDTO.getM_kakao() == null){
                    //카카오 계정 연동하기
                    session = Session.getCurrentSession();
                    session.addCallback(sessionCallback);
                    session.open(AuthType.KAKAO_LOGIN_ALL, MyPageInfoActivity.this);
                } else {
                    //카카오 계정 연동 해제 하기
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyPageInfoActivity.this);
                    builder.setMessage("정말 카카오 계정 연동을 해제 하시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                                @Override
                                public void onCompleteLogout() {
                                    //DB에서 카카오 정보 삭제
                                    MySnsUpdate mySnsUpdate = new MySnsUpdate(loginDTO.getM_tel(), null, loginDTO.getM_naver());
                                    mySnsUpdate.execute();
                                    loginDTO.setM_kakao(null);

                                    //자동로그인 정보 갱신
                                    SharedPreferences sf = getSharedPreferences("WithPetM", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sf.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(loginDTO);
                                    editor.putString("loginDTO", json);
                                    editor.commit();

                                    //MainActivity 로 이동
                                    Intent intent = new Intent(MyPageInfoActivity.this, MainActivity.class);
                                    MyPageInfoActivity.this.finish();
                                    startActivity(intent);

                                }
                            });
                        }
                    });

                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.show();
                }
            }
        });

        //네이버 연동
        myNaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginNaver = new LoginNaver();
                if(loginDTO.getM_naver() == null){
                    //네이버 계정 연동하기
                    loginNaver.Login(MyPageInfoActivity.this);
                } else {
                    //네이버 계정 로그아웃하기
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyPageInfoActivity.this);
                    builder.setMessage("정말 네이버 계정 연동을 해제 하시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            loginNaver.signOut(mContext);

                            MySnsUpdate mySnsUpdate = new MySnsUpdate(loginDTO.getM_tel(), loginDTO.getM_kakao(), null);
                            mySnsUpdate.execute();

                            Toast.makeText(MyPageInfoActivity.this, "연동 해제 되었습니다", Toast.LENGTH_SHORT).show();
                            loginDTO.setM_naver(null);

                            //자동로그인 정보 갱신
                            SharedPreferences sf = getSharedPreferences("WithPetM", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sf.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(loginDTO);
                            editor.putString("loginDTO", json);
                            editor.commit();

                            //메인액티비티시작
                            Intent intent = new Intent(mContext, MainActivity.class);
                            finish();
                            mContext.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.show();
                }
            }
        });

        //회원 로그아웃
        myLogout = findViewById(R.id.myLogout);
        myLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyPageInfoActivity.this);
                builder.setMessage("정말 로그아웃 하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //loginDTO null로 변경
                        loginDTO = null;
                        //자동로그인 파일 내용 삭제
                        SharedPreferences sf = getSharedPreferences("WithPetM", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sf.edit();
                        editor.clear();
                        editor.commit();
                        //메인액티비티시작
                        Intent intent = new Intent(MyPageInfoActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });

        //회원탈퇴
        myDelete = findViewById(R.id.myDelete);
        myDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원 탈퇴 확인 액티비티로
                Intent intent = new Intent(MyPageInfoActivity.this, MyDeleteActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar 의 back 키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.my_update:{ //개인정보 수정
                //개인정보 수정화면으로
                Intent intent = new Intent(getApplicationContext(), MyUpdateActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void snsUpdate(int result){
        if(result == 1){
            Toast.makeText(mContext, "SNS 계정이 정상적으로 연동되었습니다", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, MainActivity.class);
            finish();
            mContext.startActivity(intent);
        } else {
            Toast.makeText(mContext, "SNS 계정연동에 실패하였습니다", Toast.LENGTH_SHORT).show();
        }
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
    protected void onResume() {
        if(loginDTO.getM_kakao() != null) {
            myKakaoTv.setText("카카오 계정 연동 해제");
        } else {
            myKakaoTv.setText("카카오 계정 연동");
        }

        if(loginDTO.getM_naver() != null) {
            myNaverTv.setText("네이버 계정 연동 해제");
        } else {
            myNaverTv.setText("네이버 계정 연동");
        }

        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        nickname.setText(loginDTO.getM_name());
        myHomeEmailTv.setText(loginDTO.getM_email());
        if(loginDTO.getM_pic() == null){    //사용자 프로필사진 등록 여부 확인
            //프로필 사진없음
            Glide.with(this).load(filePath + "defalt.jpg").signature(new ObjectKey(System.currentTimeMillis())).into(myPic);
        } else {
            //프로필 사진 있음
            Glide.with(this).load(filePath + loginDTO.getM_pic()).signature(new ObjectKey(System.currentTimeMillis())).into(myPic);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }
}
