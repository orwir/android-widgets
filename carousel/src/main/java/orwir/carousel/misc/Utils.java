package orwir.carousel.misc;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {

    public static float toPx(Resources r, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, r.getDisplayMetrics());
    }

    private Utils() {}
}
