package orwir.widget.example;

import android.content.Context;
import android.content.Intent;

import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import orwir.widget.example.common.ActivityItem;
import orwir.widget.example.common.BaseActivity;

public class StretchableTextActivity extends BaseActivity {

    public static final PrimaryDrawerItem DRAWER_ITEM = new ActivityItem()
            .withStartActivity(StretchableTextActivity::startActivity)
            .withName(R.string.stretchable_text_activity);

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, StretchableTextActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentRes() {
        return R.layout.activity_stretchable_text;
    }

    @Override
    protected long getDrawerId() {
        return DRAWER_ITEM.getIdentifier();
    }

}
