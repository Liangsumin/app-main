package com.xuexiang.xuidemo.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.xuexiang.xui.utils.KeyboardUtils;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.base.BaseActivity;
import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.xutil.display.Colors;
/**
 * 修改信息页面
 */
public class ModifyMessageActivity extends BaseActivity {
    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_message);
        ivBack = findViewById(R.id.iv_go_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(MainActivity.class);
            }
        });
    }

    @Override
    protected void initStatusBarStyle() {
        StatusBarUtils.initStatusBarStyle(this, false, Colors.TRANSPARENT);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return KeyboardUtils.onDisableBackKeyDown(keyCode) && super.onKeyDown(keyCode, event);
    }
}
