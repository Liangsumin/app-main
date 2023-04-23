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
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.tabbar.TabSegment;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.adapter.NewsCardViewListAdapter;
import com.xuexiang.xuidemo.base.BaseFragment;
import com.xuexiang.xuidemo.fragment.Res.CaseRes;
import com.xuexiang.xuidemo.fragment.Res.KnowledgeRes;
import com.xuexiang.xuidemo.fragment.Res.PhraseRes;

import butterknife.BindView;

@Page(name = "搜索结果")
public class SearchRes extends BaseFragment {

    @BindView(R.id.edit_search)
    EditText editText;

    @BindView(R.id.search_button)
    TextView button;

    @BindView(R.id.tabSegment)
    TabSegment mTabSegment;

    @BindView(R.id.contentViewPager)
    ViewPager mContentViewPager;

    @BindView(R.id.back)
    ImageView back;

    public static String searchKey;

    private NewsCardViewListAdapter mAdapter;

    private String content;

    private KnowledgeRes knowledgeRes;

    private PhraseRes phraseRes;

    private CaseRes caseRes;

    String[] pages = {"短语","知识","案例"};

    @Override
    protected TitleBar initTitle() {return null;}

    @Override
    protected int getLayoutId() {
        return R.layout.aaa;
    }

    @Override
    protected void initViews() {

        StatusBarUtils.translucent(getActivity(), Color.parseColor("#00000000"));
        StatusBarUtils.setStatusBarLightMode(getActivity());

        Bundle bundle = getArguments();
        searchKey = bundle.getString("text");
        editText.setText(searchKey);

        setSearchRes();
    }

    protected void setSearchRes() {

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        mTabSegment.addTab(new TabSegment.Tab(pages[0]));
        phraseRes = new PhraseRes();
        adapter.addFragment(phraseRes, pages[0]);

        mTabSegment.addTab(new TabSegment.Tab(pages[1]));
        knowledgeRes = new KnowledgeRes();
        adapter.addFragment(knowledgeRes, pages[1]);

        mTabSegment.addTab(new TabSegment.Tab(pages[2]));
        caseRes = new CaseRes();
        adapter.addFragment(caseRes, pages[2]);

        mContentViewPager.setAdapter(adapter);
        mContentViewPager.setCurrentItem(0, false);
        mTabSegment.setupWithViewPager(mContentViewPager, false);
        mTabSegment.setMode(TabSegment.MODE_FIXED);
        mTabSegment.setIndicatorDrawable(getResources().getDrawable(R.drawable.indicator));
    }

    @Override
    protected void initListeners() {

        back.setOnClickListener(v -> {
            popToBack();
        });

        button.setOnClickListener(v -> {
            searchKey = String.valueOf(editText.getText());
            phraseRes.refresh();
            knowledgeRes.refresh();
            caseRes.refresh();
        });

    }

}
