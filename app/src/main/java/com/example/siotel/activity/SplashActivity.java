package com.example.siotel.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.airbnb.lottie.LottieAnimationView;
import com.example.siotel.R;
import com.example.siotel.SharedPrefManager;
import com.example.siotel.models.Token;

public class SplashActivity extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_splash);

        lottieAnimationView=findViewById(R.id.animation_view);

        lottieAnimationView.addAnimatorUpdateListener((animation) -> {
            // Do something.
        });
        lottieAnimationView.playAnimation();

        if (lottieAnimationView.isAnimating()) {
            // Do something.
        }

        // Custom animation speed or duration.
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(animation -> {lottieAnimationView.setProgress((Float) animation.getAnimatedValue());});
        animator.start();



        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over

                finish();
                SharedPrefManager sharedPrefManager=new SharedPrefManager(getApplicationContext());
                boolean ans=sharedPrefManager.isLoggedIn();
                if(ans)
                {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 5000);
    }
}