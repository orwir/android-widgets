package orwir.widget.example.carousel;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import orwir.widget.carousel.HoldableCarouselAdapter;
import orwir.widget.example.R;

public class TestCarouselAdapter extends HoldableCarouselAdapter<TestCarouselAdapter.TestHolder> {

    private final List<Object> objects = new ArrayList<>();
    {
        objects.add(new Object());
        objects.add(new Object());
        objects.add(new Object());
        objects.add(new Object());
        objects.add(new Object());
    }

    @Override
    public int getRealCount() {
        return objects.size();
    }

    @Override
    protected TestHolder onCreateViewHolder(ViewGroup parent) {
        return new TestHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel, parent, false));
    }

    @Override
    protected void onBindViewHolder(TestHolder holder, int position) {
        holder.text.setText("Item #" + position % getRealCount());
    }

    class TestHolder extends HoldableCarouselAdapter.ViewHolder {

        @BindView(R.id.text) TextView text;

        public TestHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
