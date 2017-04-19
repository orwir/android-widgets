package orwir.widget.adjust;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;

public class AdjustableTextView extends android.support.v7.widget.AppCompatTextView {

    private Paint testPaint = new Paint();

    public AdjustableTextView(Context context) {
        super(context);
        init(context, null);
    }

    public AdjustableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AdjustableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        if (!isInEditMode()) {
            super.onAttachedToWindow();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        fit(getText().toString(), MeasureSpec.getSize(widthMeasureSpec));
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        fit(text.toString(), getWidth());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw) {
            fit(getText().toString(), w);
        }
    }

    protected void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        setMaxLines(1);
        fit(getText().toString(), getWidth());
    }

    protected void fit(String text, float maxWidth) {
        maxWidth -= getPaddingStart() - getPaddingEnd();
        if (maxWidth <= 0 || text.isEmpty()) {
            return;
        }
        float hi = 100;
        float lo = 2;
        final float threshold = 0.5f; // How close we have to be
        float modifier = .5f;
        testPaint.set(getPaint());

        while ((hi - lo) > threshold) {
            float size = (hi + lo) * modifier;
            testPaint.setTextSize(size);
            if (testPaint.measureText(text) >= maxWidth) {
                hi = size; // too big
                modifier = .5f; //stretch
            } else {
                lo = size; // too small
                modifier = 2; //shrink
            }
        }
        // Use lo so that we undershoot rather than overshoot
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, lo);
    }

}
