package com.example.WithPet02.view.community;

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

import com.bumptech.glide.Glide;
import com.example.WithPet02.CheckDangerousPermissions.Camera;
import com.example.WithPet02.R;
import com.example.WithPet02.common.CommonMethod;
import com.example.WithPet02.dto.FreeBoardDTO;
import com.example.WithPet02.view.community.atask.FreeBoardUpdate;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.common.CommonMethod.ipConfig;

public class CommunityBoardUpdate extends AppCompatActivity {

    Context context;
    FreeBoardDTO dto;

    ImageView uploadPic;
    EditText etTitle, etContent;

    int f_num;
    String f_title, f_content, f_file;

    String filePath = ipConfig + "/app/resources/upload/freeboard/";
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
        setContentView(R.layout.activity_community_board_update);

        dto = (FreeBoardDTO) getIntent().getSerializableExtra("dto");

        context = this;

        //툴바
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //뷰에서 찾기
        uploadPic = findViewById(R.id.uploadPic);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);

        //수정할 글 정보 출력
        if(dto.getF_file() != null){
            Glide.with(this).load(filePath + dto.getF_file()).into(uploadPic);
        }
        etTitle.setText(dto.getF_title());
        etContent.setText(dto.getF_content());
        imageDbPath = dto.getF_file();


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        time = sdf.format(cal.getTime());

        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //카메라, 외부 저장소 사용 권한 확인
                Camera camera = new Camera(context, CommunityBoardUpdate.this);
                camera.checkDangerousPermissions();

                //사진 업로드 방식 선택을 위한 AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String[] list = {"사진 촬영하기", "내 갤러리에서 선택하기", "기존 사진 삭제하기"};
                builder.setTitle("사진 업로드 방식 선택");
                builder.setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            //사진 촬영해서 등록
                            try {
                                file = createFile();
                            } catch (Exception e) {
                                Log.d("FreeBoardAdd:filepath", "Something Wrong", e);
                            }

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // API24 이상 부터
                                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                        FileProvider.getUriForFile(getApplicationContext(),
                                                getApplicationContext().getPackageName() + ".fileprovider", file));
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
                        uploadPic.setImageResource(0);
                        imageDbPath = null;
                        imageRealPath = null;
                        }
                    }
                });
                builder.show();
            }
        });

    }

    //파일 생성
    private File createFile() throws IOException {
        String imageFileName = time + ".jpg";
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar 의 back 키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.my_ok:{
                //수정 처리후 CommunityActivity 로 다시 이동
                //글쓴이 정보 가져오기
                //사용자가 입력한 제목, 내용 가져오기
                f_num = dto.getF_num();
                f_title = etTitle.getText().toString();
                f_content = etContent.getText().toString();
                f_file = dto.getF_file();

                if(f_title.trim().equals("")) {
                    Toast.makeText(context, "제목을 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else if(f_content.trim().equals("")){
                    Toast.makeText(context, "내용을 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    //수정 처리
                    FreeBoardUpdate freeBoardUpdate = new FreeBoardUpdate(f_num, f_title, f_content, f_file, imageRealPath, imageDbPath);
                    try {
                        state = freeBoardUpdate.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(state.equals("1")){
                        //CommunityActivity 로 이동
                        Toast.makeText(context, "수정완료!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, CommunityActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                        finish();
                        return true;
                    } else {
                        Toast.makeText(context, "수정에 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
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
                int pos = imageRealPath.lastIndexOf(".");
                String uploadFileType = imageRealPath.substring(pos + 1);
                imageDbPath = time + "." + uploadFileType;

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

                int pos = imageRealPath.lastIndexOf(".");
                String uploadFileType = imageRealPath.substring(pos + 1);
                imageDbPath = time + "." + uploadFileType;

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
