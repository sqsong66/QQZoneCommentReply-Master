package com.example.qqzonecommentdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qqzonecommentdemo.R;
import com.example.qqzonecommentdemo.model.Comment;
import com.example.qqzonecommentdemo.utils.CommentHelper;
import com.example.qqzonecommentdemo.utils.CommentTagHandler;

import java.util.List;

/**
 * Created by 青松 on 2016/9/24.
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder> {

    private Context context;
    private List<Comment> commentList;
    private LayoutInflater inflater;
    private CommentHelper commentHelper;
    private CommentTagHandler tagHandler;

    public CommentListAdapter(final Context context, List<Comment> commentList, CommentTagHandler.OnCommentClickListener listener) {
        this.context = context;
        this.commentList = commentList;
        this.inflater = LayoutInflater.from(context);
        this.commentHelper = new CommentHelper();
        this.tagHandler = new CommentTagHandler(context, listener);
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.comment_tv.setTag(CommentTagHandler.KEY_COMMENT_LIST, commentList);
        holder.comment_tv.setTag(CommentTagHandler.KEY_COMMENT_ADAPTER, this);
        holder.comment_tv.setTag(CommentTagHandler.KEY_COMMENT_ITEM_POSITION, position);
        commentHelper.formatCommentText(comment, holder.comment_tv, tagHandler);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView comment_tv;

        public CommentViewHolder(View itemView) {
            super(itemView);
            this.comment_tv = (TextView) itemView.findViewById(R.id.comment_textview);
        }
    }

}
