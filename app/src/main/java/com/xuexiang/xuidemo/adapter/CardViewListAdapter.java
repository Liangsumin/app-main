package com.xuexiang.xuidemo.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.adapter.entity.NewInfo;

import java.util.List;

/**
 * @author XUE
 * @since 2019/5/9 10:41
 */
public class CardViewListAdapter extends BaseRecyclerAdapter<NewInfo> {

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.adapter_card_view_list_item;
    }

    @Override
    public void bindData(@NonNull RecyclerViewHolder holder, int position, NewInfo model) {
        if (model != null) {
            holder.text(R.id.tv_title, model.getTitle());
            holder.text(R.id.tv_summary, model.getSummary());
//            holder.image(R.id.iv_image, model.getImageUrl());
            holder.backgroundResId(R.id.card_item,model.getResId());
        }
    }
}
