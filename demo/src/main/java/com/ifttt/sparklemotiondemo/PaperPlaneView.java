package com.ifttt.sparklemotiondemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import com.larvalabs.svgandroid.SVGParser;

/**
 *
 */
public final class PaperPlaneView extends View {

    private Path mPath;
    private final Path mSegmentPath = new Path();
    private final float[] mPlaneCoordinate = new float[2];
    private final float[] mPlaneAngle = new float[2];
    private final Matrix mMatrix = new Matrix();
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float mLength;
    private Drawable mPlaneDrawable;

    private final Object mAnimationLock = new Object();

    public PaperPlaneView(Context context) {
        super(context);
        initPaint();
    }

    public PaperPlaneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PaperPlaneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaperPlaneView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(20);
        mPaint.setPathEffect(new DashPathEffect(new float[] { 50f, 50f }, 1f));

        mPlaneDrawable = ContextCompat.getDrawable(getContext(), R.drawable.plane);
        mPath = SVGParser.parsePath("M944.293274,987.60553c0,0 32.20105,-161.474915\n"
                + "-145.138733,-329.695129c-177.339966,-168.220245 -603.123993,-630.910349\n"
                + "-22.524353,-630.910349c537.923035,0 179.286804,506.571665 -682.630203,514.795664");
    }

    @Override
    protected void onSizeChanged(final int w, final int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        Matrix matrix = new Matrix();
        RectF rectF = new RectF();
        mPath.computeBounds(rectF, false);
        RectF largeRectF = new RectF(0, 0, w, h);
        matrix.setRectToRect(rectF, largeRectF, Matrix.ScaleToFit.CENTER);
        matrix.postTranslate(0, h / 5f);
        mPath.transform(matrix);

        mPathMeasure = new PathMeasure(mPath, false);
        mLength = mPathMeasure.getLength();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        synchronized (mAnimationLock) {
            canvas.drawPath(mSegmentPath, mPaint);

            float angle = (float) (Math.atan2(mPlaneAngle[1], mPlaneAngle[0]) * 180.0f / Math.PI);
            Bitmap planeBitmap = ((BitmapDrawable) mPlaneDrawable).getBitmap();

            mMatrix.reset();
            mMatrix.postRotate(180f + angle, planeBitmap.getWidth() / 2, planeBitmap.getHeight() / 2);
            mMatrix.postTranslate(mPlaneCoordinate[0] - planeBitmap.getWidth() / 2,
                    mPlaneCoordinate[1] - planeBitmap.getHeight() / 2);

            canvas.drawBitmap(planeBitmap, mMatrix, null);
            canvas.restore();
        }
    }

    public void animate(float progress) {
        if (mPathMeasure == null) {
            return;
        }

        synchronized (mAnimationLock) {
            int stopD = (int) (mLength * progress);
            mSegmentPath.reset();
            mPathMeasure.getSegment(0, stopD, mSegmentPath, true);
            mPathMeasure.getPosTan(stopD, mPlaneCoordinate, mPlaneAngle);
            invalidate();
        }
    }
}
