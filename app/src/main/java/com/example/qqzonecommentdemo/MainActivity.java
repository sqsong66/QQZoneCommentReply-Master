package com.example.qqzonecommentdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.qqzonecommentdemo.adapter.DynamicListAdapter;
import com.example.qqzonecommentdemo.model.Comment;
import com.example.qqzonecommentdemo.model.Dynamic;
import com.example.qqzonecommentdemo.model.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Dynamic> mDynamicList = new ArrayList<>();
    private DynamicListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvents();
        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initEvents() {
        mAdapter = new DynamicListAdapter(this, mDynamicList);
        recyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            Dynamic dynamic = new Dynamic();
            List<Comment> commentList = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                Comment comment = new Comment();
                comment.setCommentUser(getUsre(j, 0));
                if (j % 2 == 0) {
                    comment.setContent("哈哈，这图片蛮漂亮的!");
                } else {
                    comment.setReplayUser(getUsre(j, 1));
                    comment.setContent("赞同，确实不错!");
                }
                commentList.add(comment);
            }
            dynamic.setCommentList(commentList);
            mDynamicList.add(dynamic);
        }
        mAdapter.notifyDataSetChanged();
    }

    private User getUsre(int index, int type) {
        User user = new User();
        user.setId(index);
        if (type == 0) {
            user.setName("张三" + index);
        } else {
            user.setName("李四" + index);
        }
        return user;
    }

}
