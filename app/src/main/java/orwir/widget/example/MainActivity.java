package orwir.widget.example;


import android.content.Context;
import android.content.Intent;

import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import orwir.widget.example.common.ActivityItem;
import orwir.widget.example.common.BaseActivity;

public class MainActivity extends BaseActivity {

    public static final PrimaryDrawerItem DRAWER_ITEM = new ActivityItem()
            .withStartActivity(MainActivity::startActivity)
            .withName(R.string.main_activity);

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentRes() {
        return R.layout.activity_main;
    }

    @Override
    protected long getDrawerId() {
        return DRAWER_ITEM.getIdentifier();
    }

}
