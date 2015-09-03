package com.ifttt.sparklemotiondemo;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

public final class ScreenConfig {

    private ScreenConfig() {
        throw new AssertionError("No instance");
    }

    /**
     * Get the window's size, that will exclude decorations (status bar for example) size.
     *
     * @param context Context object.
     * @return [width, height] for the window.
     */
    public static int[] getWindowSize(Context context) {
        int screenWidth, screenHeight;
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Point point = new Point();
            display.getSize(point);
            screenWidth = point.x;
            screenHeight = point.y;
        } else {
            screenWidth = display.getWidth();
            screenHeight = display.getHeight();
        }

        return new int[]{screenWidth, screenHeight};
    }
}
