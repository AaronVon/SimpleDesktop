package com.pioneer.aaron.simpledesktop.module;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;


/**
 * Created by Aaron on 5/26/16.
 */
public class App {
    public Drawable app_icon;
    public String app_label;

    public App(Drawable app_icon, String app_label) {
        this.app_icon = app_icon;
        this.app_label = app_label;
    }
}
