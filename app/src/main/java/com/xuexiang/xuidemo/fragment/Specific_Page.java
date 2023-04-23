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

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.base.BaseFragment;
import com.xuexiang.xuidemo.utils.HttpCallbackListener;
import com.xuexiang.xuidemo.utils.HttpUtil;
import com.xuexiang.xuidemo.utils.ViewLikeUtils;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import butterknife.BindView;

@Page(name = "")
public class Specific_Page extends BaseFragment {

    @BindView(R.id.special_title)
    TextView title;

    @BindView(R.id.special_author)
    TextView author;

    @BindView(R.id.special_txt)
    TextView txt;

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.favorites_icon)
    ImageView f_icon;

    @BindView(R.id.good_icon)
    ImageView g_icon;

    @BindView(R.id.favorites_text)
    TextView f_text;

    @BindView(R.id.good_no)
    TextView g_no;

    private boolean IS_GOOD = false;

    private boolean IS_FAVOR = false;

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.specific_page;
    }

    @Override
    protected void initViews() {
        StatusBarUtils.translucent(getActivity(),Color.parseColor("#00000000"));
        StatusBarUtils.setStatusBarLightMode(getActivity());

        Bundle bundle = getArguments();
        System.out.println(bundle.getString("id","no"));

        setContent(bundle.getString("id","1"));

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
                g_icon.setImageResource(R.drawable.good_grey);
                g_no.setTextColor(Color.parseColor("#707070"));
                int no = Integer.parseInt((String) g_no.getText()) - 1;
                g_no.setText(String.valueOf(no));
                IS_GOOD = false;
            } else {
                viewLikeUtils.startLikeAnim(animator);
                g_icon.setImageResource(R.drawable.good_fill);
                int no = Integer.parseInt((String) g_no.getText()) + 1;
                g_no.setText(String.valueOf(no));
                IS_GOOD = true;
            }
        });

        new ViewLikeUtils(f_icon, textView, (view, toggle, viewLikeUtils) -> {
            if (IS_FAVOR) {
                f_icon.setImageResource(R.drawable.favorites_grey);
                f_text.setTextColor(Color.parseColor("#707070"));
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

    protected void setContent(String id) {

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            String url = "http://1.12.74.230:10010/cases/" + id;
            String comurl = HttpUtil.getURLWithParams(url,params);
            HttpUtil.sendHttpRequest(comurl, new HttpCallbackListener() {
                @Override
                public String onFinish(String response) {
                    JSONObject jsonObject = JSON.parseObject(response);
                    System.out.println(response);
                    String Title = jsonObject.getJSONObject("data").getString("title");
                    String Content = jsonObject.getJSONObject("data").getString("text");
                    String Author = jsonObject.getJSONObject("data").getString("createTime");

                    runOnUiThread(() -> {
                        title.setText(Title);
                        author.setText(Author);

                        RichText.initCacheDir(getActivity());
                        RichText.from(Content).bind(this)
                                .showBorder(false)
                                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                                .into(txt);

                    });
                    return Title;
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
