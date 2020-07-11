package com.example.WithPet02.MainView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.WithPet02.MainView.main_login.MainLogIn1;
import com.example.WithPet02.MainView.main_login.MainLogIn2;
import com.example.WithPet02.MainView.main_login.MainLogIn3;
import com.example.WithPet02.MainView.main_login.MainNotLogIn;

import java.util.ArrayList;

import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

public class MainLogIn {
    public void Mli (MyPagerAdapter adapter, ViewPager pager){
        if(loginDTO != null) {
            MainLogIn1 mainLogIn1 = new MainLogIn1();
            MainLogIn2 mainLogIn2 = new MainLogIn2();
            MainLogIn3 mainLogIn3 = new MainLogIn3();

            //adapter에 fragment를 넣어줌
            adapter.addItem(mainLogIn1);
            adapter.addItem(mainLogIn2);
            adapter.addItem(mainLogIn3);



            //pager에 어뎁터를 실행할 수 있게 해줌
            pager.setAdapter(adapter);
        }else {
            MainNotLogIn mainNotLogIn = new MainNotLogIn();

            //adapter에 fragment를 넣어줌
            adapter.addItem(mainNotLogIn);

            //pager에 어뎁터를 실행할 수 있게 해줌
            pager.setAdapter(adapter);
        }
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

       /* //어떤 글자를 넘겨줄 것인가?
        public CharSequence getPageTitle(int position) {
            return "페이지" + (position + 1);
        }//getPageTitle()*/

    }
}
