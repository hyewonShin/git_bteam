package com.example.WithPet02.view.mypage;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.WithPet02.CheckDangerousPermissions.Camera;
import com.example.WithPet02.R;
import com.example.WithPet02.common.CommonMethod;
import com.example.WithPet02.dto.MemberDTO;
import com.example.WithPet02.view.join.JoinCheck;
import com.example.WithPet02.view.login.atask.LoginSelect;
import com.example.WithPet02.view.mypage.atask.MyUpdate;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.common.CommonMethod.ipConfig;
import static com.example.WithPet02.common.CommonMethod.isNetworkConnected;
import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

public class MyUpdateActivity extends AppCompatActivity {

    ImageView myUpdatePic;
    EditText etName, etEmail, etCurPw, etPw, etPwChk;
    String email = "", name = "", pw = "", pwChk = "", tel = "", curPw;
    String m_email, state;

    TextView tvCurPw;

    JoinCheck joinCheck;

    String filePath = ipConfig + "/app/resources/upload/member/";

    public String imageRealPath = null, imageDbPath = null;

    final int CAMERA_REQUEST = 1000;
    final int LOAD_IMAGE = 1001;

    File file = null;
    long fileSize = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_update);

        joinCheck = new JoinCheck(MyUpdateActivity.this);
        tel = loginDTO.getM_tel();

        //툴바 설정
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //View에서 찾기
        myUpdatePic = findViewById(R.id.myUpdatePic);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etCurPw = findViewById(R.id.etCurPw);
        etPw = findViewById(R.id.etPw);
        etPwChk = findViewById(R.id.etPwChk);

        etCurPw = findViewById(R.id.etCurPw);
        tvCurPw = findViewById(R.id.tvCurPw);

        //현재 회원정보 출력
        etName.setText(loginDTO.getM_name());
        etEmail.setText(loginDTO.getM_email());
        Log.d("update : ", "loginDTO.getM_email(): " + loginDTO.getM_email());

        //이미지 서버에서 가져오기
        if(loginDTO.getM_pic() == null){    //사용자 프로필사진 등록 여부 확인
            //프로필 사진없음
            Glide.with(this).load(filePath + "defalt.jpg").signature(new ObjectKey(System.currentTimeMillis())).into(myUpdatePic);
        } else {
            //프로필 사진 있음
            Glide.with(this).load(filePath + loginDTO.getM_pic()).signature(new ObjectKey(System.currentTimeMillis())).into(myUpdatePic);
            imageDbPath = loginDTO.getM_pic();
        }

        //사진 수정 클릭시
        myUpdatePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //카메라, 외부 저장소 사용 권한 확인
                Camera camera = new Camera(MyUpdateActivity.this, MyUpdateActivity.this);
                camera.checkDangerousPermissions();

                //사진 업로드 방식 선택을 위한 AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MyUpdateActivity.this);
                String[] list = {"사진 촬영하기", "내 갤러리에서 선택하기", "사진 삭제하기"};
                builder.setTitle("사진 업로드 방식 선택");
                builder.setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            //사진 촬영해서 등록
                            try {
                                file = createFile();
                                Log.d("FilePath ", file.getAbsolutePath());
                            } catch (Exception e) {
                                Log.d("Sub1Add:filepath", "Something Wrong", e);
                            }

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // API24 이상 부터
                                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                        FileProvider.getUriForFile(getApplicationContext(),
                                                getApplicationContext().getPackageName() + ".fileprovider", file));
                                Log.d("sub1:appId", getApplicationContext().getPackageName());
                            }else {
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                            }

                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(intent, CAMERA_REQUEST);
                            }
                        } else if(i == 1){
                            //갤러리에서 사진 선택
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_PICK);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
                        } else if(i == 2){
                            //사진 삭제하기
                            myUpdatePic.setImageResource(R.drawable.defalt);
                            imageDbPath = null;
                            imageRealPath = null;
                        }
                    }
                });
                builder.show();
            }
        });


        //이메일 체크
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                email = etEmail.getText().toString().trim();
                joinCheck.chkEmail(email);
            }
        });

        //닉네임 체크
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                name = etName.getText().toString().trim();
                joinCheck.chkName(name);
            }
        });

        //변경할 비밀번호 체크
        etPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                pw = etPw.getText().toString().trim();
                if(pw.length() >= 0){
                    joinCheck.chkPw(pw);
                }
            }
        });

        //변경할 비밀번호 확인이 변경할 비밀번호와 같은지 체크
        etPwChk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                pw = etPw.getText().toString().trim();
                pwChk = etPwChk.getText().toString().trim();
                if(pwChk.length() >= 0){
                    joinCheck.chkPwChk(pw, pwChk);
                }
            }
        });
    }

    //파일 생성
    private File createFile() throws IOException {
        String imageFileName = loginDTO.getM_tel() + ".jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File curFile = new File(storageDir, imageFileName);
        return curFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            try {
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(file.getAbsolutePath());
                if(newBitmap != null){
                    myUpdatePic.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPath = file.getAbsolutePath();
                //String uploadFileName = imageRealPath.split("/")[imageRealPath.split("/").length - 1];
                int pos = imageRealPath.lastIndexOf(".");
                String uploadFileType = imageRealPath.substring(pos + 1);
                imageDbPath = loginDTO.getM_tel() + "." + uploadFileType;

                fileSize = file.length();
            } catch (Exception e){
                e.printStackTrace();
            }

        } else if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {

            try {
                String path = "";
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedImageUri);
                }
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(path);
                if(newBitmap != null){
                    myUpdatePic.setImageResource(0);
                    myUpdatePic.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPath = path;
                //String uploadFileName = imageRealPath.split("/")[imageRealPath.split("/").length - 1];
                //imageDbPath = ipConfig + "/app/resources/" + uploadFileName;

                int pos = imageRealPath.lastIndexOf(".");
                String uploadFileType = imageRealPath.substring(pos + 1);
                imageDbPath = loginDTO.getM_tel() + "." + uploadFileType;

                fileSize = file.length();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Get the real path from the URI
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_update_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.my_ok:{ //개인정보 수정
                //변경
                ProgressDialog dialog = new ProgressDialog(MyUpdateActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("회원정보 변경중...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                //수정할 데이터를 변수에 저장
                email = etEmail.getText().toString().trim();
                name = etName.getText().toString().trim();
                pw = etPw.getText().toString().trim();
                pwChk = etPwChk.getText().toString().trim();

                Log.d("update : ", "loginDTO.getM_tel(): " + loginDTO.getM_tel());
                Log.d("update : ", "loginDTO.getM_email(): " + loginDTO.getM_email());
                //현재 로그인된 계정의 이메일 가져옴
                m_email = loginDTO.getM_email();

                //loginDTO를 임시 변수에 저장
                MemberDTO tempDTO = loginDTO;
                loginDTO = null;

                //현재 비밀번호 입력유뮤 확인
                curPw = etCurPw.getText().toString().trim();
                if(curPw.length() <= 0){
                    dialog.dismiss();
                    Toast.makeText(this, "현재 사용중인 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    tvCurPw.setText("현재 사용중인 비밀번호를 입력해주세요");
                    return false;
                } else {
                    tvCurPw.setText("");
                }

                //입력한 비밀번호가 현재 비밀번호가 맞는지 확인
                LoginSelect loginSelect = new LoginSelect(m_email, curPw);
                try {
                    MemberDTO dto = loginSelect.execute().get();
                    if(dto == null){
                        dialog.dismiss();
                        loginDTO = tempDTO;
                        Toast.makeText(this, "현재 비밀번호가 잘못 입력되었습니다.", Toast.LENGTH_SHORT).show();
                        tvCurPw.setText("현재 비밀번호가 잘못 입력되었습니다.");
                        return false;
                    } else {
                        tvCurPw.setText("");
                        loginDTO = tempDTO;
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //수정할 데이터가 형식에 안맞거나 중복되지 않았는지 확인
                if (!joinCheck.chkEmail(email) || !joinCheck.chkName(name)){
                    dialog.dismiss();
                    Toast.makeText(MyUpdateActivity.this, "입력란을 다시 작성하여주십시오.", Toast.LENGTH_SHORT).show();
                    return false;
                }

                Log.d("MyUpdateActivity", "pw: " + pw);
                Log.d("MyUpdateActivity", "pwChk: " + pwChk);

                if(!pw.equals("") || !pwChk.equals("")){    //비밀번호 변경란 입력 여부
                    if(!joinCheck.chkPw(pw) || !joinCheck.chkPwChk(pw, pwChk)){ //비밀번호 변경란 체크
                        dialog.dismiss();
                        Toast.makeText(MyUpdateActivity.this, "입력란을 다시 작성하여주십시오.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    pw = curPw;
                }

                if(isNetworkConnected(this) == true){   //네트워크 연결 여부 확인

                    if(fileSize <= 30000000){  // 파일크기가 30메가 보다 작아야 업로드 할수 있음
                        state = "-100";

                        Log.d("myPicUpdate", "imageDbPath: " + imageDbPath);
                        Log.d("myPicUpdate", "imageRealPath: " + imageRealPath);

                        //업데이트 처리
                        MyUpdate myUpdate = new MyUpdate(tel, email, name, pw, imageDbPath, imageRealPath);
                        try {
                            state = myUpdate.execute().get().trim();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //현재 로그인 정보 갱신
                        loginDTO.setM_email(email);
                        loginDTO.setM_name(name);
                        loginDTO.setM_pic(imageDbPath);

                        //자동로그인 정보 갱신
                        SharedPreferences sf = getSharedPreferences("WithPetM", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sf.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(loginDTO);
                        editor.putString("loginDTO", json);
                        editor.commit();


                        dialog.dismiss();

                        Toast.makeText(this, "회원 정보가 변경되었습니다", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MyPageInfoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        this.startActivity(intent);
                        finish();
                        return true;
                    }else{
                        // 알림창 띄움
                        dialog.dismiss();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("알림");
                        builder.setMessage("파일 크기가 30MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택해 주십시요!!!");
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }

                }else {
                    Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
