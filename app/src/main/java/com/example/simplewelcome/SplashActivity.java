package dev.simplesolution.one;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.view.ViewGroup;

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
                handler.postDelayed(this, 1000); // 每帧 300ms
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
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setBackgroundColor(Color.BLACK);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        setContentView(imageView);

        handler.post(frameRunnable);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(frameRunnable);
        super.onDestroy();
    }
}
