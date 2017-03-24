package orwir.widget.stretch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class StretchableTextView extends android.support.v7.widget.AppCompatTextView {


    public StretchableTextView(Context context) {
        super(context);
        init(context, null);
    }

    public StretchableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StretchableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        if (!isInEditMode()) {
            super.onAttachedToWindow();
        }
    }

    protected void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        setMaxLines(1);
    }

}
