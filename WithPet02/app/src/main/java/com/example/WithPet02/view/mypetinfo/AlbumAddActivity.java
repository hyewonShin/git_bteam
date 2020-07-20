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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.WithPet02.CheckDangerousPermissions.Camera;
import com.example.WithPet02.R;
import com.example.WithPet02.common.CommonMethod;
import com.example.WithPet02.view.mypetinfo.atask.AlbumInsert;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.common.CommonMethod.ipConfig;
import static com.example.WithPet02.view.login.LoginActivity.loginDTO;
import static com.example.WithPet02.view.mypetinfo.MyPetInfo.myPetList;

public class AlbumAddActivity extends AppCompatActivity {

    Context context;

    ImageView uploadPic;
    EditText etTitle, etContent;

    int a_pet;
    String a_title, a_content;

    String filePath = ipConfig + "/app/resources/upload/album/";
    public String imageRealPath, imageDbPath;

    final int CAMERA_REQUEST = 1000;
    final int LOAD_IMAGE = 1001;

    File file = null;
    long fileSize = 0;

    String time;

    String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_add);

        context = this;

        //툴바
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //선택된 동물의 번호 받기
        a_pet = getIntent().getIntExtra("a_pet", 0);
        filePath += a_pet + "/";

        //뷰에서 찾기
        uploadPic = findViewById(R.id.uploadPic);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        time = sdf.format(cal.getTime());

        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //카메라, 외부 저장소 사용 권한 확인
                Camera camera = new Camera(context, AlbumAddActivity.this);
                camera.checkDangerousPermissions();

                //사진 업로드 방식 선택을 위한 AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String[] list = {"사진 촬영하기", "내 갤러리에서 선택하기"};
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
                        }
                    }
                });
                builder.show();
            }
        });
    }

    //파일 생성
    private File createFile() throws IOException {
        String imageFileName = a_pet + "_" + time + ".jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File curFile = new File(storageDir, imageFileName);
        return curFile;
    }

    // 툴바 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_update_menu, menu);
        return true;
    }

    // 툴바 메뉴 선택
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar 의 back 키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.my_ok:{
                //앨범에 등록 처리후 MyPetInfoActivity 로 다시 이동
                //사용자가 입력한 제목, 내용 가져오기
                a_title = etTitle.getText().toString();
                a_content = etContent.getText().toString();
                if(imageDbPath == null) {
                    Toast.makeText(context, "사진을 등록해주세요!", Toast.LENGTH_SHORT).show();
                } else if(a_title.trim().equals("")){
                    Toast.makeText(context, "제목을 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    //등록 처리
                    AlbumInsert albumInsert = new AlbumInsert(a_pet, a_title, a_content, imageRealPath, imageDbPath);
                    try {
                        state = albumInsert.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(state.equals("1")){
                        //MyPetInfoActivity 로 이동
                        Toast.makeText(context, "등록완료!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MyPetInfo.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                        finish();
                        return true;
                    } else {
                        Toast.makeText(context, "등록에 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                }

            }
        }
        return super.onOptionsItemSelected(item);
    }


    //ActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //사진 가져오기
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            try {
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(file.getAbsolutePath());
                if(newBitmap != null){
                    uploadPic.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPath = file.getAbsolutePath();
                //String uploadFileName = imageRealPath.split("/")[imageRealPath.split("/").length - 1];
                int pos = imageRealPath.lastIndexOf(".");
                String uploadFileType = imageRealPath.substring(pos + 1);
                imageDbPath = a_pet + "_" + time + "." + uploadFileType;

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
                    uploadPic.setImageResource(0);
                    uploadPic.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPath = path;
                //String uploadFileName = imageRealPath.split("/")[imageRealPath.split("/").length - 1];
                //imageDbPath = ipConfig + "/app/resources/" + uploadFileName;

                int pos = imageRealPath.lastIndexOf(".");
                String uploadFileType = imageRealPath.substring(pos + 1);
                imageDbPath = a_pet + "_" + time + "." + uploadFileType;

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
