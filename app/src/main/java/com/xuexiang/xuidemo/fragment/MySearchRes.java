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

import static com.xuexiang.xutil.XUtil.runOnUiThread;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.adapter.FlexboxLayoutAdapter;
import com.xuexiang.xuidemo.adapter.NewsCardViewListAdapter;
import com.xuexiang.xuidemo.adapter.entity.NewInfo;
import com.xuexiang.xuidemo.base.BaseFragment;
import com.xuexiang.xuidemo.utils.HttpCallbackListener;
import com.xuexiang.xuidemo.utils.HttpUtil;
import com.xuexiang.xuidemo.utils.Utils;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

@Page(name = "搜索结果")
public class MySearchRes extends BaseFragment {

    @BindView(R.id.edit_search)
    EditText editText;

    @BindView(R.id.search_button)
    TextView button;

    @BindView(R.id.recyclerView)
    SwipeRecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.back)
    ImageView back;

    private String searchKey;

    private NewsCardViewListAdapter mAdapter;

    private String content;

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

        getContent();
    }

    @Override
    protected void initListeners() {

        back.setOnClickListener(v -> {
            popToBack();
        });

        button.setOnClickListener(v -> {
            searchKey = String.valueOf(editText.getText());
            getContent();
        });

    }

    public List<NewInfo> getInfo() {

        JSONObject res = JSON.parseObject(content);
        JSONObject data = res.getJSONObject("data");
        int total = data.getIntValue("total");
        JSONArray records = data.getJSONArray("records");
        List<NewInfo> list = new ArrayList<>();

        for (int i=0; i<total; i++) {
            JSONObject record = (JSONObject) records.get(i);
            System.out.println(record.getString("title"));
            list.add(new NewInfo(" ",record.getString("title"))
                    .setSummary(record.getString("text"))
                    .setDetailUrl(record.getString("cid")));
        }

        return list;
    }

    protected void getContent() {

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            String url = "http://134.175.217.138:8001/cases/page/1/100?title=" + searchKey ;
            String comurl = HttpUtil.getURLWithParams(url,params);
            HttpUtil.sendHttpRequest(comurl, new HttpCallbackListener() {
                @Override
                public String onFinish(String response) {
                    System.out.println("susses");
                    content = response;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());

                            recyclerView.setAdapter(mAdapter = new NewsCardViewListAdapter());

                            mAdapter.refresh(getInfo());

                            mAdapter.setOnItemClickListener((itemView, item, position) -> openNewPage(Specific_Page.class,"id",item.getDetailUrl()));

                            swipeRefreshLayout.setEnabled(false);
                        }
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
}
