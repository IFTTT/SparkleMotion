package com.ifttt.sparklemotiondemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public final class PaperPlaneView extends View {
    public PaperPlaneView(Context context) {
        super(context);
    }

    public PaperPlaneView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaperPlaneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaperPlaneView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
