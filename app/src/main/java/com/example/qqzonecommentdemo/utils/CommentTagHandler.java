package com.example.qqzonecommentdemo.utils;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.TextAppearanceSpan;
import android.view.View;

import com.example.qqzonecommentdemo.R;
import com.example.qqzonecommentdemo.model.User;

import org.xml.sax.XMLReader;

import java.util.HashMap;

/**
 * Created by 青松 on 2016/9/24.
 */
public class CommentTagHandler implements Html.TagHandler {

    public static final String TAG_COMMENTOR = "commentor";
    public static final String TAG_REPLYER = "replyer";
    public static final String TAG_CONTENT = "content";

    public static final int KEY_COMMENTOR = -2016;
    public static final int KEY_REPLYER = -20162;
    public static final int KEY_CONTENT = -42163;

    public static final int KEY_COMMENTOR_START = 10;
    public static final int KEY_REPLYER_START = 11;
    public static final int KEY_CONTENT_START = 12;

    public static final int CLICK_TYPE_COMMENTOR = 100;
    public static final int CLICK_TYPE_REPLYER = 101;
    public static final int CLICK_TYPE_CONTENT = 102;

    private HashMap<Integer, Integer> mMaps = new HashMap<Integer, Integer>();

    private Context context;
    private OnCommentClickListener listener;
    private ClickableSpan commentorSpan, replyerSpan, contentSpan;

    public CommentTagHandler(Context context, OnCommentClickListener l) {
        this.context = context;
        this.listener = l;
        this.commentorSpan = new BasicClickableSpan(context, CLICK_TYPE_COMMENTOR, l);
        this.replyerSpan = new BasicClickableSpan(context, CLICK_TYPE_REPLYER, l);
        this.contentSpan = new BasicClickableSpan(context, CLICK_TYPE_CONTENT, l);
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable editable, XMLReader xmlReader) {
        if (!tag.equalsIgnoreCase(TAG_COMMENTOR) && !tag.equalsIgnoreCase(TAG_CONTENT) && !tag.equalsIgnoreCase(TAG_REPLYER)) {
            return;
        }
        if (opening) {
            int start = editable.length();
            if (tag.equalsIgnoreCase(TAG_COMMENTOR)) {
                mMaps.put(KEY_COMMENTOR_START, start);
            } else if (tag.equalsIgnoreCase(TAG_REPLYER)) {
                mMaps.put(KEY_REPLYER_START, start);
            } else if (tag.equalsIgnoreCase(TAG_CONTENT)) {
                mMaps.put(KEY_CONTENT_START, start);
            }
        } else {
            int end = editable.length();
            if (tag.equalsIgnoreCase(TAG_COMMENTOR)) {
                int start = mMaps.get(KEY_COMMENTOR_START);
                editable.setSpan(new TextAppearanceSpan(context, R.style.Comment), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                editable.setSpan(commentorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (tag.equalsIgnoreCase(TAG_REPLYER)) {
                int start = mMaps.get(KEY_REPLYER_START);
                editable.setSpan(new TextAppearanceSpan(context, R.style.Comment), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                editable.setSpan(replyerSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (tag.equalsIgnoreCase(TAG_CONTENT)) {
                int start = mMaps.get(KEY_CONTENT_START);
                editable.setSpan(new TextAppearanceSpan(context, R.style.Comment), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                editable.setSpan(contentSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    class BasicClickableSpan extends ClickableSpan {

        private Context context;
        private int clickType;
        private OnCommentClickListener listener;

        public BasicClickableSpan(Context context, int clickType, OnCommentClickListener l) {
            this.context = context;
            this.clickType = clickType;
            this.listener = l;
        }

        @Override
        public void onClick(View view) {
            User commentUser = (User) view.getTag(KEY_COMMENTOR);
            User replyUser = (User) view.getTag(KEY_REPLYER);
            String content = (String) view.getTag(KEY_CONTENT);
            switch (clickType) {
                case CLICK_TYPE_COMMENTOR:
                    if (listener != null) {
                        listener.onCommentorClicked(view, commentUser);
                    }
                    break;
                case CLICK_TYPE_REPLYER:
                    if (listener != null) {
                        listener.onReplyerClicked(view, replyUser);
                    }
                    break;
                case CLICK_TYPE_CONTENT:
                    if (listener != null) {
                        listener.onCommentContentClicked(view, content, commentUser, replyUser);
                    }
                    break;
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            if (clickType == CLICK_TYPE_CONTENT) {
                ds.setColor(context.getResources().getColor(R.color.color_808080));
            } else {
                ds.setColor(context.getResources().getColor(R.color.colorNormalLinks));
            }
            ds.setUnderlineText(false);
        }
    }

    public interface OnCommentClickListener {

        void onCommentorClicked(View view, User commentUser);

        void onReplyerClicked(View view, User replyUser);

        void onCommentContentClicked(View view, String content, User commentUser, User replyUser);

    }

}
