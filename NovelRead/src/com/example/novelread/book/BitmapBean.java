package com.example.novelread.book;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapBean {
	public Bitmap bitmap = null;
	public int startIndex = 0;
	public int endIndex = 0;
	public int startSectionIndex = -1;
	public int endSectionIndex = -1;

	public BitmapBean(String bitmapPath, int index){
		File is = new File(bitmapPath);
		try {
			bitmap = BitmapFactory.decodeStream(new FileInputStream(is));
			startIndex = index;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BitmapBean(){
		
	}
}
