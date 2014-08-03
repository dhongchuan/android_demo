package com.dhongchuan.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 滑动开关组件
 * 三种状态：打开、关闭、正在滑动
 * @author dhongchuan
 *
 */
public class SlideSwitch extends View{
	public static final String TAG = "SlideSwitch";
	public static final int SWITCH_OFF = 0;
	public static final int SWITCH_ON = 1;
	public static final int SWITCH_SCROLING = 2;
	
	//显示文本
	private String mOnText = "打开";
	private String mOffText = "关闭";
	
	private int mSwitchStatus = SWITCH_OFF;
	private Boolean mHasScrolled = false;//表示是否发生过滚动
	
	private int mSrcX = 0;
	private int mDstX = 0;
	
	private int mBmpWidth = 0;
	private int mBmpHeight = 0;
	private int mThumbWidth = 0;
	
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//	private OnSwitchChangedListener mOnSwitchChangedListener = null;
	
	//开关状态图
	Bitmap mSwitch_off;
	Bitmap mSwitch_on;
	Bitmap mSwitch_thumb;

	public SlideSwitch(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public SlideSwitch(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}
	
	public SlideSwitch(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		init();
	}
	
	//初始化三幅图
	private void init(){
		Resources res = getResources();
//		mSwitch_off = BitmapFactory.decodeResource(res, id);
	}
}
