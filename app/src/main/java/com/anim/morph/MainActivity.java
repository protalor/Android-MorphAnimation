package com.anim.morph;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.width_wrap_to_match).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!v.isSelected()) {
                    v.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                } else {
                    v.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                v.setSelected(!v.isSelected());
                v.requestLayout();

                MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE_WIDTH);
            }
        });


        findViewById(R.id.height_visible_to_gone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setVisibility(View.GONE);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setVisibility(View.VISIBLE);
                        MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE_HEIGHT);
                    }
                }, 1000);
                MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE_HEIGHT);
            }
        });


        findViewById(R.id.height_x2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!v.isSelected()) {
                    v.getLayoutParams().height = (int) convertDpToPixel(140, MainActivity.this);
                } else {
                    v.getLayoutParams().height = (int) convertDpToPixel(70, MainActivity.this);
                }
                v.setSelected(!v.isSelected());
                v.requestLayout();

                MorphAnimation.morph(v);
            }
        });


        findViewById(R.id.width_height_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setVisibility(View.GONE);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setVisibility(View.VISIBLE);
                        MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE);
                    }
                }, 1000);
                MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE);
            }
        });

        findViewById(R.id.width_height_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setVisibility(View.GONE);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setVisibility(View.VISIBLE);
                        MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE);
                    }
                }, 1000);
                MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE);
            }
        });

        findViewById(R.id.width_height_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setVisibility(View.GONE);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setVisibility(View.VISIBLE);
                        MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE);
                    }
                }, 1000);
                MorphAnimation.morph(v, MorphAnimation.ANIMATION_MODE);
            }
        });
    }



    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
}
