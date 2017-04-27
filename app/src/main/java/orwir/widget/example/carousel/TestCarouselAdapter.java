package orwir.widget.example.carousel;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import orwir.widget.carousel.CarouselAdapter;
import orwir.widget.example.R;

public class TestCarouselAdapter extends CarouselAdapter {

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
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_carousel, container, false);
        view.setTag(position);
        container.addView(view);
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText("Item #" + position % getRealCount());
        return view;
    }

}
