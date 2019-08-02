package com.tool.jackntest;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

public class JkApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
