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
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.activity.MainActivity;
import com.xuexiang.xuidemo.adapter.MyRecycleAdapter;
import com.xuexiang.xuidemo.base.BaseFragment;
import com.xuexiang.xuidemo.utils.DataModel;
import com.xuexiang.xuidemo.utils.HttpCallbackListener;
import com.xuexiang.xuidemo.utils.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

@Page(name = "个人短语集")
public class Edit_specific extends BaseFragment {

    @BindView(R.id.ks_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.title)
    TextView title;

    ContentView contentView;

    PopupWindow popupWindow;

    private MyRecycleAdapter mAdapter;

    private LinearLayoutManager mManager;

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.aaa_ks;
    }

    @Override
    protected void initViews() {

        StatusBarUtils.translucent(getActivity(), Color.parseColor("#00000000"));
        StatusBarUtils.setStatusBarLightMode(getActivity());

        mManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mManager);

        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        Bundle bundle = getArguments();
        setContent(bundle.getString("id"));
    }

    protected void setContent(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            String url = "http://1.12.74.230:10010/phrase-public/" + id;
            String comurl = HttpUtil.getURLWithParams(url,params);
            HttpUtil.sendHttpRequest(comurl, new HttpCallbackListener() {
                @Override
                public String onFinish(String response) {

                    runOnUiThread(() -> {
                        mAdapter=new MyRecycleAdapter(getContext());
                        mAdapter.setManager(mManager);
                        initData(response);
                        mRecyclerView.setAdapter(mAdapter);
                    });
                    return "success";
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

    protected void initData(String content) {

        List<DataModel> datas=new ArrayList<>();
        DataModel dataModel;

        JSONObject res = JSON.parseObject(content);
        JSONObject data = res.getJSONObject("data");
        title.setText(data.getString("title"));

//        for (int i=0; i<records.size(); i++) {
//            dataModel = new DataModel();
//            dataModel.type = DataModel.TYPE_ITEM;
//            dataModel.no = String.valueOf(i + 1);
//            JSONObject record = (JSONObject) records.get(i);
//            dataModel.Content = record.getString("content");
//            datas.add(dataModel);
//        }

        mAdapter.setData(datas);
    }

    @Override
    protected void initListeners() {

        back.setOnClickListener(v -> {
            popToBack();
        });
    }

    private void initPopupWindow() {
        //要在布局中显示的布局
        contentView = (ContentView) LayoutInflater.from(getContext()).inflate(R.layout.popup_layout, null, false);
        //实例化PopupWindow并设置宽高
        popupWindow = new PopupWindow((View) contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失，这里因为PopupWindow填充了整个窗口，所以这句代码就没用了
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画
//        popupWindow.setAnimationStyle(R.style.MyPopWindowAnim);
    }

    private void showPopWindow() {
        View rootview = LayoutInflater.from(getContext()).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }
}
