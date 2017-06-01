package orwir.widget.expandable.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;
import orwir.widget.expandable.R;
import orwir.widget.expandable.util.AnimUtils;

public class ExpandableLayout extends FrameLayout {

    public static Disposable singleExpanded(RxAppCompatActivity context, ViewGroup root, boolean nested) {
        List<ExpandableLayout> layouts = new ArrayList<>();
        searchExpandableLayouts(root, layouts, nested);
        return Observable.merge(transform(layouts, ExpandableLayout::expandedEvent))
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view -> {
                    for (ExpandableLayout layout : layouts) {
                        if (layout != view) {
                            layout.setState(false);
                        }
                    }
                });
    }

    private static void searchExpandableLayouts(ViewGroup root, List<ExpandableLayout> found, boolean nested) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View view = root.getChildAt(i);
            if (view instanceof ExpandableLayout) {
                found.add((ExpandableLayout) view);
            } else if (nested && view instanceof ViewGroup) {
                searchExpandableLayouts((ViewGroup) view, found, true);
            }
        }
    }

    private static <IN, OUT> List<OUT> transform(@NonNull Collection<IN> source, @NonNull Function<IN, OUT> transformer) {
        List<OUT> result = new ArrayList<>(source.size());
        for (IN val : source) {
            try {
                result.add(transformer.apply(val));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @NonNull protected ViewGroup header;
    @NonNull protected TextView title;
    @NonNull protected ImageView switcher;
    @NonNull protected Drawable iconExpand;
    @NonNull protected Drawable iconCollapse;
    @NonNull protected ViewGroup content;
    private boolean initialized;
    private final BehaviorSubject<Boolean> expandSubject = BehaviorSubject.create();

    public ExpandableLayout(@NonNull Context context) {
        super(context);
        initialize(context, null);
        checkInitialization();
    }

    public ExpandableLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
        checkInitialization();
    }

    public ExpandableLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
        checkInitialization();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandableLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs);
        checkInitialization();
    }

    public ViewGroup getHeader() {
        return header;
    }

    public ViewGroup getContent() {
        return content;
    }

    public void setTitle(@Nullable String text) {
        title.setText(text);
    }

    public void setTitle(@StringRes int textRes) {
        title.setText(textRes);
    }

    public void toggle() {
        setState(!expandSubject.getValue());
    }

    public void setState(boolean expand) {
        switcher.setImageDrawable(expand ? iconCollapse : iconExpand);
        if (isInEditMode()) {
            content.setVisibility(expand ? VISIBLE : GONE);
            expandSubject.onNext(expand);
        } else if (expand) {
            AnimUtils.expand(content)
                    .filter(Integer.valueOf(VISIBLE)::equals)
                    .subscribe(v -> expandSubject.onNext(true));
        } else {
            AnimUtils.collapse(content)
                    .filter(Integer.valueOf(GONE)::equals)
                    .subscribe(v -> expandSubject.onNext(false));
        }
    }

    public Observable<Boolean> isExpanded() {
        return expandSubject;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        while (getChildCount() > 2) {
            View view = getChildAt(2);
            if (view != header && view != content) {
                removeView(view);
                content.addView(view);
            }
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        FrameLayout.LayoutParams params = (LayoutParams) content.getLayoutParams();
        params.setMargins(0, header.getMeasuredHeight(), 0, 0);
        content.setLayoutParams(params);
    }

    protected void initialize(Context context, AttributeSet attrs) {
        @LayoutRes int headerRes;
        String titleText = "";
        boolean expanded = false;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandableLayout);
            try {
                headerRes = a.getResourceId(R.styleable.ExpandableLayout_header, R.layout.header);
                titleText = a.getString(R.styleable.ExpandableLayout_title);
                iconExpand = getDrawable(context, a.getResourceId(R.styleable.ExpandableLayout_icon_expanded, R.drawable.ic_expand));
                iconCollapse = getDrawable(context, a.getResourceId(R.styleable.ExpandableLayout_icon_collapsed, R.drawable.ic_collapse));
                expanded = a.getBoolean(R.styleable.ExpandableLayout_expanded, false);
            } finally {
                a.recycle();
            }
        } else {
            headerRes = R.layout.header;
            iconExpand = getDrawable(context, R.drawable.ic_expand);
            iconCollapse = getDrawable(context, R.drawable.ic_collapse);
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        header = (ViewGroup) inflater.inflate(headerRes, this, false);
        content = (ViewGroup) inflater.inflate(R.layout.content, this, false);

        title = (TextView) header.findViewById(R.id.title);
        title.setText(titleText);
        switcher = (ImageView) header.findViewById(R.id.switcher);
        switcher.setOnClickListener(v -> toggle());

        addView(header);
        addView(content);

        setState(expanded);
        initialized = true;
    }

    private Drawable getDrawable(Context context, @DrawableRes int drawableResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getResources().getDrawable(drawableResId, context.getTheme());
        } else {
            return getResources().getDrawable(drawableResId);
        }
    }

    private Observable<ExpandableLayout> expandedEvent() {
        return expandSubject.distinctUntilChanged().filter(Boolean.TRUE::equals).map(expanded -> this);
    }

    private void checkInitialization() {
        if (!initialized) {
            throw new IllegalStateException("If you override initialize do not forget invoke super.initialize first");
        }
    }

}
