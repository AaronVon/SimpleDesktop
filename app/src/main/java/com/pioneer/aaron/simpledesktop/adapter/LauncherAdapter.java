package com.pioneer.aaron.simpledesktop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pioneer.aaron.simpledesktop.R;
import com.pioneer.aaron.simpledesktop.module.App;

import java.util.List;

/**
 * Created by Aaron on 5/26/16.
 */
public class LauncherAdapter extends RecyclerView.Adapter<LauncherAdapter.ViewHolder> {

    private List<App> mAppList;
    private RecyclerViewItemClickListener mItemClickListener;
    private RecyclerViewItemLongClickListener mItemLongClickListener;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options;

    public LauncherAdapter(List<App> appList) {
        mAppList = appList;
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
        return new ViewHolder(view, mItemClickListener, mItemLongClickListener);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        App app = mAppList.get(position);
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

    public void setItemLongClickListener(RecyclerViewItemLongClickListener itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView mImageView;
        private TextView mTextView;
        private RecyclerViewItemClickListener mItemClickListener;
        private RecyclerViewItemLongClickListener mItemLongClickListener;

        public ViewHolder(View itemView, RecyclerViewItemClickListener itemClickListener, RecyclerViewItemLongClickListener itemLongClickListener) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.app_icon);
            mTextView = (TextView) itemView.findViewById(R.id.app_label);
            this.mItemClickListener = itemClickListener;
            this.mItemLongClickListener = itemLongClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mItemLongClickListener != null) {
                mItemLongClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
