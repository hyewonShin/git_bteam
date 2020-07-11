package com.example.WithPet02.view.join;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.example.WithPet02.R;
import com.example.WithPet02.view.join.atask.JoinSelect;
import com.example.WithPet02.view.login.LoginActivity;

import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

public class JoinCheck {

    private String colorRed = "#FF0000";
    private String colorGreen = "#00AA00";

    private Context context;
    private String resultEmail, resultName;
    private TextView joinTvEmailChk, joinTvNameChk, joinTvPwChk, joinTvPwCChk;

    public JoinCheck(Context context) {
        this.context = context;

        joinTvEmailChk = ((Activity)context).findViewById(R.id.joinTvEmailChk);
        joinTvNameChk = ((Activity)context).findViewById(R.id.joinTvNameChk);
        joinTvPwChk = ((Activity)context).findViewById(R.id.joinTvPwChk);
        joinTvPwCChk = ((Activity)context).findViewById(R.id.joinTvPwCChk);
    }

    // 이메일 유효성 검사
    public boolean chkEmail(String email){
        //회원 정보 수정시 이메일 변경여부체크
        if(loginDTO != null) {
            if(email.equals(loginDTO.getM_email())){
                return true;
            }
        }

        final String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        resultEmail = null;
        if(email.length() <= 0) {
            joinTvEmailChk.setTextColor(Color.parseColor(colorRed));
            joinTvEmailChk.setText("이메일을 입력해주세요");
            return false;
        } else if (!email.matches(emailPattern)){
            joinTvEmailChk.setTextColor(Color.parseColor(colorRed));
            joinTvEmailChk.setText("이메일 형식이 잘못되었습니다.");
            return false;
        } else {
            JoinSelect emailSelect = new JoinSelect("m_email", email);
            try {
                resultEmail = emailSelect.execute().get().trim();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!resultEmail.equals("1")){
                joinTvEmailChk.setTextColor(Color.parseColor(colorGreen));
                joinTvEmailChk.setText("사용할 수 있는 이메일입니다.");
                return true;
            } else {
                joinTvEmailChk.setTextColor(Color.parseColor(colorRed));
                joinTvEmailChk.setText("이미 가입된 이메일입니다.");
                return false;
            }
        }
    }

    // 닉네임 유효성 검사
    public boolean chkName(String name){
        //회원 정보 수정시 닉네임 변경여부체크
        if(loginDTO != null) {
            if (name.equals(loginDTO.getM_name())) {
                return true;
            }
        }

        final String namePattern = "^[a-zA-Z0-9가-힣]{2,10}$";
        if(name.length() <= 0){
            joinTvNameChk.setTextColor(Color.parseColor(colorRed));
            joinTvNameChk.setText("닉네임을 입력해주세요.");
            return false;
        } else if(!name.matches(namePattern)){
            joinTvNameChk.setTextColor(Color.parseColor(colorRed));
            joinTvNameChk.setText("2-10자리 영문, 숫자, 한글만 입력 가능합니다.");
            return false;
        } else  {
            JoinSelect nameSelect = new JoinSelect("m_name", name);
            try {
                resultName = nameSelect.execute().get().trim();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!resultName.equals("1")){
                joinTvNameChk.setTextColor(Color.parseColor(colorGreen));
                joinTvNameChk.setText("사용할 수 있는 닉네임입니다.");
                return true;
            } else {
                joinTvNameChk.setTextColor(Color.parseColor(colorRed));
                joinTvNameChk.setText("이미 사용중인 닉네임입니다.");
                return false;
            }
        }
    }

    // 비밀번호 유효성 검사
    public boolean chkPw(String pw){
        final String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$";

        if(pw.length() <= 0){
            joinTvPwChk.setTextColor(Color.parseColor(colorRed));
            joinTvPwChk.setText("비밀번호를 입력해주세요.");
            return false;
        } else if(!pw.matches(pwPattern)){
            joinTvPwChk.setTextColor(Color.parseColor(colorRed));
            joinTvPwChk.setText("알파벳,숫자,특수문자포함 8-20자만 가능합니다.");
            return false;
        } else  {
            joinTvPwChk.setTextColor(Color.parseColor(colorGreen));
            joinTvPwChk.setText("사용할 수 있는 비밀번호입니다.");
            return true;
        }
    }

    // 비밀번호확인 일치 확인
    public boolean chkPwChk(String pw, String pwChk){
        joinTvPwCChk.setText("");
        if(pwChk.length() <= 0){
            joinTvPwCChk.setTextColor(Color.parseColor(colorRed));
            joinTvPwChk.setText("비밀번호확인을 입력해주세요.");
            return false;
        } else if(!pwChk.matches(pw)){
            joinTvPwCChk.setTextColor(Color.parseColor(colorRed));
            joinTvPwCChk.setText("입력하신 비밀번호와 확인이 일치하지않습니다.");
            return false;
        } else  {
            joinTvPwCChk.setTextColor(Color.parseColor(colorGreen));
            joinTvPwCChk.setText("입력하신 비밀번호와 확인이 일치합니다.");
            return true;
        }
    }

}