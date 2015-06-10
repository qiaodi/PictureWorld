package com.qiaodi.pictureworldapplication;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.lucasr.twowayview.widget.SpannableGridLayoutManager;

import java.util.ArrayList;

/**
 * Created by QIAODI on 2015/4/22.
 */
public class MainViewAdapter  extends RecyclerView.Adapter<PictureItemHolder>{

    private ArrayList<PictureObject>  mPictureObjects;

//    static{
//        mPictureObjects = new ArrayList<PictureObject>();
//        mPictureObjects.add(new PictureObject("http://image.tianjimedia.com/uploadImages/2012/088/10G3888TY0HX_1000x500.jpg", null));
//        mPictureObjects.add(new PictureObject("http://image.tianjimedia.com/uploadImages/2012/088/CW71CYPXQ6PO_1000x500.jpg", null));
//        mPictureObjects.add(new PictureObject("http://image.tianjimedia.com/uploadImages/2012/088/3O4P49PRB99R_1000x500.jpg", null));
//        mPictureObjects.add(new PictureObject("http://image.tianjimedia.com/uploadImages/2012/088/Q172G69LA1RT_1000x500.jpg", null));
//        mPictureObjects.add(new PictureObject("http://image.tianjimedia.com/uploadImages/2012/088/BFPWRM8PBV74_1000x500.jpg", null));
//
//        mPictureObjects.add(new PictureObject("http://img4q.duitang.com/uploads/item/201503/08/20150308190453_nCZCB.jpeg", null));
//        mPictureObjects.add(new PictureObject("http://cdnq.duitang.com/uploads/item/201503/20/20150320045435_Wh3Ah.jpeg", null));
//        mPictureObjects.add(new PictureObject("http://cdnq.duitang.com/uploads/item/201503/01/20150301223521_hi8Za.jpeg", null));
//        mPictureObjects.add(new PictureObject("http://img5q.duitang.com/uploads/item/201503/03/20150303202714_WXYMB.jpeg", null));
//        mPictureObjects.add(new PictureObject("http://img4q.duitang.com/uploads/item/201502/26/20150226231036_hhPQu.jpeg", null));
//    }

    public void setPictureObjects(ArrayList<PictureObject> list){
        mPictureObjects = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(PictureItemHolder holder, int position) {
        if(mPictureObjects != null){
            holder.setImageURI(mPictureObjects.get(position));
            setPositon(holder.getView(), position);
        }
    }

    private void setPositon(View itemView, int itemId) {
        final SpannableGridLayoutManager.LayoutParams lp =
                (SpannableGridLayoutManager.LayoutParams) itemView.getLayoutParams();

        final int span1 = (itemId == 0 || itemId == 3 ? 2 : 1);
        final int span2 = (itemId == 0 ? 2 : (itemId == 3 ? 3 : 1));

        final int colSpan = (span2);
        final int rowSpan = (span1);

        if (lp.rowSpan != rowSpan || lp.colSpan != colSpan) {
            lp.rowSpan = rowSpan;
            lp.colSpan = colSpan;
            itemView.setLayoutParams(lp);
        }
    }

    @Override
    public int getItemCount() {
        return mPictureObjects != null? mPictureObjects.size() : 0;
    }

    @Override
    public PictureItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture_world , parent, false);
        return new PictureItemHolder(view);
    }
}
