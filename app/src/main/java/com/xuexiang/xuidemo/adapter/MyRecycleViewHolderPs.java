package com.xuexiang.xuidemo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.adapter.MyRecycleViewHolder;
import com.xuexiang.xuidemo.base.BaseFragment;
import com.xuexiang.xuidemo.fragment.KnowledgePage;
import com.xuexiang.xuidemo.utils.DataModel;
import com.xuexiang.xuidemo.utils.KnowledgeData;

import java.util.ArrayList;
import java.util.List;

public class MyRecycleViewHolderPs extends MyRecycleViewHolder {

    public TextView title;
    public ListView list;

    public MyRecycleViewHolderPs(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        list = itemView.findViewById(R.id.listView);
    }
    @Override
    public void bindHolder(DataModel data) {

        List<KnowledgeData> kdatas = new ArrayList<>();

        KnowledgeData kdata;

        System.out.println(data.ids);

        for (int i=0; i<data.titles.size(); i++) {

            kdata = new KnowledgeData();
            kdata.title = data.titles.get(i);
            kdata.content = data.content.get(i);
            kdata.picture = data.picture.get(i);
            kdata.id = data.ids.get(i);
            kdatas.add(kdata);
        }

        PhraseAdapter adapter = new PhraseAdapter(itemView.getContext(), R.layout.aaa_item, kdatas);
        list.setAdapter(adapter);

        title.setText(data.title);

        int totalHeight = 0;
        for (int i = 0,len = adapter.getCount(); i < len; i++) {
            View listItem = adapter.getView(i, null, list);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = list.getLayoutParams();
        params.height = totalHeight + (list.getDividerHeight() * (adapter.getCount() - 1) );
        list.setLayoutParams(params);

        initOnClickListener();
    }

    public void initOnClickListener() {

    }
}