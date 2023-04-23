/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
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

package com.xuexiang.xuidemo.fragment.components.layout;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.grouplist.XUICommonListItemView;
import com.xuexiang.xui.widget.grouplist.XUIGroupListView;
import com.xuexiang.xui.widget.progress.loading.MiniLoadingView;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.activity.MainActivity;
import com.xuexiang.xuidemo.activity.ModifyMessageActivity;
import com.xuexiang.xuidemo.base.BaseFragment;
import com.xuexiang.xutil.app.ActivityUtils;

import butterknife.BindView;

/**
 * {@link XUIGroupListView} 的使用示例
 *
 * @author xuexiang
 * @since 2019/1/3 上午11:38
 */
@Page(name = "我",anim = CoreAnim.none)
public class MineFragment extends BaseFragment {

    @BindView(R.id.ll_modify_message)
    LinearLayout llModify;

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews() {
        llModify.setOnClickListener(v -> ActivityUtils.startActivity(ModifyMessageActivity.class));
    }


}
