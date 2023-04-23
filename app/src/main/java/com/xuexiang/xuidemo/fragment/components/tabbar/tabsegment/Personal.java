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

import androidx.viewpager.widget.ViewPager;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.tabbar.TabSegment;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.base.BaseFragment;
import com.xuexiang.xuidemo.fragment.MyCase;
import com.xuexiang.xuidemo.fragment.MyKnowledge;
import com.xuexiang.xuidemo.fragment.MyPhrase;
import com.xuexiang.xuidemo.fragment.SimpleListFragment;

import butterknife.BindView;

/**
 * @author xuexiang
 * @since 2018/12/26 下午5:40
 */
@Page(name = "个人",anim = CoreAnim.none)
public class Personal extends BaseFragment {

    @BindView(R.id.tabSegment)
    TabSegment mTabSegment;
    @BindView(R.id.contentViewPager)
    ViewPager mContentViewPager;

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.personal_tab;
    }

    @Override
    protected void initViews() {
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        mTabSegment.addTab(new TabSegment.Tab("短语"));
        adapter.addFragment(new MyPhrase(), "短语");

        mTabSegment.addTab(new TabSegment.Tab("知识"));
        adapter.addFragment(new MyKnowledge(), "知识");

        mTabSegment.addTab(new TabSegment.Tab("案例"));
        adapter.addFragment(new SimpleListFragment(), "案例");

        mContentViewPager.setAdapter(adapter);
        mContentViewPager.setCurrentItem(0, false);
        mTabSegment.setupWithViewPager(mContentViewPager, false);
        mTabSegment.setIndicatorDrawable(getResources().getDrawable(R.drawable.indicator));
        mTabSegment.setMode(TabSegment.MODE_SCROLLABLE);
        mTabSegment.setItemSpaceInScrollMode(110);
    }

}

