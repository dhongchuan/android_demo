package com.dhongchuan.view;

import com.example.demo.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

public class SlideView extends LinearLayout{
	private static final String TAG = "SlideView";
	private Context mContext;
	
	//用来放置所有View的容器
	private LinearLayout mViewContent;
	//用来放置内置View的容器
	private RelativeLayout mHolder;
	private Scroller mScroller;
	private OnSlideListener mOnSlideListener;
	
	private int mHolderWidth = 120;
	private int mLastX = 0;
	private int mLastY = 0;
	
	//滑动角度
	private static final int TAN = 2;
	
	public interface OnSlideListener{
		public static final int SLIDE_STATUS_OFF = 0;
		public static final int SLIDE_STATUS_START_SCROLL = 1;
		public static final int SLIDE_STATUS_ON = 2;
		
		public void OnSlide(View view, int status);
	}
	
	public SlideView(Context context){
		super(context);
		initView();
	}
	
	public SlideView(Context context, AttributeSet attrs){
		super(context, attrs);
		initView();
	}

	@SuppressLint("NewApi")
	public SlideView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
		// TODO Auto-generated constructor stub
	}
	
	private void initView(){
		mContext = getContext();
		mScroller = new Scroller(mContext);
		setOrientation(LinearLayout.HORIZONTAL);
		View.inflate(mContext, R.layout.slide_view_layout, this);
		mViewContent = (LinearLayout) findViewById(R.id.view_content);
		mHolderWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mHolderWidth, getResources().getDisplayMetrics()));
		
	}
	
	public void setButtonText(CharSequence text){
		((TextView) findViewById(R.id.delete)).setText(text);
	}
	
	public void setContentView(View view){
		mViewContent.addView(view);
	}
	
	public void setOnSlideListener(OnSlideListener onSlideListener){
		mOnSlideListener = onSlideListener;
	}
	
	public void shrink(){
		
	}
	
	public void onRequireTouchEvent(MotionEvent event){
		int x = (int) event.getX();
		int y = (int) event.getY();
		int scrollX = getScrollX();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(!mScroller.isFinished())
				mScroller.abortAnimation();
			if(mOnSlideListener != null)
				mOnSlideListener.OnSlide(this, OnSlideListener.SLIDE_STATUS_START_SCROLL);
			
			break;
			
		case MotionEvent.ACTION_MOVE:
			int deltaX = x - mLastX;
			int deltaY = y- mLastY;
			//滑动不满足条件，不做横向滑动
			if(Math.abs(deltaX) < Math.abs(deltaY) * TAN)
				break;
			
			break;

		default:
			break;
		}
	}

}
