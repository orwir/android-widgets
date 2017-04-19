package orwir.widget.example;

import android.content.Context;
import android.content.Intent;

import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import orwir.widget.example.common.ActivityItem;
import orwir.widget.example.common.BaseActivity;

public class AdjustableTextActivity extends BaseActivity {

    public static final PrimaryDrawerItem DRAWER_ITEM = new ActivityItem()
            .withStartActivity(AdjustableTextActivity::startActivity)
            .withName(R.string.adjustable_text_activity);

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AdjustableTextActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentRes() {
        return R.layout.activity_adjustable_text;
    }

    @Override
    protected long getDrawerId() {
        return DRAWER_ITEM.getIdentifier();
    }

}
