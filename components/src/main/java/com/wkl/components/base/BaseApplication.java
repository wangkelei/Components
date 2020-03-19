package com.wkl.components.base;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    protected static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
