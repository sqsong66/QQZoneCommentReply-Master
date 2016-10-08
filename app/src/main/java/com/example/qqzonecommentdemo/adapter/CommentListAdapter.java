package com.example.qqzonecommentdemo.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qqzonecommentdemo.R;
import com.example.qqzonecommentdemo.model.Comment;
import com.example.qqzonecommentdemo.model.User;
import com.example.qqzonecommentdemo.utils.CommentTagHandler;
import com.example.qqzonecommentdemo.utils.LinkTouchMovementMethod;

import java.util.List;

/**
 * Created by 青松 on 2016/9/24.
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder> {

    private List<Comment> commentList;
    private LayoutInflater inflater;
    private CommentTagHandler tagHandler;

    public CommentListAdapter(final Context context, List<Comment> commentList, CommentTagHandler.OnCommentClickListener listener) {
        this.commentList = commentList;
        this.inflater = LayoutInflater.from(context);
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
        formatCommentText(comment, holder.comment_tv, tagHandler);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    private void formatCommentText(Comment comment, TextView textView, CommentTagHandler tagHandler) {
        User commentUser = comment.getCommentUser();
        User replayUser = comment.getReplayUser();
        String content = comment.getContent();

        String formatText = "";
        if (replayUser == null) {
            formatText = String.format("<html><%s>%s</%s>: <%s>%s</%s></html>",
                    CommentTagHandler.TAG_COMMENTOR, commentUser.getName(), CommentTagHandler.TAG_COMMENTOR,
                    CommentTagHandler.TAG_CONTENT, content, CommentTagHandler.TAG_CONTENT);
        } else {
            formatText = String.format("<html><%s>%s</%s>回复<%s>%s</%s>: <%s>%s</%s></html>",
                    CommentTagHandler.TAG_REPLYER, replayUser.getName(), CommentTagHandler.TAG_REPLYER,
                    CommentTagHandler.TAG_COMMENTOR, commentUser.getName(), CommentTagHandler.TAG_COMMENTOR,
                    CommentTagHandler.TAG_CONTENT, content, CommentTagHandler.TAG_CONTENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(formatText, Html.FROM_HTML_MODE_LEGACY, null, tagHandler));
        } else {
            textView.setText(Html.fromHtml(formatText, null, tagHandler));
        }
        textView.setClickable(true);
        textView.setMovementMethod(new LinkTouchMovementMethod());
        textView.setTag(CommentTagHandler.KEY_COMMENTOR, commentUser);
        textView.setTag(CommentTagHandler.KEY_REPLYER, replayUser);
        textView.setTag(CommentTagHandler.KEY_CONTENT, content);
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView comment_tv;

        public CommentViewHolder(View itemView) {
            super(itemView);
            this.comment_tv = (TextView) itemView.findViewById(R.id.comment_textview);
        }
    }

}
