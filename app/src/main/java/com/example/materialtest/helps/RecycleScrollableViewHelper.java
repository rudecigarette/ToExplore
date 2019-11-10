package com.example.materialtest.helps;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sothree.slidinguppanel.ScrollableViewHelper;

public class RecycleScrollableViewHelper extends ScrollableViewHelper {
    @Override
    public int getScrollableViewScrollPosition(View scrollableView, boolean isSlidingUp) {
        return super.getScrollableViewScrollPosition(scrollableView, isSlidingUp);
    }
}
