package com.example.qqzonecommentdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qqzonecommentdemo.R;
import com.example.qqzonecommentdemo.model.Comment;
import com.example.qqzonecommentdemo.model.User;
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

    public CommentListAdapter(final Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
        this.inflater = LayoutInflater.from(context);
        this.commentHelper = new CommentHelper();
        this.tagHandler = new CommentTagHandler(context, new CommentTagHandler.OnCommentClickListener() {
            @Override
            public void onCommentorClicked(View view, User commentUser) {
                Toast.makeText(context, commentUser.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReplyerClicked(View view, User replyUser) {
                Toast.makeText(context, replyUser.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCommentContentClicked(View view, String content, User commentUser, User replyUser) {
                Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
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
