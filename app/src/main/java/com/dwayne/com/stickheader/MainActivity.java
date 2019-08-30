package com.dwayne.com.stickheader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout stickyHeader;
    RecyclerView recyclerView;
    TextView tvHeader;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stickyHeader = findViewById(R.id.sticky_header);
        recyclerView = findViewById(R.id.recycler_view);
        tvHeader = findViewById(R.id.tv_header);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(this, generateData());
        recyclerView.setAdapter(itemAdapter);
        recyclerView.addOnScrollListener(new ScrollListener());
    }

    private List<String> generateData() {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            list.add("测试1");
        }
        for(int i = 0; i < 5; i++) {
            list.add("测试2");
        }
        for(int i = 0; i < 4; i++) {
            list.add("测试3");
        }
        for(int i = 0; i < 6; i++) {
            list.add("测试4");
        }
        return list;
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            View view = recyclerView.getChildAt(0);
            if(view != null && view.getContentDescription() != null) {
                stickyHeader.setVisibility(View.VISIBLE);
                tvHeader.setText(String.valueOf(view.getContentDescription()));
            }
            View underView =
                    recyclerView.findChildViewUnder(stickyHeader.getMeasuredWidth() / 2
                            , stickyHeader.getMeasuredHeight() + 1);//获取位于头部下方的itemView
            if(underView != null && underView.getTag() != null) {
                int tag = (int) underView.getTag();
                int deltaY = underView.getTop() - stickyHeader.getMeasuredHeight();
                if(tag == ItemAdapter.HAS_STICKY_VIEW) {//当该item显示头部
                    if(underView.getTop() > 0) {//当该item还未移出RecyclerView
                        stickyHeader.setTranslationY(deltaY); //移动头部
                    } else {//当Item移出顶部，粘性头部复原
                        stickyHeader.setTranslationY(0);
                    }
                } else {//当Item不包含粘性头部时
                    stickyHeader.setTranslationY(0);
                }
            }
        }
    }

}
