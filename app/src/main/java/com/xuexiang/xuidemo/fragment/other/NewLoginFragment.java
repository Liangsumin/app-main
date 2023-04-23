/*
 * Copyright (C) 2021 xuexiangjys(xuexiangjys@163.com)
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

package com.xuexiang.xuidemo.fragment.other;

import static com.xuexiang.xuidemo.fragment.other.ServiceProtocolFragment.KEY_PROTOCOL_TITLE;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.utils.DrawableUtils;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.ThemeUtils;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.activity.MainActivity;
import com.xuexiang.xuidemo.base.BaseFragment;
import com.xuexiang.xuidemo.utils.PrivacyUtils;
import com.xuexiang.xuidemo.utils.SettingSPUtils;
import com.xuexiang.xuidemo.utils.TokenUtils;
import com.xuexiang.xuidemo.utils.sdkinit.UMengInit;
import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.xutil.common.RandomUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 登录页面
 *
 * @author xuexiang
 * @since 2019-11-17 22:15
 */
@Page(anim = CoreAnim.none)
public class NewLoginFragment extends BaseFragment {

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_new;
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle()
                .setImmersive(true);
        titleBar.setBackgroundColor(Color.TRANSPARENT);
        titleBar.setTitle("");
//        titleBar.setLeftImageDrawable(DrawableUtils.setTint(ResUtils.getVectorDrawable(getContext(), R.drawable.ic_login_close), ThemeUtils.getMainThemeColor(getContext())));
        return titleBar;
    }

    @Override
    protected void initViews() {

    }

    @SingleClick
    @OnClick({ R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                ActivityUtils.startActivity(MainActivity.class);
                break;
            default:
                break;
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
