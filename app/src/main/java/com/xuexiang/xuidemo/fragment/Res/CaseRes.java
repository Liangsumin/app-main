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

package com.xuexiang.xuidemo.fragment.Res;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xuidemo.adapter.entity.NewInfo;

import java.util.ArrayList;
import java.util.List;

@Page(name = "搜索结果")
public class CaseRes extends Res {

    @Override
    protected void initViews() {
        super.initViews();
        URL = "http://1.12.74.230:10010/cases/page/1/100?title=";
        getContent();
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
}
