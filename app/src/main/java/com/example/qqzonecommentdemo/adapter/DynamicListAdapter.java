package com.example.qqzonecommentdemo.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qqzonecommentdemo.R;
import com.example.qqzonecommentdemo.model.Comment;
import com.example.qqzonecommentdemo.model.Dynamic;

import java.util.List;

/**
 * Created by 青松 on 2016/9/24.
 */
public class DynamicListAdapter extends RecyclerView.Adapter<DynamicListAdapter.DynamicViewHolder> {

    private Context context;
    private List<Dynamic> dynamicList;
    private LayoutInflater inflater;

    public DynamicListAdapter(Context context, List<Dynamic> dynamicList) {
        this.context = context;
        this.dynamicList = dynamicList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public DynamicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_dynamic, parent, false);
        return new DynamicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DynamicViewHolder holder, int position) {
        Dynamic dynamic = dynamicList.get(position);
        List<Comment> commentList = dynamic.getCommentList();
        holder.comment_count_tv.setText("评论(" + commentList.size() + ")");

        holder.commentRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        CommentListAdapter adapter = new CommentListAdapter(context, commentList);
        holder.commentRecyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return dynamicList.size();
    }

    public static class DynamicViewHolder extends RecyclerView.ViewHolder {

        RecyclerView commentRecyclerView;
        TextView comment_count_tv;

        public DynamicViewHolder(View itemView) {
            super(itemView);
            commentRecyclerView = (RecyclerView) itemView.findViewById(R.id.comment_recyclerview);
            comment_count_tv = (TextView) itemView.findViewById(R.id.comment_count_tv);
        }
    }

}
