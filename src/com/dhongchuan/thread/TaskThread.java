package com.dhongchuan.thread;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

public class TaskThread<TaskToken> extends HandlerThread{
	private static final String TAG = "TaskThread";
	private static final int TASK_TYPE = 1;
	//当前线程的Handler，处理消息队列中的任务
	Handler mHandler;
	//源线程的Handler，任务处理完成后调用
	Handler mResponseHandler;
	//任务处理完成后调用，由mResponseHandler所在的线程处理
	Listener<TaskToken> mListener;
	Map<TaskToken, String> requestMap = Collections.synchronizedMap(new HashMap<TaskToken, String>());
	
	public interface Listener<TaskToken>{
		void onTask(TaskToken taskToken);
	}
	
	public void setListener(Listener<TaskToken> listener){
		mListener = listener;
	}
	
	public TaskThread(Handler responseHandler){
		super(TAG);
		mResponseHandler = responseHandler;
	}
	
	@SuppressLint("HandlerLeak") @Override
	protected void onLooperPrepared(){
		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg){
				if(msg.what == TASK_TYPE){
					@SuppressWarnings("unchecked")
					TaskToken taskToken = (TaskToken) msg.obj;
					handleRequest(taskToken);
				}
			}
		};
	}
	
	//处理Task，完成后回调处理
	private void handleRequest(final TaskToken taskToken){
		try {
			final String url = requestMap.get(taskToken);
			if(url == null)
				return;
			
			mResponseHandler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(requestMap.get(taskToken) != url)
						return;
					requestMap.remove(taskToken);
					mListener.onTask(taskToken);
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void queueTask(TaskToken taskToken, String url){
		requestMap.put(taskToken, url);
		mHandler.obtainMessage(TASK_TYPE, taskToken).sendToTarget();
	}
	
	//清理方法
	public void clearQueue(){
		mHandler.removeMessages(TASK_TYPE);
		requestMap.clear();
	}
}
