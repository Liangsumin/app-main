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
import android.widget.TextView;

import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.utils.DataModel;

public class MyRecycleViewHolderKs extends MyRecycleViewHolder{

    private TextView item_no;

    private TextView item_content;

    public MyRecycleViewHolderKs(View itemView) {
        super(itemView);
        item_no = itemView.findViewById(R.id.item_no);
        item_content = itemView.findViewById(R.id.item_content);
    }

    @Override
    public void bindHolder(DataModel data) {
        item_no.setText(data.no);
        item_content.setText(data.Content);
    }
}
