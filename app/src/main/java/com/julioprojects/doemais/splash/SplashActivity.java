package com.julioprojects.doemais.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.julioprojects.doemais.Login.Login;
import com.julioprojects.doemais.R;
import com.julioprojects.doemais.databinding.ActivitySplashBinding;
import com.julioprojects.doemais.view.Home;

public class SplashActivity extends AppCompatActivity {

    // Declarative binding
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Settings binding
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Set Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Top animation
        Animation animationTop = AnimationUtils.loadAnimation(this, R.anim.top_wave);

        // Start animationTop
        binding.ivTop.setAnimation(animationTop);

        // Object animation init
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(binding.ivLogo,
                PropertyValuesHolder.ofFloat("scaleX", 1.1f),
                PropertyValuesHolder.ofFloat("scaleY", 1.1f));

        // Set duration
        objectAnimator.setDuration(500);

        // Repeat
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);

        // Repeat mode
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);

        // Start animator
        objectAnimator.start();

        // Bottom Animation
        Animation animationBottom = AnimationUtils.loadAnimation(this, R.anim.bottom_wave);

        binding.ivBottom.setAnimation(animationBottom);

        // Handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Redirect to main activity
                startActivity(new Intent(SplashActivity.this, Login.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                // Finish activity
                finish();
            }
        }, 4000);


    }
}