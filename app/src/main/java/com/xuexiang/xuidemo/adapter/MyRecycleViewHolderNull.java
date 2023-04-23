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

package com.xuexiang.xuidemo.adapter;

import android.view.View;
import android.widget.LinearLayout;

import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.utils.DataModel;

public class MyRecycleViewHolderNull extends MyRecycleViewHolder{

    public LinearLayout linearLayout;
    public MyRecycleViewHolderNull(View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.Null_Layout);
    }

    @Override
    public void bindHolder(DataModel data) {
        linearLayout.setMinimumHeight(data.height);
    }
}
