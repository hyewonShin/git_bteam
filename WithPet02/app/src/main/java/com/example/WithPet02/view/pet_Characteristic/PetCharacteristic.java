package com.example.WithPet02.view.pet_Characteristic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.WithPet02.R;
import com.google.android.material.tabs.TabLayout;

public class PetCharacteristic extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Toolbar toolbar;
    TabLayout tabs;
    CharacteristicFragmet1 fragment1;
    CharacteristicFragmet2 fragment2;
    CharacteristicFragmet3 fragment3;

    Fragment selected = null;

    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_characteristic);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setDisplayShowTitleEnabled(false);  // 타이틀 안보이게 하기


        fragment1 = new CharacteristicFragmet1();
        fragment2 = new CharacteristicFragmet2();
        fragment3 = new CharacteristicFragmet3();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment1).commit();

        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("강아지"));
        tabs.addTab(tabs.newTab().setText("고양이"));
        tabs.addTab(tabs.newTab().setText("토끼"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d(TAG, "선택된 탭 : " + position);

                if (position == 0) {
                    selected = fragment1;
                } else if (position == 1) {
                    selected = fragment2;
                } else if (position == 2) {
                    selected = fragment3;
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

    public void fragBtnClick(Bundle bundle) {
        this.mBundle = bundle;


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
