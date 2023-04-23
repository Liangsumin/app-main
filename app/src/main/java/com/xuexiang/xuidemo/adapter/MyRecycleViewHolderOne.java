package com.xuexiang.xuidemo.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.adapter.MyRecycleViewHolder;
import com.xuexiang.xuidemo.utils.DataModel;

public class MyRecycleViewHolderOne extends MyRecycleViewHolder {

        public TextView tab1;
        public TextView tab2;
        public TextView tab3;

        public MyRecycleViewHolderOne(View itemView) {
            super(itemView);
            tab1 = itemView.findViewById(R.id.tab1);
            tab2 = itemView.findViewById(R.id.tab2);
            tab3 = itemView.findViewById(R.id.tab3);

        }
        @Override
        public void bindHolder(DataModel data) {
            tab1.setText(data.tab1);
            tab2.setText(data.tab2);
            tab3.setText(data.tab3);
        }

}