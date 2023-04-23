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
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.adapter.MyRecycleAdapter;
import com.xuexiang.xuidemo.adapter.NewsCardViewListAdapter;
import com.xuexiang.xuidemo.adapter.entity.NewInfo;
import com.xuexiang.xuidemo.base.BaseFragment;
import com.xuexiang.xuidemo.fragment.SimpleListFragment;
import com.xuexiang.xuidemo.utils.DataModel;
import com.xuexiang.xuidemo.utils.HttpCallbackListener;
import com.xuexiang.xuidemo.utils.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

@Page(name = "个人知识")
public class MyKnowledge extends BaseFragment {

    private static final String KEY_IS_SPECIAL = "key_is_special";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private MyRecycleAdapter mAdapter;

    private LinearLayoutManager mManager;

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.aaa_knowledge_recycleview;
    }

    @Override
    protected void initViews() {
        getContent();
    }

    protected void getContent() {

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            String url = "http://1.12.74.230:10010/knowledge-set/page1/1/100";
            String comurl = HttpUtil.getURLWithParams(url,params);
            HttpUtil.sendHttpRequest(comurl, new HttpCallbackListener() {
                @Override
                public String onFinish(String response) {
                    System.out.println("susses");

                    runOnUiThread(() -> {
                        mManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        mRecyclerView.setLayoutManager(mManager);
                        mAdapter=new MyRecycleAdapter(getContext());
                        mAdapter.setManager(mManager);
                        initData(response);
                        mRecyclerView.setAdapter(mAdapter);
                    });

                    return response;
                }

                @Override
                public void onError(Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    protected void initData(String content) {

        System.out.println(content);
        List<DataModel> datas=new ArrayList<>();
        DataModel dataModel;

        dataModel = new DataModel();
        dataModel.type = DataModel.TYPE_TITLE;
        dataModel.title = "常用知识集";
        dataModel.titles.add("输入法知识集");
        dataModel.content.add("9条, 可在输入法中使用");
        dataModel.picture.add(R.drawable.bg3);
        dataModel.ids.add("47");
        datas.add(dataModel);

        dataModel = new DataModel();
        dataModel.type = DataModel.TYPE_TITLE;
        dataModel.title = "知识集收藏";

        JSONObject res = JSON.parseObject(content);
        JSONObject data = res.getJSONObject("data");
        int total = data.getIntValue("total");
        JSONArray records = data.getJSONArray("records");

        for (int i=0; i<total; i++) {
            JSONObject record = (JSONObject) records.get(i);
            JSONArray text = record.getJSONArray("text");
            dataModel.titles.add(record.getString("title"));
            dataModel.content.add("共" + text.size() + "条知识");
            dataModel.picture.add(R.drawable.bg1);
            dataModel.ids.add(record.getString("ksid"));
        }

        datas.add(dataModel);
        mAdapter.setData(datas);

    }

}
