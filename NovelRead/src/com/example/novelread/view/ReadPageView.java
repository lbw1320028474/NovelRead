package com.example.novelread.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.novelread.book.BitmapBean;
import com.example.novelread.book.BookBean;
import com.example.novelread.book.BookBitmapFactory;
import com.example.novelread.book.PageBean;
import com.example.novelread.util.Gloable;
import com.example.novelread.util.ScreenUtil;

public class ReadPageView extends View{
	public static final int ACTION_TYPE_TO_RIGHT = 1;
	public static final int ACTION_TYPE_TO_LEFT = -1;
	public static final int ACTION_TYPE_CLICK = 0;
	public static final int ACTION_TYPE_NOACTION = 100;
	private BookBean bookBean = null;		//ͼ�����
	private int mViewHeight = 0;		//�ؼ���
	private int mViewWidth = 0;		//�ؼ���
	private int screenHeight = 0;		//��Ļ��
	private int screenWdith = 0;		//��Ļ��
	private PageBean pageBean = null;		//bitmap����
	private PointF startDownPoint = null;		//toach��ʼ��
	private PointF endPoint = null;		//toachĿǰ��������
	private boolean oneActionIsOver = true;		//һ��toach�����Ƿ����
	private int scroolMode = 100;		//�������ͣ�0������� 1���󻬶��� 2���һ���, 100:Ĭ�ϣ��޶�����
	private boolean isCanToachAgain = true;	//�Ƿ�ɵ��
	private boolean isHaveDown = false;		//�Ƿ��а��µĶ�����ȷ������toach������������
	private boolean isFirst = true;		//�Ƿ��ǵ�һ������
	private float pointX = -100;
	private int tagChapterIndex = 0;
	private int tagChapterShowTextIndex = 0;

	public ReadPageView(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}
	private void init() {
		// TODO Auto-generated method stub
		screenHeight = ScreenUtil.screen_height;
		screenWdith = ScreenUtil.screen_widtht;
		startDownPoint = new PointF();
		endPoint = new PointF();
	}
	public ReadPageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	public ReadPageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}
	@Override
	public void addOnLayoutChangeListener(OnLayoutChangeListener listener) {
		// TODO Auto-generated method stub
		super.addOnLayoutChangeListener(listener);
		init();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//Log.i(Gloable.TAG, "�ؼ����: mViewWidth = " + mViewWidth + ", mViewHeight = " + mViewHeight);
		//super.onDraw(canvas);
		int distanceX = (int) (endPoint.x - startDownPoint.x);
		//	canvas.drawBitmap(pageBean.getCurrentPageBitmap(), 0, 0, null);
		if(pageBean == null){
			Log.e(Gloable.TAG, "pageBean == null");
			return;
		}
		if(scroolMode == ACTION_TYPE_TO_RIGHT){
			BitmapBean curBitmap = pageBean.getCurrentPageBitmap();
			if(curBitmap != null){
				canvas.drawBitmap(curBitmap.bitmap, new Rect(0, 0, curBitmap.bitmap.getWidth(), curBitmap.bitmap.getHeight()), new Rect(0, 0, getWidth() + 50, getHeight()), null);
			}
			BitmapBean preBitmap = pageBean.getPrePageBitmap();
			if(preBitmap != null){
				canvas.drawBitmap(preBitmap.bitmap, new Rect(0, 0, preBitmap.bitmap.getWidth(), preBitmap.bitmap.getHeight()), new Rect(-(int)(getWidth() - endPoint.x), 0, (int)(endPoint.x) + 50 , getHeight()), null);
			}

			if(endPoint.x == mViewWidth){
				loadPrePageBean();
				scroolReset();
				//scroolMode = ACTION_TYPE_NOACTION;
				//	isCanToachAgain = true;
			}
			//scroolToRight();
			//scroolMode = ACTION_TYPE_NOACTION;
			//if()
		}else if(scroolMode == ACTION_TYPE_TO_LEFT){
			BitmapBean nextBitmap = pageBean.getNextPageBitmap();
			if(nextBitmap != null){
				canvas.drawBitmap(nextBitmap.bitmap, new Rect(0, 0, nextBitmap.bitmap.getWidth(), nextBitmap.bitmap.getHeight()), new Rect(0, 0, getWidth() + 50, getHeight()), null);
			}
			if(endPoint.x > 0){
				BitmapBean curBitmap = pageBean.getCurrentPageBitmap();
				if(curBitmap != null){
					canvas.drawBitmap(curBitmap.bitmap, new Rect(0, 0, curBitmap.bitmap.getWidth(), curBitmap.bitmap.getHeight()), new Rect(-(int)(getWidth() - endPoint.x), 0, (int)(endPoint.x) + 50 , getHeight()), null);
				}
			}
			if(endPoint.x == 0){
				loadNextPageBean();
				scroolReset();
			}
			//scroolToLeft();
			//scroolMode = ACTION_TYPE_NOACTION;
		}else if(scroolMode == ACTION_TYPE_NOACTION){
			//isFirst = false;
			BitmapBean curBitmap = pageBean.getCurrentPageBitmap();
			if(curBitmap != null){
				canvas.drawBitmap(curBitmap.bitmap, new Rect(0, 0, curBitmap.bitmap.getWidth(), curBitmap.bitmap.getHeight()), new Rect(0, 0, getWidth() + 50, getHeight()), null);
			}
		}
	}

	private void scroolReset(){
		scroolMode = ACTION_TYPE_NOACTION;
		isHaveDown = false;
		oneActionIsOver = true;
		isCanToachAgain = true;
	}

	/**
	 * ����ʣ�µ�
	 */
	private void scroolToLeft() {
		Log.i(Gloable.TAG, "�������Զ�����");
		isCanToachAgain = false;
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					while(true){
						Thread.sleep(5);
						if(scroolMode == ACTION_TYPE_TO_LEFT){
							if(endPoint.x <= 0){
								break;
							}
							scroolHandler.sendEmptyMessage(0);
						}else if(scroolMode == ACTION_TYPE_TO_RIGHT){
							if(endPoint.x >= mViewWidth){
								break;
							}
							scroolHandler.sendEmptyMessage(0);
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	private Handler scroolHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(scroolMode == ACTION_TYPE_TO_LEFT){
				if(endPoint.x - 10 <= 0){
					endPoint.x = 0;
				}else{
					int scrollDis = 10;
					if(endPoint.x < 40){
						scrollDis = 5;
					}
					endPoint.x = endPoint.x - scrollDis;
				}
			}else if(scroolMode == ACTION_TYPE_TO_RIGHT){
				if(endPoint.x + 10 >= mViewWidth){
					endPoint.x = mViewWidth;
				}else{
					int scrollDis = 10;
					if(endPoint.x > mViewWidth - 40){
						scrollDis = 5;
					}
					endPoint.x =  endPoint.x + scrollDis;
				}
			}
			invalidate();
		}
	};


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(!isCanToachAgain){
			return true;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isHaveDown = true;
			startDownPoint.x = event.getX();
			startDownPoint.y = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			if(!isHaveDown){		//
				return true;
			}
			endPoint.x = event.getX();
			endPoint.y = event.getY();

			//if(oneActionIsOver){
			if(oneActionIsOver && Math.abs(endPoint.x - startDownPoint.x) > 50){
				oneActionIsOver = false;
				if(endPoint.x - startDownPoint.x > 0){
					scroolMode = ACTION_TYPE_TO_RIGHT;
				}else{
					scroolMode = ACTION_TYPE_TO_LEFT;
				}
			}else if(scroolMode != ACTION_TYPE_NOACTION){
				if(scroolMode == ACTION_TYPE_TO_LEFT){
					if(pageBean.getNextPageBitmap() != null){
						invalidate();
					}else{
						scroolReset();
					}
				}else if(scroolMode == ACTION_TYPE_TO_RIGHT){
					if(pageBean.getPrePageBitmap() != null){
						invalidate();
					}else{
						scroolReset();
					}
				}
			}
			//}
			break;
		case MotionEvent.ACTION_UP:
			if(!isHaveDown){		//
				return true;
			}
			isCanToachAgain = false;
			endPoint.x = event.getX();
			endPoint.y = event.getY();
			if(Math.abs(endPoint.x - startDownPoint.x) < 50 && scroolMode == 100){
				scroolMode = ReadPageView.ACTION_TYPE_CLICK;
				scroolReset();
			}else{
				scroolToLeft();
			}
			break;
		default:
			break;
		}
		return true;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public BookBean getBookBean() {
		return bookBean;
	}

	public void setBookBean(BookBean bookBean) {
		this.bookBean = bookBean;
		//��ȡ��ǩ
		tagChapterIndex = getTagChapterIndex(bookBean);
		tagChapterShowTextIndex = getTagChapterShowTextIndex(bookBean);
		BookBitmapFactory.getInstance().setBookReadInfo(bookBean, tagChapterIndex);
	}

	private void loadNextPageBean(){
		Log.i(Gloable.TAG, "������һҳ");
		PageBean bean = BookBitmapFactory.getInstance().getNextPageBean(mViewWidth, mViewHeight);
		if(bean != null){
			pageBean = bean;
		}
	}

	private void loadPrePageBean(){
		Log.i(Gloable.TAG, "������һҳ");
		PageBean bean = BookBitmapFactory.getInstance().getPrePageBean(mViewWidth, mViewHeight);
		if(bean != null){
			pageBean = bean;
		}
	}

	private void loadCurPageBean(){
		PageBean bean = BookBitmapFactory.getInstance().getPageBeanAgain(mViewWidth, mViewHeight);
		if(bean != null){
			pageBean = bean;
		}
	}

	//��ȡС˵�½��Ķ�����ǩ
	private int getTagChapterShowTextIndex(BookBean bookBean2) {
		// TODO Auto-generated method stub
		return 0;
	}

	//��ȡ�Ķ���С˵�½���ǩ
	private int getTagChapterIndex(BookBean bookBean2) {
		// TODO Auto-generated method stub
		return 0;
	}
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
		invalidate();
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//Toast.makeText(getContext(), "onMeasure", 0).show();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(measuredWidth(widthMeasureSpec), measuredHeight(heightMeasureSpec));
		//	Log.i(Gloable.TAG, "�������Ŀؼ���ߣ�viewHeight = " + mViewHeight + ", viewWidth = " + mViewWidth);
		//loadCurPageBean();
		if(pageBean != null){
			pageBean = BookBitmapFactory.getInstance().getPageBitmap(pageBean.getCurrentPageBitmap().startIndex, mViewWidth, mViewHeight);
		}else{
			pageBean = BookBitmapFactory.getInstance().getPageBitmap(tagChapterShowTextIndex, mViewWidth, mViewHeight);
		}
		//invalidate();
	}

	private int measuredHeight(int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int sizeMode = MeasureSpec.getMode(heightMeasureSpec);
		int size = MeasureSpec.getSize(heightMeasureSpec);
		if(sizeMode == MeasureSpec.EXACTLY){
			mViewHeight = size;
		}else if(sizeMode == MeasureSpec.AT_MOST){
			mViewHeight = Math.min(size, screenHeight);
		}
		return mViewHeight;
	}

	private int measuredWidth(int widthMeasureSpec) {
		// TODO Auto-generated method stub
		int sizeMode = MeasureSpec.getMode(widthMeasureSpec);
		int size = MeasureSpec.getSize(widthMeasureSpec);
		if(sizeMode == MeasureSpec.EXACTLY){
			mViewWidth = size;
		}else if(sizeMode == MeasureSpec.AT_MOST){
			mViewWidth = Math.min(size, screenWdith);
		}
		return mViewWidth;
	}
}
