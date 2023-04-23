/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuexiang.xuidemo.fragment.components.tabbar.tabsegment;

import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.tabbar.TabSegment;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.base.BaseFragment;
import com.xuexiang.xuidemo.fragment.AllCase;
import com.xuexiang.xuidemo.fragment.AllKnowledge;
import com.xuexiang.xuidemo.fragment.AllPhrase;
import com.xuexiang.xuidemo.fragment.MySearchView;
import com.xuexiang.xuidemo.fragment.SimpleListFragment;
import com.xuexiang.xuidemo.fragment.components.tabbar.ContentPage;
import com.xuexiang.xuidemo.utils.HttpCallbackListener;
import com.xuexiang.xuidemo.utils.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import butterknife.BindView;

/**
 * @author xuexiang
 * @since 2018/12/26 下午5:40
 */
@Page(name = "首页",anim = CoreAnim.none)
public class TabSegmentFixMode extends BaseFragment {

    @BindView(R.id.tabSegment)
    TabSegment mTabSegment;
    @BindView(R.id.contentViewPager)
    ViewPager mContentViewPager;

    @BindView(R.id.search_view)
    LinearLayout search_view;

    String[] pages = {"短语","知识","案例"};

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tabsegment;
    }

    @Override
    protected void initViews() {
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        mTabSegment.addTab(new TabSegment.Tab(pages[0]));
        AllPhrase allPhrase = new AllPhrase();
        adapter.addFragment(allPhrase, pages[0]);

        mTabSegment.addTab(new TabSegment.Tab(pages[1]));
        AllKnowledge allKnowledge = new AllKnowledge();
        adapter.addFragment(allKnowledge, pages[1]);

        mTabSegment.addTab(new TabSegment.Tab(pages[2]));
        AllCase allCase = new AllCase();
        adapter.addFragment(allCase, pages[2]);

        mContentViewPager.setAdapter(adapter);
        mContentViewPager.setCurrentItem(0, false);
        mTabSegment.setupWithViewPager(mContentViewPager, false);
        mTabSegment.setMode(TabSegment.MODE_FIXED);
        mTabSegment.setIndicatorDrawable(getResources().getDrawable(R.drawable.indicator));
    }

    @Override
    protected void initListeners() {
        search_view.setOnClickListener(v -> {
            openNewPage(MySearchView.class);
        });
    }

}

