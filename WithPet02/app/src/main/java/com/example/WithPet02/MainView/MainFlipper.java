package com.example.WithPet02.MainView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.example.WithPet02.R;
import com.example.WithPet02.view.customerc_service.SiteCsActivity;
import com.example.WithPet02.view.pet_Characteristic.PetCharacteristic;

public class MainFlipper implements View.OnTouchListener {

    Context context;
    ViewFlipper v_flipper;

    float down_x, up_x;
    long interval;

    public MainFlipper(Context context, ViewFlipper v_flipper) {
        this.context = context;
        this.v_flipper = v_flipper;
    }

    public void setFlipper(){
        slide();
        v_flipper.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v != v_flipper) {
            return false;
        }
        v_flipper.stopFlipping();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 터치 시작지점 x좌표 저장
            down_x = event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // 터치 끝난 지점 X좌표 저장
            up_x = event.getX();
            if (up_x - down_x < -20) {
                // 터치할때 왼쪽 방향으로 진행
                showNext();
            } else if (up_x - down_x > 20) {
                // 터치할때 오른쪽 방향으로 진행
                showPrevious();
            } else {

                int position = v_flipper.getDisplayedChild();

                Intent intent;
                switch (position){
                    case 0 :
                        intent = new Intent(context, PetCharacteristic.class);
                        context.startActivity(intent);
                        break;

                    case 1 :
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.animal.go.kr/front/awtis/protection/protectionList.do?menuNo=1000000060"));
                        context.startActivity(intent);
                        break;

                    case 2 :
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/HlN2o3Pwt1o"));
                        context.startActivity(intent);
                        break;

                    case 3 :
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/AwrFPJk_BGU"));
                        context.startActivity(intent);
                        break;

                    case 4 :
                        intent = new Intent(context, SiteCsActivity.class);
                        context.startActivity(intent);
                        break;
                }
            }
        }
        return true;

    }

    private void slide(){
        v_flipper.setFlipInterval(4000);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                v_flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_in));
                v_flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_out));
                v_flipper.showNext();
                v_flipper.startFlipping();
            }
        }, 4000);
    }

    private void showNext() {
        v_flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_in));
        v_flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_out));
        v_flipper.showNext();
        if( System.currentTimeMillis() > interval + 4000){
            interval = System.currentTimeMillis();
            slide();
        }
    }

    private void showPrevious() {
        v_flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_out));
        v_flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_in));
        v_flipper.showPrevious();
        if( System.currentTimeMillis() > interval + 4000){
            interval = System.currentTimeMillis();
            slide();
        }
    }

}
