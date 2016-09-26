package com.example.qqzonecommentdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 青松 on 2016/9/24.
 */
public class Dynamic implements Parcelable {

    private String content;
    private int imageCount;
    private List<Comment> commentList;

    public Dynamic() {
    }

    protected Dynamic(Parcel in) {
        content = in.readString();
        imageCount = in.readInt();
        commentList = in.createTypedArrayList(Comment.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeInt(imageCount);
        dest.writeTypedList(commentList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Dynamic> CREATOR = new Creator<Dynamic>() {
        @Override
        public Dynamic createFromParcel(Parcel in) {
            return new Dynamic(in);
        }

        @Override
        public Dynamic[] newArray(int size) {
            return new Dynamic[size];
        }
    };

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
