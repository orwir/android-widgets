package orwir.widget.example.common;

import android.content.Context;
import android.support.annotation.Nullable;

import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import io.reactivex.functions.Consumer;

public class ActivityItem extends PrimaryDrawerItem {

    private @Nullable Consumer<Context> startActivity;

    public ActivityItem withStartActivity(Consumer<Context> action) {
        startActivity = action;
        return this;
    }

    public void startActivity(Context context) {
        if (startActivity != null) {
            try {
                startActivity.accept(context);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
