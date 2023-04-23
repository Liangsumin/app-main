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

package com.xuexiang.xuidemo.fragment;

import android.graphics.Color;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.umeng.commonsdk.debug.I;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.adapter.FlexboxLayoutAdapter;
import com.xuexiang.xuidemo.base.BaseFragment;
import com.xuexiang.xuidemo.utils.Utils;

import butterknife.BindView;

@Page(name = "搜索页")
public class MySearchView extends BaseFragment {

    @BindView(R.id.edit_search)
    EditText editText;

    @BindView(R.id.search_button)
    TextView button;

    @BindView(R.id.search_history)
    RecyclerView history;

    @BindView(R.id.search_suggest)
    RecyclerView suggest;

    @BindView(R.id.back)
    ImageView back;

    private FlexboxLayoutAdapter mHistoryAdapter;

    private FlexboxLayoutAdapter mSuggestAdapter;


    @Override
    protected TitleBar initTitle() {return null;}

    @Override
    protected int getLayoutId() {
        return R.layout.aaa_search_view;
    }

    @Override
    protected void initViews() {

        StatusBarUtils.translucent(getActivity(), Color.parseColor("#00000000"));
        StatusBarUtils.setStatusBarLightMode(getActivity());

        String[] array = ResUtils.getStringArray(getContext(), R.array.tags_values);

        history.setLayoutManager(Utils.getFlexboxLayoutManager(getContext()));
        history.setAdapter(mHistoryAdapter = new FlexboxLayoutAdapter(array));

        suggest.setLayoutManager(Utils.getFlexboxLayoutManager(getContext()));
        suggest.setAdapter(mSuggestAdapter = new FlexboxLayoutAdapter(array));

    }

    @Override
    protected void initListeners() {

        mHistoryAdapter.setOnItemClickListener((itemView, item, position) -> XToastUtils.toast("点击了：" + item));
        mSuggestAdapter.setOnItemClickListener((itemView, item, position) -> XToastUtils.toast("点击了：" + item));

        button.setOnClickListener(v -> {
            openNewPage(SearchRes.class,"text",String.valueOf(editText.getText()));
        });

        back.setOnClickListener(v -> {
            popToBack();
        });

    }
}
