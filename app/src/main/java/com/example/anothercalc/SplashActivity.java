package com.example.anothercalc;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private ImageView view;
    private TextView view2;
    private Context splashActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        splashActivity = this.getApplicationContext();
        view = findViewById(R.id.imageView2);
        view2 = findViewById(R.id.textView3);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(splashActivity.getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
        handleAnimation(view,view2);
    }
    public void handleAnimation(View view, View view2){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "y", 420f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view2, "y", -100f);
        animator.setDuration(SPLASH_TIME_OUT);
        animator2.setDuration(SPLASH_TIME_OUT);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, animator2);
        animatorSet.start();
    }

}