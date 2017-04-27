package orwir.widget.example;

import android.content.Context;
import android.content.Intent;

import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import orwir.widget.example.common.ActivityItem;
import orwir.widget.example.common.BaseActivity;


public class CarouselActivity extends BaseActivity {

    public static final PrimaryDrawerItem DRAWER_ITEM = new ActivityItem()
            .withStartActivity(CarouselActivity::startActivity)
            .withName(R.string.carousel_activity);

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CarouselActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentRes() {
        return R.layout.activity_carousel;
    }

    @Override
    protected long getDrawerId() {
        return DRAWER_ITEM.getIdentifier();
    }

}
