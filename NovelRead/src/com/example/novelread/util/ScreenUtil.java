package com.example.novelread.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenUtil {
	public static int screen_height = 0;
	public static int screen_widtht = 0;
	public static void initScreenInfo(Context context){
		WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        screen_widtht = metrics.widthPixels;
        screen_height = metrics.heightPixels;
	}
}
