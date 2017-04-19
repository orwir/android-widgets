package orwir.widget.stretch;

import android.text.method.SingleLineTransformationMethod;

public class FitTransformationMethod extends SingleLineTransformationMethod {

    private static final class Holder {
        private static final FitTransformationMethod INSTANCE = new FitTransformationMethod();
    }

    public static final FitTransformationMethod getInstance() {
        return Holder.INSTANCE;
    }


}
