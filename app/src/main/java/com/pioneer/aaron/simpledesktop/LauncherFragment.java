package com.pioneer.aaron.simpledesktop;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Aaron on 5/21/16.
 */
public class LauncherFragment extends ListFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<ResolveInfo> activities = new ArrayList<>();
        activities = initList();

        ArrayAdapter<ResolveInfo> adapter = new ArrayAdapter<ResolveInfo>(getActivity(),android.R.layout.simple_list_item_1,activities){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                PackageManager pm = getActivity().getPackageManager();
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                ResolveInfo resolveInfo = getItem(position);
                textView.setText(resolveInfo.loadLabel(pm).toString());
                return view;
            }
        };

        setListAdapter(adapter);
    }

    private List<ResolveInfo> initList() {
        Intent startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(startIntent, 0);

        // sort list from A to Z
        Collections.sort(list, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo lhs, ResolveInfo rhs) {
                PackageManager pm = getActivity().getPackageManager();

                return String.CASE_INSENSITIVE_ORDER.compare(
                        lhs.loadLabel(pm).toString(),
                        rhs.loadLabel(pm).toString()
                );
            }
        });

        // filter activities
        list = filterActivities(list);

        return list;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ResolveInfo resolveInfo = (ResolveInfo) l.getAdapter().getItem(position);
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        if (activityInfo == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName(activityInfo.applicationInfo.packageName, activityInfo.name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private List<ResolveInfo> filterActivities(List<ResolveInfo> data) {
        String filterString = "下载\n" +
                "便签\n" +
                "信息\n" +
                "图库\n" +
                "应用中心\n" +
                "录音机\n" +
                "文档\n" +
                "日历\n" +
                "时钟\n" +
                "浏览器\n" +
                "电话\n" +
                "画板\n" +
                "相机\n" +
                "系统升级\n" +
                "联系人\n" +
                "视频\n" +
                "计算器\n" +
                "设置\n" +
                "邮件\n" +
                "阅读\n" +
                "音乐";

        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> list = new ArrayList<>();

        for (ResolveInfo ri : data) {
            String name = ri.loadLabel(pm).toString();
            if (!filterString.contains(name)) {
                list.add(ri);
            }
        }

        return list;
    }
}
