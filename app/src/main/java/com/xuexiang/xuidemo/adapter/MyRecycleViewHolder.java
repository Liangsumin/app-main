package com.xuexiang.xuidemo.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xuidemo.utils.DataModel;

public  abstract class MyRecycleViewHolder extends RecyclerView.ViewHolder {

        public MyRecycleViewHolder(View itemView) {
            super(itemView);

        }

        public abstract void bindHolder(DataModel data);

    public interface OnItemClickListener<T> {
        /**
         * 条目点击
         *
         * @param itemView 条目
         * @param item     数据
         * @param position 索引
         */
        void onItemClick(View itemView, T item, int position);
    }

}