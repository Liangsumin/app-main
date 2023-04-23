package com.xuexiang.xuidemo.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.base.BaseFragment;
import com.xuexiang.xuidemo.fragment.KnowledgePage;
import com.xuexiang.xuidemo.fragment.Knowledge_specific;
import com.xuexiang.xuidemo.fragment.Phrase_specific;
import com.xuexiang.xuidemo.utils.KnowledgeData;

public class PhraseAdapter extends ArrayAdapter<KnowledgeData> {

    public PhraseAdapter(@NonNull Context context, int resource, @NonNull List<KnowledgeData> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        KnowledgeData data =getItem(position);
        View view=LayoutInflater.from(getContext()).inflate(R.layout.aaa_item,parent,false);

        ImageView image =view.findViewById(R.id.iv_image);
        image.setImageResource(data.picture);

        TextView title =view.findViewById(R.id.tv_title);
        title.setText(data.title);

        TextView content=view.findViewById(R.id.tv_summary);
        content.setText(data.content);

        view.setOnClickListener(v -> {
            System.out.println("item " + position);
            BaseFragment baseFragment = new BaseFragment() {
                @Override
                protected int getLayoutId() {
                    return 0;
                }

                @Override
                protected void initViews() {

                }
            };
            baseFragment.openNewPage(Phrase_specific.class,"id",data.id);
        });

        return view;
    }
}