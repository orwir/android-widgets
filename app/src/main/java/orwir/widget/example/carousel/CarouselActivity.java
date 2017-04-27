package orwir.widget.example.carousel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import butterknife.BindView;
import butterknife.OnClick;
import orwir.widget.carousel.CarouselView;
import orwir.widget.example.R;
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

    @BindView(R.id.carousel) CarouselView carousel;

    @Override
    protected int getContentRes() {
        return R.layout.activity_carousel;
    }

    @Override
    protected long getDrawerId() {
        return DRAWER_ITEM.getIdentifier();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carousel.setAdapter(new TestCarouselAdapter());
    }

    @OnClick(R.id.prev)
    void prev() {
        carousel.previous();
    }

    @OnClick(R.id.next)
    void next() {
        carousel.next();
    }

}
