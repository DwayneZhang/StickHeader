package com.dwayne.com.stickheader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * @author Dwayne
 * @email dev1024@foxmail.com
 * @time 2019/5/19 20:11
 * @change
 * @chang time
 * @class describe
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;
    private List<String> lists;
    private Context context;


    public ItemAdapter(Context context, List<String> lists) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // 实例化展示的view
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list
                , viewGroup, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvContent.setText("这是第" + i + "条内容");
        viewHolder.tvHeader.setText(lists.get(i));
        viewHolder.itemView.setContentDescription(lists.get(i));
        if(i == 0) {
            viewHolder.stickyHeader.setVisibility(View.VISIBLE);
            viewHolder.itemView.setTag(FIRST_STICKY_VIEW);
        } else {
            /**
             * 根据自己头部需要展示的文本判断，如果和上一条相同，则隐藏
             * 判断头部隐藏与否的方式很多，根据自己的实际情况选择
             */
            if(lists.get(i).equals(lists.get(i - 1))) {
                viewHolder.stickyHeader.setVisibility(View.GONE);
                viewHolder.itemView.setTag(NONE_STICKY_VIEW);
            } else {
                viewHolder.stickyHeader.setVisibility(View.VISIBLE);
                viewHolder.itemView.setTag(HAS_STICKY_VIEW);
            }
        }
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout stickyHeader;
        TextView tvHeader, tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            stickyHeader = itemView.findViewById(R.id.sticky_header);
            tvHeader = itemView.findViewById(R.id.tv_header);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
