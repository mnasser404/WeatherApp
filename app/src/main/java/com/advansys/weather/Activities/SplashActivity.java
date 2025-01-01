package com.advansys.weather.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.advansys.weather.R;
import com.advansys.weather.Utils;

public class SplashActivity extends AppCompatActivity {


    private static final int TIME_ANIMATION_DURATION = 3000;//millis
    private static final int TIME_ANIMATION_START = 500;//millis
    private ImageView SplashImageView;
    private LinearLayout splash_linear;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        setupViews();
    }

    private void setupViews() {
        SplashImageView = (ImageView) findViewById(R.id.splash_image);
        splash_linear = (LinearLayout) findViewById(R.id.splash_linear);
        textView = (TextView)findViewById(R.id.text);
        textView.setTypeface(Utils.get_toolbar_TypeFace(this));
        animateFadeIN(splash_linear);
        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(TIME_ANIMATION_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    startActivity(new Intent(SplashActivity.this, MapsActivity.class));
                    finish();
                }
            }
        };
        timer.start();
    }


    private void animateFadeIN(LinearLayout imgv) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(
                new DecelerateInterpolator());
        fadeIn.setStartOffset(TIME_ANIMATION_START);
        fadeIn.setDuration(TIME_ANIMATION_DURATION);
        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        imgv.setAnimation(animation);
    }


}
