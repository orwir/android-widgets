package orwir.widget.example.common;

import android.content.Context;
import android.support.annotation.Nullable;

import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import rx.functions.Action1;

public class ActivityItem extends PrimaryDrawerItem {

    private @Nullable Action1<Context> startActivity;

    public ActivityItem withStartActivity(Action1<Context> action) {
        startActivity = action;
        return this;
    }

    public void startActivity(Context context) {
        if (startActivity != null) {
            startActivity.call(context);
        }
    }

}
