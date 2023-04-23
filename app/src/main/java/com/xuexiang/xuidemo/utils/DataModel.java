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

package com.xuexiang.xuidemo.utils;

import java.util.ArrayList;
import java.util.List;

public class DataModel {

    public static  final int TYPE_TAB=1;
    public static  final int TYPE_TITLE=2;
    public static final int TYPE_ITEM=3;

    public int type;

    public String tab1;
    public String tab2;
    public String tab3;

    public String title;

    public int height;

    public List<String> titles = new ArrayList<>();

    public List<String> content = new ArrayList<>();

    public List<Integer> picture = new ArrayList<>();

    public String no;

    public String Content;

    public String id;

    public List<String> ids = new ArrayList<>();

}
