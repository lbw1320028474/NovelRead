package com.example.novelread;

import com.example.novelread.util.Gloable;
import com.example.novelread.util.ScreenUtil;

import android.app.Application;

public class MyApplication extends Application{
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		ScreenUtil.initScreenInfo(getApplicationContext());
		Gloable.mApplicationContext = this;
	}
}
