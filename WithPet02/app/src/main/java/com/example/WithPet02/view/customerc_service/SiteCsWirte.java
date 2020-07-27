package com.example.WithPet02.view.customerc_service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.WithPet02.CheckDangerousPermissions.Camera;
import com.example.WithPet02.R;
import com.example.WithPet02.common.CommonMethod;
import com.example.WithPet02.view.customerc_service.atask.BoardInsert;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.common.CommonMethod.ipConfig;
import static com.example.WithPet02.common.CommonMethod.isNetworkConnected;
import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

public class SiteCsWirte extends AppCompatActivity {

    Activity activity = SiteCsWirte.this;
    Context context = SiteCsWirte.this;

    //rootLayout 설정
    SlidingUpPanelLayout rootLayout;
    LinearLayout wrap_content;

    //file 설정해줄 file 변수
    File file = null;

    //file크기 한계주기 위한 변수
    long fileSize;

    //ImageView
    ImageView camera_picture, image_cancel, m_profile;

    //사진 직접경로(핸드폰안 경로), DB경로
    public String imageRealPath, imageDbPath;

    final  int CAMERA_REQUEST = 1004;
    final  int LOAD_IMAGE = 1001;

    //버튼들 변수
    Button board_album;

    //글쓰는 곳
    EditText title, content;

    //텍스트뷰
    TextView board_nickname;

    ScrollView scrollView;

    //슬라이더 안의 메뉴들
    LinearLayout camera, album;

    //툴바
    Toolbar toolbar;

    //로그인 사람 프로필 ip가져오기
    String filePath = ipConfig + "/app/resources/upload/member/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_cs_wirte);

        //툴바 적용
        toolbar = findViewById(R.id.cs_toolbar);
        setSupportActionBar(toolbar);

        //툴바 타이틀 보일지 말지 설정
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //홈버튼 보일지 말지 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        title = findViewById(R.id.title);   //제목 쓰는 곳
        content = findViewById(R.id.content);   //내용 쓰는 곳
        scrollView = findViewById(R.id.scrollView); //내용 쓰는 부분 스크롤뷰
        rootLayout = findViewById(R.id.rootLayout); //슬라이드패널
        wrap_content = findViewById(R.id.wrap_content); //로그인 한 사람 정보 나오는 곳 전체
        camera_picture = findViewById(R.id.camera_picture); //사진 올린것 나오는 곳
        board_album = findViewById(R.id.board_album);   //
        board_nickname = findViewById(R.id.board_nickname); //로그인 한 사람 nickname
        image_cancel = findViewById(R.id.image_cancel);     //사진올린거 취소하는 버튼
        m_profile = findViewById(R.id.m_profile);   //로그인 한 사람 프로필 나오는 곳

        camera = findViewById(R.id.camera); //슬라이드패널 안의 카메라
        album = findViewById(R.id.album);   //슬라이드패널 안의 앨범

        //로그인 한사람 정보 넣기
        Glide.with(this).load(filePath + loginDTO.getM_pic()).into(m_profile);
        board_nickname.setText(loginDTO.getM_name());

        //Spinner사용
        //Spinner에 넣을 목록 데이터
        //List<String> spinnerData = new ArrayList<>();
        //spinnerData.add("자유");
        //spinnerData.add("물음");
        //spinnerData.add("간식");
        //spinnerData.add("분양");
        //spinnerData.add("사고팔기");
        //spinnerData.add("기타");

        //spinner찾기
        //Spinner spinner = findViewById(R.id.spinner1);
        //spinner의 어뎁터 찾기
        //SpinnerAdapter adapter = new SpinnerAdapter(this, spinnerData);
        //spinner.setAdapter(adapter);

        //슬라이드 사용
        final SlidingUpPanelLayout rootLayout = findViewById(R.id.rootLayout);
        wrap_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menuSlide가 올라가 있으면 내려주기
                if(rootLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    rootLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }//if
            }//onClick()
        });//setOnClickListener()


        //사진 가져오기(갤러리에서)
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera_picture.setVisibility(View.VISIBLE);
                image_cancel.setVisibility(View.VISIBLE);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);

            }//onClick()
        });//setOnClickListener()
        //→ onActivityResult에서 받아야 한다.


        //카메라 버튼 눌렀을 때 설정
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //권한설정해주기
                Camera camera_CDP = new Camera(context, activity);
                camera_CDP.checkDangerousPermissions();

                //파일을 생성해주게 한다.
                if(file == null) {
                    file = createFile();
                }//if

                camera_picture.setVisibility(View.VISIBLE);
                image_cancel.setVisibility(View.VISIBLE);

                //Capture 할 수 있는 화면생성
                Intent cameraWindow = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //파일 이름 가지고 오기(누가버전이냐 아니냐에 따라 다름)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //누가버전 이상이면 아래와 같이 파일 이름 가지고 온다.
                    cameraWindow.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider
                            .getUriForFile(getApplicationContext(),
                                    getApplicationContext().getPackageName() + ".fileprovider", file));
                    //FileProvider~~~~, file) → Andoridmanifest.xml의 이름(applicationId)을 의미(PackageName)
                }else {
                    //누가버전 아래면 그냥 파일이름 가져옴
                    cameraWindow.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                }//if

                //사진 찍으려고 띄워진것이 null이 아니면
                if(cameraWindow.resolveActivity(getPackageManager()) != null) {
                    //intent 띄우려고 데이터 받으려면 startActivitForResult로 받아야 함
                    startActivityForResult(cameraWindow, CAMERA_REQUEST);
                }

            }//onClick()
        });//setOnClickListener()

        //이미지없애는 버튼 눌렀을 때 넣어준 이미지 없애기
        image_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera_picture.setVisibility(View.GONE);
                image_cancel.setVisibility(View.GONE);
                camera_picture.setImageResource(0);
                camera_picture.setImageBitmap(null);
            }//onClick()
        });//setOnClickListener()

    }//onCreate


    //OptionsMenu부르기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //메뉴옵션을 inflate시켜줌
        getMenuInflater().inflate(R.menu.board_menu_option, menu);

        return true;
    }//onCreateOptionsMenu()


    //OptionMenu선택시 작동되게 하기
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();

        switch (curId){
            case R.id.submit :
                //제출시 값 DB로 보내기
                //인터넷 연결이 되어 있으면
                if(isNetworkConnected(getApplicationContext()) == true) {

                    if (fileSize <= 30000000) {  //파일 사이즈가 30메가보다 작으면
                        //title, content, board_nickname
                        board_nickname.getText().toString();
                        title.getText().toString();
                        content.getText().toString();

                        Toast.makeText(activity, "제출 눌림", Toast.LENGTH_SHORT).show();

                        //DB와 연동 시키기 위해서 보내줘야 할 것들을 넣어준다.(id, name, date, imageDbPath, imageRealPath)
                        //AsyncTask를 만들어 준다.
                        BoardInsert boardInsert = new BoardInsert(board_nickname.getText().toString(), title.getText().toString(), content.getText().toString(),
                                imageDbPath, imageRealPath);
                        //AsyncTask를 실행시켜준다.
                        //.get()를 해주는 것은 Insert다 될 때 까지 기다리게 해달라는 의미
                        //이다음에 listselect가 먼저 되게 되지 않게 해주기 위함
                        try {
                            boardInsert.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        SiteCsFragment2 siteCsFragment2 = new SiteCsFragment2();
                        siteCsFragment2.setRecyclerView();

                        finish();
                    }else{  ////파일 사이즈가 30메가보다 크면
                        // 알림창 띄움
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setTitle("알림");
                        builder.setMessage("파일 크기가 30MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택해 주십시요!!!");
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }//onClick()
                        });//setPositiveButton()
                        builder.show();
                    }//if

                }else {
                    Toast.makeText(getApplicationContext(), "인터넷이 연결되어 있지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }//if

                Toast.makeText(this, "제출 눌림", Toast.LENGTH_SHORT).show();
                break;

            case android.R.id.home ://홈 메뉴 눌리면 꺼지도록 설정
                this.finish();
                break;
        }//switch

        return true;
    }//onOptionsItemSelected()

    //파일 만들기
    private File createFile() {
        String imageFileName = "test.jpg";
        //폴더(directory)를 만들어서 넣어줌
        File storageDir = new File(Environment.getExternalStorageDirectory()
                + File.separator + "AnImage");
        //파일(file)을 만들어서 넣어줌
        File curFile = null;
        if(!storageDir.exists()) {
            storageDir.mkdir();
            curFile = new File(storageDir, imageFileName);

        }else {
            curFile = new File(storageDir, imageFileName);
        }
        return curFile;
    }//createFile()

    //intent의 값을 가지고옴(넘어오는 값)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //카메라 결과가 잘 됐는지
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            try {
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(file.getAbsolutePath());
                if(newBitmap != null){
                    camera_picture.setImageBitmap(newBitmap);

                    //menuSlide가 올라가 있으면 내려주기
                    if(rootLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        rootLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    }//if
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                //핸드폰 절대 경로
                imageRealPath = file.getAbsolutePath();
                //split으로 /로 잘라서 배열로 저장
                //마지막배열에는 파일이름이 있기 때문에 → [imageRealPathA.split("/").length - 1] : 파일 이름만 가져온다.
                String uploadFileName = imageRealPath.split("/")[imageRealPath.split("/").length - 1];
                //업로드 될 때 파일이름을 위에서 가져온 파일이름으로 한다.
                imageDbPath = ipConfig + "/app/resources/" + uploadFileName;

                //파일크기
                //파일크기를 알 수 있다.
                fileSize = file.length();

            } catch (Exception e){
                e.printStackTrace();
            }//try
        }else if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {

            try {
                String path = "";
                // Get the url from data(사진을 가지고 와줌)
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedImageUri);
                }
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(path);
                if (newBitmap != null) {
                    camera_picture.setImageBitmap(newBitmap);

                    //menuSlide가 올라가 있으면 내려주기
                    if(rootLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        rootLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    }//if
                } else {
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                //핸드폰 절대 경로
                imageRealPath = path;
                Log.d("Sub1Add", "imageFilePathA Path : " + imageRealPath);
                //split으로 /로 잘라서 배열로 저장
                //마지막배열에는 파일이름이 있기 때문에 → [imageRealPathA.split("/").length - 1] : 파일 이름만 가져온다.
                String uploadFileName = imageRealPath.split("/")[imageRealPath.split("/").length - 1];
                imageDbPath = ipConfig + "/app/resources/" + uploadFileName;

                //파일크기
                //파일크기를 알 수 있다.
                fileSize = file.length();
            } catch (Exception e) {
                e.printStackTrace();
            }//try

        }//if
    }//onActivityResult()

    //갤러리에서 파일 가져오는 경로
    // Get the real path from the URI
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }//if
        cursor.close();
        return res;
    }//getPathFromURI()


    // 이미지 로테이트 및 사이즈 변경
    public static Bitmap imageRotateAndResize(String path){ // state 1:insert, 2:update
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 8;

        File file = new File(path);
        if (file != null) {
            // 돌아간 앵글각도 알기
            int rotateAngle = setImageOrientation(file.getAbsolutePath());
            Bitmap bitmapTmp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

            // 사진 바로 보이게 이미지 돌리기
            Bitmap bitmap = imgRotate(bitmapTmp, rotateAngle);

            return bitmap;
        }//if
        return null;
    }//imageRotateAndResize()

    // 사진 찍을때 돌린 각도 알아보기 : 가로로 찍는게 기본임
    public static int setImageOrientation(String path){
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }//try

        int oriention = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        return oriention;
    }//setImageOrientation()

    // 이미지 돌리기
    public static Bitmap imgRotate(Bitmap bitmap, int orientation){

        Matrix matrix = new Matrix();

        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }//switch
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }//try

    }//imgRotate()

}//class
