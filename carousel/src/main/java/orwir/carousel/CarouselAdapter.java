package orwir.carousel;

import android.support.v4.view.PagerAdapter;

public abstract class CarouselAdapter extends PagerAdapter {

    public static final int LOOP_COUNT = 200;

    public abstract int getRealCount();

}
