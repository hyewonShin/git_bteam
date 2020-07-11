package com.example.WithPet02.view;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.viewpager.widget.ViewPager;


import com.example.WithPet02.MainView.ad.MainAd1;
import com.example.WithPet02.MainView.ad.MainAd2;
import com.example.WithPet02.MainView.ad.MainAd3;

import java.util.ArrayList;

public class MainAdSlide  {
    public void Fts (MyPagerAdapter adapter, ViewPager pager) {

        MainAd1 mainAd1 = new MainAd1();
        MainAd2 mainAd2 = new MainAd2();
        MainAd3 mainAd3 = new MainAd3();

        //adapter에 fragment를 넣어줌
        adapter.addItem(mainAd1);
        adapter.addItem(mainAd2);
        adapter.addItem(mainAd3);

        //pager에 어뎁터를 실행할 수 있게 해줌
        pager.setAdapter(adapter);

        //버튼1 위치 찾기와 이벤트 설정
        //button1 = findViewById(R.id.button1);

       /* button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //페이져 설정해준 것중에 1번으로 설정
                pager.setCurrentItem(1);
            }//onClick()
        });//setOnClickListener()*/

    }

    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        //Fragment의 것들을 배열에 넣어 주기(Fragment에는 1, 2, 3의 것들을 넣어줬음
        ArrayList<Fragment> items = new ArrayList<>();

        //생성자
        public MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        //들어오는 항목을 추가시켜주는 메소드
        public void addItem(Fragment item) {
            items.add(item);
        }


        //선택한것의 위치를 반환해주는 것
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }//getItem()

        @Override
        public int getCount() { //ArrayList에 몇 개 있는지 알려주는 것
            return items.size();    //ArrayList이므로 size()를 사용
        }//getCount()

        //어떤 글자를 넘겨줄 것인가?
        public CharSequence getPageTitle(int position) {
            return "페이지" + (position + 1);
        }//getPageTitle()

    }
}//class
