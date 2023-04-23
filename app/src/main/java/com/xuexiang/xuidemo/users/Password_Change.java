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

package com.xuexiang.xuidemo.users;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.umeng.commonsdk.debug.E;
import com.xuexiang.xuidemo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Password_Change extends AppCompatActivity {

    private Button button;
    private EditText oldPassword;
    private EditText newPassword1;
    private EditText newPassword2;

    private ImageView back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_change);

        oldPassword = (EditText) findViewById(R.id.old_password);
        newPassword1 = (EditText) findViewById(R.id.new_password1);
        newPassword2 = (EditText) findViewById(R.id.new_password2);
        button = (Button) findViewById(R.id.change_password);

        // 返回上一页
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // 确认修改按钮
    public void click(View v) throws IOException {
        if (newPassword1.getText().toString() != newPassword2.getText().toString() ){
            AlertDialog alertDialog = new AlertDialog.Builder(Password_Change.this)
                    //标题
                    .setTitle("错误！")
                    //内容
                    .setMessage("新密码不一致，请重新确认新密码！")
                    //图标
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("确认", null)
                    .create();
            alertDialog.show();
        }else {
            // URL url = new URL("http://1.12.74.230/api/user/password");

            // 创建对象，添加数据
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("oldpassword",oldPassword.getText().toString());
                jsonObject.put("newpassword",newPassword1.getText().toString());
            }catch (JSONException e){
                e.printStackTrace();
            }

            // 创建对象，传递参数
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonObject.toString());

            // 创建对象，构建PUT请求
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://1.12.74.230/api/user/password")
                    .put(requestBody).build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()){
                    String responseBody = response.body().string();
                    AlertDialog alertDialog = new AlertDialog.Builder(Password_Change.this)
                            //标题
                            .setTitle("修改成功！")
                            //内容
                            .setMessage("")
                            //图标
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("确认", null)
                            .create();
                    alertDialog.show();

                }else {
                    // 网络请求出错
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

}
