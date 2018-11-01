package com.hisense.hiask.widget.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.hisense.hiask.hiask.R;
import com.hisense.hitools.utils.LogUtils;

import java.util.Hashtable;

/**
 * Created by liudunjian on 2018/5/4.
 */

public class LinePageIndicator extends View implements ViewPager.OnPageChangeListener {

    private float lineWidth;
    private float gapWidth;
    private float strokeWidth;
    private int selectedColor;
    private int unSelectedColor;

    private int currentPos = 0;
    private ViewPager viewPager;

    private Paint selectedPaint;
    private Paint unSelectedPaint;

    public LinePageIndicator(Context context) {
        this(context, null);
    }

    public LinePageIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinePageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLinePageIndicator(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.viewPager == null || this.viewPager.getAdapter().getCount() == 0)
            return;
        final int count = this.viewPager.getAdapter().getCount();
        if (currentPos >= count)
            return;

        final float lineWidthAndGap = lineWidth + gapWidth;
        final float paddingTop = getPaddingTop();

        float verticalOffset = paddingTop + ((getHeight() - paddingTop - getPaddingBottom()) / 2.0f);
        float horizontalOffset = getWidth() - lineWidthAndGap * count - getPaddingRight() + gapWidth;
        for (int i = 0; i < count; i++) {
            float dx1 = horizontalOffset + (i * lineWidthAndGap);
            float dx2 = dx1 + lineWidth;
            canvas.drawLine(dx1, verticalOffset, dx2, verticalOffset, (i == currentPos) ? selectedPaint : unSelectedPaint);
        }
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        if (this.viewPager == viewPager)
            return;
        if (this.viewPager != null) {
            //Clear us from the old pager.
            viewPager.clearOnPageChangeListeners();
        }
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        this.viewPager = viewPager;
        this.viewPager.addOnPageChangeListener(this);
        this.invalidate();
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
        invalidate();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPos = position;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private void initLinePageIndicator(Context context, AttributeSet attrs) {
        if (isInEditMode())
            return;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LinePageIndicator);
        try {
            lineWidth = ta.getDimension(R.styleable.LinePageIndicator_line_page_indicator_width, -1);
            gapWidth = ta.getDimension(R.styleable.LinePageIndicator_line_page_indicator_gap_width, -1);
            strokeWidth = ta.getDimension(R.styleable.LinePageIndicator_line_page_indicator_stroke_width, -1);
            selectedColor = ta.getColor(R.styleable.LinePageIndicator_line_page_indicator_selected_color, Color.TRANSPARENT);
            unSelectedColor = ta.getColor(R.styleable.LinePageIndicator_line_page_indicator_unselected_color, Color.TRANSPARENT);

        } finally {
            ta.recycle();
        }
        selectedPaint = selectedPaint();
        unSelectedPaint = unSelectedPaint();
    }

    private Paint selectedPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(selectedColor);
        return paint;
    }

    private Paint unSelectedPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(unSelectedColor);
        return paint;
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        float result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY) || (viewPager == null)) {
            //We were told how big to be
            result = specSize;
        } else {
            //Calculate the width according the views count
            final int count = viewPager.getAdapter().getCount();
            result = getPaddingLeft() + getPaddingRight() + (count * lineWidth) + ((count - 1) * gapWidth);
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return (int) Math.ceil(result);
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        float result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            //We were told how big to be
            result = specSize;
        } else {
            //Measure the height
            result = selectedPaint.getStrokeWidth() + getPaddingTop() + getPaddingBottom();
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return (int) Math.ceil(result);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        LogUtils.d("onSaveInstanceState() --------------------");
        Parcelable superState = super.onSaveInstanceState();
        SaveState saveState = new SaveState(superState);
        saveState.currentState = currentPos;
        return saveState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        LogUtils.d("onRestoreInstanceState --------------------");
        SaveState saveState = (SaveState) state;
        super.onRestoreInstanceState(saveState.getSuperState());
        currentPos = saveState.currentState;
        requestLayout();
    }

    static class SaveState extends BaseSavedState {

        int currentState;

        public SaveState(Parcel source) {
            super(source);
            currentState = source.readInt();
        }

        public SaveState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(currentState);
        }

        public static final Parcelable.Creator<SaveState> CREATOR = new Parcelable.Creator<SaveState>() {

            @Override
            public SaveState createFromParcel(Parcel source) {
                return new SaveState(source);
            }

            @Override
            public SaveState[] newArray(int size) {
                return new SaveState[size];
            }
        };
    }
}
