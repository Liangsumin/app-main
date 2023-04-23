package com.xuexiang.xuidemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xuidemo.R;
import com.xuexiang.xuidemo.input.MyKeyboard;

import java.util.List;

public class KeyBoardAdapter extends RecyclerView.Adapter<KeyBoardAdapter.RecycleHolder> {

    public List<String> data;
    private Context context;
    public MyKeyboard keyboardService;

    public KeyBoardAdapter(List<String> data,MyKeyboard keyboardService, Context context) {
        this.data = data;
        this.context = context;
        this.keyboardService = keyboardService;
    }

    @NonNull
    @Override
    public RecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.keyboard_words_one,null);
        return new RecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleHolder holder, int position) {
        holder.wordView.setText(data.get(position));
        holder.wordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardService.choseCandidate(position);
            }
        });
        //holder.wordView.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String url);
    }

    public class RecycleHolder extends RecyclerView.ViewHolder {

        private TextView wordView;

        public RecycleHolder(@NonNull View itemView) {
            super(itemView);
            wordView = itemView.findViewById(R.id.word_one);
        }
    }
}
