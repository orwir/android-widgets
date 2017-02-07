package orwir.widget.expandable.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class AnimUtils {

    private static final int MIN_DURATION = 50;
    private static final int MAX_DURATION = 600;

    public static Observable<Integer> expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        int duration = (int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density);
        return expand(v, Math.min(duration, MAX_DURATION));
    }

    public static Observable<Integer> expand(final View v, int duration) {
        BehaviorSubject<Integer> subject = BehaviorSubject.create();
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        if (v.getLayoutParams() != null) {
            v.getLayoutParams().height = 1;
        } else {
            v.setMinimumHeight(1);
        }
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
                if (interpolatedTime == 1) {
                    subject.onNext(View.VISIBLE);
                    subject.onCompleted();
                }
            }
        };

        a.setDuration(Math.max(MIN_DURATION, duration));
        v.startAnimation(a);
        return subject;
    }

    public static Observable<Integer> collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();
        int duration = (int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density);
        return collapse(v, Math.min(duration, MAX_DURATION));
    }

    public static Observable<Integer> collapse(final View v, int duration) {
        BehaviorSubject<Integer> subject = BehaviorSubject.create();
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                    subject.onNext(View.GONE);
                    subject.onCompleted();
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }
        };

        a.setDuration(Math.max(MIN_DURATION, duration));
        v.startAnimation(a);
        return subject;
    }

    private AnimUtils() {}
}
