package orwir.widget.carousel.indicator;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class Indicator extends View {

    protected int count;
    protected int current;

    public Indicator(Context context) {
        super(context);
        init(context, null);
    }

    public Indicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Indicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Indicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void setCount(int count) {
        this.count = count;
        invalidate();
    }

    public void setCurrent(int current) {
        this.current = current;
        invalidate();
    }

    protected void init(@NonNull Context context, @Nullable AttributeSet attrs) {

    }

}
