package com.example.qqzonecommentdemo.utils;

import android.os.Build;
import android.text.Html;
import android.widget.TextView;

import com.example.qqzonecommentdemo.model.Comment;
import com.example.qqzonecommentdemo.model.User;

/**
 * Created by 青松 on 2016/9/24.
 */
public class CommentHelper {

    public void formatCommentText(Comment comment, TextView textView, CommentTagHandler tagHandler) {
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

}
