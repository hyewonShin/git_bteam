package com.example.WithPet02.view.site;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.WithPet02.R;
import com.google.android.material.tabs.TabLayout;

public class SitePetCharActivity extends AppCompatActivity {

    TabLayout tabs;
    SitePetCharFragment1 fragment1;
    SitePetCharFragment2 fragment2;
    SitePetCharFragment3 fragment3;

    Fragment selected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_pet_char);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setDisplayShowTitleEnabled(false);  // 타이틀 안보이게 하기


        fragment1 = new SitePetCharFragment1();
        fragment2 = new SitePetCharFragment2();
        fragment3 = new SitePetCharFragment3();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment1).commit();

        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("강아지"));
        tabs.addTab(tabs.newTab().setText("고양이"));
        tabs.addTab(tabs.newTab().setText("토끼"));


        final SitePetCharFragment1 finalFragment = fragment1;
        final SitePetCharFragment2 finalFragment1 = fragment2;
        final SitePetCharFragment3 finalFragment2 = fragment3;
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();


                if (position == 0) {
                    selected = finalFragment;
                } else if (position == 1) {
                    selected = finalFragment1;
                } else if (position == 2) {
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