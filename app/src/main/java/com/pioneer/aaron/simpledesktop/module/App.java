package com.pioneer.aaron.simpledesktop.module;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;


/**
 * Created by Aaron on 5/26/16.
 */
public class App {
    private Drawable app_icon;
    private String app_label;
    private ResolveInfo mResolveInfo;
    private Context mContext;

    public App(ResolveInfo resolveInfo, Context context) {
        mResolveInfo = resolveInfo;
        mContext = context;
        PackageManager pm = mContext.getPackageManager();
        app_icon = resolveInfo.loadIcon(pm);
        app_label = resolveInfo.loadLabel(pm).toString();
    }

    public Drawable getApp_icon() {
        Drawable protectDrawable = app_icon;
        return protectDrawable;
    }

    public String getApp_label() {
        String protectLabel = app_label;
        return protectLabel;
    }

    public ResolveInfo getResolveInfo() {
        ResolveInfo protectInfo = mResolveInfo;
        return protectInfo;
    }
}
