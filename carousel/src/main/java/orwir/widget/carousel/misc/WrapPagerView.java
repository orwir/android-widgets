package orwir.widget.carousel.misc;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class WrapPagerView extends ViewPager {

    private Boolean mAnimStarted = false;
    private Animation a;
    private int targetHeight;
    private int heightChange;
    private int currentHeight;

    {
        a = new Animation() {

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime >= 1) {
                    getLayoutParams().height = targetHeight;
                } else {
                    int stepHeight = (int) (heightChange * interpolatedTime);
                    getLayoutParams().height = currentHeight + stepHeight;
                }
                requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }

        };

        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mAnimStarted = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mAnimStarted = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public WrapPagerView(Context context) {
        super(context);
    }

    public WrapPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        if (getChildCount() > 0) {
            View view = findViewWithTag(getCurrentItem());
            if (view != null) {
                view.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                hSize = view.getMeasuredHeight();

                if (hSize > 0) {
                    targetHeight = hSize;
                    currentHeight = getLayoutParams().height;
                    heightChange = targetHeight - currentHeight;

                    if (!mAnimStarted) {
                        a.setDuration(100);
                        startAnimation(a);
                        mAnimStarted = true;
                    }
                }
            }
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(hSize, MeasureSpec.AT_MOST));
    }
}