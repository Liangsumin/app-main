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

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.adapter.MyRecycleAdapter;
import com.xuexiang.xuidemo.base.BaseFragment;
import com.xuexiang.xuidemo.utils.CopyLinkTextHelper;
import com.xuexiang.xuidemo.utils.DataModel;
import com.xuexiang.xuidemo.utils.HttpCallbackListener;
import com.xuexiang.xuidemo.utils.HttpUtil;
import com.xuexiang.xuidemo.utils.ViewLikeUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

@Page(name = "知识集")
public class Knowledge_specific extends BaseFragment {

    @BindView(R.id.ks_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.favorites)
    LinearLayout favorites;

    @BindView(R.id.good)
    LinearLayout good;

    @BindView(R.id.favorites_icon)
    ImageView f_icon;

    @BindView(R.id.good_icon)
    ImageView g_icon;

    @BindView(R.id.favorites_text)
    TextView f_text;

    @BindView(R.id.good_no)
    TextView g_no;

    View contentView;

    PopupWindow popupWindow;

    LinearLayout delete;
    LinearLayout copy;
    TextView text;

    private boolean IS_GOOD = false;

    private boolean IS_FAVOR = false;

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

        initPopupWindow();
    }

    protected void setContent(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            String url = "http://1.12.74.230:10010/knowledge-set/" + id;
            String comurl = HttpUtil.getURLWithParams(url,params);
            HttpUtil.sendHttpRequest(comurl, new HttpCallbackListener() {
                @Override
                public String onFinish(String response) {

                    runOnUiThread(() -> {
                        mAdapter=new MyRecycleAdapter(getContext());
                        mAdapter.setManager(mManager);
                        initData(response);
                        mAdapter.setClickListener((itemView, item, position) -> {
                            showPopWindow(item.Content);
                        });
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
        JSONArray text = data.getJSONArray("text");
        title.setText(data.getString("title"));

        for (int i=0; i<text.size(); i++) {
            dataModel = new DataModel();
            dataModel.type = DataModel.TYPE_ITEM;
            dataModel.no = String.valueOf(i + 1);
            dataModel.Content = text.getString(i);
            datas.add(dataModel);
        }

        mAdapter.setData(datas);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initListeners() {

        back.setOnClickListener(v -> {
            popToBack();
        });

        TextView textView = new TextView(getContext());
        textView.setText("+1");
        textView.setTextColor(R.color.blue_start);
        ValueAnimator animator = ValueAnimator.ofInt(10, 200);
        animator.setDuration(800);
        new ViewLikeUtils(g_icon, textView, (view, toggle, viewLikeUtils) -> {
            if (IS_GOOD) {
                g_icon.setImageResource(R.drawable.good);
                g_no.setTextColor(this.getResources().getColor(R.color.white));
                int no = Integer.parseInt((String) g_no.getText()) - 1;
                g_no.setText(String.valueOf(no));
                IS_GOOD = false;
            } else {
                viewLikeUtils.startLikeAnim(animator);
                g_no.setTextColor(R.color.blue_start);
                g_icon.setImageResource(R.drawable.good_fill);
                int no = Integer.parseInt((String) g_no.getText()) + 1;
                g_no.setText(String.valueOf(no));
                IS_GOOD = true;
            }
        });

        new ViewLikeUtils(f_icon, textView, (view, toggle, viewLikeUtils) -> {
            if (IS_FAVOR) {
                f_icon.setImageResource(R.drawable.favorites);
                f_text.setTextColor(this.getResources().getColor(R.color.white));
                int no = Integer.parseInt((String) f_text.getText()) - 1;
                f_text.setText(String.valueOf(no));
                IS_FAVOR = false;
            } else {
                viewLikeUtils.startLikeAnim(animator);
                f_icon.setImageResource(R.drawable.favorites_fill);
                f_text.setTextColor(R.color.blue_start);
                int no = Integer.parseInt((String) f_text.getText()) + 1;
                f_text.setText(String.valueOf(no));
                IS_FAVOR = true;
            }
        });
    }

    private void initPopupWindow() {
        //要在布局中显示的布局
        contentView = LayoutInflater.from(getContext()).inflate(R.layout.popup_layout, null, false);
        //实例化PopupWindow并设置宽高
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失，这里因为PopupWindow填充了整个窗口，所以这句代码就没用了
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        //进入退出的动画
//        popupWindow.setAnimationStyle(R.style.MyPopWindowAnim);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //弹窗关闭  dismiss()时恢复原样
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });

    }

    public void showPopWindow(String content) {
        View rootview = LayoutInflater.from(getContext()).inflate(R.layout.popup_layout, null);

        text = popupWindow.getContentView().findViewById(R.id.popupText);
        text.setText(content);

        copy = popupWindow.getContentView().findViewById(R.id.copy);
        copy.setOnClickListener(v -> {
            CopyLinkTextHelper copyLinkTextHelper = new CopyLinkTextHelper(getContext());
            copyLinkTextHelper.CopyText((String) text.getText());
        });

        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
        });
    }

}
