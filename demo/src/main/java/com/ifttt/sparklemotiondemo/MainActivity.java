package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.alpha_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AlphaViewPagerActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.scale_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ScaleViewPagerActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.rotation_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RotationViewPagerActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.parallax_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ParallaxViewPagerActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.zoom_out_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ZoomOutViewPagerActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.path_and_translation_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SparkleDemoActivity.class);
                startActivity(i);
            }
        });
    }
}
