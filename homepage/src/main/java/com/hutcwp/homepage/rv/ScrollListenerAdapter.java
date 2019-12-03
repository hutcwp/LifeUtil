package com.hutcwp.homepage.rv;


import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yarolegovich on 16.03.2017.
 */
public class ScrollListenerAdapter<T extends RecyclerView.ViewHolder> implements DiscreteScrollView.ScrollStateChangeListener<T> {

    private DiscreteScrollView.ScrollListener<T> adaptee;

    public ScrollListenerAdapter(DiscreteScrollView.ScrollListener<T> adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void onScrollStart(T currentItemHolder, int adapterPosition) {

    }

    @Override
    public void onScrollEnd(T currentItemHolder, int adapterPosition) {

    }

    @Override
    public void onScroll(float scrollPosition,
                         int currentIndex, int newIndex,
                         T currentHolder, T newCurrentHolder) {
        adaptee.onScroll(scrollPosition, currentIndex, newIndex, currentHolder, newCurrentHolder);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ScrollListenerAdapter) {
            return adaptee.equals(((ScrollListenerAdapter) obj).adaptee);
        } else {
            return super.equals(obj);
        }
    }
}
