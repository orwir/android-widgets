package orwir.widget.carousel;

import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.Queue;

public abstract class HoldableCarouselAdapter<VH extends HoldableCarouselAdapter.ViewHolder> extends CarouselAdapter {

    private final int maxCacheSize = 5;
    private final Queue<VH> cache = new LinkedList<>();

    protected abstract VH onCreateViewHolder(ViewGroup parent);

    protected abstract void onBindViewHolder(VH holder, int position);

    @Override
    public final Object instantiateItem(ViewGroup container, int position) {
        VH holder = cache.poll();
        if (holder == null) {
            holder = onCreateViewHolder(container);
        }
        onBindViewHolder(holder, position);
        container.addView(holder.itemView);
        return holder.itemView;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof View) {
            container.removeView((View) object);
            VH holder = (VH) ((View) object).getTag();
            if (holder != null && cache.size() < maxCacheSize) {
                cache.add(holder);
            }
        } else if (object instanceof ViewHolder) {
            container.removeView(((VH) object).itemView);
            if (cache.size() < maxCacheSize) {
                cache.add((VH) object);
            }
        }
    }

    public static abstract class ViewHolder {

        public View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
            itemView.setTag(this);
        }

    }

}
