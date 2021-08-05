package com.zhou.autoshell;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAdmin extends DeviceAdminReceiver {

    private final String TAG = "DeviceAdminReceiver";

    @Override
    public void onEnabled(Context context, Intent intent) {
        //设备管理可用
        Log.i(TAG, "onEnabled: MyAdmin");
        App.runExec(context);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        //设备管理不可用
        Log.i(TAG, "onDisabled: MyAdmin");
    }
}

