package com.example.novelread.book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.novelread.util.Gloable;

public class BookFactory {
	public static BookBean getAbook(Context context){
		BookBean bookBean = new BookBean();
		bookBean.setBookName("霸道总裁");
		bookBean.setBookAuthor("米米佳");
		bookBean.setChapterCount(1);
		bookBean.setWordCount(2342342);
		List<ChapterBean> chapterBeans = getChapterList(context);
		bookBean.setChapterList(chapterBeans);
		return bookBean;
	}

	private static List<ChapterBean> getChapterList(Context context) {
		// TODO Auto-generated method stub
		List<ChapterBean> chapterBeansList = new ArrayList<ChapterBean>();
		try {
			String[] textList = context.getAssets().list("novel_txt");
			for (String tl: textList){
				Log.i(Gloable.TAG, tl);
				try {  
					//Return an AssetManager instance for your application's package
					InputStream is = context.getAssets().open("novel_txt/TrueNovel.txt"); 
					ChapterBean chapterBean = new ChapterBean();

					//chapterBean.setContent(text);
					chapterBean.setSectionList(readTxtFile(is));
					//Log.i(Gloable.TAG, chapterBean.getContent());
					chapterBean.setChapterName("霸道仲裁爱上你");
					Log.i(Gloable.TAG, "chapterSectionSize = " + chapterBean.getSectionList().size());
					//	Log.i(Gloable.TAG, "1 index = " + chapterBean.getSectionList().get(4));
					is.close();  
					chapterBeansList.add(chapterBean);
				} catch (IOException e) {  
					// Should never happen!  
					e.printStackTrace();
				} 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return chapterBeansList;
	}

	private static List<String> readTxtFile(InputStream inputStream){
		try {
			String encoding="GBK";
			List<String> sectionList = new ArrayList<String>();
			InputStreamReader read = new InputStreamReader(
					inputStream,encoding);//考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while((lineTxt = bufferedReader.readLine()) != null){
				if(TextUtils.isEmpty(lineTxt)){
					continue;
				}
				lineTxt = lineTxt.replaceAll(" ", "");
				sectionList.add(lineTxt);
				//System.out.println(lineTxt);
			}
			read.close();
			return sectionList;
			//System.out.println("找不到指定的文件");
		} catch (Exception e) {
			//System.out.println("读取文件内容出错");
			e.printStackTrace();
			return null;
		}

	}
}
