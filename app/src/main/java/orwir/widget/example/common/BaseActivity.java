package orwir.widget.example.common;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import orwir.widget.example.AdjustableTextActivity;
import orwir.widget.example.ExpandableLayoutActivity;
import orwir.widget.example.MainActivity;
import orwir.widget.example.R;

public abstract class BaseActivity extends RxAppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    Drawer drawer;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @LayoutRes
    protected abstract int getContentRes();

    protected abstract long getDrawerId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentRes());
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        MainActivity.DRAWER_ITEM,
                        ExpandableLayoutActivity.DRAWER_ITEM,
                        AdjustableTextActivity.DRAWER_ITEM
                )
                .withOnDrawerNavigationListener(view -> {
                    onBackPressed();
                    return true;
                })
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if (drawerItem instanceof ActivityItem) {
                        ((ActivityItem) drawerItem).startActivity(this);
                    }
                    return false;
                })
                .build();
        drawer.setSelection(getDrawerId(), false);
    }

}
