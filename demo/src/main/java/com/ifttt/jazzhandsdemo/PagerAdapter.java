package com.ifttt.jazzhandsdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhelu on 3/27/15.
 */
public class PagerAdapter extends ViewPagerAdapter {
    @Override
    protected View getView(int position, ViewGroup container) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());

        ViewGroup v;
        if (position == 0) {
            v = (ViewGroup) inflater.inflate(R.layout.page_0_layout, container, false);
        } else if (position == 1) {
            v = (ViewGroup) inflater.inflate(R.layout.page_1_layout, container, false);
        } else if (position == 2) {
            v = (ViewGroup) inflater.inflate(R.layout.page_2_layout, container, false);
        } else if (position == 3) {
            v = (ViewGroup) inflater.inflate(R.layout.page_3_layout, container, false);
        } else {
            v = (ViewGroup) inflater.inflate(R.layout.page_layout, container, false);
        }

        v.setTag(position);
        return v;
    }

    @Override
    public int getCount() {
        return 5;
    }
}

