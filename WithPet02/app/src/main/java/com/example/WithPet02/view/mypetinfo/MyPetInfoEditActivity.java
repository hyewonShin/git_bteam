package com.example.WithPet02.view.mypetinfo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.WithPet02.CheckDangerousPermissions.Camera;
import com.example.WithPet02.MainActivity;
import com.example.WithPet02.R;
import com.example.WithPet02.common.CommonMethod;
import com.example.WithPet02.common.DatePickerActivity;
import com.example.WithPet02.view.mypage.MyPageInfoActivity;
import com.example.WithPet02.view.mypetinfo.atask.MyPetDelete;
import com.example.WithPet02.view.mypetinfo.atask.MyPetInsert;
import com.example.WithPet02.view.mypetinfo.atask.MyPetUpdate;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.common.CommonMethod.ipConfig;
import static com.example.WithPet02.common.CommonMethod.isNetworkConnected;
import static com.example.WithPet02.view.login.LoginActivity.loginDTO;
import static com.example.WithPet02.view.mypetinfo.MyPetInfo.myPetList;

public class MyPetInfoEditActivity extends AppCompatActivity {

    EditText name, etAnimal1, etAnimal2, etBirth;
    ImageView genMale, genFemale, petUpdatePic;
    LinearLayout btnBirth;
    Button btnDelete;

    String p_tel="", p_name = "", p_animal = "", p_a_animal = "", p_birth = "", p_gender ="여";
    String birth;
    int cur, position;
    private int mYear =0, mMonth=0, mDay=0;

    String filePath = ipConfig + "/app/resources/upload/pet/";
    public String imageRealPath = null, imageDbPath = null;

    final int ACT_SET_BIRTH = 1004;
    final int CAMERA_REQUEST = 1000;
    final int LOAD_IMAGE = 1001;

    File file = null;
    long fileSize = 0;

    String state;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_info_edit);

        context = this;

        //수정할 동물의 번호 확인
        cur = getIntent().getIntExtra("p_num", 0);

        //myPetList 중 수정할 동물의 인덱스 확인
        position = getIntent().getIntExtra("position", 0);

        //툴바 설정
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //뷰에서 찾기
        petUpdatePic = findViewById(R.id.petUpdatePic);
        name = findViewById(R.id.etName);
        genMale = findViewById(R.id.genMale);
        genFemale = findViewById(R.id.genFemale);
        btnBirth = findViewById(R.id.btnBirth);
        etBirth = findViewById(R.id.etBirth);
        etAnimal1 = findViewById(R.id.etAnimal1);
        //etAnimal2 = findViewById(R.id.etAnimal2);
        btnDelete = findViewById(R.id.btnDelete);

        //현재 동물 정보 가져오기
        name.setText(myPetList.get(position).getP_name());              //이름
        if(myPetList.get(position).getP_gender().equals("여")){          //성별
            genMale.setImageResource(R.drawable.btn_male_1);
            genFemale.setImageResource(R.drawable.btn_female_2);
            p_gender = "여";
        } else {
            genMale.setImageResource(R.drawable.btn_male_2);
            genFemale.setImageResource(R.drawable.btn_female_1);
            p_gender = "남";
        }
        birth = myPetList.get(position).getP_birth().substring(0, 10).replace("-", ".");
        etBirth.setText(birth);                                         //생일
        etAnimal1.setText(myPetList.get(position).getP_animal());
        //etAnimal2.setText(myPetList.get(position).getP_a_animal());

        //현재 동물 사진 가져오기
        if(myPetList.get(position).getP_pic() != null){
            Glide.with(this).load(filePath + myPetList.get(position).getP_pic()).signature(new ObjectKey(System.currentTimeMillis())).into(petUpdatePic);
            imageDbPath = myPetList.get(position).getP_pic();
        }

        //성별설정
        genMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genMale.setImageResource(R.drawable.btn_male_2);
                genFemale.setImageResource(R.drawable.btn_female_1);
                p_gender = "남";
            }
        });

        genFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genMale.setImageResource(R.drawable.btn_male_1);
                genFemale.setImageResource(R.drawable.btn_female_2);
                p_gender = "여";
            }
        });

        //생년월일설정
        btnBirth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPetInfoEditActivity.this, DatePickerActivity.class);
                intent.putExtra("birth", birth);
                startActivityForResult(intent, ACT_SET_BIRTH);
            }

        });

        //프로필 사진 업로드
        petUpdatePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //카메라, 외부 저장소 사용 권한 확인
                Camera camera = new Camera(MyPetInfoEditActivity.this, MyPetInfoEditActivity.this);
                camera.checkDangerousPermissions();

                //사진 업로드 방식 선택을 위한 AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MyPetInfoEditActivity.this);
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
                            petUpdatePic.setImageResource(R.drawable.a_defalt);
                            imageDbPath = null;
                            imageRealPath = null;
                        }
                    }
                });
                builder.show();
            }
        });

        //동물 삭제
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MyPetInfoEditActivity.this);
                builder.setMessage("정말 동물 정보를 삭제하시겠습니까?\n현재 동물 관련 정보가 모두 삭제됩니다.");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //삭제처리
                        MyPetDelete myPetDelete = new MyPetDelete(myPetList.get(position).getP_num(), myPetList.get(position).getP_pic());
                        try {
                            state = myPetDelete.execute().get().trim();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(state.equals("1")){
                            Toast.makeText(context, "정상적으로 삭제되었습니다", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MyPetInfo.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(context, "삭제에 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }

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

    }

    //파일 생성
    private File createFile() throws IOException {
        String imageFileName = "myPet" + cur + ".jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File curFile = new File(storageDir, imageFileName);
        return curFile;
    }

    //툴바에 메뉴추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_update_menu, menu);
        return true;
    }

    //툴바 메뉴 기능 구현
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.my_ok: { //정보등록처리

                if(!isNetworkConnected(context)){   //네트워크 연결 여부 확인
                    Toast.makeText(context, "인터넷이 연결되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if(fileSize > 30000000){  // 파일크기가 30메가 보다 클 경우
                    // 알림창 띄움
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("알림");
                    builder.setMessage("파일 크기가 30MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택해 주십시요!!!");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                    return false;
                }

                state = "-100";

                //입력정보 가져오기
                p_name = name.getText().toString().trim();
                p_animal = etAnimal1.getText().toString().trim();
                //p_a_animal = etAnimal2.getText().toString().trim();
                p_birth = etBirth.getText().toString().trim();

                if(p_name.equals("")){
                    Toast.makeText(context, "이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if(p_animal.equals("")){
                    Toast.makeText(context, "동물 종류를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return false;
                }

                //업데이트 처리
                MyPetUpdate myPetUpdate = new MyPetUpdate(cur, p_name, p_animal, p_a_animal, p_birth, p_gender, imageDbPath, imageRealPath);
                try {
                    state = myPetUpdate.execute().get().trim();
                    Log.d("MyPetInfoAddActivity", " state: " + state);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(state.equals("1")){
                    Toast.makeText(context, "정상적으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MyPetInfo.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    finish();
                    return true;
                } else {
                    Toast.makeText(context, "등록에 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
        }
        return super.onOptionsItemSelected(item);
    }

    //ActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //DatePickerActivity 에서 생년월일 받기
        if(requestCode == ACT_SET_BIRTH){
            mYear = data.getIntExtra("mYear", mYear);
            mMonth = data.getIntExtra("mMonth", mMonth);
            mDay = data.getIntExtra("mDay", mDay);

            etBirth.setText(mYear + "." + (mMonth + 1) + "." + mDay);
        }

        //사진 가져오기
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            try {
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(file.getAbsolutePath());
                if(newBitmap != null){
                    petUpdatePic.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPath = file.getAbsolutePath();
                //String uploadFileName = imageRealPath.split("/")[imageRealPath.split("/").length - 1];
                int pos = imageRealPath.lastIndexOf(".");
                String uploadFileType = imageRealPath.substring(pos + 1);
                imageDbPath = loginDTO.getM_tel() + "_pet" + cur +  "." + uploadFileType;

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
                    petUpdatePic.setImageResource(0);
                    petUpdatePic.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPath = path;
                //String uploadFileName = imageRealPath.split("/")[imageRealPath.split("/").length - 1];
                //imageDbPath = ipConfig + "/app/resources/" + uploadFileName;

                int pos = imageRealPath.lastIndexOf(".");
                String uploadFileType = imageRealPath.substring(pos + 1);
                imageDbPath = loginDTO.getM_tel() + "_pet" + cur + "." + uploadFileType;

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
}
