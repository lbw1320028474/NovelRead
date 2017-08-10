package com.example.novelread.action;

import com.example.novelread.util.Gloable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BatteryReceiver extends BroadcastReceiver{
	@Override  
	public void onReceive(Context context, Intent intent) {  
		// TODO Auto-generated method stub  
		//判断它是否是为电量变化的Broadcast Action  
		if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){  
			//获取当前电量  
			int level = intent.getIntExtra("level", 0);  
			//电量的总刻度  
			int scale = intent.getIntExtra("scale", 100);  
			//把它转成百分比  
			Gloable.batterTotal = scale;
			Gloable.batterLevel = level;
			Log.i(Gloable.TAG, "电量" + level + " / " + scale);
		}  
	} 
}
