package orwir.widget.example;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import butterknife.OnCheckedChanged;
import io.reactivex.disposables.Disposable;
import orwir.widget.example.common.ActivityItem;
import orwir.widget.example.common.BaseActivity;
import orwir.widget.expandable.layout.ExpandableLayout;

public class ExpandableLayoutActivity extends BaseActivity {

    public static final PrimaryDrawerItem DRAWER_ITEM = new ActivityItem()
            .withStartActivity(ExpandableLayoutActivity::startActivity)
            .withName(R.string.expandable_layout_activity);

    private Disposable singleExpandedSubscription;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ExpandableLayoutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentRes() {
        return R.layout.activity_expandable_layout;
    }

    @Override
    protected long getDrawerId() {
        return DRAWER_ITEM.getIdentifier();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnCheckedChanged(R.id.single_expanded)
    void toggleSingleExpanded(boolean checked) {
        if (checked && singleExpandedSubscription == null) {
            singleExpandedSubscription = ExpandableLayout.singleExpanded(this, (ViewGroup) findViewById(android.R.id.content), true);
        } else if (!checked && singleExpandedSubscription != null) {
            singleExpandedSubscription.dispose();
            singleExpandedSubscription = null;
        }
    }

}
