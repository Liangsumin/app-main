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

package com.xuexiang.xuidemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Process;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.imageview.ImageLoader;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.adapter.NewsCardViewListAdapter;
import com.xuexiang.xuidemo.adapter.entity.NewInfo;
import com.xuexiang.xuidemo.base.BaseActivity;
import com.xuexiang.xuidemo.fragment.SearchRes;
import com.xuexiang.xuidemo.input.MyKeyboard;
import com.xuexiang.xuidemo.utils.CopyLinkTextHelper;
import com.xuexiang.xuidemo.utils.HttpCallbackListener;
import com.xuexiang.xuidemo.utils.HttpUtil;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class InputCaseActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    SwipeRecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.edit_search)
    EditText editText;
    private NewsCardViewListAdapter mAdapter = new NewsCardViewListAdapter();
    private String content;
    private String URL;
    PopupWindow popupWindow;
    LinearLayout copy;
    ImageView imageView;
    TextView case_title;
    TextView case_content;
    View contentView;

    @Override
    protected int getLayoutId() {
        return R.layout.aaa_input_case_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.translucent(this, Color.parseColor("#00000000"));
        StatusBarUtils.setStatusBarLightMode(this);
        URL = "http://1.12.74.230:10010/cases/page/1/100?title=";
        getContent();
        initPopupWindow();
    }

    @Override
    protected void onStart() {
        super.onStart();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                refresh(s.toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public List<NewInfo> getInfo() {

        JSONObject res = JSON.parseObject(content);
        JSONObject data = res.getJSONObject("data");
        List<NewInfo> list = new ArrayList<>();

        if (data != null) {

            int total = data.getIntValue("total");
            JSONArray records = data.getJSONArray("records");

            for (int i=0; i<total; i++) {
                JSONObject record = (JSONObject) records.get(i);
                System.out.println(record.getString("title"));
                list.add(new NewInfo(" ",record.getString("title"))
                        .setSummary(record.getString("text"))
                        .setImageUrl("https://pic4.zhimg.com/v2-1236d741cbb3aabf5a9910a5e4b73e4c_1200x500.jpg")
                        .setDetailUrl(record.getString("cid")));
            }
        }

        return list;
    }

    protected void getContent() {

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            String url = URL + "" ;
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

                        mAdapter.setOnItemClickListener((itemView, item, position) -> {
                            showPopWindow(item);
                        });

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

    public void refresh(String searchKey) {

        HashMap<String, String> params = new HashMap<>();
        try {
            String url = URL + searchKey;
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

    private void initPopupWindow() {
        //要在布局中显示的布局
        contentView = LayoutInflater.from(this).inflate(R.layout.popup_layout_case, null, false);
        //实例化PopupWindow并设置宽高
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失，这里因为PopupWindow填充了整个窗口，所以这句代码就没用了
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
    }

    public void showPopWindow(NewInfo item) {
        View rootView = LayoutInflater.from(this).inflate(R.layout.popup_layout_case, null);

        copy = popupWindow.getContentView().findViewById(R.id.copy);
        copy.setOnClickListener(v -> {
            Intent intent = new Intent("com.example.action");
            intent.putExtra("data", item.getImageUrl());
            sendBroadcast(intent);
            finish();
        });

        case_title = popupWindow.getContentView().findViewById(R.id.Case_title);
        case_title.setText(item.getTitle());
        case_content = popupWindow.getContentView().findViewById(R.id.Case_content);
        case_content.setText(item.getSummary());
        imageView = popupWindow.getContentView().findViewById(R.id.iv_image);
        ImageLoader.get().loadImage(imageView,item.getImageUrl());

        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getWindow().getAttributes();
            lp1.alpha = 1f;
            getWindow().setAttributes(lp1);
        });
    }
}
