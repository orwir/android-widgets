package orwir.widget.carousel;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class CarouselAdapter extends PagerAdapter {

    public static final int LOOP_COUNT = 200;

    public abstract int getRealCount();

    @Override
    public int getCount() {
        return getRealCount() < 2 ? getRealCount() : getRealCount() * LOOP_COUNT;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof View) {
            container.removeView((View) object);
        }
    }

}
