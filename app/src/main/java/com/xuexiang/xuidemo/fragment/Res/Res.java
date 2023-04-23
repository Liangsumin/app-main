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

package com.xuexiang.xuidemo.fragment.Res;

import static com.xuexiang.xutil.XUtil.runOnUiThread;

import android.graphics.Color;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.adapter.NewsCardViewListAdapter;
import com.xuexiang.xuidemo.adapter.entity.NewInfo;
import com.xuexiang.xuidemo.base.BaseFragment;
import com.xuexiang.xuidemo.fragment.SearchRes;
import com.xuexiang.xuidemo.fragment.Specific_Page;
import com.xuexiang.xuidemo.utils.HttpCallbackListener;
import com.xuexiang.xuidemo.utils.HttpUtil;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

@Page(name = "搜索结果")
public abstract class Res extends BaseFragment {

    @BindView(R.id.recyclerView)
    SwipeRecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    protected NewsCardViewListAdapter mAdapter = new NewsCardViewListAdapter();

    protected String content;

    protected String URL;

    @Override
    protected TitleBar initTitle() {return null;}

    @Override
    protected int getLayoutId() {
        return R.layout.include_recycler_view_refresh;
    }

    @Override
    protected void initViews() {
        StatusBarUtils.translucent(getActivity(), Color.parseColor("#00000000"));
        StatusBarUtils.setStatusBarLightMode(getActivity());
    }

    public abstract List<NewInfo> getInfo();

    protected void getContent() {

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            String url = URL + SearchRes.searchKey ;
            String comurl = HttpUtil.getURLWithParams(url,params);
            HttpUtil.sendHttpRequest(comurl, new HttpCallbackListener() {
                @Override
                public String onFinish(String response) {
                    System.out.println("susses");
                    content = response;

                    runOnUiThread(() -> {

                        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());

                        recyclerView.setAdapter(mAdapter);

                        mAdapter.refresh(getInfo());

                        mAdapter.setOnItemClickListener((itemView, item, position) -> openNewPage(Specific_Page.class,"id",item.getDetailUrl()));

                        swipeRefreshLayout.setEnabled(false);
                    });

                    return response;
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("error");
                }
            });
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void refresh() {

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            String url = URL + SearchRes.searchKey ;
            String comurl = HttpUtil.getURLWithParams(url,params);
            HttpUtil.sendHttpRequest(comurl, new HttpCallbackListener() {
                @Override
                public String onFinish(String response) {
                    System.out.println("susses");
                    content = response;

                    runOnUiThread(() -> mAdapter.refresh(getInfo()));

                    return response;
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("error");
                }
            });
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
