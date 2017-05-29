package orwir.widget.progress;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;


public class AlphaProgressBar extends View {

    private static final int DEFAULT_ANIMATION_DURATION_MS = 180;
    private static final int DEFAULT_IMAGE_COUNT = 4;
    private static final Timer timer = new Timer(true);

    private int colorSegment;
    private ColorDrawable[] images;
    private int animationDuration = DEFAULT_ANIMATION_DURATION_MS;
    private int counter;
    private TimerTask task;

    public AlphaProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public AlphaProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AlphaProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AlphaProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    public void hide() {
        setVisibility(View.GONE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            task = new TimerTask() {
                @Override
                public void run() {
                    int pos = counter++ % images.length;
                    for (int i = images.length - 1; i >= 0; i--, pos++) {
                        images[i].setAlpha((pos % images.length) * colorSegment + colorSegment);
                    }
                    post(() -> invalidate());
                }
            };
            timer.scheduleAtFixedRate(task, 0, animationDuration);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (task != null) {
            task.cancel();
            timer.purge();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float maxSize = Math.min(getWidth() / images.length, getHeight() / images.length);
        float offset = .25f * maxSize;
        int size = Math.round(maxSize - offset);
        int hs = size / 2;
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int start = Math.round(cx - size * (images.length / 2) - offset * (images.length / 2 - 1));
        if (images.length % 2 == 0) {
            start += offset;
        } else {
            start -= offset;
        }
        for (int i = 0; i < images.length; i++) {
            int x = Math.round(start + i * size + i * offset);
            images[i].setBounds(x - hs, cy - hs, x + hs, cy + hs);
            images[i].draw(canvas);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        int color = Color.BLACK;
        int imageCount = DEFAULT_IMAGE_COUNT;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlphaProgress);
            try {
                color = a.getColor(R.styleable.AlphaProgress_color, Color.BLACK);
                imageCount = a.getInt(R.styleable.AlphaProgress_count, DEFAULT_IMAGE_COUNT);
                animationDuration = a.getInt(R.styleable.AlphaProgress_duration, DEFAULT_ANIMATION_DURATION_MS);
            } finally {
                a.recycle();
            }
        }
        colorSegment = 255 / imageCount;
        images = new ColorDrawable[imageCount];
        for (int i = 0; i < imageCount; i++) {
            images[i] = new ColorDrawable(color);
            images[i].setAlpha(i * colorSegment + colorSegment);
        }
    }

}
