package com.pioneer.aaron.simpledesktop;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.pioneer.aaron.simpledesktop.adapter.LauncherAdapter;
import com.pioneer.aaron.simpledesktop.adapter.RecyclerViewItemClickListener;
import com.pioneer.aaron.simpledesktop.adapter.RecyclerViewItemLongClickListener;
import com.pioneer.aaron.simpledesktop.module.App;
import com.pioneer.aaron.simpledesktop.module.AppUtil;
import com.pioneer.aaron.simpledesktop.util.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Aaron on 5/26/16.
 */
public class LauncherFragment extends Fragment implements RecyclerViewItemClickListener, RecyclerViewItemLongClickListener {

    private View rootView;
    private ArrayList<App> mApps;
    private RecyclerView recyclerView;
    private PullRefreshLayout pullRefreshLayout;
    private LauncherAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mApps = AppUtil.get(getActivity()).getFilteredApps();
        mAdapter = new LauncherAdapter(mApps);
        mAdapter.setItemClickListener(this);
        mAdapter.setItemLongClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.launcher_layout, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pullRefreshLayout = (PullRefreshLayout) rootView.findViewById(R.id.swipRefreshLayout);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_WATER_DROP);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncTask() {

                    @Override
                    protected Object doInBackground(Object[] params) {
                        mApps = AppUtil.get(getActivity()).getFilteredApps();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        mAdapter.notifyDataSetChanged();
                        pullRefreshLayout.setRefreshing(false);
                        super.onPostExecute(o);
                    }
                }.execute();
            }
        });
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onItemClick(View view, int position) {
        App item = mApps.get(position);

        ResolveInfo resolveInfo = item.getResolveInfo();
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        if (activityInfo == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName(activityInfo.applicationInfo.packageName, activityInfo.name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        App item = mApps.get(position);
        mApps.remove(item);
        Constant.DEFAULT_FILTER += item.getApp_label();
        mAdapter.notifyDataSetChanged();
    }
}
