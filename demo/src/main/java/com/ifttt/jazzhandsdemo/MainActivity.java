package com.ifttt.jazzhandsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.view_pager_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ViewPagerActivity.class);
                startActivity(i);
            }
        });

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

        findViewById(R.id.path_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PathViewPagerActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.story_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, StoryViewPagerActivity.class);
                startActivity(i);
            }
        });

        final View container = findViewById(R.id.testing_btn_container);
        final View btn = findViewById(R.id.testing_btn);

        container
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        container.animate().translationX(1000).setDuration(2000).start();
                        btn.animate().translationX(-1000).setDuration(2000).start();
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
