package com.example.WithPet02;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class BoardList1 extends AppCompatActivity {
    //메뉴폴더의 아이템 사용하기
    MenuItem search_bar;
    MenuItem camera;
    //툴바사용하기
    Toolbar toolbar;
    //버튼 사용하기
    Button search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list1);

        //툴바위치 찾기
        toolbar = findViewById(R.id.toolbar);
        //툴바을 이 Activity에서 액션바처럼 쓸지 설정
        setSupportActionBar(toolbar);

        //툴바 타이틀 보일지 말지 설정
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //홈버튼 보일지 말지 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.album:
                        return true;
                    case R.id.logo:
                        return true;
                    case R.id.camera:

                        return true;
                }//switch
                return false;
            }//onNavigationItemSelected()
        });//setOnNavigationItemSelectedListener()

    }//onCreate()


    //OptionMenu부르기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //board_menu_option.xml등록
        getMenuInflater().inflate(R.menu.board_list1_action, menu);

        search_bar = menu.findItem(R.id.search_bar);

        //돋보기 클릭했을 경우 확장, 취소시 축소
        /*search_bar.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            //확장
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }//onMenuItemActionExpand()

            //축소
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return false;
            }//onMenuItemActionCollapse()
        });//setOnActionExpandListener()*/

        //menuItem을 이용해서 SearchView변수 설정
        SearchView searchView = (SearchView) search_bar.getActionView();
        //확인버튼 활성화
        searchView.setSubmitButtonEnabled(true);
        //searchView의 검색 이벤트
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //검색버튼 눌렀을 경우
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }//onQueryTextSubmit()

            //텍스트가 바뀔 때 마다 호출(결과 화면에서 검색식 : ??)
            @Override
            public boolean onQueryTextChange(String newText) {
                TextView searchResult = (TextView)findViewById(R.id.searchResult);
                searchResult.setText("검색식 : " + newText);
                return true;
            }//onQueryTextChange()
        });//setOnQueryTextListener()
        return true;
    }//onCreateOptionsMenu()


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();
        switch (curId){
            case android.R.id.home ://홈 메뉴 눌리면 꺼지도록 설정
                this.finish();
                break;
        }//switch

        return true;
    }//onOptionsItemSelected()



}//class















