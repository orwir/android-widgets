package orwir.widget.carousel;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

import orwir.widget.carousel.indicator.Indicator;
import orwir.widget.carousel.misc.FixedSpeedScroller;


public class CarouselView extends FrameLayout {

    protected DataSetObserver dataSetObserver;
    private ViewPager pager;
    private CarouselAdapter adapter;
    private Indicator indicator;
    private boolean tapped;

    public CarouselView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CarouselView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CarouselView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CarouselView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void setAdapter(CarouselAdapter adapter) {
        if (this.adapter != null && dataSetObserver != null) {
            this.adapter.unregisterDataSetObserver(dataSetObserver);
        }
        this.adapter = adapter;
        pager.setAdapter(adapter);
        onDataSetChanged();
        adapter.registerDataSetObserver(dataSetObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                onDataSetChanged();
            }
        });
    }

    public void setCurrentItem(int position) {
        checkAdapter();
        if (adapter.getRealCount() > 0) {
            indicator.setCurrent(position % adapter.getRealCount());
        }
        pager.setCurrentItem(position, true);
    }

    public void next() {
        checkAdapter();
        setCurrentItem(pager.getCurrentItem() == adapter.getCount() - 1 ? 0 : pager.getCurrentItem() + 1);
    }

    public void previous() {
        checkAdapter();
        setCurrentItem(pager.getCurrentItem() == 0 ? adapter.getCount() - 1 : pager.getCurrentItem() - 1);
    }

    @Override
    protected void onAttachedToWindow() {
        if (!isInEditMode()) {
            super.onAttachedToWindow();
        }
    }

    protected void init(@NonNull Context context, AttributeSet attrs) {
        int layoutRes = R.layout.default_carousel;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CarouselView);
            try {
                layoutRes = a.getResourceId(R.styleable.CarouselView_layout, layoutRes);
            } finally {
                a.recycle();
            }
        }
        LayoutInflater.from(context).inflate(layoutRes, this, true);
        pager = (ViewPager) findViewById(R.id.pager);
        indicator = (Indicator) findViewById(R.id.indicator);
        checkRequiredViews();

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                indicator.setCurrent(position % adapter.getRealCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        pager.setOnTouchListener((v, event) -> {
            int action = event.getAction() & MotionEventCompat.ACTION_MASK;
            tapped = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE;
            return false;
        });
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            scrollerField.set(pager, new FixedSpeedScroller(context, 1250));
        } catch (Exception ignored) {}

        invalidate();
    }

    private void onDataSetChanged() {
        indicator.setCount(adapter.getRealCount());
        if (adapter.getCount() > 0) {
            pager.setCurrentItem(0, true);
            indicator.setCurrent(0);
            pager.setCurrentItem(adapter.getRealCount() * (CarouselAdapter.LOOP_COUNT / 2), false);
        }
        if (adapter.getRealCount() <= 1) {
            indicator.setVisibility(GONE);
        }
    }

    private void checkAdapter() {
        if (adapter == null) {
            throw new IllegalStateException("Adapter not set");
        }
    }

    private void checkRequiredViews() {
        StringBuilder views = new StringBuilder();
        if (pager == null) {
            if (views.length() > 0) {
                views.append(", ");
            }
            views.append("pager");
        }
        if (indicator == null) {
            if (views.length() > 0) {
                views.append(", ");
            }
            views.append("indicator");
        }
        if (views.length() > 0) {
            throw new IllegalStateException("Required views not found: " + views.toString());
        }
    }

}
