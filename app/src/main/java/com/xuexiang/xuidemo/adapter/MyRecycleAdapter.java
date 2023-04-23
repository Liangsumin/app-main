/*
 * Copyright (C) 2023 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.xuidemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.utils.DataModel;

import java.util.ArrayList;
import java.util.List;

public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleViewHolder> {
    private LayoutInflater mInflater;
    private List<DataModel> mList = new ArrayList<>();

    private LinearLayoutManager mManager;

    private MyRecycleViewHolder.OnItemClickListener<DataModel> clickListener;

    public MyRecycleAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<DataModel> model) {
        mList.addAll(model);
    }

    public void setManager(LinearLayoutManager manager) {
        mManager = manager;
//        System.out.println(mManager);
    }

    @Override
    public MyRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyRecycleViewHolder vh = null;
        switch (viewType) {
            case DataModel.TYPE_TAB:
                vh = new MyRecycleViewHolderPs(mInflater.inflate(R.layout.card_layout, parent,false));
                break;
            case DataModel.TYPE_TITLE:
                vh = new MyRecycleViewHolderTwo(mInflater.inflate(R.layout.card_layout, parent,false));
                break;
            case DataModel.TYPE_ITEM:
                vh = new MyRecycleViewHolderKs(mInflater.inflate(R.layout.aaa_ks_item,parent,false));
                if (clickListener != null) {
                    MyRecycleViewHolder finalVh = vh;
                    vh.itemView.setOnClickListener(v -> {
                        clickListener.onItemClick(finalVh.itemView,mList.get(finalVh.getLayoutPosition()),finalVh.getLayoutPosition());
                    });
                }
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(MyRecycleViewHolder holder, int position) {
        holder.bindHolder(mList.get(position));
    }

    @Override
    public int getItemViewType(int position) {

        return mList.get(position).type;

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setClickListener(MyRecycleViewHolder.OnItemClickListener<DataModel> clickListener) {
        this.clickListener = clickListener;
    }
}