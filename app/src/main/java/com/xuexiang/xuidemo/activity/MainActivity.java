package com.xuexiang.xuidemo.activity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.umeng.analytics.MobclickAgent;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.utils.ThemeUtils;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xui.widget.guidview.GuideCaseView;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.adapter.menu.DrawerAdapter;
import com.xuexiang.xuidemo.adapter.menu.DrawerItem;
import com.xuexiang.xuidemo.adapter.menu.SimpleItem;
import com.xuexiang.xuidemo.adapter.menu.SpaceItem;
import com.xuexiang.xuidemo.base.BaseActivity;
import com.xuexiang.xuidemo.fragment.components.layout.GroupListViewFragment;
import com.xuexiang.xuidemo.fragment.components.layout.MineFragment;
import com.xuexiang.xuidemo.fragment.components.tabbar.tabsegment.Personal;
import com.xuexiang.xuidemo.fragment.components.tabbar.tabsegment.TabSegmentFixMode;
import com.xuexiang.xuidemo.fragment.other.SettingFragment;
import com.xuexiang.xuidemo.utils.HttpCallbackListener;
import com.xuexiang.xuidemo.utils.HttpUtil;
import com.xuexiang.xuidemo.utils.SettingSPUtils;
import com.xuexiang.xuidemo.utils.TokenUtils;
import com.xuexiang.xuidemo.utils.Utils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xutil.common.ClickUtils;
import com.xuexiang.xutil.system.DeviceUtils;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.callback.DragStateListener;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;

import butterknife.BindView;


/**
 * 项目主页面
 *
 * @author xuexiang
 * @since 2018/11/13 下午5:20
 */
public class MainActivity extends BaseActivity implements DrawerAdapter.OnItemSelectedListener, ClickUtils.OnClick2ExitListener {
    private static final int POS_COMPONENTS = 0;
    private static final int POS_UTILITIES = 1;
    private static final int POS_EXPANDS = 2;
    private static final int POS_ABOUT = 3;
    private static final int POS_LOGOUT = 5;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    private SlidingRootNav mSlidingRootNav;
    private LinearLayout mLLMenu;
    private String[] mMenuTitles;
    private Drawable[] mMenuIcons;
    private DrawerAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //登记一下
        MobclickAgent.onProfileSignIn(DeviceUtils.getAndroidID());

        initData();

        initViews();

        initSlidingMenu(savedInstanceState);

        initOthers();

        test();

        StatusBarUtils.translucent(this, Color.parseColor("#00000000"));
        StatusBarUtils.setStatusBarLightMode(this);
    }

    private void test() {
        System.out.println("API TEST");
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            String url = "http://1.12.74.230:10010/user/login/123123";
            String comurl = HttpUtil.getURLWithParams(url,params);
            HttpUtil.sendHttpRequest(comurl, new HttpCallbackListener() {
                @Override
                public String onFinish(String response) {
                    System.out.println(response);
                    return response;
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

    private void initData() {
        mMenuTitles = ResUtils.getStringArray(this, R.array.menu_titles);
        mMenuIcons = ResUtils.getDrawableArray(this, R.array.menu_icons);
    }

    @Override
    protected boolean isSupportSlideBack() {
        return false;
    }

    private void initViews() {
        WidgetUtils.clearActivityBackground(this);
        initTab();
    }

    /**
     * 初始化Tab
     */
    @SuppressLint("ResourceAsColor")
    private void initTab() {

        mTabLayout.setTabTextColors(Color.parseColor("#7B7B7B"),Color.parseColor("#879AF2"));
        mTabLayout.setTabIconTint(ColorStateList.valueOf(Color.parseColor("#879AF2")));

        WidgetUtils.addTabWithoutRipple(mTabLayout, "首页", SettingSPUtils.getInstance().isUseCustomTheme() ? R.drawable.custom_selector_icon_tabbar_component : R.drawable.selector_icon_tabbar_component);
        WidgetUtils.addTabWithoutRipple(mTabLayout, "知识", SettingSPUtils.getInstance().isUseCustomTheme() ? R.drawable.custom_selector_icon_tabbar_util : R.drawable.selector_icon_tabbar_util);
        WidgetUtils.addTabWithoutRipple(mTabLayout, "我的", SettingSPUtils.getInstance().isUseCustomTheme() ? R.drawable.custom_selector_icon_tabbar_expand : R.drawable.selector_icon_tabbar_expand);
        WidgetUtils.setTabLayoutTextFont(mTabLayout);

        switchPage(TabSegmentFixMode.class);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mAdapter.setSelected(tab.getPosition());
                switch (tab.getPosition()) {
                    case POS_COMPONENTS:
                        switchPage(TabSegmentFixMode.class);
                        break;
                    case 1:
                        switchPage(Personal.class);
                        break;
                    case 2:
                        switchPage(MineFragment.class);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void initOthers() {
//        GuideTipsDialog.showTips(this);
        //静默检查版本更新
        Utils.checkUpdate(this, false);
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        initSlidingMenu(null);
    }

    public void openMenu() {
        if (mSlidingRootNav != null) {
            mSlidingRootNav.openMenu();
        }
    }

    public void closeMenu() {
        if (mSlidingRootNav != null) {
            mSlidingRootNav.closeMenu();
        }
    }

    public boolean isMenuOpen() {
        if (mSlidingRootNav != null) {
            return mSlidingRootNav.isMenuOpened();
        }
        return false;
    }

    private void initSlidingMenu(Bundle savedInstanceState) {
        mSlidingRootNav = new SlidingRootNavBuilder(this)
                .withGravity(ResUtils.isRtl(this) ? SlideGravity.RIGHT : SlideGravity.LEFT)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();


        final AppCompatImageView ivSetting = mSlidingRootNav.getLayout().findViewById(R.id.iv_setting);
        ivSetting.setOnClickListener(v -> openNewPage(SettingFragment.class));
        ViewUtils.setVisibility(mLLMenu, false);

        mAdapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_COMPONENTS).setChecked(true),
                createItemFor(POS_UTILITIES),
                createItemFor(POS_EXPANDS),
                createItemFor(POS_ABOUT),
                new SpaceItem(48),
                createItemFor(POS_LOGOUT)));
        mAdapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(mAdapter);

        mAdapter.setSelected(POS_COMPONENTS);
        mSlidingRootNav.setMenuLocked(false);
        mSlidingRootNav.getLayout().addDragStateListener(new DragStateListener() {
            @Override
            public void onDragStart() {
                ViewUtils.setVisibility(mLLMenu, true);
            }

            @Override
            public void onDragEnd(boolean isMenuOpened) {
                ViewUtils.setVisibility(mLLMenu, isMenuOpened);
                if (isMenuOpened) {
                    if (!GuideCaseView.isShowOnce(MainActivity.this, getString(R.string.guide_key_sliding_root_navigation))) {
                        final GuideCaseView guideStep1 = new GuideCaseView.Builder(MainActivity.this)
                                .title("点击进入，可切换主题样式哦～～")
                                .titleSize(18, TypedValue.COMPLEX_UNIT_SP)
                                .focusOn(ivSetting)
                                .build();

                        GuideCaseView.setShowOnce(MainActivity.this, getString(R.string.guide_key_sliding_root_navigation));
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(int position) {
        switch (position) {
            case POS_COMPONENTS:
            case POS_UTILITIES:
            case POS_EXPANDS:
                if (mTabLayout != null) {
                    TabLayout.Tab tab = mTabLayout.getTabAt(position);
                    if (tab != null) {
                        tab.select();
                    }
                }
                mSlidingRootNav.closeMenu();
                break;
            case POS_ABOUT:
                break;
            case POS_LOGOUT:
                DialogLoader.getInstance().showConfirmDialog(
                        this,
                        getString(R.string.lab_logout_confirm),
                        getString(R.string.lab_yes),
                        (dialog, which) -> {
                            dialog.dismiss();
                            TokenUtils.handleLogoutSuccess();
                            finish();
                        },
                        getString(R.string.lab_no),
                        (dialog, which) -> dialog.dismiss()
                );
                break;
            default:
                break;
        }
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(mMenuIcons[position], mMenuTitles[position])
                .withIconTint(ThemeUtils.resolveColor(this, R.attr.xui_config_color_content_text))
                .withTextTint(ThemeUtils.resolveColor(this, R.attr.xui_config_color_content_text))
                .withSelectedIconTint(ThemeUtils.getMainThemeColor(this))
                .withSelectedTextTint(ThemeUtils.getMainThemeColor(this));
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isMenuOpen()) {
                closeMenu();
            } else {
                ClickUtils.exitBy2Click(2000, this);
            }
        }
        return true;
    }

    /**
     * 再点击一次
     */
    @Override
    public void onRetry() {
        XToastUtils.toast("再按一次退出程序");
    }

    /**
     * 退出
     */
    @Override
    public void onExit() {
        moveTaskToBack(true);
    }
}
