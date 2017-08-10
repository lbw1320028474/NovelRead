package com.example.novelread.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String getTime(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				String s = sdf.format(date);
		return s;
	}
}
