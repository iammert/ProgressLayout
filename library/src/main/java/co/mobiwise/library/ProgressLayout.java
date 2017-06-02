/*
* Copyright (C) 2015 Mert Şimşek
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package co.mobiwise.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

public class ProgressLayout extends View implements Animatable {

    private static final int COLOR_EMPTY_DEFAULT = 0x00000000;
    private static final int COLOR_PROGRESS_DEFAULT = 0xFFFFFFFF;
    private static final float PROGRESS_ALPHA_COLOR_DEFAULT = 1.0f;
    private static final float EMPTY_ALPHA_COLOR_DEFAULT = 1.0f;
    private static final int PROGRESS_SECOND_MS = 1000;

    private static Paint paintProgress;
    private static Paint paintEmpty;

    private boolean isPlaying = false;
    private boolean isAutoProgress;

    private int mHeight;
    private int mWidth;
    private int maxProgress;
    private int currentProgress = 0;

    private Handler handlerProgress;

    private ProgressLayoutListener progressLayoutListener;

    public ProgressLayout(Context context) {
        this(context, null);
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * Indicate whether this progress layout is auto progressing. This is the same as {@link #isPlaying()}
     * @return true if it is auto progressing, false otherwise.
     */
    @Override
    public boolean isRunning() {
        return isPlaying;
    }

    @Override
    public void start() {
        if (isAutoProgress) {
            isPlaying = true;
            handlerProgress.removeCallbacksAndMessages(null);
            handlerProgress.postDelayed(mRunnableProgress, 0);
        }
    }

    @Override
    public void stop() {
        isPlaying = false;
        handlerProgress.removeCallbacks(mRunnableProgress);
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, mWidth, mHeight, paintEmpty);
        canvas.drawRect(0, 0, calculatePositionIndex(currentProgress), mHeight, paintProgress);
    }

    private void init(Context context, AttributeSet attrs) {
        setWillNotDraw(false);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.progressLayout);
        isAutoProgress = a.getBoolean(R.styleable.progressLayout_autoProgress, true);
        maxProgress = a.getInt(R.styleable.progressLayout_maxProgress, 0);
        maxProgress = maxProgress * 10;
        float progressAlphaRatio = a.getFloat(R.styleable.progressLayout_progressAlphaRatio,
                PROGRESS_ALPHA_COLOR_DEFAULT);
        float emptyAlphaRatio = a.getFloat(R.styleable.progressLayout_emptyAlphaRatio, EMPTY_ALPHA_COLOR_DEFAULT);
        int progressColor = a.getColor(R.styleable.progressLayout_progressColor, COLOR_PROGRESS_DEFAULT);
        int emptyColor = a.getColor(R.styleable.progressLayout_emptyColor, COLOR_EMPTY_DEFAULT);
        a.recycle();

        paintEmpty = new Paint();
        paintEmpty.setColor(Util.getColorWithAlpha(emptyColor, emptyAlphaRatio));
        paintEmpty.setStyle(Paint.Style.FILL);
        paintEmpty.setAntiAlias(true);

        paintProgress = new Paint();
        paintProgress.setColor(Util.getColorWithAlpha(progressColor, progressAlphaRatio));
        paintProgress.setStyle(Paint.Style.FILL);
        paintProgress.setAntiAlias(true);

        handlerProgress = new Handler();
    }

    private int calculatePositionIndex(int currentProgress) {
        return (currentProgress * mWidth) / maxProgress;
    }

    /**
     * Indicate whether this progress layout is auto progressing. This is the same as {@link #isRunning()}.
     * This will return false if {@link #setAutoProgress(boolean)} has not been set
     * @return true if it is auto progressing, false otherwise.
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Stop this progress layout from auto progressing. Note that this also sets the progress to 0.
     * Calling {@link #isPlaying()} or {@link #isRunning()} after this method will both return false.
     */
    public void cancel() {
        isPlaying = false;
        currentProgress = 0;
        handlerProgress.removeCallbacks(mRunnableProgress);
        postInvalidate();
    }

    /**
     * Sets the current progress to the specified color
     * @param currentProgress the new progress value. It shouldn't be more than the max progress
     */
    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress * 10;
        postInvalidate();
        if (progressLayoutListener != null) {
            progressLayoutListener.onProgressChanged(currentProgress / 10);

            if (maxProgress == this.currentProgress) {
                progressLayoutListener.onProgressCompleted();
            }
        }
    }

    /**
     * The highest progress that this progress layout can attain
     * @param maxProgress the max value. It should be between {@link Integer#MIN_VALUE}  and {@link Integer#MAX_VALUE}
     */
    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress * 10;
        postInvalidate();
    }

    /**
     * Whether this progress layout should auto increase its progress every 100ms
     * @param isAutoProgress true if it should auto progress, false otherwise
     */
    public void setAutoProgress(boolean isAutoProgress) {
        this.isAutoProgress = isAutoProgress;
    }

    /**
     * Sets a listener to get notified when the progress changes or is complete.
     * @param progressLayoutListener the progressLayoutListener
     */
    public void setProgressLayoutListener(ProgressLayoutListener progressLayoutListener) {
        this.progressLayoutListener = progressLayoutListener;
    }

    private final Runnable mRunnableProgress = new Runnable() {
        @Override
        public void run() {
            if (isPlaying) {
                if (currentProgress == maxProgress) {
                    if (progressLayoutListener != null) {
                        progressLayoutListener.onProgressCompleted();
                    }
                    currentProgress = 0;
                    setCurrentProgress(currentProgress);
                    stop();
                } else {
                    postInvalidate();
                    currentProgress += 1;
                    if (progressLayoutListener != null) {
                        progressLayoutListener.onProgressChanged(currentProgress / 10);
                    }
                    handlerProgress.postDelayed(mRunnableProgress, PROGRESS_SECOND_MS / 10);
                }
            }
        }
    };

    /**
     * Sets the color to be used as the background of the progress layout
     * @param emptyColor the empty color
     * @param alphaRatio alpha ratio to apply to the color. 0.0 for full transparency and 1.0 for no tranparency
     */
    public void setColorEmpty(@ColorInt int emptyColor, float alphaRatio) {
        paintEmpty.setColor(Util.getColorWithAlpha(emptyColor, alphaRatio));
        postInvalidate();
    }

    /**
     * Sets the color to used as the progress color of the progress layout
     * @param progressColor the empty color
     * @param alphaRatio alpha ratio to apply to the color. 0.0 for full transparency and 1.0 for no tranparency
     */
    public void setProgressColor(@ColorInt int progressColor, float alphaRatio) {
        paintProgress.setColor(Util.getColorWithAlpha(progressColor, alphaRatio));
        postInvalidate();
    }

    /**
     * Get the current color being used as background color
     * @return the background color
     */
    public int getColorEmpty() {
        return paintEmpty.getColor();
    }

    /**
     * Get the current color being used as progress color
     * @return the progress color
     */
    public int getProgressColor() {
        return paintProgress.getColor();
    }

    /**
     * Return the max value the progress layout can attain
     * @return the max progress value
     */
    public int getMaxProgress() {
        return maxProgress;
    }

    /**
     * Gets the current progress. Except you're manually setting the progress yourself,
     * this method is not needed. Rather you should use
     * {@link #setProgressLayoutListener(ProgressLayoutListener)}
     * @return the current progress
     */
    public int getCurrentProgress() {
        return currentProgress;
    }

}
