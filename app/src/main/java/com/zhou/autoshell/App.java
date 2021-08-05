package com.zhou.autoshell;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

public class App extends Application {

    public static final String SHELL_KEY = "SHELL_KEY";

    private static boolean isRun = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    public static void runExec(Context context) {
        if (isRun || context == null) {
            return;
        }
        String execCmd;
        if (!TextUtils.isEmpty((execCmd = PreUtils.get(context, SHELL_KEY, "")))) {
            ShellUtils.execCommand(execCmd, ShellUtils.checkRootPermission());
        }
    }

}
