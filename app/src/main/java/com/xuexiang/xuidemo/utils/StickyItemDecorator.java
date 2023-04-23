package com.xuexiang.xuidemo.utils;

import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StickyItemDecorator extends RecyclerView.ItemDecoration {

    private static final String TAG = "StickyItemDecorator";

    private LinearLayoutManager mLayoutManager;

    private int index;

    private SortShowListener listener;

    public StickyItemDecorator(SortShowListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (parent.getAdapter().getItemCount() <= 0) return;
        mLayoutManager = (LinearLayoutManager) parent.getLayoutManager();
        index = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
        if(index >= 0) {
            listener.showSort(true);
        } else {
            listener.showSort(false);
        }
    }

    public interface SortShowListener {
        void showSort(boolean show);
    }

}
