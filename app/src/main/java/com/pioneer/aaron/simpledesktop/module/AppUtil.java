package com.pioneer.aaron.simpledesktop.module;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.pioneer.aaron.simpledesktop.util.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Aaron on 5/27/16.
 */
public class AppUtil {
    private ArrayList<App> mApps;
    private static AppUtil sAppUtil;
    private Context mContext;

    public AppUtil(Context context) {
        mContext = context;
        mApps = new ArrayList<>();
    }

    // Get AppUtil instance
    public static AppUtil get(Context context) {
        if (sAppUtil == null) {
            sAppUtil = new AppUtil(context);
        }
        return sAppUtil;
    }

    private void addApp(App app) {
        mApps.add(app);
    }

    private void deleteApp(App app) {
        mApps.remove(app);
    }

    public ArrayList<App> getFilteredApps() {
        Intent startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = mContext.getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(startIntent, 0);
        // sort list from A to Z
        Collections.sort(list, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo lhs, ResolveInfo rhs) {
                PackageManager pm = mContext.getPackageManager();

                return String.CASE_INSENSITIVE_ORDER.compare(
                        lhs.loadLabel(pm).toString(),
                        rhs.loadLabel(pm).toString()
                );
            }
        });

        for (ResolveInfo ri : list) {
            String label = ri.loadLabel(pm).toString();
            if (!Constant.DEFAULT_FILTER.contains(label)) {
                App app = new App(ri, mContext);
                mApps.add(app);
            }
        }

        return mApps;
    }

    public ArrayList<App> getFullApps() {
        Intent startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = mContext.getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(startIntent, 0);
        // sort list from A to Z
        Collections.sort(list, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo lhs, ResolveInfo rhs) {
                PackageManager pm = mContext.getPackageManager();

                return String.CASE_INSENSITIVE_ORDER.compare(
                        lhs.loadLabel(pm).toString(),
                        rhs.loadLabel(pm).toString()
                );
            }
        });
        ArrayList<App> result = new ArrayList<>();
        for (ResolveInfo ri : list) {
            App app = new App(ri, mContext);
            result.add(app);
        }

        return result;
    }
}
