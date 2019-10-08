package com.example.materialtest.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class StatusBarUtils {
    public static int getHeight(Context context){
        int statusBarHeight = 0;
        try{
            int resourceId = context.getResources().getIdentifier("status_bar_height","dimen","android");
            if(resourceId>0){
                statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return statusBarHeight;
    }
    public static void setColor(@NonNull Window window, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(color);
        }

    }
    public static void setColor(Context context, @ColorInt int color) {
        if (context instanceof Activity) {
            setColor(((Activity) context).getWindow(), color);
        }

    }
}

