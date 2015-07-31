package com.ifttt.jazzhandsdemo.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ScreenConfig {

    private ScreenConfig() {
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

    public static int getWindowWidth(Context context) {
        return getWindowSize(context)[0];
    }

    public static int getWindowHeight(Context context) {
        return getWindowSize(context)[1];
    }

    /**
     * Gets the screen's dimensions in its current configuration.
     *
     * @return [width, height]
     */
    public static int[] getScreenDimensions(Context context) {
        int screenWidth, screenHeight;
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Point point = new Point();
            display.getRealSize(point);
            screenWidth = point.x;
            screenHeight = point.y;
        } else {
            // For ICS.
            Method mGetRawH;
            try {
                mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                screenWidth = (Integer) mGetRawW.invoke(display);
                screenHeight = (Integer) mGetRawH.invoke(display);
            } catch (NoSuchMethodException
                    | InvocationTargetException
                    | IllegalAccessException e) {
                e.printStackTrace();
                //Backup plan if ICS devices failed to get the width and height for SDK 14,15,16 using the
                // hidden method above.
                screenWidth = display.getWidth();
                screenHeight = display.getHeight();
            }
        }
        return new int[]{screenWidth, screenHeight};
    }

    public static int getScreenHeight(Context context) {
        return getScreenDimensions(context)[1];
    }

    public static int getScreenWidth(Context context) {
        return getScreenDimensions(context)[0];
    }
}
