package com.neatflix.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import com.neatflix.R;
import com.neatflix.app.BaseActivity;
import com.neatflix.databinding.ActivitySplashBinding;
import com.neatflix.utils.StatusBarUtils;

public class SplashActivity extends BaseActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash);
        StatusBarUtils.statusBarColor(this,R.color.dark_purple);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.shrink);
        binding.nameImg.setAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        },2000);
    }
}