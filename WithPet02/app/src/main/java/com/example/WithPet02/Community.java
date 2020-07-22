package com.example.WithPet02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.WithPet02.view.community.CommunityFragment1;
import com.example.WithPet02.view.community.CommunityFragment2;
import com.google.android.material.tabs.TabLayout;

public class Community extends AppCompatActivity {


    Toolbar toolbar;
    TabLayout c_tabs;
    CommunityFragment1 fragment1;
    CommunityFragment2 fragment2;

    Fragment selected = null;

    Bundle mBundle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);



        Toolbar c_toolbar = (Toolbar) findViewById(R.id.c_toolbar);
        setSupportActionBar(c_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setDisplayShowTitleEnabled(false);  // 타이틀 안보이게 하기


        fragment1 = new CommunityFragment1();
        fragment2 = new CommunityFragment2();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment1).commit();

        //c_tabs = findViewById(R.id.c_tabs);
        c_tabs.addTab(c_tabs.newTab().setText("자유게시판"));
        c_tabs.addTab(c_tabs.newTab().setText("1:1 문의"));


        final CommunityFragment1 finalFragment1 = fragment1;
        final CommunityFragment2 finalFragment2 = fragment2;
        c_tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();


                if (position == 0) {
                    selected = finalFragment1;
                } else if (position == 1) {
                    selected = finalFragment2;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, selected).commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
