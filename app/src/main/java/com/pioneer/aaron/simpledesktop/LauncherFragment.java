package com.pioneer.aaron.simpledesktop;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private RecyclerView mRecyclerView;
    private PullRefreshLayout mPullRefreshLayout;
    private ArrayList<App> mApps;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.launcher_layout, container, false);

        initFragment();
        return rootView;
    }

    private void initFragment() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPullRefreshLayout = (PullRefreshLayout) rootView.findViewById(R.id.swipRefreshLayout);
        mPullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_WATER_DROP);
        mPullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        mApps = new ArrayList<>();
        mApps = AppUtil.get(getActivity()).getFilteredApps();

        LauncherAdapter adapter = new LauncherAdapter(mApps);
        adapter.setItemClickListener(this);
        adapter.setItemLongClickListener(this);

        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
