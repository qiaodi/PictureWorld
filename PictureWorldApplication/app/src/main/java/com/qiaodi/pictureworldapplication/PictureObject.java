package com.qiaodi.pictureworldapplication;

import android.support.annotation.Nullable;

/**
 * Created by QIAODI on 2015/4/23.
 */
public class PictureObject {

    private String mPictureUri;

    private String mRelevantUri;

    private boolean mIsShowned;

    private boolean mIsInterested;

    public PictureObject(String mPictureUri, @Nullable String mRelevantUri){
        this.mPictureUri = mPictureUri;
        this.mRelevantUri = mRelevantUri;
        this.mIsInterested = false;
        this.mIsShowned = false;
    }

    public boolean isShowned() {
        return mIsShowned;
    }

    public boolean isInterested() {
        return mIsInterested;
    }

    public String getPictureUri() {
        return mPictureUri;
    }

    public String getRelevantUri() {
        return mRelevantUri;
    }

    public void setIsInterested(boolean mIsInterested) {
        this.mIsInterested = mIsInterested;
    }

    public void setIsShowned(boolean mIsShowned) {
        this.mIsShowned = mIsShowned;
    }

    public void setPictureUri(String mPictureUri) {
        this.mPictureUri = mPictureUri;
    }

    public void setRelevantUri(String mRelevantUri) {
        this.mRelevantUri = mRelevantUri;
    }
}
