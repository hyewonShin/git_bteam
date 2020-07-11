package com.example.WithPet02;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Board_Write extends AppCompatActivity {

    //액션바 변수 설정
    ActionBar bar;
    //rootLayout 설정
    RelativeLayout rootLayout;
    LinearLayout linearLayout;

    SlidingDrawer drawer;

    ScrollView scrollView;

    String state;

    EditText title, name, content;

    Button camera, submit, cancel, board_list, album;

    ImageView file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        scrollView = findViewById(R.id.scrollView);
        rootLayout = findViewById(R.id.rootLayout);
        linearLayout = findViewById(R.id.linearLayout);
        board_list = findViewById(R.id.board_list);
        album = findViewById(R.id.album);
        drawer = findViewById(R.id.slide);

        //액션바 가져오기
        bar = getSupportActionBar();

        //backlogo가져오기
        bar.setLogo(R.drawable.back);
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        //액션바 클릭하면 클릭되게 해줌
        bar.setDisplayHomeAsUpEnabled(true);
        //액션바 타이틀 바꾸기
        bar.setTitle("글쓰기");
        //액션바 색바꾸기
        bar.setBackgroundDrawable(new ColorDrawable(0xffff6666));


        //각 버튼이나 화면 눌렀을 경우 menuslide내려가게 하기
        menuListClose();


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


    //메뉴리스트 닫기
    private void menuListClose() {
        scrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlidingDrawer drawer = findViewById(R.id.slide);
                drawer.close();


            }//onClick()
        });//setOnClickListener()

        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlidingDrawer drawer = findViewById(R.id.slide);
                drawer.close();
            }//onClick()
        });//setOnClickListener()

        board_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlidingDrawer drawer = findViewById(R.id.slide);
                drawer.close();
            }//onClick()
        });//setOnClickListener()

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlidingDrawer drawer = findViewById(R.id.slide);
                drawer.close();

                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }//onClick()
        });//setOnClickListener()

        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlidingDrawer drawer = findViewById(R.id.slide);
                drawer.close();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }//onClick()
        });//setOnClickListener()

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.close();
            }
        });
    }//menuListClose()



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

}//class
