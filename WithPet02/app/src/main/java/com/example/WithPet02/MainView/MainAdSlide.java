package com.example.WithPet02.MainView;



import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.viewpager.widget.ViewPager;


import com.example.WithPet02.MainView.ad.MainAd1;
import com.example.WithPet02.MainView.ad.MainAd2;
import com.example.WithPet02.MainView.ad.MainAd3;
import com.example.WithPet02.MainView.ad.MainAd4;
import com.example.WithPet02.MainView.ad.MainAd5;

import java.util.ArrayList;
import java.util.Timer;


public class MainAdSlide  {
    public void Fts (MyPagerAdapter adapter, ViewPager pager) {

        MainAd1 mainAd1 = new MainAd1();
        MainAd2 mainAd2 = new MainAd2();
        MainAd3 mainAd3 = new MainAd3();
        MainAd4 mainAd4 = new MainAd4();
        MainAd5 mainAd5 = new MainAd5();

        //adapter에 fragment를 넣어줌
        adapter.addItem(mainAd1);
        adapter.addItem(mainAd2);
        adapter.addItem(mainAd3);
        adapter.addItem(mainAd4);
        adapter.addItem(mainAd5);

        //pager에 어뎁터를 실행할 수 있게 해줌
        pager.setAdapter(adapter);


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
        public Fragment getItem(int position) { return items.get(position); }//getItem()

        @Override
        public int getCount() { //ArrayList에 몇 개 있는지 알려주는 것
            return items.size();    //ArrayList이므로 size()를 사용
        }//getCount()

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return super.isViewFromObject(view, object);
        }

    }
}//class
