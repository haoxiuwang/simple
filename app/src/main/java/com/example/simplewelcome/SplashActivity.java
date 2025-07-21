package com.example.simplewelcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageView;
    private int[] frames = {
        R.drawable.frame1,
        R.drawable.frame2,
        R.drawable.frame3,
        R.drawable.frame4,
        R.drawable.frame5
    };
    private int frameIndex = 0;
    private Handler handler = new Handler();

    private Runnable frameRunnable = new Runnable() {
        @Override
        public void run() {
            if (frameIndex < frames.length) {
                imageView.setImageResource(frames[frameIndex]);
                frameIndex++;
                handler.postDelayed(this, 300); // 每帧 300ms
            } else {
                // 动画完成，进入主界面
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setContentView(imageView);

        handler.post(frameRunnable);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(frameRunnable);
        super.onDestroy();
    }
}
