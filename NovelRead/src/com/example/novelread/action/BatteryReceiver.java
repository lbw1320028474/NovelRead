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
		//�ж����Ƿ���Ϊ�����仯��Broadcast Action  
		if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){  
			//��ȡ��ǰ����  
			int level = intent.getIntExtra("level", 0);  
			//�������̶ܿ�  
			int scale = intent.getIntExtra("scale", 100);  
			//����ת�ɰٷֱ�  
			Gloable.batterTotal = scale;
			Gloable.batterLevel = level;
			Log.i(Gloable.TAG, "����" + level + " / " + scale);
		}  
	} 
}
