package com.example.novelread.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Environment;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View.MeasureSpec;
import android.widget.TextView;

import com.example.novelread.R;
import com.example.novelread.config.MyThem;
import com.example.novelread.util.DateUtil;
import com.example.novelread.util.Gloable;

public class BookBitmapFactory {
	private TextPaint textPaint = null;	//画笔
	private Paint pagePain = null;
	private BookBean bookBean = new BookBean();
	private static BookBitmapFactory bitmapFactory = null;
	private int currentShowTextIndex = 0;		//当前text位置
	private int currentDrawTextIndex = 0;		//当前画的text位置
	private PageBean pageBean = null;
	private ChapterBean showChapter = null;
	private int viewWidth = 0;
	private int viewHeight = 0;
	private MyThem myThem = null;
	private float topSpace = 0;		//顶部绘制区域高度
	private float bottomSpace = 0;		//底部绘制区域高度

	private List<BitmapBean> pageList = null;
	/*
	 * 以下是测试数据
	 */
	private BitmapBean bean1 = new BitmapBean(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.jpg", 0);
	private BitmapBean bean2 = new BitmapBean(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test1.jpg", 1);
	private BitmapBean bean3 = new BitmapBean(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test3.jpg", 2);
	private BitmapBean bean4 = new BitmapBean(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.jpg", 3);
	private BitmapBean bean5 = new BitmapBean(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test1.jpg", 4);
	private BitmapBean bean6 = new BitmapBean(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test3.jpg", 5);
	private BitmapBean bean7 = new BitmapBean(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.jpg", 6);
	private BitmapBean bean8 = new BitmapBean(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test1.jpg", 7);
	private BitmapBean bean9 = new BitmapBean(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test3.jpg", 8);
	private BitmapBean bean10 = new BitmapBean(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.jpg",9);
	/*	private BitmapBean bean2 = new BitmapBean(R.drawable.test1, 1);
	private BitmapBean bean3 = new BitmapBean(R.drawable.test3, 2);
	private BitmapBean bean4 = new BitmapBean(R.drawable.test, 3);
	private BitmapBean bean5 = new BitmapBean(R.drawable.test1, 4);
	private BitmapBean bean6 = new BitmapBean(R.drawable.test3, 5);
	private BitmapBean bean7 = new BitmapBean(R.drawable.test, 6);
	private BitmapBean bean8 = new BitmapBean(R.drawable.test1, 7);
	private BitmapBean bean9 = new BitmapBean(R.drawable.test3, 8);
	private BitmapBean bean10 = new BitmapBean(R.drawable.test,9);
	private BitmapBean bean11 = new BitmapBean(R.drawable.test1, 10);*/
	private BitmapBean[] testBitmap = new BitmapBean[]{bean1, bean2, bean3, bean4, bean5, bean6, bean7, bean8, bean9, bean10};

	/**
	 * 测试数据
	 */

	private BookBitmapFactory() {
		// TODO Auto-generated constructor stub
		textPaint = new TextPaint();
		pageBean = new PageBean();
		myThem = new MyThem();
		pagePain = new Paint();
		pageList = new ArrayList<BitmapBean>();
	}

	public static BookBitmapFactory getInstance(){
		if(bitmapFactory == null){
			bitmapFactory = new BookBitmapFactory();
		}
		return bitmapFactory;
	}

	/*public List<BitmapBean> drawPageList(){
		int drawPageIndex = 0;
		if(showChapter.getOneLineContent().length() <= 0){
			return null;
		}
		if(pageList == null){
			pageList = new ArrayList<BitmapBean>();
		}
		pageList.clear();
		Log.i(Gloable.TAG, "内容大小: = " + showChapter.getOneLineContent().length());
		while(true){

			BitmapBean bitmapBean = drawNextPage(drawPageIndex);
			Log.i(Gloable.TAG, "页数大小: = " + pageList.size());
			Log.i(Gloable.TAG, "drawPageIndex大小: = " + drawPageIndex);
			if(bitmapBean != null){
				drawPageIndex = bitmapBean.endIndex;
				pageList.add(bitmapBean);
			}else if(drawPageIndex == showChapter.getOneLineContent().length()){
				Log.i(Gloable.TAG, "页数大小1: = " + pageList.size());
				return pageList;
			}else{

			}
		}
	}*/

	public MyThem getMyThem() {
		return myThem;
	}

	public void setMyThem(MyThem myThem) {
		this.myThem = myThem;
	}

	public BitmapBean drawNextPage(){
		if(pageBean.getCurrentPageBitmap() == null || pageBean.getCurrentPageBitmap().bitmap == null){
			return null;
		}
		currentShowTextIndex = pageBean.getCurrentPageBitmap().endIndex;
		//	currentShowTextIndex = index;

		if(currentShowTextIndex >= 0 && currentShowTextIndex < showChapter.getOneLineContent().length()){
			BitmapBean bitmapBean = new BitmapBean();
			Bitmap bitmap = Bitmap.createBitmap(viewWidth + 50, viewHeight, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);

			//画背景
			Paint paint = new Paint();
			paint.setColor(myThem.getReadPageBgColor());
			canvas.drawRect(0, 0, viewWidth, viewHeight, paint);		

			//画阴影
			LinearGradient lg=new LinearGradient(viewWidth, 0, viewWidth + 50, 0, Gloable.mApplicationContext.getResources().getColor(R.color.page_shadow),Color.TRANSPARENT,Shader.TileMode.MIRROR);  //
			pagePain.setShader(lg);
			canvas.drawRect(new Rect(viewWidth, 0, viewWidth + 50, viewHeight), pagePain); 

			//画电量
			paint.reset();
			paint.setColor(myThem.getTextColor());
			paint.setStrokeWidth((float) 1.5);              //线宽  
			paint.setStyle(Style.STROKE);
			float left = viewWidth - myThem.getPaddingRight() - myThem.getTextSize() * 1.1f - 10;
			float top = viewHeight - myThem.getPaddingBottom() - myThem.getTextSize() * 0.5f;
			float right = viewWidth - myThem.getPaddingRight() - 13;
			float bottom = viewHeight - myThem.getPaddingBottom();
			canvas.drawRect(left, top, right, bottom, paint);
			paint.setStyle(Style.FILL);
			float totalLenth = (right - left);
			float totalHeight = (bottom - top);
			canvas.drawRect(right, top + totalHeight/2 - 6, right + 4, top + totalHeight/2 + 6, paint);
			right = left + totalLenth * (Gloable.batterLevel/Gloable.batterTotal);
			canvas.drawRect(left, top, right, bottom, paint);
			bottomSpace = totalLenth + myThem.getPaddingBottom();


			//画时间
			paint.reset();
			paint.setTextSize(40);
			textPaint.setAntiAlias(true);
			textPaint.setDither(true);
			paint.setColor(myThem.getTextColor());
			Rect targetRect = new Rect(0, viewHeight - 10, viewWidth, viewHeight);
			canvas.drawText(DateUtil.getTime(), targetRect.centerX(), viewHeight - myThem.getPaddingBottom(), paint);

			Log.i(Gloable.TAG, "currentShowTextIndex = " + currentShowTextIndex);
			drawCurText(currentShowTextIndex, canvas);		//画文字

			if(showChapter.getOneLineContent().length() > 0){
				Log.i(Gloable.TAG, "当前位置:" + currentShowTextIndex);
				float present = (currentShowTextIndex/showChapter.getOneLineContent().length()) ;
				String presentStr = present + " %";
				paint.reset();
				paint.setTextSize(40);
				textPaint.setAntiAlias(true);
				textPaint.setDither(true);
				paint.setColor(myThem.getTextColor());
				canvas.drawText(presentStr, myThem.getPaddingLeft(), viewHeight - myThem.getPaddingBottom(), paint);
			}

			bitmapBean.bitmap = bitmap;
			bitmapBean.startIndex = currentShowTextIndex;
			bitmapBean.endIndex = currentDrawTextIndex;
			return bitmapBean;
		}else{
			return null;
		}
	}

	public BitmapBean drawPrePage(){
		if(pageBean.getCurrentPageBitmap() == null || pageBean.getCurrentPageBitmap().bitmap == null){
			return null;
		}
		currentShowTextIndex = pageBean.getCurrentPageBitmap().startIndex;

		if(currentShowTextIndex >= 0 && currentShowTextIndex < showChapter.getOneLineContent().length()){
			BitmapBean bitmapBean = new BitmapBean();
			Bitmap bitmap = Bitmap.createBitmap(viewWidth + 50, viewHeight, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);

			//画背景
			Paint paint = new Paint();
			paint.setColor(myThem.getReadPageBgColor());
			canvas.drawRect(0, 0, viewWidth, viewHeight, paint);

			//画阴影
			LinearGradient lg=new LinearGradient(viewWidth, 0, viewWidth + 50, 0, Gloable.mApplicationContext.getResources().getColor(R.color.page_shadow),Color.TRANSPARENT,Shader.TileMode.MIRROR);  //
			pagePain.setShader(lg);
			canvas.drawRect(new Rect(viewWidth, 0, viewWidth + 50, viewHeight), pagePain); 

			//画电量
			paint.reset();
			paint.setColor(myThem.getTextColor());
			paint.setStrokeWidth((float) 1.5);              //线宽  
			paint.setStyle(Style.STROKE);
			float left = viewWidth - myThem.getPaddingRight() - myThem.getTextSize() * 1.1f - 10;
			float top = viewHeight - myThem.getPaddingBottom() - myThem.getTextSize() * 0.5f;
			float right = viewWidth - myThem.getPaddingRight() - 13;
			float bottom = viewHeight - myThem.getPaddingBottom();
			canvas.drawRect(left, top, right, bottom, paint);
			paint.setStyle(Style.FILL);
			float totalLenth = (right - left);
			float totalHeight = (bottom - top);
			canvas.drawRect(right, top + totalHeight/2 - 6, right + 4, top + totalHeight/2 + 6, paint);
			right = left + totalLenth * (Gloable.batterLevel/Gloable.batterTotal);
			canvas.drawRect(left, top, right, bottom, paint);
			bottomSpace = totalLenth + myThem.getPaddingBottom();


			//画时间
			paint.reset();
			paint.setTextSize(40);
			textPaint.setAntiAlias(true);
			textPaint.setDither(true);
			paint.setColor(myThem.getTextColor());
			Rect targetRect = new Rect(0, viewHeight - 10, viewWidth, viewHeight);
			canvas.drawText(DateUtil.getTime(), targetRect.centerX(), viewHeight - myThem.getPaddingBottom(), paint);


			drawCurText(currentShowTextIndex, canvas);		//画文字

			if(showChapter.getOneLineContent().length() > 0){
				float present = (currentShowTextIndex/showChapter.getOneLineContent().length()) ;
				String presentStr = present + " %";
				paint.reset();
				paint.setTextSize(40);
				textPaint.setAntiAlias(true);
				textPaint.setDither(true);
				paint.setColor(myThem.getTextColor());
				canvas.drawText(presentStr, myThem.getPaddingLeft(), viewHeight - myThem.getPaddingBottom(), paint);
			}else{
				return null;
			}

			bitmapBean.bitmap = bitmap;
			bitmapBean.startIndex = currentShowTextIndex;
			bitmapBean.endIndex = currentDrawTextIndex;
			return bitmapBean;
		}else{
			return null;
		}
	}

	public BitmapBean drawCurPage(int textIndex){
		if(textIndex >= 0 && textIndex < showChapter.getOneLineContent().length()){
			BitmapBean bitmapBean = new BitmapBean();
			Bitmap bitmap = Bitmap.createBitmap(viewWidth + 50, viewHeight, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);

			//画背景
			Paint paint = new Paint();
			paint.setColor(myThem.getReadPageBgColor());
			canvas.drawRect(0, 0, viewWidth, viewHeight, paint);		

			//画阴影
			LinearGradient lg=new LinearGradient(viewWidth, 0, viewWidth + 50, 0, Gloable.mApplicationContext.getResources().getColor(R.color.page_shadow),Color.TRANSPARENT,Shader.TileMode.MIRROR);  //
			pagePain.setShader(lg);
			canvas.drawRect(new Rect(viewWidth, 0, viewWidth + 50, viewHeight), pagePain); 

			//画电量
			paint.reset();
			paint.setColor(myThem.getTextColor());
			paint.setStrokeWidth((float) 1.5);              //线宽  
			paint.setStyle(Style.STROKE);
			float left = viewWidth - myThem.getPaddingRight() - myThem.getTextSize() * 1.1f - 10;
			float top = viewHeight - myThem.getPaddingBottom() - myThem.getTextSize() * 0.5f;
			float right = viewWidth - myThem.getPaddingRight() - 13;
			float bottom = viewHeight - myThem.getPaddingBottom();
			canvas.drawRect(left, top, right, bottom, paint);
			paint.setStyle(Style.FILL);
			float totalLenth = (right - left);
			float totalHeight = (bottom - top);
			canvas.drawRect(right, top + totalHeight/2 - 6, right + 4, top + totalHeight/2 + 6, paint);
			right = left + totalLenth * (Gloable.batterLevel/Gloable.batterTotal);
			canvas.drawRect(left, top, right, bottom, paint);
			bottomSpace = totalLenth + myThem.getPaddingBottom();


			//画时间
			paint.reset();
			paint.setTextSize(40);
			textPaint.setAntiAlias(true);
			textPaint.setDither(true);
			paint.setColor(myThem.getTextColor());
			Rect targetRect = new Rect(0, viewHeight - 10, viewWidth, viewHeight);
			canvas.drawText(DateUtil.getTime(), targetRect.centerX(), viewHeight - myThem.getPaddingBottom(), paint);

			drawCurText(textIndex, canvas);		//画文字

			if(showChapter.getOneLineContent().length() > 0){
				Log.i(Gloable.TAG, "当前位置:" + currentShowTextIndex);
				float present = (currentShowTextIndex/showChapter.getOneLineContent().length()) ;
				String presentStr = present + " %";
				paint.reset();
				paint.setTextSize(40);
				textPaint.setAntiAlias(true);
				textPaint.setDither(true);
				paint.setColor(myThem.getTextColor());
				canvas.drawText(presentStr, myThem.getPaddingLeft(), viewHeight - myThem.getPaddingBottom(), paint);
			}else{
				return null;
			}
			bitmapBean.bitmap = bitmap;
			bitmapBean.startIndex = currentShowTextIndex;
			bitmapBean.endIndex = currentDrawTextIndex;
			return bitmapBean;
		}else{
			return null;
		}
	}





	private void drawPreText(int textIndex, Canvas canvas){
		int lineLocation = (int) myThem.getPaddingTop();
		//画章节名
		//TextView textView = new TextView(Gloable.mApplicationContext);
		TextPaint textPaint = new TextPaint();
		textPaint.setColor(myThem.getTextColor());
		textPaint.setTextSize(40);
		textPaint.setAntiAlias(true);
		textPaint.setDither(true);
		lineLocation = lineLocation + myThem.getTextSize() - 10;
		canvas.drawText("第" + (this.showChapter.getChapterOrder() + 1) + "章：" + this.showChapter.getChapterName(), myThem.getPaddingLeft(), lineLocation, textPaint);
		topSpace = lineLocation + myThem.getPaddingTop();

		textPaint.setTextSize(myThem.getTextSize());
		//画文字
		int sectionIndex = getCurrenSectionIndex(textIndex);
		currentDrawTextIndex = currentShowTextIndex;
		int lenCount = 0;
		if(sectionIndex > 0){
			while(true){
				//处理文字绘画区域
				int textDrawSpageWidth = (int) (viewWidth - myThem.getPaddingLeft() - myThem.getPaddingRight());
				int lineTextCount = 0;
				int deawX = 0;
				if((showChapter.getSectionIndex().get(sectionIndex - 1) == null && currentDrawTextIndex == 0) || 		//判断是否是段首
						showChapter.getSectionIndex().get(sectionIndex - 1) != null && 
						currentDrawTextIndex == showChapter.getSectionIndex().get(sectionIndex - 1)){		//
					lineTextCount = textPaint.breakText(showChapter.getOneLineContent(), false, textDrawSpageWidth - myThem.getTextSize() * 2, null);
					deawX = (int) (myThem.getTextSize() * 2 + myThem.getPaddingLeft());
					lineLocation = lineLocation + myThem.getLineSpace() + 20;
				}else{
					lineLocation = lineLocation + myThem.getLineSpace();
					deawX = (int) (myThem.getPaddingLeft());
					lineTextCount = textPaint.breakText(showChapter.getOneLineContent(), false, textDrawSpageWidth, null);
				}
				Log.i(Gloable.TAG, "一行可画字数0=" + lineTextCount);
				String drawText = "";
				if(lineLocation > viewHeight - bottomSpace){
					break;
				}
				if(showChapter.getSectionIndex().get(sectionIndex + 1) != null && currentDrawTextIndex + lineTextCount >= showChapter.getSectionIndex().get(sectionIndex)){	//如果是换段了
					drawText = this.showChapter.getOneLineContent().substring(currentDrawTextIndex, showChapter.getSectionIndex().get(sectionIndex));
					currentDrawTextIndex = showChapter.getSectionIndex().get(sectionIndex);
					sectionIndex +=1;
					//	textPaint.setTextScaleX(1);
				}else{
					if(currentDrawTextIndex + lineTextCount <= this.showChapter.getOneLineContent().length()){
						drawText = this.showChapter.getOneLineContent().substring(currentDrawTextIndex, currentDrawTextIndex + lineTextCount);
						currentDrawTextIndex += lineTextCount;
					}else{
						drawText = this.showChapter.getOneLineContent().substring(currentDrawTextIndex, this.showChapter.getOneLineContent().length());
						currentDrawTextIndex = this.showChapter.getOneLineContent().length();
					}
				}
				lenCount+=1;
				float testSpace = textPaint.measureText(drawText);
				float space = deawX + testSpace;
				Log.i(Gloable.TAG, "测量数据" +  "testSpace = " + testSpace + ", space = " + (deawX + testSpace));
				if(space > viewWidth - myThem.getPaddingRight()){
					Log.i(Gloable.TAG, "字体倍数" +  (viewWidth - deawX - myThem.getPaddingRight()) /testSpace);
					textPaint.setTextScaleX((viewWidth - deawX - myThem.getPaddingRight()) /testSpace);
				}
				Log.i(Gloable.TAG, "drawText = " + drawText + ", space = " + (viewWidth - deawX) + ", testSpace = " + testSpace);
				canvas.drawText(drawText, deawX, lineLocation, textPaint);
				if(currentDrawTextIndex >= showChapter.getOneLineContent().length()){
					break;
				}
			}
			Log.i(Gloable.TAG, "行数 = " + lenCount);
		}
	}

	//画当前页的文字
	private void drawCurText(int textIndex, Canvas canvas) {
		int lineLocation = (int) myThem.getPaddingTop();
		//画章节名
		//TextView textView = new TextView(Gloable.mApplicationContext);
		TextPaint textPaint = new TextPaint();
		textPaint.setColor(myThem.getTextColor());
		textPaint.setTextSize(40);
		textPaint.setAntiAlias(true);
		textPaint.setDither(true);
		lineLocation = lineLocation + myThem.getTextSize() - 10;
		canvas.drawText("第" + (this.showChapter.getChapterOrder() + 1) + "章：" + this.showChapter.getChapterName(), myThem.getPaddingLeft(), lineLocation, textPaint);
		topSpace = lineLocation + myThem.getPaddingTop();

		textPaint.setTextSize(myThem.getTextSize());
		//画文字
		int sectionIndex = getCurrenSectionIndex(textIndex);
		currentDrawTextIndex = currentShowTextIndex;
		int lenCount = 0;
		if(sectionIndex > 0){
			while(true){
				//处理文字绘画区域
				int textDrawSpageWidth = (int) (viewWidth - myThem.getPaddingLeft() - myThem.getPaddingRight());
				int lineTextCount = 0;
				int deawX = 0;
				if((showChapter.getSectionIndex().get(sectionIndex - 1) == null && currentDrawTextIndex == 0) || 		//判断是否是段首
						showChapter.getSectionIndex().get(sectionIndex - 1) != null && 
						currentDrawTextIndex == showChapter.getSectionIndex().get(sectionIndex - 1)){		//
					lineTextCount = textPaint.breakText(showChapter.getOneLineContent(), false, textDrawSpageWidth - myThem.getTextSize() * 2, null);
					deawX = (int) (myThem.getTextSize() * 2 + myThem.getPaddingLeft());
					lineLocation = lineLocation + myThem.getLineSpace() + 20;
				}else{
					lineLocation = lineLocation + myThem.getLineSpace();
					deawX = (int) (myThem.getPaddingLeft());
					lineTextCount = textPaint.breakText(showChapter.getOneLineContent(), false, textDrawSpageWidth, null);
				}
				Log.i(Gloable.TAG, "一行可画字数0=" + lineTextCount);
				String drawText = "";
				if(lineLocation > viewHeight - bottomSpace){
					break;
				}
				if(showChapter.getSectionIndex().get(sectionIndex + 1) != null && currentDrawTextIndex + lineTextCount >= showChapter.getSectionIndex().get(sectionIndex)){	//如果是换段了
					drawText = this.showChapter.getOneLineContent().substring(currentDrawTextIndex, showChapter.getSectionIndex().get(sectionIndex));
					currentDrawTextIndex = showChapter.getSectionIndex().get(sectionIndex);
					sectionIndex +=1;
					//	textPaint.setTextScaleX(1);
				}else{
					if(currentDrawTextIndex + lineTextCount <= this.showChapter.getOneLineContent().length()){
						drawText = this.showChapter.getOneLineContent().substring(currentDrawTextIndex, currentDrawTextIndex + lineTextCount);
						currentDrawTextIndex += lineTextCount;
					}else{
						drawText = this.showChapter.getOneLineContent().substring(currentDrawTextIndex, this.showChapter.getOneLineContent().length());
						currentDrawTextIndex = this.showChapter.getOneLineContent().length();
					}
				}
				lenCount+=1;
				float testSpace = textPaint.measureText(drawText);
				float space = deawX + testSpace;
				Log.i(Gloable.TAG, "测量数据" +  "testSpace = " + testSpace + ", space = " + (deawX + testSpace));
				if(space > viewWidth - myThem.getPaddingRight()){
					Log.i(Gloable.TAG, "字体倍数" +  (viewWidth - deawX - myThem.getPaddingRight()) /testSpace);
					textPaint.setTextScaleX((viewWidth - deawX - myThem.getPaddingRight()) /testSpace);
				}
				Log.i(Gloable.TAG, "drawText = " + drawText + ", space = " + (viewWidth - deawX) + ", testSpace = " + testSpace);
				canvas.drawText(drawText, deawX, lineLocation, textPaint);
				if(currentDrawTextIndex >= showChapter.getOneLineContent().length()){
					break;
				}
			}
			Log.i(Gloable.TAG, "行数 = " + lenCount);
		}
	}

	//获取当前字符位置的段落索引
	private int getCurrenSectionIndex(int textIndex) {
		// TODO Auto-generated method stub
		if(showChapter.getSectionIndex() == null || showChapter.getSectionIndex().size() <= 0){
			return -1;
		}
		Map<Integer, Integer> map = showChapter.getSectionIndex();
		for (int i = 1; map.get(i) > 0; ++i) {  
			int currenValue = map.get(i);
			if(currenValue > 0 && textIndex < currenValue){
				Log.i(Gloable.TAG, "获取到的textIndex段落位置 = " + i);
				return i;
			}
		} 
		Log.i(Gloable.TAG, "获取到的textIndex段落位置失败");
		return -1;
	}

	/*if(currentShowTextIndex < testBitmap.length && currentShowTextIndex >= 0){
			BitmapBean bitmapBean = new BitmapBean();
			//Bitmap drawBitmap =  testBitmap[currentShowTextIndex].bitmap;
			Bitmap bitmap = Bitmap.createBitmap(viewWidth + 50, viewHeight, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			canvas.drawBitmap(drawBitmap, new Rect(0, 0, drawBitmap.getWidth(), drawBitmap.getHeight()), 
					new Rect(0, 0, viewWidth, viewHeight), null);
			Paint paint = new Paint();
			paint.setColor(myThem.getReadPageBgColor());
			canvas.drawRect(0, 0, viewWidth, viewHeight, paint);
			LinearGradient lg=new LinearGradient(viewWidth, 0, viewWidth + 50, 0, Gloable.mApplicationContext.getResources().getColor(R.color.page_shadow),Color.TRANSPARENT,Shader.TileMode.MIRROR);  //
			pagePain.setShader(lg);
			canvas.drawRect(new Rect(viewWidth, 0, viewWidth + 50, viewHeight), pagePain); //参数3为画圆的半径，类型为float型。
			bitmapBean.bitmap = bitmap;
			bitmapBean.startIndex = currentShowTextIndex;
			return bitmapBean;
		}else{
			return null;
		}*/
	//}

	public PageBean getPageBitmap(int showIndex, int mViewWidth, int mViewHeight){
		currentShowTextIndex = showIndex;
		this.viewWidth  = mViewWidth;
		this.viewHeight = mViewHeight;
		Log.i(Gloable.TAG, "传进来的宽高：viewWidth  = " + mViewWidth + ", viewHeight = " + viewHeight);
		//toDrawPageList();
		pageBean.setCurrentPageBitmap(drawCurPage(showIndex));
		pageBean.setPrePageBitmap(drawPrePage());
		pageBean.setNextPageBitmap(drawNextPage());
		Log.i(Gloable.TAG, "当前位置：" + currentShowTextIndex);

		return pageBean;
	}

	private void toDrawPageList() {
		// TODO Auto-generated method stub
		/*		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
		 */			//	drawPageList();
		 /*			}
		}).start();*/
	}

	/**
	 * 重画一次bitmap列表（可能由于屏幕像素改变等等原因需要重画)
	 * @param mViewWidth
	 * @param mViewHeight
	 * @return
	 */
	public PageBean getPageBeanAgain(int mViewWidth, int mViewHeight) {
		// TODO Auto-generated method stub
		return getPageBitmap(this.currentShowTextIndex, mViewWidth, mViewHeight);
	}

	/**
	 * 获取上一页bitmap列表
	 * @param mViewWidth
	 * @param mViewHeight
	 * @return
	 */
	public PageBean getPrePageBean(int mViewWidth, int mViewHeight){
		if(pageBean.getPrePageBitmap() != null){
			return getPageBitmap(pageBean.getPrePageBitmap().startIndex, mViewWidth, mViewHeight);
		}
		return null;
	}

	/**
	 * 获取下一页的bitmap列表
	 * @param mViewWidth
	 * @param mViewHeight
	 * @return
	 */
	public PageBean getNextPageBean(int mViewWidth, int mViewHeight){
		if(pageBean.getNextPageBitmap() != null){
			return getPageBitmap(pageBean.getNextPageBitmap().startIndex, mViewWidth, mViewHeight);
		}
		return null;
	}

	public BookBean getBookBean() {
		return bookBean;
	}

	public void setBookBean(BookBean bookBean) {
		setBookReadInfo(bookBean, 0);
	}

	/**
	 * 
	 * @param bookBean
	 * @param chapterIndex 显示的章节
	 */
	public void setBookReadInfo(BookBean bookBean, int chapterIndex) {
		if(bookBean == null || bookBean.getChapterList() == null || bookBean.getChapterList().size() <= 0){
			Log.i(Gloable.TAG, "图书无内容" + (bookBean == null) + " + " + (bookBean.getChapterList() == null) + " + " + (bookBean.getChapterList().size() <= 0) );
			return;
		}
		this.bookBean = bookBean;
		this.showChapter = bookBean.getChapterList().get(chapterIndex);
		dealChapter();
		Log.i(Gloable.TAG, "处理后的结果：" + this.showChapter.getOneLineContent());
		pageBean.setCurrentPageBitmap(null);		//重置页面
		pageBean.setPrePageBitmap(null);
		pageBean.setNextPageBitmap(null);
		//getPageBitmap(textShowIndex);
	}


	private void dealChapter() {
		if(this.showChapter == null || this.showChapter.getSectionList().size() <= 0){
			Log.e(Gloable.TAG, "章节无段落内容，解析错误");
			return;
		}
		Log.e(Gloable.TAG, "开始格式化章节段落:" + this.showChapter.getSectionList().size());
		this.showChapter.setOneLineContent("");
		Map<Integer, Integer> sectionIndex = new HashMap<Integer, Integer>();
		StringBuffer chapterContent = new StringBuffer("");
		int sectionPosition = 1;
		for (int i = 0; i < this.showChapter.getSectionList().size(); ++i){
			String sectionStr = this.showChapter.getSectionList().get(i);
			if(TextUtils.isEmpty(sectionStr)){
				continue;
			}
			chapterContent.append(sectionStr);
			sectionIndex.put(sectionPosition, chapterContent.length());
			sectionPosition+=1;
		}
		this.showChapter.setOneLineContent(chapterContent.toString());
		this.showChapter.setSectionIndex(sectionIndex);
	}

	public ChapterBean getShowChapter() {
		return showChapter;
	}

	public void setShowChapterIndex(int index) {
		try {
			this.showChapter = bookBean.getChapterList().get(0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Log.e(Gloable.TAG, e.getMessage());
			return;
		}
	}

	public int getCurrentShowTextIndex() {
		return currentShowTextIndex;
	}

	public void setCurrentShowTextIndex(int currentShowTextIndex) {
		this.currentShowTextIndex = currentShowTextIndex;
	}




}
