package com.android.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation anim,left_anim,right_anim;
    ImageView img;
    TextView app_name,tag_line;
    private static final int splash_time = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.splashImg);
        app_name = findViewById(R.id.AppName);
        tag_line = findViewById(R.id.TagLine);
        anim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        left_anim = AnimationUtils.loadAnimation(this,R.anim.left_anim);
        right_anim = AnimationUtils.loadAnimation(this,R.anim.right_anim);
        img.setAnimation(anim);
        app_name.setAnimation(left_anim);
        tag_line.setAnimation(right_anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(".Screens.MainScreen");
                startActivity(intent);
                finish();
            }
        }, splash_time);
    }

}
