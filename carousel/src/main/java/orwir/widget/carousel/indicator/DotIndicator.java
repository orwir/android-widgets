package orwir.widget.carousel.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import orwir.widget.carousel.R;

import static orwir.widget.carousel.misc.Utils.toPx;

public class DotIndicator extends Indicator {

    private static final int DEFAULT_RADIUS = 3;
    private static final int DEFAULT_OFFSET = 7;

    private float radius;
    private float radiusCurrent;
    private Paint currentPaint;
    private Paint indicatorPaint;

    public DotIndicator(Context context) {
        super(context);
    }

    public DotIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DotIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DotIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow() {
        if (!isInEditMode()) {
            super.onAttachedToWindow();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (count > 0) {
            float centerX = getWidth() / 2;
            float centerY = getHeight() / 2;
            float offset = toPx(getResources(), DEFAULT_OFFSET);
            float start = centerX - radiusCurrent - (radius * (count - 1)) - (offset * (count / 2 - 1));
            for (int i = 0; i < count; i++) {
                float radius = (i == current) ? radiusCurrent : this.radius;
                Paint paint = (i == current) ? currentPaint : indicatorPaint;
                float x = start + i * radius * 2;
                canvas.drawCircle(x + i * offset, centerY, radius, paint);
            }
        }
    }

    @Override
    protected void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        super.init(context, attrs);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DotIndicator);
            try {
                radius = a.getDimension(R.styleable.DotIndicator_radius, toPx(context.getResources(), DEFAULT_RADIUS));
                radiusCurrent = a.getDimension(R.styleable.DotIndicator_radiusCurrent, toPx(context.getResources(), DEFAULT_RADIUS));
                indicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                indicatorPaint.setStyle(Paint.Style.FILL);
                indicatorPaint.setColor(a.getColor(R.styleable.DotIndicator_color, Color.LTGRAY));
                currentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                currentPaint.setStyle(Paint.Style.FILL);
                currentPaint.setColor(a.getColor(R.styleable.DotIndicator_colorCurrent, Color.BLACK));
            } finally {
                a.recycle();
            }
        }

        setCount(3);
        setCurrent(1);
    }
}
