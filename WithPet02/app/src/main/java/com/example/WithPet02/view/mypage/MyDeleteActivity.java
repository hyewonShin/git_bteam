package com.example.WithPet02.view.mypage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.WithPet02.R;
import com.example.WithPet02.SplashActivity;
import com.example.WithPet02.view.mypage.atask.MyDelete;

import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

public class MyDeleteActivity extends AppCompatActivity {

    EditText myDelChkEt;
    Button myDelChkBtn, myDelCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_delete);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // 회원탈퇴 확인 창에 현재 닉네임 출력
        myDelChkEt = findViewById(R.id.myDelChkEt);
        myDelChkEt.setHint(loginDTO.getM_name());

        //회원 탈퇴 버튼 클릭
        myDelChkBtn = findViewById(R.id.myDelChkBtn);
        myDelChkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chkName = myDelChkEt.getText().toString();
                if(chkName.length() <= 0){
                    Toast.makeText(MyDeleteActivity.this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (!chkName.equals(loginDTO.getM_name())){
                    Toast.makeText(MyDeleteActivity.this, "닉네임을 잘못 입력하셨습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyDeleteActivity.this);
                    builder.setTitle("회원탈퇴");
                    builder.setMessage("정말 탈퇴 하시겠습니까?");
                    builder.setPositiveButton("회원탈퇴", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //회원정보삭제
                            MyDelete myDelete = new MyDelete(loginDTO.getM_tel(), loginDTO.getM_pic());
                            String result = "";
                            try {
                                result = myDelete.execute().get().trim();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            //회원정보 삭제 성공
                            if(result.equals("1")){
                                //loginDTO null로 변경
                                loginDTO = null;

                                //자동로그인 파일 내용 삭제
                                SharedPreferences sf = getSharedPreferences("WithPetM", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sf.edit();
                                editor.clear();
                                editor.commit();

                                //탈퇴처리완료알림
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyDeleteActivity.this);
                                builder.setTitle("회원탈퇴");
                                builder.setMessage("정상적으로 탈퇴처리되었습니다. \n이용해주셔서 감사합니다.");
                                builder.setCancelable(false);
                                builder.setPositiveButton("메인화면으로", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //로딩화면으로
                                        Intent intent = new Intent(MyDeleteActivity.this, SplashActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                builder.show();

                            } else {
                                //회원 탈퇴 실패
                                Toast.makeText(MyDeleteActivity.this,
                                        "탈퇴 처리에 실패하였습니다! \n관리자에게 문의하여 주십시오.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.show();
                }
            }
        });

        //취소 버튼 클릭 ▶ finish()
        myDelCancle = findViewById(R.id.myDelCancle);
        myDelCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // 툴바 뒤로가기
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home ){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
