package com.ifttt.sparklemotiondemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.larvalabs.svgandroid.SVGParser;

/**
 * Simple View that animates a Bitmap (a paper plane) along a dash path based on a given progress using
 * {@link #animate(float)}. The View uses a SVG path to build {@link Path}, and use its segments to animate
 * the dash path.
 */
public final class PaperPlaneView extends View {

    /**
     * SVG path string for building the {@link Path}.
     */
    private static final String PATH = "M944.293274,987.60553c0,0 32.20105,-161.474915\n"
            + "-145.138733,-329.695129c-177.339966,-168.220245 -603.123993,-630.910349\n"
            + "-22.524353,-630.910349c537.923035,0 179.286804,506.571665 -682.630203,514.795664";

    /**
     * Scale factor that adjusts the size of the path.
     */
    private static final float SCALE_FACTOR = 1.6f;

    private Path mPath;
    private final Path mSegmentPath = new Path();
    private final float[] mPlaneCoordinate = new float[2];
    private final float[] mPlaneAngle = new float[2];
    private final Matrix mMatrix = new Matrix();
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float mLength;
    private Bitmap mPlaneBitmap;
    private float mProgress;

    public PaperPlaneView(Context context) {
        super(context);
        init();
    }

    public PaperPlaneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaperPlaneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaperPlaneView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init() {
        // Setup the paint.
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(10);
        mPaint.setPathEffect(new DashPathEffect(new float[] { 50f, 50f }, 100f));

        // Setup the bitmap and path.
        mPlaneBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.plane);

        // Make sure the plane is not shown before the animation.
        mPlaneCoordinate[0] = -mPlaneBitmap.getWidth();
        mPlaneCoordinate[1] = -mPlaneBitmap.getHeight();

        mPath = SVGParser.parsePath(PATH);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable state = super.onSaveInstanceState();
        SavedState ss = new SavedState(state);
        ss.progress = mProgress;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        mProgress = ss.progress;
        animate(mProgress);
    }

    @Override
    protected void onSizeChanged(final int w, final int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Scale and translate the SVG path.
        Matrix matrix = new Matrix();
        RectF rectF = new RectF();
        mPath.computeBounds(rectF, false);
        RectF largeRectF = new RectF(0, 0, w * SCALE_FACTOR, h * SCALE_FACTOR);
        matrix.setRectToRect(rectF, largeRectF, Matrix.ScaleToFit.CENTER);

        float pathTranslationY = getResources().getDimension(R.dimen.path_translation_y);
        float pathTranslationX = getResources().getDimension(R.dimen.path_translation_x);
        matrix.postTranslate(pathTranslationX, pathTranslationY);
        mPath.transform(matrix);

        mPathMeasure = new PathMeasure(mPath, false);
        mLength = mPathMeasure.getLength();

        if (mProgress != 0) {
            // Animate the restored frame.
            animate(mProgress);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw path.
        canvas.drawPath(mSegmentPath, mPaint);

        // Draw the paper plane bitmap.
        float angle = (float) (Math.atan2(mPlaneAngle[1], mPlaneAngle[0]) * 180.0f / Math.PI);
        float bitmapCenterX = mPlaneBitmap.getWidth() / 2;
        float bitmapCenterY = mPlaneBitmap.getHeight() / 2;
        mMatrix.reset();
        mMatrix.postRotate(180f + angle, bitmapCenterX, bitmapCenterY);
        mMatrix.postTranslate(mPlaneCoordinate[0] - bitmapCenterX,
                mPlaneCoordinate[1] - bitmapCenterY);

        canvas.drawBitmap(mPlaneBitmap, mMatrix, null);
    }

    /**
     * Update path segment and the position/angle of the paper plane based on the progress given.
     *
     * @param progress Progression of the animation.
     */
    public void animate(float progress) {
        if (mPathMeasure == null) {
            return;
        }

        mProgress = progress;
        int stopD = (int) (mLength * mProgress);
        mSegmentPath.reset();
        mPathMeasure.getSegment(0, stopD, mSegmentPath, true);
        mSegmentPath.rLineTo(0.0f, 0.0f);
        mPathMeasure.getPosTan(stopD, mPlaneCoordinate, mPlaneAngle);
        invalidate();
    }

    /**
     * Subclass of {@link android.view.View.BaseSavedState} to store the progress of the animation, so that when the
     * View's View hierarchy state is restored, the progress is also restored.
     */
    private static class SavedState extends BaseSavedState {

        float progress;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            progress = source.readFloat();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(progress);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
