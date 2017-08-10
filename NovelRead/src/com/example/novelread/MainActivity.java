package com.example.novelread;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.novelread.action.BatteryReceiver;
import com.example.novelread.book.BookBean;
import com.example.novelread.book.BookFactory;
import com.example.novelread.view.ReadPageView;

public class MainActivity extends Activity {
	private BookBean bookBean = null;
	private ReadPageView readPageView = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bookBean = BookFactory.getAbook(this);
		initView();
		initBroadCase();
		if (bookBean != null){
			/*BookBitmapFactory.getInstance().setBookBean(bookBean);
			PageBean pageBean = BookBitmapFactory.getInstance().getPageBitmap(0);*/
			readPageView.setBookBean(bookBean);
		}
	}

	//注册电量变化广播监听
	private void initBroadCase() {
		// TODO Auto-generated method stub
		//注册广播接受者java代码  
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);  
		//创建广播接受者对象  
		BatteryReceiver batteryReceiver = new BatteryReceiver();  

		//注册receiver  
		registerReceiver(batteryReceiver, intentFilter); 
	}
	private void initView() {
		// TODO Auto-generated method stub
		readPageView = (ReadPageView)this.findViewById(R.id.read_page_readview);
	}
}
