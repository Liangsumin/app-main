package com.xuexiang.xuidemo.fragment;

import static com.xuexiang.xutil.XUtil.runOnUiThread;

import android.os.Bundle;
import android.os.Handler;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xuidemo.DemoDataProvider;
import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.adapter.NewsCardViewListAdapter;
import com.xuexiang.xuidemo.adapter.entity.NewInfo;
import com.xuexiang.xuidemo.base.BaseFragment;
import com.xuexiang.xuidemo.utils.HttpCallbackListener;
import com.xuexiang.xuidemo.utils.HttpUtil;
import com.xuexiang.xuidemo.utils.Utils;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @author XUE
 * @since 2019/5/9 11:54
 */
public class SimpleListFragment extends BaseFragment {

    private static final String KEY_IS_SPECIAL = "key_is_special";

    @BindView(R.id.recyclerView)
    SwipeRecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private NewsCardViewListAdapter mAdapter;

    private String content;

    @AutoWired(name = KEY_IS_SPECIAL)
    boolean isSpecial;

    public List<NewInfo> getInfo() {

        JSONObject res = JSON.parseObject(content);
        JSONObject data = res.getJSONObject("data");
        int total = data.getIntValue("total");
        JSONArray records = data.getJSONArray("records");
        List<NewInfo> list = new ArrayList<>();

        for (int i=0; i<total; i++) {
            JSONObject record = (JSONObject) records.get(i);
            System.out.println(record.getString("title"));
            list.add(new NewInfo(" ",record.getString("title"))
                    .setSummary(record.getString("text"))
                    .setDetailUrl(record.getString("cid")));
        }

        return list;
    }

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected TitleBar initTitle() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.include_recycler_view_refresh;
    }

    @Override
    protected void initViews() {

        getContent();
    }

    @Override
    protected void initListeners() {

    }

    protected void getContent() {

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            String url = "http://134.175.217.138:8001/cases/page/1/100";
            String comurl = HttpUtil.getURLWithParams(url,params);
            HttpUtil.sendHttpRequest(comurl, new HttpCallbackListener() {
                @Override
                public String onFinish(String response) {
                    System.out.println("susses");

                    runOnUiThread(() -> {
                        content = response;

                        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());

                        recyclerView.setAdapter(mAdapter = new NewsCardViewListAdapter());

                        mAdapter.refresh(getInfo());

                        mAdapter.setOnItemClickListener((itemView, item, position) -> openNewPage(Specific_Page.class,"id",item.getDetailUrl()));

                        swipeRefreshLayout.setEnabled(true);
                        swipeRefreshLayout.setOnRefreshListener(() -> {
                            getContent();
                            swipeRefreshLayout.setRefreshing(false);
                        });
                    });

                    return response;
                }

                @Override
                public void onError(Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
