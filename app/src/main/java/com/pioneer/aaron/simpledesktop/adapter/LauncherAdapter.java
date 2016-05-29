package com.pioneer.aaron.simpledesktop.adapter;

import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pioneer.aaron.simpledesktop.R;
import com.pioneer.aaron.simpledesktop.helper.ItemTouchHelperAdapter;
import com.pioneer.aaron.simpledesktop.helper.ItemTouchHelperViewHolder;
import com.pioneer.aaron.simpledesktop.helper.OnStartDragListener;
import com.pioneer.aaron.simpledesktop.module.App;
import com.pioneer.aaron.simpledesktop.util.Constant;

import java.util.Collections;
import java.util.List;

/**
 * Created by Aaron on 5/26/16.
 */
public class LauncherAdapter extends RecyclerView.Adapter<LauncherAdapter.ViewHolder> implements ItemTouchHelperAdapter{

    private List<App> mAppList;
    private RecyclerViewItemClickListener mItemClickListener;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options;
    private OnStartDragListener mDragListener;

    public LauncherAdapter(List<App> appList, OnStartDragListener onStartDragListener) {
        mAppList = appList;
        mDragListener = onStartDragListener;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(null)
                .showImageForEmptyUri(null)
                .showImageOnFail(null)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(false)
                .build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.launcher_item, parent, false);
        ViewHolder holder = new ViewHolder(view, mItemClickListener);
        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        App app = mAppList.get(position);

        holder.mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
        holder.mImageView.setImageDrawable(app.getApp_icon());
        holder.mTextView.setText(app.getApp_label());

    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }

    public void setItemClickListener(RecyclerViewItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mAppList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        Constant.DEFAULT_FILTER += mAppList.get(position).getApp_label();
        mAppList.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, ItemTouchHelperViewHolder{

        private ImageView mImageView;
        private TextView mTextView;
        private RecyclerViewItemClickListener mItemClickListener;

        public ViewHolder(View itemView, RecyclerViewItemClickListener itemClickListener) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.app_icon);
            mTextView = (TextView) itemView.findViewById(R.id.app_label);
            this.mItemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
