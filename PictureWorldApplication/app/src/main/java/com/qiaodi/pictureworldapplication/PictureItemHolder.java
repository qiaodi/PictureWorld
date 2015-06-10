package com.qiaodi.pictureworldapplication;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by QIAODI on 2015/4/22.
 */
public class PictureItemHolder extends RecyclerView.ViewHolder {

    private SimpleDraweeView mPicture_Context;

    private PictureObject mPictureObject;

    public PictureItemHolder(View view){
        super(view);
        mPicture_Context = (SimpleDraweeView)view.findViewById(R.id.picture_content);
    }

    public void setImageURI(PictureObject object){
        mPictureObject = object;
        mPicture_Context.setImageURI(Uri.parse(object.getPictureUri()));
    }

    public View getView(){
        return mPicture_Context;
    }
}
