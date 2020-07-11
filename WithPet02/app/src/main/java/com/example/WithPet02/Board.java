package com.example.WithPet02;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WithPet02.Adapter.SpinnerAdapter;
import com.example.WithPet02.CheckDangerousPermissions.Camera;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Board extends AppCompatActivity {

    Activity activity = Board.this;
    Context context = Board.this;

    //액션바 변수 설정
    ActionBar bar;
    //rootLayout 설정
    SlidingUpPanelLayout rootLayout;
    LinearLayout wrap_content;

    //file 설정해줄 file 변수
    File file = null;

    //찍은 사진 나오는 곳
    ImageView camera_picture;

    //버튼들 변수
    Button camera, submit, cancel, album;

    //글쓰는 곳
    EditText title, name, content;

    //recyclerView사용
    RecyclerView board_list;


    ScrollView scrollView;

    String state;





    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        scrollView = findViewById(R.id.scrollView);
        rootLayout = findViewById(R.id.rootLayout);
        wrap_content = findViewById(R.id.wrap_content);

        album = findViewById(R.id.album);
        camera = findViewById(R.id.camera);

        //액션바 가져오기
        bar = getSupportActionBar();

        //backlogo가져오기
        bar.setLogo(R.drawable.back);
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        //액션바 클릭하면 클릭되게 해줌
        bar.setDisplayHomeAsUpEnabled(true);
        //액션바 타이틀 바꾸기
        bar.setTitle("게시물 만들기");
        //액션바 색바꾸기
        bar.setBackgroundDrawable(new ColorDrawable(0xffff6666));

        //Spinner사용
        //Spinner에 넣을 목록 데이터
        List<String> spinnerData = new ArrayList<>();
        spinnerData.add("자유게시판"); spinnerData.add("물어보세요");
        //spinner찾기
        Spinner spinner = findViewById(R.id.spinner1);
        //spinner의 어뎁터 찾기
        SpinnerAdapter adapter = new SpinnerAdapter(this, spinnerData);
        spinner.setAdapter(adapter);




        //recyclerview사용
        /*board_list = findViewById(R.id.board_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        board_list.setLayoutManager(layoutManager);

        //boardMenuAdapter를 객체로 생성하고 addItem메소드를 사용해 DTO를 넣어준다.
        BoardMenuAdatper adatper = new BoardMenuAdatper();
        adatper.addItem(new BoardMenuDTO("자유게시판", R.drawable.album));
        adatper.addItem(new BoardMenuDTO("물어보세요", R.drawable.dog));
        //Adapter가져오기
        board_list.setAdapter(adatper);*/



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
                    startActivityForResult(cameraWindow, 1004);
                }

            }//onClick()
        });//setOnClickListener()


        /*submit.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "Mainboard";

            @Override
            public void onClick(View v) {
                String b_title = title.getText().toString();
                String b_name = name.getText().toString();
                String b_content = content.getText().toString();

                //위의 받은 값을 DB와 통신 할 수 있도록 해준다
                BoardInsert boardInsert = new BoardInsert(b_title, b_name, b_content);

                //실행
               *//* try {
                    //옳게 됐는지 state로 받을 수 있음
                    state = BoardInsert.execute().get().trim();
                }catch (ExecutionException e){
                    e.printStackTrace();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }//try*//*
                
                //성공여부 확인
                if(state.equals("1")) {
                    Log.d(TAG, "onClick: 삽입성공");
                }else {
                    Log.d(TAG, "onClick: 삽입실패");
                    finish();
                }
            }//onClick()
        });//setOnClickListener()*/

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
                Toast.makeText(this, "제출 눌림", Toast.LENGTH_SHORT).show();
                break;

            case android.R.id.home ://홈 메뉴 눌리면 꺼지도록 설정
                this.finish();
                break;
        }//switch

        return true;
    }//onOptionsItemSelected()

    //intent의 값을 가지고옴(넘어오는 값)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1004 && resultCode == RESULT_OK) {   //requestCode와 resultCode가 맞다면
            BitmapFactory.Options options = new BitmapFactory.Options();
            //핸드폰 해상도를 1/8로 바꿔달라는 것(핸드폰의 해상도가 높기 때문에)
            options.inSampleSize = 8;
            if(file != null) {  //사진을 찍었다는 의미가 됨
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                camera_picture = findViewById(R.id.camera_picture);
                //절대경로에서 가져오면서 options도 가지고 옴
                camera_picture.setImageBitmap(bitmap);
                rootLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }else { //사진이 안찍혔거나 잘 못 됐을 경우
                Toast.makeText(this, "File is null", Toast.LENGTH_SHORT).show();
            }//if
        }//if
    }//onActivityResult()


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
}//class
