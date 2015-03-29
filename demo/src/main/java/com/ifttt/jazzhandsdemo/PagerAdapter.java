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

        switch(position) {
            case 0:
                return inflater.inflate(R.layout.sunrise_page, container, false);
            case 1:
                return inflater.inflate(R.layout.sunset_page, container, false);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}

